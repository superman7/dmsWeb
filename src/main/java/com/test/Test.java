/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/** 
* @ClassName: Test 
* @Description: (这里用一句话描述这个类的作用)
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月16日 下午3:52:42 
* 
*/
public class Test {
	
	public static void main(String[] args) throws IOException {
		 byte[] byteImg = getContent("/Users/pudgefan/Desktop/test.png");
		 System.out.print(byteImg.length);
	}
	public static byte[] getContent(String filePath) throws IOException {  
        File file = new File(filePath);  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // 确保所有数据均被读取  
        if (offset != buffer.length) {  
        	throw new IOException("Could not completely read file " + file.getName());  
        }  
        fi.close();  
        return buffer;  
    } 
}
