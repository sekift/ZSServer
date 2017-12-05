<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <title>慧数招商-搜索结果</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="慧数招商平台，是一个关于园区产业招商的大数据管理平台">
    <meta name="keywords" content="慧数，招商，慧数招商，招商平台，园区，园区招商，园区招商平台，科技园，产业园，大数据，产业">
    <meta name="author" content="张鑫，慧数科技，中科点击">
    <meta name="application-name" content="慧数招商">
    <!-- css共用部分 start -->
    <#include "/common/link.ftl"/>
    <!-- css 共用部分 end -->
    <!--本页面的css部分-->
    <link rel="stylesheet" href="/css/allCompany.css" />
    <!-- js 兼容低版本IE start -->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- js 兼容低版本IE end -->
</head>
<body class="bg2">
<!-- header部分  start -->
<#include "/common/header2.ftl"/>
<!-- header部分  end -->
<div class="wrapper remove-bottom">
    <div class="page-content">
        <#include "/common/searchSidebar.ftl"/>
        <div class="right-content">
            <div class="container">
                <div class="model-box">
                    <div class="search-container">
                        <div class="search-input">
                            <span class="glyphicon icon-color glyphicon-search"></span>
                            <input type="text" id="mySearch">
                        </div>
                        <button type="button" class="btn btn-fill btn-blue search-company-two">搜索</button>
                    </div>
                    <div class="model-body border-box remove-padding">
                        <!--开始本页面中的内容-->
                        <div class="row img-list-box remove-padding ">
                        	 <ul id="city_list"></ul>
                        </div>
                    </div>
					<div class="page-box clearfix">
						<ul class="page pull-right" id="page"></ul>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include  "/common/footer.ftl"/>
<!-- js 共用部分 start -->
<#include  "/common/script.ftl"/>
<!-- js 共用部分 end -->
<script type="text/javascript" src="/js/page2.js"></script>
<script src="/js/beamSearch/beamSearch.js"></script>
<script>
	var areaRatio= new Array();
	<#if Request.data?? && (Request.data?size > 0) >
		<#list Request.data as list>
	    	areaRatio.push('${list}');
	    </#list>
    </#if>
    showCompany(areaRatio);
</script>
</body>
</html>