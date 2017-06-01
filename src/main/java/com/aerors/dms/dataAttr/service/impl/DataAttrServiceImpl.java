/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dataAttr.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerors.dms.dataAttr.dao.IDataAttrDao;
import com.aerors.dms.dataAttr.model.DataAttrModel;
import com.aerors.dms.dataAttr.service.IDataAttrService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: DataAttrServiceImpl 
* @Description: DataAttrModel的Service层实现类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月18日 上午11:14:42 
* 
*/
@Service
public class DataAttrServiceImpl implements IDataAttrService{
	@Autowired
    private IDataAttrDao dataAttrDaoImpl;
	
	public void save(DataAttrModel t) {
		dataAttrDaoImpl.save(t);
	}

	public void remove(DataAttrModel t) {
		dataAttrDaoImpl.del(t);
	}

	public Long count() {
		return dataAttrDaoImpl.queryCount(null);
	}

	public List<DataAttrModel> findAllWithPage(int startIndex, int pageSize) {
		return dataAttrDaoImpl.queryPageList(startIndex, pageSize);
	}

	public DataAttrModel queryById(Object id) {
		return dataAttrDaoImpl.queryById(id);
	}

	public void removeById(Object id) {
		dataAttrDaoImpl.delById(id);
	}

	public List<DataAttrModel> list() {
		return dataAttrDaoImpl.queryList(null);
	}

	/**
	 * 根据参数拼装DataAttrModel实体类
	 */
	public DataAttrModel stringToDataAttrModel(String param) throws Exception{
		DataAttrModel dam = new DataAttrModel();
		Class<?> clazz = dam.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for(Field field : fields){
			fieldMap.put(field.getName(), field);
		}
		JSONObject otherInfo = new JSONObject();
		JSONObject dataAttrObject = JSONObject.fromObject(param);
		Iterator iterator = dataAttrObject.keys();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			if(fieldMap.containsKey(key)){
				String method = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method fieldSetMethod = clazz.getMethod(method, fieldMap.get(key).getType());
				fieldSetMethod.invoke(dam, dataAttrObject.get(key));
			}else{
				otherInfo.put(key, dataAttrObject.get(key));
			}
		}
		//处理位置信息
		List<JSONObject> geoJsonAndOtherInfo = productGeoJson(otherInfo);
		dam.setLocation(geoJsonAndOtherInfo.get(0));
		dam.setOtherInfo(geoJsonAndOtherInfo.get(1));
		return dam;
	}
	
	public List<JSONObject> productGeoJson(JSONObject jsonObject){
		JSONObject geoJson = new JSONObject();
		if(jsonObject.containsKey("leftTopLongitude") && jsonObject.containsKey("leftTopLatitude") &&
			jsonObject.containsKey("rightTopLongitude") && jsonObject.containsKey("rightTopLatitude") &&
			jsonObject.containsKey("rightButtomLongitude") && jsonObject.containsKey("rightButtomLatitude") &&
			jsonObject.containsKey("leftButtomLongitude") && jsonObject.containsKey("leftButtomLatitude") ){
			geoJson.put("type", "Polygon");
			JSONArray coord = new JSONArray();
			JSONArray coordinates = new JSONArray();
			JSONArray coordLeftTop = new JSONArray();
			JSONArray coordLeftButtom = new JSONArray();
			JSONArray coordRightTop = new JSONArray();
			JSONArray coordRightButtom = new JSONArray();
			coordLeftTop.add(Double.valueOf((String) jsonObject.get("leftTopLongitude")));
			coordLeftTop.add(Double.valueOf((String) jsonObject.get("leftTopLatitude")));
			coordRightTop.add(Double.valueOf((String) jsonObject.get("rightTopLongitude")));
			coordRightTop.add(Double.valueOf((String) jsonObject.get("rightTopLatitude")));
			coordRightButtom.add(Double.valueOf((String) jsonObject.get("rightButtomLongitude")));
			coordRightButtom.add(Double.valueOf((String) jsonObject.get("rightButtomLatitude")));
			coordLeftButtom.add(Double.valueOf((String) jsonObject.get("leftButtomLongitude")));
			coordLeftButtom.add(Double.valueOf((String) jsonObject.get("leftButtomLatitude")));
			coord.add(coordLeftTop);
			coord.add(coordRightTop);
			coord.add(coordRightButtom);
			coord.add(coordLeftButtom);
			coord.add(coordLeftTop);
			coordinates.add(coord);
			geoJson.put("coordinates", coordinates);

			jsonObject.remove("leftTopLongitude");
			jsonObject.remove("leftTopLatitude");
			jsonObject.remove("rightTopLongitude");
			jsonObject.remove("rightTopLatitude");
			jsonObject.remove("rightButtomLongitude");
			jsonObject.remove("rightButtomLatitude");
			jsonObject.remove("leftButtomLongitude");
			jsonObject.remove("leftButtomLatitude");
		}
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		jsonList.add(geoJson);
		jsonList.add(jsonObject);
		return jsonList;
	}

}
