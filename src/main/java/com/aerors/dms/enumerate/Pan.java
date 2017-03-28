package com.aerors.dms.enumerate;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import java.io.File;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.enumerate
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/3 10:25
 */
public class Pan {
    //表示未发送错误
    public static final int NOERROR = 0;
    //表示有错误产生
    public static final int HASERROR = 0;


    //表示是文件夹
    public static final  int ISDIR = 1;
    //表示是文件
    public static final  int ISFILE = 0;

    //表示是非图片
    public static final  int NOTIMAGE = 0;
    //表示是图片
    public static final  int ISIMAGE = 1;

}
