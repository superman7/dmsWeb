package com.aerors.dms.service.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IFileOperationDao;
import com.aerors.dms.dao.IParseDao;
import com.aerors.dms.dao.ISystemConfigDao;
import com.aerors.dms.model.FieldConfig;
import com.aerors.dms.model.ParseModel;
import com.aerors.dms.model.WholeFileMeta;
import com.aerors.dms.service.IParseService;
import com.aerors.dms.service.IParseServiceOld;
import com.aerors.dms.utils.IFieldConfigName;
import com.aerors.dms.utils.ParseUtil;
import com.aerors.dms.utils.PolygonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import org.apache.commons.lang.StringUtils;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.*;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service.impl
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/11 15:26
 */
/*@Service*/
public class ParseServiceImplOld implements IParseServiceOld {
   /* @Autowired
    private IFileOperationDao mongoDbGridFsDaoImpl;

    @Autowired
    private ISystemConfigDao systemConfigDaoImpl;

    @Autowired
    private IParseDao parseDaoImpl;

    public void parse(WholeFileMeta wholeFileMeta) {
        String extName = ParseUtil.getExtName(wholeFileMeta.getName());
        if (extName != null) {
            FieldConfig fc = this.systemConfigDaoImpl.queryById(IFieldConfigName.parsetObject);
            Set<String> set = fc.getFields().keySet();
            for (String key : set) {
                //解析文件
                if (extName.equals(key)) {
                    InputStream is = this.getFileByWFM(wholeFileMeta);
                    if (is != null) {
                        String jsonStr = ParseUtil.parseTypeFile(extName, is);
                        if (jsonStr != null) {
                            BasicDBObject value = BasicDBObject.parse(jsonStr);
                            GeoJsonPolygon envelop = this.getEnvelop(value);
                            if (envelop != null) {
                                ParseModel pm = new ParseModel();
                                //是否包含四角坐标,包含了进行图形解析
                                //按顺序(左下、右下，右上，左上)获取四角坐标
                                pm.setGeometry(envelop);
                                pm.setParseValue(value);
                                pm.setWholeFile(wholeFileMeta);
                                this.parseDaoImpl.save(pm);
                            }
                        }
                    }
                }
            }
        }
    }

    public InputStream getFileByWFM(WholeFileMeta wholeFileMeta) {
        SequenceInputStream result = null;
        //开始解析;
        LinkedList<String> blocks = wholeFileMeta.getBlocks();
        if (blocks.size() > 0) {
            Vector<InputStream> vector = new Vector<InputStream>();
            for (String block : blocks) {
                GridFSDBFile file = this.mongoDbGridFsDaoImpl.queryByMd5(block);
                if (file != null && file.getLength() > 0) {
                    vector.add(file.getInputStream());
                }
            }
            if (vector.size() > 0) {
                result = new SequenceInputStream(vector.elements());
            }
        }

        return result;
    }

    private GeoJsonPolygon getEnvelop(BasicDBObject paresJson) {
        Map<String, Object> keyMap = ParseUtil.getAllKeys(net.sf.json.JSONObject.fromObject(paresJson), null, null);
        Point bottomLeft = null;
        Point bottomRight = null;
        Point topRight = null;
        Point topLeft = null;
        try {
            bottomLeft = this.getPoint(IFieldConfigName.LeftBottomLongitude, IFieldConfigName.LeftBottomLatitude, keyMap);
            bottomRight = this.getPoint(IFieldConfigName.RightBottomLongitude, IFieldConfigName.RightBottomLatitude, keyMap);
            topRight = this.getPoint(IFieldConfigName.RightTopLongitude, IFieldConfigName.RightTopLatitude, keyMap);
            topLeft = this.getPoint(IFieldConfigName.LeftTopLongitude, IFieldConfigName.LeftTopLatitude, keyMap);
            return new GeoJsonPolygon(bottomLeft, bottomRight, topRight, topLeft, bottomLeft);
        } catch (Exception e) {
            return null;
        }


    }

    private Point getPoint(String longitudeKeyId, String latitudeKeyStr, Map<String, Object> keyMap) throws Exception {
        return new Point(Double.parseDouble(this.getKey(keyMap, longitudeKeyId)), Double.parseDouble(this.getKey(keyMap, latitudeKeyStr)));
    }

    private String getKey(Map<String, Object> keyMap, String keyId) {
        String result = null;
        FieldConfig fc = this.systemConfigDaoImpl.queryById(keyId);
        Set<String> keys = fc.getFields().keySet();
        for (String key : keyMap.keySet()) {
            if (keys.contains(ParseUtil.repalcePointToX(key))) {
                result = keyMap.get(key).toString();
                //keyset.remove(key);
                break;
            }
        }
        return result;
    }

    *//**
     * 获取解析的数据总条目数
     *
     * @return long 总条目数
     *//*
    public long getCount() {
        return this.parseDaoImpl.queryCount(null);
    }

    *//**
     * 分页查询数据
     *
     * @param {int}    startIndex 开始位置
     * @param {int}    pageSize 数据长度
     * @param {String} orderColumn 排序字段
     * @param {String} asc 正序/倒序
     * @param {String} geometryJson 图形表达 geojson字符串
     * @return {JSONObject} {list:list,total:size}
     *//*
    public JSONObject getListByPage(int startIndex, int pageSize, String orderColumn, String orderDir, String geometryJson) {
        JSONObject result = new JSONObject();
        List<GeoJson> slist = null;
        if (StringUtils.isNotEmpty(geometryJson)) {
            try {
                GeometryJSON gjson = new GeometryJSON();
                Geometry geometry = gjson.read(geometryJson);
                List<Geometry> glist = PolygonUtil.autoCut(geometry);
                slist = new ArrayList<GeoJson>();
                for (Geometry g : glist) {
                    Coordinate[] coordinates = g.getCoordinates();
                    List<Point> plist = new ArrayList<Point>();
                    for (Coordinate coor : coordinates) {
                        plist.add(new Point(coor.x, coor.y));
                    }
                    slist.add(new GeoJsonPolygon(plist));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Query query = this.parseDaoImpl.formatterGeoQuery(slist);
        result.put("total", this.parseDaoImpl.queryCount(query));
        List<JSONObject> data = new ArrayList<JSONObject>();
        List<ParseModel> list = this.parseDaoImpl.queryPageList(startIndex, pageSize, orderColumn, orderDir, query);

        for (ParseModel pm : list) {
            WholeFileMeta wfm = pm.getWholeFile();
            JSONObject item = new JSONObject();
            String name = wfm.getName();
            if (name != null) {
                name = name.substring(0, name.lastIndexOf("."));
            }
            item.put("id", pm.getId());
            //获取外接矩形的北,东,南,西
            item.put("envelope", this.getEnvelopJSON(pm.getGeometry()));
            item.put("name", name);
            WholeFileMeta path = pm.getWholeFile().getParent();
            if (path != null) {
                JSONObject pathObject = new JSONObject();
                pathObject.put("id", path.getId());
                pathObject.put("name", path.getToRootPath());
                item.put("path", pathObject);
            }
            data.add(item);
        }
        result.put("list", data);
        return result;
    }


    *//**
     * 通过 wholeFileMeta对象的旧ID查看,来查看是否需要复制对象
     *
     * @param {String} oldWFMId 原来的xml
     * @param {String} wholeFileMeta 新的文件对象
     *//*
    public void copy(String oldWFMId, WholeFileMeta wholeFileMeta) {
        ParseModel pm = null;
        if (StringUtils.isNotEmpty(oldWFMId)) {
            pm = this.parseDaoImpl.queryByWFMId(oldWFMId);
        }
        if (pm == null) {
            this.parse(wholeFileMeta);
        } else {
            pm.setId(null);
            pm.setWholeFile(wholeFileMeta);
            this.parseDaoImpl.insert(pm);
        }
    }

    *//**
     * 获取外界多边矩形的描述
     *
     * @param {GeoJsonPolygon} polygon
     * @return
     *//*
    private JSONObject getEnvelopJSON(GeoJsonPolygon polygon) {
        JSONObject envelope = null;
        if (polygon != null) {
            envelope = new JSONObject();
            List<Point> points = polygon.getPoints();
            double north = -90, south = 90, east = -180, west = 180;
            for (Point point : points) {
                north = Math.max(north, point.getY());
                south = Math.min(south, point.getY());
                east = Math.max(east, point.getX());
                west = Math.min(west, point.getX());
            }
            envelope.put("north", north);
            envelope.put("south", south);
            envelope.put("east", east);
            envelope.put("west", west);
        }
        return envelope;
    }*/
}
