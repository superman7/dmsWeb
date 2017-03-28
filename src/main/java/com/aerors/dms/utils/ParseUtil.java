package com.aerors.dms.utils;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.mongodb.BasicDBObject;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.utils
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 17:49
 */
public class ParseUtil {
    /**
     * 根据类型来解析文件
     *
     * @param {String} name 文件名称
     * @param {File}   xmlFile 文件对象
     * @return {String} json 字符串对象
     */
    public static String parseNameFile(String name, File xmlFile) {
        try {
            return parseNameFile(name, new FileInputStream(xmlFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseNameFile(String name, InputStream fis) {
        return parseTypeFile(getExtName(name), fis);
    }

    public static String getExtName(String name) {
        return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
    }

    public static String parseTypeFile(String type, File xmlFile) {
        try {
            return parseTypeFile(type, new FileInputStream(xmlFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String repalcePointToX(String name) {
        return name.toLowerCase().replaceAll("\\.", "*");
    }

    public static String repalceXToPoint(String name) {
        return name.replaceAll("\\*", "\\.");
    }

    public static String parseTypeFile(String type, InputStream fis) {
        String result = null;
        if (type.toLowerCase().equals("xml")) {
            result = parseXMLFileInputStream(fis);
        }
        if (result != null) {
            result = result.toLowerCase();
        }
        return result;
    }

    private static String parseXMLFileInputStream(InputStream fis) {
        return Xml2JsonUtil.xml2JSON(fis);
    }

    public static Map<String, Object> getAllKeys(JSONObject json, Map<String, Object> result, StringBuffer parentField) {
        if (result == null) {
            result = new HashMap<String, Object>();
        }
        if (parentField == null) {
            parentField = new StringBuffer();
        }
        Set<String> keySet = json.keySet();
        for (String key : keySet) {
            String addKey = null;
            try {
                addKey = parentField.toString() + key;

                JSONObject child = json.getJSONObject(key);
                StringBuffer parentKey = new StringBuffer(addKey);
                parentKey.append(".");
                result = getAllKeys(child, result, parentKey);
            } catch (JSONException e) {
                result.put(addKey, json.get(key));
            }
        }
        return result;
    }

}
