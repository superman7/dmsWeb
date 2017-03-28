package com.aerors.dms.model;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.model
 * @描述: 存取文件的信息
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/17 14:14
 */
public class WholeFileMeta implements Serializable {
    @Id
    private String id;
    private String md5;
    private int dir;

    private String relname;

    private String name;
    private long size;
    private String type;
    private Long mtime;
    private int isComplete;
    private LinkedList<String> blocks;
    @DBRef(lazy = true)
    private WholeFileMeta parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(LinkedList<String> blocks) {
        this.blocks = blocks;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
        if (this.dir == 1) {
            this.setIsComplete(1);
        }
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }


    public WholeFileMeta getParent() {
        return parent;
    }

    public void setParent(WholeFileMeta parent) {
        this.parent = parent;
    }

    public void setParent(String parentId) {
        if (this.parent == null) {
            this.parent = new WholeFileMeta();
        }
        parent.setParent(parentId);
    }

    public String getToRootIds() {
        return this.getToRootPath(this, null, "id");
    }

    public String getToRootPath() {
        return this.getToRootPath(this, null, "name");
    }

    private String getToRootPath(WholeFileMeta wfm, StringBuffer pathStr, String type) {
        if (pathStr == null) {
            pathStr = new StringBuffer();
        }
        if (wfm != null) {
            WholeFileMeta parentWFM = wfm.getParent();
            if (parent != null) {
                this.getToRootPath(parentWFM, pathStr, type);
            }
            pathStr.append("/");
            if (type.equals("name")) {
                pathStr.append(wfm.getName());
            } else if (type.equals("id")) {
                pathStr.append(wfm.getId());
            }
        }
        if ((pathStr.length() > 0) && pathStr.charAt(0) == '/') {
            pathStr.deleteCharAt(0);
        }
        return pathStr.toString();
    }


}
