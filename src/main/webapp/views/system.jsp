<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>DMS..配置中心</title>
<script data-main="static/javascripts/views/system"
	src="static/javascripts/libs/requirejs-2.1.9/require.min.js"></script>
</head>
<body class="sticky-header">
	<jsp:include page="leftside.jsp"></jsp:include>
	<!-- main content start-->
	<div class="main-content" style="background-color: #eff0f4 !important;">
		<!-- header section start-->
		<div class="header-section">
			<!--toggle button start-->
			<a class="toggle-btn"> <i class="fa fa-bars"></i>
			</a>
			<!--toggle button end-->
			<div class="tools"></div>
		</div>
		<div class="wrapper" style="padding: 15px">
			<div class="container-fluid">
				<table id="fieldConfigTable"
					class="table table-responsive table-condensed table-bordered table-striped">

				</table>
			</div>
			<div id="editPanel"></div>
		</div>
	</div>
</body>