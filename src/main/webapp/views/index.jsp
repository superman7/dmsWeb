<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>DMS..主页</title>
<script data-main="static/javascripts/views/index"
	src="static/javascripts/libs/requirejs-2.1.9/require.min.js"></script>
</head>
<body class="sticky-header">
	<%@include file="leftside.jsp"%>
	<!-- main content start-->
	<div class="main-content">

		<!-- header section start-->
		<div class="header-section">

			<!--toggle button start-->
			<a class="toggle-btn"> <i class="fa fa-bars"></i>
			</a>
			<!--toggle button end-->

			<div class="tools">
				<!-- Split button -->
				<div class="btn-group">
					<button type="button" class="btn btn-info uplaodFileBtn">
						<i class="fa fa-upload"></i><span class="label">上传</span>
					</button>
					<!-- ko if: canBroswerDirectory() -->
					<button type="button" class="btn btn-info dropdown-toggle"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<span class="caret"></span> <span class="sr-only">Toggle
							Dropdown</span>
					</button>
					<ul class="dropdown-menu">
						<li><a href="#" class="uplaodFileBtn">上传文件 </a></li>
						<li><a href="#" class="uplaodDirectoryBtn">上传文件夹</a></li>
					</ul>
					<!-- /ko -->
				</div>

				<button class="btn btn-warning">
					<i class="fa fa-folder"></i> <span class="label"
						data-bind="click:createDirectory">新建文件夹</span>
				</button>
			</div>
			<div class="right-tools">
				<div class="searchForm input-group ">
					<input type="text" class="form-control"
						data-bind="textInput:searchText,event:{propertychange:searchtTextChange,input:searchtTextChange,keypress:searchFiles}"
						placeholder="搜索我的文件" />
					<!-- ko if: showSearchClear -->
					<span class="search-clear" data-bind="click:clearSearchText">
						<i class="fa fa-times-circle"></i>
					</span>
					<!-- /ko -->
					<span class="input-group-btn">
						<button type="button" class="btn btn-default"
							data-bind="click:searchFiles,event:{mouseout:mouseoutSearchBtn}">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-default"
						data-bind="css:{'active':showList()},click:showInListView">
						<i class="fa fa-list"></i>
					</button>
					<button type="button" class="btn btn-default"
						data-bind="css:{'active':!showList()},click:showInGridView">
						<i class="fa fa-th-large"></i>
					</button>
				</div>
			</div>
		</div>
		<!-- header section end-->

		<!-- page heading start-->
		<div class="page-heading">
			<div class="module-history-list">
				<span class="history-list-dir">全部文件</span>
				<ul class="historylistmanager-history"
					data-bind="visible:currentPathArray().length>0">
					<li><a href="javascript:void(0);"
						data-bind="click:returnBackPath">返回上一级</a> <span
						class="historylistmanager-separator">|</span> <a
						href="javascript:void(0);" data-bind="click:openRootPath">全部文件</a>
						<span class="historylistmanager-separator-gt">&gt;</span></li>
					<li>
						<!-- ko if: showEllipsis --> <label>...</label> <span
						class="historylistmanager-separator-gt">&gt;</span> <!-- /ko --> <!-- ko foreach:last3PathArray() -->
						<!-- ko if: isLast --> <span
						data-bind="text:value,attr:{title:value}"></span> <!-- /ko --> <!-- ko ifnot: isLast||value=='' -->
						<a href="javascript:void(0);"
						data-bind="text:value,attr:{title:value},click:$parent.itemClick"></a>
						<span class="historylistmanager-separator-gt">&gt;</span> <!-- /ko -->
						<!-- /ko -->
					</li>
				</ul>
				<span class="history-list-tips">已全部加载，共<label
					data-bind="text:filesToShow().length"></label>个
				</span>
			</div>
			<div class="list-header">
				<ul class="list-cols">
					<li class="col"
						data-bind="css:{checked:isCheckedAll},style:{width:showList()?'60%':'100%'}">
						<div class="col-item check">
							<span class="check-icon" data-bind="click:checkAllItems"></span>
						</div> <!-- ko if: showList() --> <span>文件名</span> <!-- /ko -->
					</li>
					<!-- ko if: showList() -->
					<li class="col" style="width: 16%"><span>大小</span></li>
					<li class="col" data-bind="style:{width:showPath()?'14%':'24%'}">
						<span class="text">修改日期</span>
					</li>
					<!-- ko if : showPath() -->
					<li class="col" style="width: 10%"><span class="text">所在目录</span>
					</li>
					<!-- /ko -->
					<!-- /ko -->
				</ul>
				<div class="list-header-operatearea"
					data-bind="visible:checkedCount()>0">
					<span class="count-tips">已选中<label
						data-bind="text:checkedCount()"></label>个文件/文件夹
					</span>
					<div class="list-header-operate">
						<button type="button" class="btn btn-default">
							<i class="fa fa-arrow-circle-down"></i> <span class="label">下载</span>
						</button>
						<button type="button" class="btn btn-default"
							data-bind="click:delFiles">
							<i class="fa fa-trash-o"></i> <span class="label">删除</span>
						</button>
						<!-- ko if:checkedCount()==1 -->
						<button type="button" class="btn btn-default"
							data-bind="click:rename">
							<i class="fa fa-edit"></i> <span class="label">重命名</span>
						</button>
						<!-- /ko -->
						<button type="button" class="btn btn-default"
							data-bind="click:move">
							<i class="fa fa-indent"></i> <span class="label">移动</span>
						</button>
					</div>
				</div>
			</div>

		</div>
		<!-- page heading end-->
		<!--body wrapper start-->
		<div class="wrapper module-list">
			<!-- ko if: showList() -->
			<div class="list-view" data-bind="foreach:filesToShow">
				<div class="list-view-item" data-bind="css:{'item-active':checked}">
					<span class="checkbox" data-bind="click:$parent.checkitem"></span>
					<div class="fileicon" data-bind="css:cssName"></div>
					<div class="file-name" style="width: 60%">
						<div class="text">
							<!-- ko if: dir==1 -->
							<a href="javascript:void(0);" class="filename"
								data-bind="text:name,click:$parent.itemClick"></a>
							<!-- /ko -->
							<!-- ko if: dir==0 -->
							<span data-bind="text:name"></span>
							<!-- /ko -->
						</div>
					</div>
					<div class="file-size" style="width: 16%" data-bind="text:size"></div>
					<div class="ctime"
						data-bind="text:mtime,style:{width:$parent.showPath()?'13%':'23%'}"></div>
					<!-- ko if : $parent.showPath() -->
					<div class="path-info" style="width: 10%">
						<span class="search-feild"
							data-bind="text:$parent.formatterPath($data),attr:{title:$parent.formatterPath($data)},click:$parent.openSeachPath"></span>
					</div>
					<!-- /ko -->
				</div>
			</div>

			<!-- /ko -->
			<!-- ko ifnot: showList() -->
			<div class="grid-view" data-bind="foreach:filesToShow">
				<div class="grid-view-item"
					data-bind="css:{'item-active':checked},attr:{title:name},event:{mouseover:$parent.itemMouseover,mouseout:$parent.itemMouseout}">
					<div class="fileicon "
						data-bind="css:cssName+'-l',click:$parent.itemClick">
						<!--ko if: src -->
						<img class="thumb"
							data-bind="attr:{src:src,max:maxSrc,alt:name},event:{load:$parent.onImageLoad }">
						<!--/ko -->
						<span class="checkbox"
							data-bind="click:$parent.checkitem,clickBubble:false"></span>
					</div>
					<div class="file-name" data-bind="text:name"></div>
				</div>
			</div>
			<!-- /ko -->
			<!-- ko if : isEditFileName -->
			<div class="module-edit-name"
				data-bind="css:{'module-edit-name-grid':!showList()}">
				<div class="new-dir-item">
					<input class="box" type="text" data-bind="value:newFileName" /> <span
						class="sure" data-bind="click:submitCreateDirectoryOrRename">
						<i class="fa fa-check"></i>
					</span> <span class="cancel"
						data-bind="click:cancelCreteDirectoryOrRename"> <i
						class="fa fa-times"></i>
					</span>
				</div>
			</div>
			<!-- /ko -->
		</div>
		<!--body wrapper end-->
	</div>
	<!-- main content end-->
</body>
</html>
