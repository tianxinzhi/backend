package com.pccw.backend.ctrl;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.ResultRecode;
import com.pccw.backend.bean.stock_take.*;
import com.pccw.backend.entity.DbResRepo;
import com.pccw.backend.entity.DbResSku;
import com.pccw.backend.entity.DbResStockTake;
import com.pccw.backend.entity.DbResStockTakeDtl;
import com.pccw.backend.repository.ResRepoRepository;
import com.pccw.backend.repository.ResSkuRepoRepository;
import com.pccw.backend.repository.ResSkuRepository;
import com.pccw.backend.repository.ResStockTakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: ChenShuCheng
 * @create: 2020-07-30 10:54
 **/
@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("/stock_take")
public class Stock_TakeCtrl extends BaseCtrl<DbResStockTake>{

    @Autowired
    ResStockTakeRepository stockTakeRepository;

    @Autowired
    ResSkuRepoRepository skuRepoRepository;

    @Autowired
    ResRepoRepository repoRepository;

    @Autowired
    ResSkuRepository skuRepository;

    @Autowired
    HttpServletResponse response;

    private String inPath =  "excel/stock_take.xls";
    private String outPath = System.getProperty("user.dir")+"/data/stock_take.xls";

    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public JsonResult search(@RequestBody SearchBean b){

        try {
            log.info(b.toString());
            JsonResult<DbResStockTake> jsonResult = this.search(stockTakeRepository, b);
            List<SearchVO> result = jsonResult.getData().stream().map(entity -> {
                SearchVO searchVO = new SearchVO();
                BeanUtils.copyProperties(entity, searchVO);
                DbResRepo repo = repoRepository.findById(entity.getChannelId()).get();
                searchVO.setChannelCode(repo.getRepoCode());
                return searchVO;
            }).collect(Collectors.toList());
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/searchSkuQty")
    public JsonResult searchSkuQty(@Validated @RequestBody SearchQtyBean b){

        try {
            List<Map<String,Object>> currentQty = skuRepoRepository.findCurrentQty(b.getChannelId(), b.getSkuIds(),3L);
            List<Map<String, Object>> result = ResultRecode.returnHumpNameForList(currentQty);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    @RequestMapping(method = RequestMethod.POST,path = "/create")
    public JsonResult create(@RequestBody CreateBean b){

        try {
           return this.create(stockTakeRepository,DbResStockTake.class,b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/edit")
    public JsonResult edit(@RequestBody EditBean b){

        try {
            return this.edit(stockTakeRepository,DbResStockTake.class,b);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/searchSkuByRepoId")
    public JsonResult findSkuByRepoId(@RequestBody SearchQtyBean b){

        try {
            List<Map<String,Object>> currentQty = skuRepoRepository.findSkuByRepoId(b.getChannelId(),3L);
            List<Map<String, Object>> result = ResultRecode.returnHumpNameForList(currentQty);
            return JsonResult.success(result);
        } catch (Exception e) {
            return JsonResult.fail(e);
        }
    }


    @RequestMapping(method = RequestMethod.GET,path = "/printb")
    public JsonResult print(){
        try {
            DbResStockTake stockTake = stockTakeRepository.findById(6L).get();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map map = new HashMap<>();
            map.put("number",stockTake.getStockTakeNumber());
            map.put("channel",repoRepository.findById(stockTake.getChannelId()).get().getRepoCode());
            map.put("time",stockTake.getCompleteTime() == null ?"":sdf.format(stockTake.getCompleteTime()));
            String skuCode = "";
            List<Map> dtls = new LinkedList<>();
            for (DbResStockTakeDtl dtl : stockTake.getLine()) {
                Map kMap = new HashMap();
                DbResSku sku = skuRepository.findById(dtl.getSkuId()).get();
                skuCode += sku.getSkuCode()+",";
                kMap.put("sku",sku.getSkuCode()==null?"":sku.getSkuCode());
                kMap.put("one",dtl.getStockTakeOne()==null?"":dtl.getStockTakeOne());
                kMap.put("two",dtl.getStockTakeTwo()==null?"":dtl.getStockTakeTwo());
                kMap.put("three",dtl.getStockTakeThree()==null?"":dtl.getStockTakeThree());
                kMap.put("balance",dtl.getStockTakeBalance()==null?"":dtl.getStockTakeBalance());
                dtls.add(kMap);
            }
            map.put("sku",skuCode);
            map.put("list",dtls);
            System.out.println("printb:导出前查询list:=====================");
            System.out.println(map.toString());
            //得到文档的路径
            Resource resource = new ClassPathResource(inPath);
            InputStream in = resource.getInputStream();

            //列表数据将存储到指定的excel文件路径，这个路径是在项目编译之后的target目录下
            FileOutputStream out = new FileOutputStream(outPath);
            //这里的context是jxls框架上的context内容
            Context context = new Context();
            //将列表参数放入context中
            context.putVar("test", map);
            //将List<Exam>列表数据按照模板文件中的格式生成到scoreOutput.xls文件中
            JxlsHelper.getInstance().processTemplate(in, out, context);
//            JxlsHelper jxlsHelper = JxlsHelper.getInstance();
//            Transformer transformer = jxlsHelper.createTransformer(in, out);
//
//            jxlsHelper.processTemplate(context, transformer);
            //指定数据生成后的文件输入流（将上述out的路径作为文件的输入流）
            FileInputStream fileInputStream = new FileInputStream(outPath);
            //导出excel文件，设置文件名
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = URLEncoder.encode("stock_take"+sdf.format(new Date())+".xls", "UTF-8");
            //设置下载头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
//            response.setHeader("Content-Type","");
            response.setContentType("application/ms-excel;charset=UTF-8");
//            response.setContentType("application/vnd..ms-excel;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            //将文件写入浏览器
            byte[] bys = new byte[fileInputStream.available()];
            fileInputStream.read(bys);
            outputStream.write(bys);
            outputStream.flush();
            outputStream.close();
            System.out.println("printb:导出完毕！");
            return JsonResult.success(Arrays.asList());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
    }

    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public JsonResult testExport2(@RequestBody EditBean bean) throws Exception{
        DbResStockTake stockTake = stockTakeRepository.findById(bean.getId()).get();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map map = new HashMap<>();
        map.put("number",stockTake.getStockTakeNumber());
        map.put("channel",repoRepository.findById(stockTake.getChannelId()).get().getRepoCode());
        map.put("time",stockTake.getCompleteTime() == null ?"":sdf.format(stockTake.getCompleteTime()));
        String skuCode = "";
        List<Map> dtls = new LinkedList<>();
        for (DbResStockTakeDtl dtl : stockTake.getLine()) {
            Map kMap = new HashMap();
            DbResSku sku = skuRepository.findById(dtl.getSkuId()).get();
            skuCode += sku.getSkuCode()+",";
            kMap.put("sku",sku.getSkuCode()==null?"":sku.getSkuCode());
            kMap.put("one",dtl.getStockTakeOne()==null?"":dtl.getStockTakeOne());
            kMap.put("two",dtl.getStockTakeTwo()==null?"":dtl.getStockTakeTwo());
            kMap.put("three",dtl.getStockTakeThree()==null?"":dtl.getStockTakeThree());
            kMap.put("balance",dtl.getStockTakeBalance()==null?"":dtl.getStockTakeBalance());
            dtls.add(kMap);
        }
        map.put("sku",skuCode);
        map.put("list",dtls);

        System.out.println("导出前查询list:=====================");
        System.out.println(map.toString());

        InputStream in = new ClassPathResource(inPath).getInputStream();
        String filename = URLEncoder.encode("stock_take"+sdf.format(new Date())+".xls", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+filename);
        /*response.setContentType("application/ms-excel;charset=UTF-8");*/
        response.setContentType("application/vnd..ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        Context context = new Context();
        context.putVar("test",map);

//        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
//        Transformer transformer = jxlsHelper.createTransformer(in, out);
//        jxlsHelper.processTemplate(context, transformer);
        JxlsHelper.getInstance().processTemplate(in, out, context);
        System.out.println("导出完毕！");
        return JsonResult.success(Arrays.asList());
    }

}
