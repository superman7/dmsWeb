package com.aerors.dms.utils;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.utils
 * @描述: 图片处理工具类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/5 15:50
 */
public class ImageUtils {
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 获取图片缩略图,使用原图的宽高比
     * 如果缩略图尺寸大于原图尺寸,使用原图尺寸
     *
     * @param {InputStream} imgFile 原图片文件流
     * @param {int}         w      缩略图宽度
     * @param {int}         h      缩略图高度
     * @return {BufferedImage} 生成图片
     */
    public static BufferedImage thumbnailImage(InputStream imgFile, int w, int h) {
        BufferedImage bi = null;
        //如果图片存在
        try {
            log.debug("缩略图尺寸, width:{}, height:{}!", w, h);
            BufferedImage img = ImageIO.read(imgFile);

            int width = img.getWidth(null);
            int height = img.getHeight(null);
            int out_w = w, out_h = h;
            if (w <= 0 || h <= 0) {
                out_w = width;
                out_h = height;
            } else {
                //宽高比
                double aspec = 1.0 * width / height;
                //根据宽高比计算缩略图实际的尺寸
                if (aspec > 1) { //如果图片比较宽时
                    if (w > width) {
                        out_w = width;
                    }
                    out_h = (int) (out_w / aspec);
                } else {//如果图片比较高时
                    if (h > height) {
                        out_h = height;
                    }
                    out_w = (int) (out_h * aspec);
                }
            }

            bi = new BufferedImage(out_w, out_h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bi.createGraphics();
            g.drawImage(img, 0, 0, out_w, out_h, null);
            g.dispose();
        } catch (IOException e) {
            log.error("生成缩略图失败!");
            e.printStackTrace();
        }
        return bi;
    }
}
