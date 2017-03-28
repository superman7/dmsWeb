package com.aerors.dms.utils;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.model.JarParseModel;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.utils
 * @描述: jar 操作类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/28 10:03
 */
public class JarUtil {
    public static final String METHOD_NAME = "parseToCommon";
    public static final String TYPE = ".jar";
    private static Class<?> FIRST_PARAM_CLASS = InputStream.class;

    public static Map<Pattern, JarParseModel> jarMap = new HashMap<Pattern, JarParseModel>();


    public static JSONObject checkJar(File jarFile) {
        JSONObject result = new JSONObject();
        String error = null;
        JarFile jf = null;
        Class<?> clz = null;
        URLClassLoader classLoader = null;
        Method[] methods = null;
        Class<?>[] parameterTypes = null;
        try {
            if (jarFile == null) {
                throw new IllegalArgumentException("传入参数不能为空");
            }
            if (jarFile.length() < 1) {
                throw new IllegalArgumentException("jar文件不能为空");
            }
            URL url = jarFile.toURI().toURL();
            classLoader = new URLClassLoader(new URL[]{url});//创建类加载器
            jf = new JarFile(jarFile);
            Enumeration<JarEntry> enumeration = jf.entries();
            int type = 0;
            String extName = ".CLASS";
            while (enumeration.hasMoreElements()) {
                JarEntry element = enumeration.nextElement();
                String name = element.getName();//该名称为com/xx/xx.class
                if (name != null) {
                    String upperCaseName = name.toUpperCase();
                    if (upperCaseName.endsWith(extName)) {
                        String className = name.substring(0, upperCaseName.indexOf(extName));
                        if (className != null) {
                            //改变成classLoader需要的类名称表达
                            className = className.replaceAll("/", ".");
                            clz = classLoader.loadClass(className);//加载指定类，注意一定要带上类的包名
                            //如果是接口的话,就不管了
                            if (Modifier.isInterface(clz.getModifiers())) {
                                continue;
                            }
                            type = 1;
                            methods = clz.getDeclaredMethods();
                            for (Method method : methods) {
                                if (method.getName().equals(METHOD_NAME)) {
                                    type = 2;
                                    parameterTypes = method.getParameterTypes();
                                    if (parameterTypes.length == 1 && parameterTypes[0].equals(FIRST_PARAM_CLASS)) {
                                        type = 3;
                                        result.put("className", className);
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }
                if (type == 3) {
                    break;
                }
            }
            switch (type) {
                case 0:
                    error = "jar中没有类文件";
                    break;
                case 1:
                    error = "jar中有类文件,但没有解析方法";
                    break;
                case 2:
                    error = "jar有解析方法,但方法参数不正确";
                    break;
            }
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } catch (IOException e) {
            error = "jar文件不合法!";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (jf != null) {
                try {
                    jf.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    jf = null;
                }
            }
            parameterTypes = null;
            methods = null;
            clz = null;
            classLoader = null;
            // 执行一次gc垃圾回收
            System.gc();
        }
        boolean isSuccess = StringUtils.isEmpty(error) ? true : false;
        result.put("success", isSuccess);
        if (!isSuccess) {
            result.put("error", error);
        }

        return result;
    }

    /**
     * 通过文件名获取解析jar
     *
     * @param {String} name 文件名
     * @return {JarParseModel} 解析jar
     */
    public static JarParseModel getJarPojo(String name) {
        JarParseModel jarpojo = null;
        if (!StringUtils.isEmpty(name)) {
            Set<Pattern> set = jarMap.keySet();
            for (Pattern pattern : set) {
                boolean isCanParse = pattern.matcher(name).matches();
                if (isCanParse) {
                    jarpojo = jarMap.get(pattern);
                    break;
                }
            }
        }
        return jarpojo;
    }

    public static String parse(InputStream jar, InputStream is,String className) throws Exception {
        File f = File.createTempFile("parseJar", JarUtil.TYPE);
        FileUtils.copyToFile(jar,f);
        URL url = f.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});//创建类加载器

        Class<?> clz = classLoader.loadClass(className);
        Method method = clz.getMethod(METHOD_NAME, FIRST_PARAM_CLASS);
        Object obj = null;
        if (!Modifier.isStatic(method.getModifiers())) {
            obj = clz.newInstance();//初始化一个实例
        }
        Object o = method.invoke(obj, is);//调用得到的上边的方法method(静态方法，第一个参数可以为null)
        String result = null;
        if (o != null) {
            result = o.toString();
        }
        o = null;
        method = null;
        clz = null;
        url = null;
        classLoader = null;
        System.gc();
        removeFile(f);
        f = null;

        return result;
    }

    /**
     * 删除文件及同目录下的jar文件
     *
     * @param f
     */
    public static void removeFile(File f) {
        File[] childFile = f.getParentFile().listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(JarUtil.TYPE);
            }
        });
        //删除过期的jar
        for (File child : childFile) {
            if (child.canWrite()) {
                child.delete();
            }
        }
        if (f.exists()) {
            f.delete();
        }
    }
}
