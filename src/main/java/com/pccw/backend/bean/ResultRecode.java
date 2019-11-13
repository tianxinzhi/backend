package com.pccw.backend.bean;

import com.pccw.backend.util.Convertor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ResultRecode {
    private List<Map> data;

    /**返回一个map的key为小写的list
     *
     * @param data
     * @return
     */
    public static List<Map> returnResult(List<Map> data){
        List<Map> resultList = new ArrayList<Map>();
        for (Map orgMap:data){
            Map resultMap = Convertor.transformLowerCase(orgMap);
            resultList.add(resultMap);
        }
        return resultList;
    }
}
