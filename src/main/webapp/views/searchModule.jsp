<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>DMS..组件管理</title>
<script data-main="static/javascripts/views/searchModule"
	src="static/javascripts/libs/requirejs-2.1.9/require.min.js"></script>
</head>
<body class="sticky-header">
	<jsp:include page="leftside.jsp"></jsp:include>
	<!-- main content start-->
	<div class="main-content">
		<!-- header section start-->
		<div class="header-section">
			<!--toggle button start-->
			<a class="toggle-btn"> <i class="fa fa-bars"></i>
			</a>
			<!--toggle button end-->
		</div>

		<div class="container-fluid">
			<table id="resultTable"
				class="table table-responsive table-condensed table-bordered table-striped">
			</table>
		</div>

		<!--      <div class="col-xs-10 col-xs-offset-2"> -->
		<!--                         <button id="submitBtn" type="button" class="btn btn-success"> -->
		<!--                             <i class="fa fa-file-o"></i> 添加组件 -->
		<!--                         </button> -->
		<!--                         <button id="resetBtn" type="button" class="btn btn-warning"> -->
		<!--                             <i class="fa fa-undo"></i> 删除组件 -->
		<!--                         </button> -->
		<!--      </div> -->
	</div>
</body>