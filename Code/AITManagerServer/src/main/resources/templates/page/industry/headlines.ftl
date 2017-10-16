<!DOCTYPE html>
<html lang="en">
<head>
<!-- Basic -->
<meta charset="UTF-8" />
<title>慧数招商后台系统</title>
<!-- Mobile Metas -->
<!--专家观点-->
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<#include "/common/link.ftl">
</head>
<body class="">
	<div class="layui-layout layui-layout-admin">
		<#include "/common/header.ftl">
		<!-- Start: Content -->

		<#include "/common/sidebar.ftl">
		<!-- 内容 Page -->
		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div class="layui-tab" lay-filter="demo">
				<ul class="layui-tab-title">
					<li lay-id="11" class="layui-this"
						onclick="myClick(industry,industryLabel,time,'产业头条')">产业头条</li>
					<li lay-id="22" onclick="myClick2(industry,industryLabel,time,'产业云图')">产业云图</li>
				</ul>
				<div class="layui-tab-content">
					<!-- 选项卡1 -->
					<div class="layui-tab-item layui-show">
						<div class="layui-nav layui-bg-cyan">

							<ul class="" lay-filter="">
								<li class="layui-nav-item"><a href="">产业分类：</a></li>
								<li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="getLabel('互联网+');myClick('互联网+', '不限', time, '产业头条');">互联网+</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel('高科技');myClick('高科技', '不限', time, '产业头条');">高科技</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel('港口物流');myClick('港口物流', '不限', time, '产业头条');">港口物流</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel('文化创意');myClick('文化创意', '不限', time, '产业头条');">文化创意</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel('其他');myClick('其他', '不限', time, '产业头条');">其他</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel('精英配套');myClick('精英配套', '不限', time, '产业头条');">精英配套</a></li>
							</ul>
							<ul class="" lay-filter="" id="label">
							</ul>
							<ul class="" lay-filter="">
								<li class="layui-nav-item"><a href="">时排序间：</a></li>
								<li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '一年', '产业头条');">全部</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '今日', '产业头条');">今日</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '昨日', '产业头条');">昨日</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '近3天', '产业头条');">近3天</a></li>
							</ul>
							<ul  style="display:none">
								<li class="layui-nav-item"><a href="">关键词：</a></li>
							    <li id="searchName" style="position:relative;display:inline-block;">
							     <span></span>
							     <i class="layui-icon" style="position:absolute;right:-10px;top:-10px;cursor:pointer;" class="remove" onclick="remove();">&#x1007;</i>  	
							    </li>
							</ul>
						</div>
						<div class="layui-col-md12">
							<div>
								<ul id="biuuu_city_list_1"></ul>
								<li id="demo1"></li>
							</div>
						</div>
					</div>
					<!--选项卡2-->
					<div class="layui-tab-item" class="layui-col-md12">
						<div class="layui-nav layui-bg-cyan">

							<ul class="" lay-filter="">
								<li class="layui-nav-item"><a href="">产业分类：</a></li>
								<li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="getLabel('互联网+');myClick('互联网+', '不限', time, '产业头条');">互联网+</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel2('高科技');myClick('高科技', '不限', time, '产业头条');">高科技</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel2('港口物流');myClick('港口物流', '不限', time, '产业头条');">港口物流</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel2('文化创意');myClick('文化创意', '不限', time, '产业头条');">文化创意</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel2('其他');myClick('其他', '不限', time, '产业头条');">其他</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="getLabel2('精英配套');myClick('精英配套', '不限', time, '产业头条');">精英配套</a></li>
							</ul>
							<ul class="" lay-filter="" id="label2">
								
							</ul>
							<ul class="" lay-filter="">
								<li class="layui-nav-item"><a href="">时排序间：</a></li>
								<li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '一年', '产业头条');">全部</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '今日', '产业头条');">今日</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '昨日', '产业头条');">昨日</a></li>
								<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(industry, industryLabel, '近3天', '产业头条');">近3天</a></li>
							</ul>
						</div>
						<div>
							<ul id="biuuu_city_list_2"></ul>
							<div id="demo2"></div>
						</div>
					</div>

				</div>

			</div>
		</div>
		<#include "/common/script.ftl">
</body>
<script>
	function remove(){
		$("#searchName").parent().css("display","none");
		myClick(industry, industryLabel, time, '产业头条');
	}
	function onDel(id) {
		layer.confirm('确定删除该文章？', function(index) {
			$.ajax({
				url : "/art/delete",
				contentType : 'application/json',
				data : {
					id : id
				},
				success : function(response) {
					layer.alert(response.data);
					myClick(industry, industryLabel, time, dimension,current);
				}
			});
			layer.close(index);
		});
	}
	function toTop() {
		layer.open({
			content : '确认置顶该片数据',
			btn : [ '确认', '取消' ],
			yes : function(index, layero) {
				//按钮【按钮一】的回调
			},
			btn2 : function(index, layero) {
				//按钮【按钮二】的回调

				//return false 开启该代码可禁止点击该按钮关闭
			},
			cancel : function() {
				//右上角关闭回调

				//return false 开启该代码可禁止点击该按钮关闭
			}
		});
	}
	$(function() {
		getLabel(industry);
		getLabel2(industry);
		myClick(industry, industryLabel, time, '产业头条');
		myClick2(industry, industryLabel, time, '产业云图');
	});
	function getLabel(industry){
	$.ajax({
		type : 'get',
		url : '/head/getLabel.json?industry='+industry,
		success : function(response){
			var before = '<li class="layui-nav-item"><a href="">产业子类：</a></li><li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="myClick(\''+response.industry+'\',\'不限\',\''+time+'\',\'产业头条\');">不限</a></li>';
			var arr = [];
			for(var i=0;i<response.label.length;i++){
				arr.push('<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(\''+response.industry+'\',\''+response.label[i]+'\',\''+time+'\',\'产业头条\')" >'+response.label[i]+'</a></li>');
			}
			var s=before+arr.join('');
			$("#label").html(s);
		}
	});
}
function getLabel2(industry){
	$.ajax({
		type : 'get',
		url : '/head/getLabel.json?industry='+industry,
		success : function(response){
			var before = '<li class="layui-nav-item"><a href="">产业子类：</a></li><li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="myClick(\''+response.industry+'\',\'不限\',\''+time+'\',\'产业云图\');">不限</a></li>';
			var arr = [];
			for(var i=0;i<response.label.length;i++){
				arr.push('<li class="layui-nav-item"><a href="javascript:void(0)" onclick="myClick(\''+response.industry+'\',\''+response.label[i]+'\',\''+time+'\',\'产业云图\')" >'+response.label[i]+'</a></li>');
			}
			var s=before+arr.join('');
			$("#label2").html(s);
		}
	});
}
	function myRequest(str, url) {
		$.ajax({
			type : 'post',
			url : url,
			async : false,
			contentType : 'application/json',
			data : JSON.stringify(str),
			success : function(response) {
				layui.use('layer', function() {
					var layer = layui.layer;
					if (response.success) {
						if ('产业头条' == str.dimension) {
							showTable(response.data, 'demo1', str.dimension);
						} else {
							showTable2(response.data, 'demo2', str.type);
						}
					} else {
						if (response.code != null) {
							window.location.href = "/login";
						} else {
							layer.alert(response.message);
						}
					}
				});
			}
		});
	}
	
	function myClick2(a, b, c, d,e) {

	industry = a;
	industryLabel = b;
	time = c;
	dimension = d;
	var req = {
		type : '产业云图',
		"msg" : ["互联网","不限","今日"],
		time : '一年'
		//industry : industry,
		//industryLabel : industryLabel,
		//time : time,
		//dimension : dimension,
		//pageNum : e==null?0:e,
		//pageSize : 10
	};
	myRequest(req, '/head/getHeadlineKeyWord.json');
}
	function myClick3(a, b, c, d,e) {

	industry = a;
	industryLabel = b;
	time = c;
	dimension = d;
	var req = {
		//type : '产业云图',
		//"msg" : ["互联网","不限","今日"],
		//time : '一年'
		industry : a,
		industryLabel : b,
		time : c,
		dimension : d,
		searchName : e,
		pageNum : 0,
		pageSize : 10
	};
	myRequest(req, '/head/getExpertOpinion.json');
	$("#searchName").find('span').text(e);
	$("#searchName").parent().css("display","block");
	layui.use('element', function() {

		var element = layui.element;
		element.tabChange('demo', '11');
	});
}
	function showTable2(data, elem, dimension) {
	layui.use('table', function() {
		var table = layui.table;
		var laypage = layui.laypage;
		// 调用分页
		laypage.render({
			elem : elem,
			count : data.totalElements,
			curr:current+1,
			jump : function(obj, first) {
				if (first) {
					show(data, dimension);
				}
				// 模拟渲染
				if (!first) {
					current = obj.curr;
					var req = {
						industry : industry,
						industryLabel : industryLabel,
						time : time,
						dimension : dimension,
						pageNum : obj.curr,
						pageSize : obj.limit
					};
					$.ajax({
						type : 'post',
						url : "/head/getHeadlineKeyWord.json",
						async : false,
						contentType : 'application/json',
						data : JSON.stringify(req),
						success : function(response) {
							// document.getElementById('biuuu_city_list').innerHTML
							// ="";
							show(response, dimension);
						}
					});
				}
			}
		});
	});
}
	function show(d, dimension) {
		var showTab;
		if ('产业头条' == dimension) {
			showTab = document.getElementById('biuuu_city_list_1');
			showTab.innerHTML = function() {
			var before = '<table class="layui-table" lay-even="" lay-skin="nob">' + '<colgroup><col width="90"><col width="200"><col width="450"><col width="200"><col width="220"><col></colgroup>'
					+ '<thead><tr><th>作者</th><th>标题</th><th>详情</th><th>时间</th><th>来源</th><th>操作</th></tr></thead><tbody>';
			var arr = []
			layui.each(d, function(index, item){
		          arr.push('<tr><td>'+item.author+'</td><td>'+item.title+'</td><td><a href="/head/getDetail.json?id='+item.id+'">'+item.summary+
		          			'</a></td><td>'+item.publishTime+'</td><td>'+item.source+
		          			'</td><td id="appendix"><a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="detail" onclick="onDel(\''+item.id+'\')">删除</a>'+
		          			'<a class="layui-btn layui-btn-mini" lay-event="detail" onclick="myTop(\''+item.id+'\')">置顶</a></td></tr>');
		        });
			var inner = arr.join('');
			var after = '</tbody></table> ';
			return before + inner + after;
		}();
		} else {
			showTab = document.getElementById('biuuu_city_list_2');
			showTab.innerHTML = function() {
			var before = '<table class="layui-table" lay-even="" lay-skin="nob">' + '<colgroup><col width="90"><col width="200"><col width="450"><col width="200"><col width="220"><col></colgroup>'
					+ '<thead><tr><th>编号</th><th>名称</th><th>数值</th></tr></thead><tbody>';
			var arr = []
			layui.each(d, function(index, item){
		          arr.push('<tr><td>'+index+'</td><td>'+item.value+'</td><td><a href="javascript:void(0);" onclick="myClick3(\''+industry+'\',\''+industryLabel+'\',\''+time+'\',\'产业头条\',\''+item.name+'\');">'+item.name+
		          			'</a></td><td id="appendix"><a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="detail" onclick="onDel(\''+item.id+'\')">删除</a>'+
		          			'<a class="layui-btn layui-btn-mini" lay-event="detail" onclick="myTop(\''+item.id+'\')">置顶</a></td></tr>');
		        });
		    var inner = arr.join('');
			var after = '</tbody></table> ';
			return before + inner + after;
			}();
		}
		
	}
</script>

</html>