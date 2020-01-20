package com.pccw.backend.ctrl.datasync;

import com.pccw.backend.bean.BaseDeleteBean;
import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.bean.masterfile_sku.CreateBean;
import com.pccw.backend.ctrl.MasterFile_AttrCtrl;
import com.pccw.backend.entity.*;
import com.pccw.backend.repository.*;
import com.pccw.backend.util.RestTemplateUtils;
import com.pccw.backend.util.Session;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.Card;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: xiaozhi
 * @Date: 2020/1/9 17:26
 * @Desc:
 * 【1】备份数据
 * 【2】获取数据并保存 （内存问题）
 * 【3】读取并转换（内存问题）
 * 【4】写入数据（关系处理）
 */
@Slf4j
@RestController
@CrossOrigin(methods = RequestMethod.POST,origins = "*", allowCredentials = "false")
@RequestMapping("data_convertion")
@Api(value="DataConvertion",tags={"DataConvertion"})
public class DataConvertionCtrl {

    @Autowired
    private ResSkuRepository skuRepository;
    @Autowired
    private ResSkuLisRepository skuLisRepository;
    @Autowired
    private ResClassLisRepository classLisRepository;
    @Autowired
    private ResClassRepository classRepository;
    @Autowired
    private ResTypeRepository typeRepository;
    @Autowired
    private ResSpecRepository specRepository;
    @Autowired
    private ResAttrRepository attrRepository;
    @Autowired
    private ResAttrValueRepository attrValueRepository;

    @Autowired
    Session session;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private BufferedReader br = null;
    private FileReader fr = null;
    private FileWriter fw = null;
    private BufferedWriter bw = null;

    //存放attrValue，attr,spec,class,sku的数据结构
    Set<String> attrValueDataSet =  new HashSet<>();
    Map<String,Set> attrDataMap =  new HashMap<>();
    Map<String,Map<String,Set>> specDataMap =  new HashMap<>();
    Set<String> classDataSet =  new HashSet<>();
    //Set<String> classLisDataSet =  new HashSet<>();
    //Map<String,Map<String,Set>> typeDataMap =  new HashMap<>();
    Set<Map> skuDataSet =  new HashSet<>();
    //Set<Map> skuLisDataSet =  new HashSet<>();

    //文件存放路径
    private static final String dataPath = System.getProperty("user.dir")+"/data";
    //batchjob ip端口
    private static final String host = "http://localhost:8081/";


    @ApiOperation(value="获取lis存量数据",tags={"DataConvertion"},notes="注意问题点")
    @RequestMapping(method = RequestMethod.POST,value = "/getStockDataFromLis")
    @Transactional
    public JsonResult getStockDataFromLis() {
        try {
            long begTime = System.currentTimeMillis();
            backData();
            getBacthJobData();
            transferData();
            syncData();
            log.info("------- 插入数据总共用时:"+(System.currentTimeMillis()-begTime)/1000+"秒 -------");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(e);
        }
        return JsonResult.success(Arrays.asList());
    }


    /**
     * [1]备份hkt数据库
     *
     * item保存res_sku,res_sku_lis
     * catalog保存res_class,res_class_lis
     * element保存res_sku_attr_value,res_sku_attr_value_lis
     */
    void backData() {
        //getNewOrUpdateJSONData
    }


    /**
     * [2]调用batchjob api 获取存量（存放到data目录下）
     */
     void getBacthJobData() {
        try {
            //清空文件夹
            if(new File(dataPath).isDirectory()){
                for (File file : new File(dataPath).listFiles()) {
                    file.delete();
                }
            }
            //获取存量api
            String[] apiPaths =  new String[]{"syncElementFileToSmp","syncCategoryFileToSmp","syncItemFileToSmp"};
            for (String apiPath : apiPaths) {
                //请求api
                ResponseEntity<Resource> entity = RestTemplateUtils.post(host+apiPath, Resource.class);
                if(entity.getStatusCode() == HttpStatus.OK){
                    List<String> strings = entity.getHeaders().get("Content-Disposition");
                    String resText = strings.get(0);
                    resText = resText.substring(resText.indexOf("=")+1, resText.length());
                    //System.out.println(substring);
                    if(!new File(dataPath).exists()) new File(dataPath).mkdirs();
                    File lisFile = new File(dataPath+"/"+resText);
                    InputStreamReader isr = new InputStreamReader(entity.getBody().getInputStream());
                    br = new BufferedReader(isr);
                    fw = new FileWriter(lisFile);
                    bw = new BufferedWriter(fw);
                    String str = null;
                    while ((str=(br.readLine()))!=null){
                        bw.write(str);
                        bw.newLine();
                    }
                }
                closeReader();
            }
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     *
     * [3]解析txt,获取数据并构造成smp对应逻辑结构map，并将其存入redis备用
     */
     void transferData() {
        try {
            File f = new File(dataPath);
            if(!f.isDirectory()) return;
            Map<String, List<String[]>> stringListMap = readFileData(f.listFiles());
            geneSkuMapData(stringListMap);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 读取文件每行内容并放入data Map
     * @param dataList 分别放入item文件，category文件，element文件，分别对应sku，class，attrValue的数据
     * @throws Exception
     */
    void geneSkuMapData(Map<String, List<String[]>> dataList) throws Exception{
        List<String[]> skuList = dataList.get("sku");
        List<String[]> classList = dataList.get("class");
        List<String[]> attrList = dataList.get("attrValue");

        if(skuList == null || attrList == null || classList == null) {
            log.info("数据读取失败");
            return;
        }
        Map<String,Map<String,Set>> skuAttrMap = new HashMap<>();//sku_attr_value 的map
        for (String[] attrs : attrList) {
            if(attrList.indexOf(attrs)==0) continue;
            long attr_skuId = Long.parseLong(attrs[0].trim());
            long attr_classId = Long.parseLong(attrs[1].trim());
            String attrName = attrs[2].trim();
            String attrValue = attrs[3].trim();

            attrValueDataSet.add(attrValue);
            //判断是否为同一attr的attrValue
            boolean findAttr = false;
            for (Map.Entry<String, Set> attrEntry : attrDataMap.entrySet()) {
                //找到attr下新的attrvalue
                if(attrEntry.getKey().equals(attrName)){
                    findAttr = true;
                    attrEntry.getValue().add(attrValue);
                    break;
                }
            }
            //找到新的attr
            if(findAttr == false){
                Set vaSet = new HashSet();
                vaSet.add(attrValue);
                attrDataMap.put(attrName,vaSet);
            }

            boolean findSpec = false;
            for (String[] specs : classList) {
                if(classList.indexOf(specs)==0) continue;
                String specDesc = specs[1].trim();

                if(attr_classId == Long.parseLong(specs[0].trim())){
                    findSpec = true;
                    boolean findMapSpec = false;
                    //查找spec对应的attr和attrValue
                    for (Map.Entry<String, Map<String, Set>> specMap : specDataMap.entrySet()) {
                        //找到spec的key时
                        if(specMap.getKey().equals(specDesc)){
                            findMapSpec = true;
                            boolean findSpecAttr = false;
                            for (Map.Entry<String, Set> attrMap : specMap.getValue().entrySet()) {
                                //找到spec下的attr key时
                                if(attrMap.getKey().equals(attrName)){
                                    findSpecAttr = true;
                                    attrMap.getValue().add(attrValue);
                                    break;
                                }
                            }
                            //未找到，说明是spec下新的attr
                            if(findSpecAttr == false){
                                Set attrValueSet = new HashSet();
                                attrValueSet.add(attrValue);
                                specMap.getValue().put(attrName,attrValueSet);
                            }
                            break;
                        }
                    }
                    //未找到，说明是新的spec
                    if(findMapSpec == false){
                        Map<String, Set> attrMap = new HashMap<>();
                        Set vaSet = new HashSet();
                        vaSet.add(attrValue);
                        attrMap.put(attrName,vaSet);
                        specDataMap.put(specDesc,attrMap);
                    }
                    classDataSet.add(specDesc);
                    boolean findSku = false;
                    for (String[] skus : skuList) {
                        if(skuList.indexOf(skus)==0) continue;
                        String skuName = skus[1].trim();
                        if(attr_skuId == Long.parseLong(skus[2].trim())) {
                            Map skuMap = new HashMap();//单个sku的map
                            findSku = true;

                            boolean findSku_Attr = false;
                            for (Map.Entry<String, Map<String,Set>> sku_AttrMap : skuAttrMap.entrySet()) {
                                //为同一个sku下的attr时
                                if(sku_AttrMap.getKey().equals(skuName)){
                                    findSku_Attr = true;
                                    boolean findSku_Attr_Value = false;
                                    for (Map.Entry<String, Set> attrMap : sku_AttrMap.getValue().entrySet()) {
                                        //sku已经加入的attr
                                        if(attrMap.getKey().equals(attrName)){
                                            findSku_Attr_Value = true;
                                            attrMap.getValue().add(attrValue);
                                            break;
                                        }
                                    }
                                    //sku下新的attr
                                    if(findSku_Attr_Value == false){
                                        Set attrValueSet = new HashSet();
                                        attrValueSet.add(attrValue);
                                        sku_AttrMap.getValue().put(attrName,attrValueSet);
                                    }
                                    break;
                                }
                            }
                            //新的sku ->attr
                            if(findSku_Attr == false){
                                skuMap = new HashMap();

                                Map<String, Set> map = new HashMap<>();
                                Set vaSet = new HashSet();
                                vaSet.add(attrValue);
                                map.put(attrName,vaSet);
                                skuAttrMap.put(skuName,map);
                            }
                            //sku
                            skuMap.put("skuName",skuName);
                            skuMap.put("skuDesc",skus[3].trim());
                            skuMap.put("skuType",specDesc);
                            skuMap.put("skuAttrValue",skuAttrMap.get(skuName));//skuAttrValue
                            skuMap.put("repoId",Long.parseLong(skus[0].trim()));
                            skuDataSet.add(skuMap);
                            break;
                        }
                    }

                    if(findSku == false){
                        log.info("------------ The INVENTORY_ITEM_ID " + attr_skuId +" 在lis_item文件中未找到！！！");
                    }
                    break;
                }

            }
            if(findSpec == false){
                log.info("------------ The ITEM_CATALOG_GROUP_ID "+attr_classId+" 在lis_catagory文件中未找到！！！");
            }

        }

        //去重
        skuDataSet = skuDataSet.stream().filter(distinctByKey(sku -> sku.get("skuName"))).collect(Collectors.toSet());
        session.set("attrValue",attrValueDataSet);
        session.set("attr",attrDataMap);
        session.set("spec",specDataMap);
        session.set("class",classDataSet);
        //session.set("classLis",classLisDataSet);
        //session.set("type",typeDataMap);
        session.set("sku",skuDataSet);
        //session.set("skuLis",skuLisDataSet);
    }



    /**
     * 根据key去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 读取data下面的文件
     * @param files
     * @return
     * @throws Exception
     */
    Map<String,List<String[]>> readFileData(File[] files) throws Exception{
        Map<String,List<String[]>> dataList = new HashMap();
        for (File file : files) {
            if ( file.getName().contains("lis_item") ) {
                dataList.put("sku",readWord(file));
            } else if ( file.getName().contains("lis_category") ) {
                dataList.put("class",readWord(file));
            } else if ( file.getName().contains("lis_element") ) {
                dataList.put("attrValue",readWord(file));
            }
        }
        return dataList;
    }

    /**
     * 读取单个文件内容
     * @param file
     * @return
     * @throws Exception
     */
    List<String[]> readWord(File file) throws Exception{
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        //读取文件每个字段
        String str = null;
        List<String[]> objectList= new LinkedList<>();
        while ((str=(br.readLine()))!=null){
            objectList.add(str.split("\\|"));
        }
        return objectList;
    }


    /**
     * [4]从redis获取存入的结构map，更新数据到表
     */
    void syncData() {
        try {
            List<String> attrValueData = (List) session.get("attrValue");
            Map<String, Set> attrData = (Map) session.get("attr");
            Map<String, Map<String, Set>> specData = (Map) session.get("spec");
            List<String> classData = (List)session.get("class");
            //classLisDataSet = (Set<String>)session.get("classLis");
            //typeDataMap = (Map<String, Map>) session.get("type");
            List<Map> skuData = (List) session.get("sku");
            //skuLisDataSet = (Set<Map>) session.get("skuLis");

            List<DbResAttrValue> attrValueSet = new LinkedList<>();
            List<DbResAttr> attrSet = new LinkedList<>();
            List<DbResSpec> specSet = new LinkedList<>();
            List<DbResClass> classSet = new LinkedList<>();
            List<DbResType> typeSet = new LinkedList<>();
            List<DbResSku> skuSet = new LinkedList<>();

            List<DbResSkuLis> skuLisSet = new LinkedList<>();
            List<DbResClassLis> classLisSet = new LinkedList<>();
            //Set<DbResSkuAttrValueLis> skuAttrValueLisSet = new LinkedHashSet<>();
            long time = System.currentTimeMillis();

            for (String s : attrValueData) {
                DbResAttrValue value = new DbResAttrValue();
                value.setAttrValue(s);
                value.setCreateAt(time);
                value.setUpdateAt(time);
                value.setActive("Y");
                attrValueSet.add(value);
            }
            attrValueRepository.saveAll(attrValueSet);

            //attr
            for (Map.Entry<String, Set> attrName : attrDataMap.entrySet()) {
                DbResAttr value = new DbResAttr();
                value.setAttrName(attrName.getKey());
                value.setAttrDesc(attrName.getKey());
                value.setCreateAt(time);
                value.setUpdateAt(time);
                value.setActive("Y");

                List<DbResAttrAttrValue> attrAttrValueList = new LinkedList<>();
                //中间表
                for (Object o : attrName.getValue()) {
                    DbResAttrValue attrValue = attrValueRepository.getDbResAttrValuesByAttrValue(o.toString()).get(0);

                    DbResAttrAttrValue attrAttrValue = new DbResAttrAttrValue();
                    attrAttrValue.setAttr(value);
                    attrAttrValue.setAttrValue(attrValue);
                    attrAttrValue.setCreateAt(time);
                    attrAttrValue.setUpdateAt(time);
                    attrAttrValue.setActive("Y");
                    attrAttrValueList.add(attrAttrValue);
                }
                value.setAttrAttrValueList(attrAttrValueList);
                attrSet.add(value);
            }
            attrRepository.saveAll(attrSet);
            //spec
            for (Map.Entry<String, Map<String,Set>> specEntry : specDataMap.entrySet()) {
                DbResSpec value = new DbResSpec();
                value.setSpecName(specEntry.getKey());
                value.setSpecDesc(specEntry.getKey());
                value.setCreateAt(time);
                value.setUpdateAt(time);
                value.setActive("Y");
                Map<String,Set> attrMap = specEntry.getValue();

                List<DbResSpecAttr> specAttrList = new LinkedList<>();
                for (Map.Entry<String, Set> attrEntry : attrMap.entrySet()) {
                    DbResAttr attr = attrRepository.getDbResAttrsByAttrName(attrEntry.getKey()).get(0);
                    for (Object o : attrEntry.getValue()) {
                        DbResAttrValue value1 = attrValueRepository.getDbResAttrValuesByAttrValue(o.toString()).get(0);
                        DbResSpecAttr specAttr = new DbResSpecAttr();
                        specAttr.setAttrId(attr.getId()+"");
                        specAttr.setAttrValueId(value1.getId()+"");
                        specAttr.setCreateAt(time);
                        specAttr.setUpdateAt(time);
                        specAttr.setActive("Y");
                        specAttrList.add(specAttr);
                    }
                }
                value.setResSpecAttrList(specAttrList);
                specSet.add(value);
            }
            specRepository.saveAll(specSet);
            //class,classLis,type
            for (String classDatum : classData) {
                DbResClass value = new DbResClass();
                value.setClassName(classDatum);
                value.setClassDesc(classDatum);
                value.setCreateAt(time);
                value.setUpdateAt(time);
                value.setActive("Y");
                classSet.add(value);
                //classLis
                DbResClassLis classLis = new DbResClassLis();
                classLis.setClassId(value.getId());
                classLis.setClassDesc(classDatum);
                classLis.setCreateAt(time);
                classLis.setUpdateAt(time);
                classLis.setActive("Y");
                classLisSet.add(classLis);

                //type
                DbResType type = new DbResType();
                type.setTypeCode(classDatum);
                type.setTypeName(classDatum);
                type.setTypeDesc(classDatum);
                type.setCreateAt(time);
                type.setUpdateAt(time);
                type.setActive("Y");

                DbResTypeSkuSpec value2 = new DbResTypeSkuSpec();
                value2.setType(type);
                value2.setSpecId(specRepository.getDbResSpecsBySpecName(classDatum).get(0).getId());
                value2.setCreateAt(time);
                value2.setUpdateAt(time);
                value2.setActive("Y");

                List<DbResClassType> classTypeList = new LinkedList<>();
                DbResClassType value3 = new DbResClassType();
                value3.setType(type);
                value3.setClasss(value);
                value3.setCreateAt(time);
                value3.setUpdateAt(time);
                value3.setActive("Y");
                classTypeList.add(value3);

                type.setDbResTypeSkuSpec(value2);
                type.setRelationOfTypeClass(classTypeList);

                typeSet.add(type);
            }
            classRepository.saveAll(classSet);
            classLisRepository.saveAll(classLisSet);
            typeRepository.saveAll(typeSet);

            //sku,skuLis
            for (Map skuDatum : skuData) {
                DbResSku sku = new DbResSku();
                String skuName = skuDatum.get("skuName").toString();
                sku.setSkuName(skuName);
                sku.setSkuCode(skuName);
                sku.setSkuDesc(skuDatum.get("skuDesc").toString());
                sku.setSkuOrigin(StaticVariable.SKU_ORIGIN_FROM_LIS);
                sku.setCreateAt(time);
                sku.setUpdateAt(time);
                sku.setActive("Y");
                //skuLis
                DbResSkuLis skuLis = new DbResSkuLis();
                BeanUtils.copyProperties(sku,skuLis);
                skuLis.setSkuId(sku.getId());
                List<DbResClassLis> skuType1 = classLisRepository.getDbResClassLissByClassDesc(skuDatum.get("skuType").toString());
                skuLis.setClassLisId(skuType1 == null ? null:skuType1.get(0).getId());
                skuLis.setRepoId(Long.parseLong(skuDatum.get("repoId").toString()));
                //skuType
                List<DbResSkuType> skuTypes = new LinkedList<>();
                DbResSkuType skuType = new DbResSkuType();
                skuType.setSku(sku);
                skuType.setTypeId(typeRepository.getDbResTypesByTypeCode(skuDatum.get("skuType").toString()).get(0).getId());
                skuType.setCreateAt(time);
                skuType.setUpdateAt(time);
                skuType.setActive("Y");
                skuTypes.add(skuType);
                List<DbResSkuAttrValue> skuAttrValueList = new LinkedList<>();
                List<DbResSkuAttrValueLis> skuAttrValueLisList = new LinkedList<>();
                Map<String,List> attrMap =  (Map)skuDatum.get("skuAttrValue");
                for (Map.Entry<String, List> attrEntry : attrMap.entrySet()) {
                    DbResAttr attr = attrRepository.getDbResAttrsByAttrName(attrEntry.getKey()).get(0);
                    for (Object o : attrEntry.getValue()) {
                        DbResAttrValue attrValue = attrValueRepository.getDbResAttrValuesByAttrValue(o.toString()).get(0);
                        DbResSkuAttrValue skuAttrValue = new DbResSkuAttrValue();
                        skuAttrValue.setSku(sku);
                        skuAttrValue.setAttrId(attr.getId());
                        skuAttrValue.setAttrValueId(attrValue.getId());
                        skuAttrValue.setCreateAt(time);
                        skuAttrValue.setUpdateAt(time);
                        skuAttrValue.setActive("Y");
//                        skuAttrValue.setCreateAt(time);
//                        skuAttrValue.setUpdateAt(time);
//                        skuAttrValue.setActive("Y");
                        skuAttrValueList.add(skuAttrValue);

                        //
                        DbResSkuAttrValueLis skuAttrValueLis = new DbResSkuAttrValueLis();
                        BeanUtils.copyProperties(skuAttrValue,skuAttrValueLis);
                        skuAttrValueLis.setSkuAttrValueId(skuAttrValue.getId());
                        skuAttrValueLis.setAttrName(attrEntry.getKey());
                        skuAttrValueLis.setAttrValue(o.toString());
                        skuAttrValueLisList.add(skuAttrValueLis);
                    }
                }
                sku.setSkuTypeList(skuTypes);
                sku.setSkuAttrValueList(skuAttrValueList);
                skuSet.add(sku);
                skuLis.setSkuAttrValueLisList(skuAttrValueLisList);
                skuLisSet.add(skuLis);
            }
//            for (DbResSku sku : skuSet) {
//                System.out.println("skuName:"+sku.getSkuName());
//            }
            log.info("插入sku数量为："+skuSet.size());
            skuRepository.saveAll(skuSet);
            skuLisRepository.saveAll(skuLisSet);
            //attrValueRepository.saveAll(attrValueSet);
            //attrRepository.saveAll(attrSet);
//            specRepository.saveAll(specSet);
//            classRepository.saveAll(classSet);
//            classLisRepository.saveAll(classLisSet);
//            typeRepository.saveAll(typeSet);
//            skuRepository.saveAll(skuSet);
//            skuLisRepository.saveAll(skuLisSet);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭IO缓冲
     */
     void closeReader() {
        try {
            if(br!=null){
                br.close();
            }
            if(bw!=null){
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }

}
