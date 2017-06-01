/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
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
                , iDisplayLength: 10
                , stripeClasses: ["odd", "even"]//为奇偶行加上样式，兼容不支持CSS伪类的场合
                , serverSide: false   //启用服务器端分页
                , searching: false
                , order: []
                , "oLanguage": {
                    "sUrl": 'static/javascripts/libs/data-tables/i18n/Chinese.json'
                }
                , ajax: function (data, callback, settings) {
                    $.post('systemConfig/systemConfigList', function (data) {
                        callback(data);
                    });
                }
                , columns: [
                     {data:'systemConfigContentKey',title: '参数名', className: 'ceterCell', defaultContent: ''}
                    , {data: 'systemConfigContentValue', title: '参数值', className: 'ceterCell',defaultContent: ''}
                    , {data: 'modifyFlag', title: '是否可修改',className: 'ceterCell',defaultContent: ''}
                ],createdRow: function (row, data) {
                	
                }
                });
            

	});
});
