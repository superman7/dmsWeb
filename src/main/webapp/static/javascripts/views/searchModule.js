/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: cesium的地球
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/12 14:08
 */
require(['../config'], function () {
  
	require([
	             'common'
	            , 'datatables.net-bs'
	            , 'css!stylesheets/table.css'
	            , 'domReady!'],function(){

           
         
            var tableId = '#resultTable';
            var table = $(tableId).DataTable({
                bStateSave: true
                , autoWidth: true
                , bLengthChange: false
                , iDisplayLength: 5
                , stripeClasses: ["odd", "even"]//为奇偶行加上样式，兼容不支持CSS伪类的场合
                , serverSide: true   //启用服务器端分页
                , searching: false
                , order: []
                , "oLanguage": {
                    "sUrl": 'static/javascripts/libs/data-tables/i18n/Chinese.json'
                }
                , ajax: function (data, callback, settings) {
                    $.post('searchModule/moduleList', function (data) {
                        callback(data);
                    });
                }
                , columns: [
//                    {orderable: false, defaultContent: '',className: 'details-control'},
                     {data:'moduleName',title: '组件名', className: 'ceterCell', defaultContent: ''}
                    , {data: 'className', title: '组件类', className: 'ceterCell',defaultContent: ''}
                    , {data: 'description', title: '组件描述',className: 'ceterCell',defaultContent: ''}
                    , {data:'createTime',title: '上传时间', orderable: true, className: 'ceterCell', defaultContent: ''}
                ],createdRow: function (row, data) {
                	
                }
                });
            

	});
});
