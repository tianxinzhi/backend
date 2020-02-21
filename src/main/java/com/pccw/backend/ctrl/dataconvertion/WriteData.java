package com.pccw.backend.ctrl.dataconvertion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author: xiaozhi
 * @Date: 2020/2/20 9:43
 * @Desc:
 */
public class WriteData {
    //文件存放路径
    private static final String dataPath = System.getProperty("user.dir")+"/data/";
    private static String[] fileNames = new String[]{"lis_element_20200117.txt","lis_category_20200117.txt","lis_item_20200117.txt"};
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter(new File(dataPath+fileNames[0]));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("INVENTORY_ITEM_ID |\tITEM_CATALOG_GROUP_ID |\tELEMENT_NAME |\tELEMENT_VALUE |\tLAST_UPDATE_DATE");
            bw.newLine();
            for(int i =0 ;i<1000;i++){
                bw.append("2 | 2\t| BgColor"+i+"\t| Red"+i+"\t| 02-DEC-19 13:54:38");
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
