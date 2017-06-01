/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dataAttr.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import net.sf.json.JSONObject;

/** 
* @ClassName: DataAttrModel 
* @Description: 数据存储核心dataAttr实体类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月16日 下午2:44:19 
* 
*/
public class DataAttrModel implements Serializable {
	
	private static final long serialVersionUID = -3648286822910375760L;

	@Id
	private String dataAttrId;
	
	/** 卫星 **/
	private String satellite;

	/** 载荷 **/
	private String sensor;

	/** 产品级别 **/
	private String productLevel;

	/** 产品类别 **/
	private String productType;

	/** 产品生产时间 **/
	private String productTime;

	/** 产品归档时间 **/
	private String archiveTime;
	
	/** 轨道号 **/
	private String orbitNum;
	
	/** 轨道类别 **/
	private String orbitType;
	
	/** 产品质量 **/
	private String quality;

	/** 位置信息 **/
	private JSONObject location;
	
	/** 拇指图 **/
	private byte[] thumbImg;

	/** 浏览图 **/
	private byte[] browseImg;

	/** 实体文件ID **/
	private String wholeFileId;
	
	/** 附加信息 **/
	private JSONObject otherInfo;
	
	/** dataType **/
	private String dataType;
	
	/** 原始数据 **/
	private String rawData;
	
	/** 景序列号 **/
	private String sceneNumber;
	
	/** 景ID **/
	private String scene;
	
	/** 长条带ID **/
	private String longStripData;
	
	/** 产品ID **/
	private String productId;
	
	/** 拍摄时间 **/
	private String shootingTime;

//	public DataAttrModel(String satellite, String sensor, String productLevel, String productType, 
//			String productTime, String archiveTime, String orbitNum, String orbitType, 
//			String quality, JSONObject location, byte[] thumbImg, byte[] browseImg, 
//			String wholeFileId, JSONObject otherInfo, String dataType, String rawData, 
//			String sceneNumber, String scene, String longStripData, String productId, String shootingTime){
//		
//		this.satellite = satellite;
//		this.sensor = sensor;
//		this.productLevel = productLevel;
//		this.productType = productType;
//		this.productTime = productTime;
//		this.archiveTime = archiveTime;
//		this.orbitNum = orbitNum;
//		this.orbitType = orbitType;
//		this.quality = quality;
//		this.location = location;
//		this.thumbImg = thumbImg;
//		this.browseImg = browseImg;
//		this.wholeFileId = wholeFileId;
//		this.otherInfo = otherInfo;
//		this.dataType = dataType;
//		this.rawData = rawData;
//		this.sceneNumber = sceneNumber;
//		this.scene = scene;
//		this.longStripData = longStripData;
//		this.productId = productId;
//		this.shootingTime = shootingTime;
//	}
	public String getDataAttrId() {
		return dataAttrId;
	}

	public void setDataAttrId(String dataAttrId) {
		this.dataAttrId = dataAttrId;
	}

	public String getSatellite() {
		return satellite;
	}

	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getProductLevel() {
		return productLevel;
	}

	public void setProductLevel(String productLevel) {
		this.productLevel = productLevel;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTime() {
		return productTime;
	}

	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}

	public String getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(String archiveTime) {
		this.archiveTime = archiveTime;
	}

	public String getOrbitNum() {
		return orbitNum;
	}

	public void setOrbitNum(String orbitNum) {
		this.orbitNum = orbitNum;
	}

	public String getOrbitType() {
		return orbitType;
	}

	public void setOrbitType(String orbitType) {
		this.orbitType = orbitType;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public JSONObject getLocation() {
		return location;
	}

	public void setLocation(JSONObject location) {
		this.location = location;
	}

	public byte[] getThumbImg() {
		return thumbImg;
	}

	public void setThumbImg(byte[] thumbImg) {
		this.thumbImg = thumbImg;
	}

	public byte[] getBrowseImg() {
		return browseImg;
	}

	public void setBrowseImg(byte[] browseImg) {
		this.browseImg = browseImg;
	}

	public String getWholeFileId() {
		return wholeFileId;
	}

	public void setWholeFileId(String wholeFileId) {
		this.wholeFileId = wholeFileId;
	}

	public JSONObject getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(JSONObject otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getSceneNumber() {
		return sceneNumber;
	}

	public void setSceneNumber(String sceneNumber) {
		this.sceneNumber = sceneNumber;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getLongStripData() {
		return longStripData;
	}

	public void setLongStripData(String longStripData) {
		this.longStripData = longStripData;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getShootingTime() {
		return shootingTime;
	}

	public void setShootingTime(String shootingTime) {
		this.shootingTime = shootingTime;
	}
	
}
