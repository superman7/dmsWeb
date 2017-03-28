<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- left side start-->
<div class="left-side sticky-left-side">

    <!--logo and iconic logo start-->
    <div class="logo">
        <a href="index.view"><img src="static/images/logo.png" alt=""></a>
    </div>

    <div class="logo-icon text-center">
        <a href="index.view"><img src="static/images/logo_icon.png" alt=""></a>
    </div>
    <!--logo and iconic logo end-->

    <div class="left-side-inner">
        <!--sidebar nav start-->
        <ul class="nav custom-nav">
            <li class="active">
                <a href="index.view">
                    <i class="fa fa-home"></i><span>所有文件</span>
                </a>
            </li>
            <li>
                <a href="search.view">
                    <i class="fa fa-globe"></i><span>数据检索</span>
                </a>
            </li>
            <%--<li>
                <a href="system.view">
                    <i class="fa fa-cog"></i><span>配置中心</span>
                </a>
            </li>--%>
            <li>
                <a href="jarmanager.view">
                    <i class="fa fa-cog"></i><span>jar管理</span>
                </a>
            </li>
            <li>
                <a href="searchModule.view">
                    <i class="fa fa-cog"></i><span>任务组件管理</span>
                </a>
            </li>
        </ul>
        <!--sidebar nav end-->
    </div>
</div>
<!-- left side end-->