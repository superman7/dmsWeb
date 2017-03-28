package com.aerors.dms.service;

import com.aerors.dms.model.FieldConfig;

import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service
 * @描述: 配置中心服务类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 10:54
 */
public interface ISystemConfigServer {
    /**
     * 添加字段
     * @param {String} fcid 字段配置模型id
     * @param {Stirng} type 操纵类型 0--增加, 1--删除
     * @param {String} fieldName 字段名称
     * @return {boolean} 成功/失败
     */
    boolean editField(String fcid,int type, String fieldName);
    /**
     * 获取字段映射的总条目
     *
     * @return long 总条目数
     */
    long getCount();

    /**
     * 分页获取字段映射的集合
     * @param {int} startIndex 其实条目
     * @param {int} pageSize 每页个数
     * @return {List<JSONObject>}集合对象
     */

    List<FieldConfig> getListByPage(int startIndex, int pageSize);
    /**
     * 更改字段描述信息
     * @param {String} fcid 字段配置模型id
     * @param {String} title 描述信息
     * @return {boolean} 成功/失败
     */
    boolean editTitle(String fcid,  String title);
}
