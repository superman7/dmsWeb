package com.test;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.ui.ModelMap;

import javax.swing.*;
import java.util.*;

/**
 * @工程: gisplatform
 * @包名: com.test
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/6/21 13:22
 */
public class MemCachedTest {
    /* 单例模式 */
   /* 单例模式 */
    protected static MemCachedClient mcc = new MemCachedClient();

    private MemCachedTest() {
    }

    /* 配置服务器组 */
    static {

        /* 定义IP地址和端口 */
        String[] servers = {"127.0.0.1:11211"};

        /* 设置缓存大小 */
        Integer[] weights = {2};

        /* 拿到一个连接池的实例 */
        SockIOPool pool = SockIOPool.getInstance();

        /* 注入服务器组信息 */
        pool.setServers(servers);
        pool.setWeights(weights);

        /* 配置缓冲池的一些基础信息 */
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        /* 设置线程休眠时间 */
        pool.setMaintSleep(30);

        /* 设置关于TCP连接 */
        pool.setNagle(false);// 禁用nagle算法
        pool.setSocketConnectTO(0);
        pool.setSocketTO(3000);// 3秒超时

        /* 初始化 */
        pool.initialize();


    }

    public static boolean set(String arg0, Object arg1) {

        return mcc.set(arg0, arg1);
    }

    public static Object get(String arg0) {
        return mcc.get(arg0);
    }

    /* 测试 */
    public static void main(String[] args) {
        int max = 500000;
        for (int i = 0; i < max; i++) {
            MemCachedTest.set(i + "", "张三"+i);
        }
        long start = System.currentTimeMillis();
        int count =0;
        for (int i = 0; i < max; i++) {
            if(MemCachedTest.get(i + "").toString().indexOf("张三")>-1){
                count++;
            };
        }
//        List<String> list = new ArrayList<String>();
//        Map<String, Map<String, String>> items = mcc.statsItems();
//        for (Iterator<String> itemIt = items.keySet().iterator(); itemIt.hasNext();) {
//            String itemKey = itemIt.next();
//            Map<String, String> maps = items.get(itemKey);
//            for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt.hasNext();) {
//                String mapsKey = mapsIt.next();
//                String mapsValue = maps.get(mapsKey);
//                if (mapsKey.endsWith("number")) {  //memcached key 类型  item_str:integer:number_str
//                    String[] arr = mapsKey.split(":");
//                    int slabNumber = Integer.valueOf(arr[1].trim());
//                    int limit = Integer.valueOf(mapsValue.trim());
//                    Map<String, Map<String, String>> dumpMaps = mcc.statsCacheDump(slabNumber, limit);
//                    for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt.hasNext();) {
//                        String dumpKey = dumpIt.next();
//                        Map<String, String> allMap = dumpMaps.get(dumpKey);
//                        for (Iterator<String> allIt = allMap.keySet().iterator(); allIt.hasNext();) {
//                            String allKey = allIt.next();
//                            ModelMap mm = (ModelMap) mcc.get(allKey);
//                            if(mm.get("name").toString().indexOf("张三")>-1){
//                                count++;
//                            };
//
//                            list.add(allKey.trim());
//                        }
//                    }
//                }
//            }
//        }
        System.out.println(count);
        long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);
    }
}
