<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>DMS..检索</title>
    <script data-main="static/javascripts/views/search"
            src="static/javascripts/libs/requirejs-2.1.9/require.min.js"></script>
</head>
<body class="sticky-header">
<jsp:include page="leftside.jsp"></jsp:include>
<!-- main content start-->
<div class="main-content">
    <!-- header section start-->
    <div class="header-section">
        <!--toggle button start-->
        <a class="toggle-btn">
            <i class="fa fa-bars"></i>
        </a>
        <!--toggle button end-->
    </div>
    <div class="wrapper" style="padding:0;">
        <div class="container-fluid" style="padding: 0">
            <div class="col-sm-7" style="height: 454px;padding: 0">
                <div id="cesiumContainer" class="fullWindow"></div>
            </div>
            <div class="col-sm-5">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择区域</label>
                        <div class="col-sm-9">
                            <button id="drawBtn" class="btn btn-info" type="button">
                                <i class="fa fa-object-group"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="container-fluid">
            <table id="resultTable"
                   class="table table-responsive table-condensed table-bordered table-striped">
            </table>
        </div>
    </div>
</div>
</body>