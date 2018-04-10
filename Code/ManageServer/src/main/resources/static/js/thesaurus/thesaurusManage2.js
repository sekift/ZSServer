var keywordAttribute='0';
var pageSize = 10;
var pageNumber = 0;
var sort = '1';
var option='';
var index=1;
var length=1;
var options={
		"id":"page",//显示页码的元素
		"data":null,//显示数据
	    "maxshowpageitem":5,//最多显示的页码个数
	    "pagelistcount":8,//每页显示数据个数
	    "callBack":function(){}
};
$(function(){
	$('#keyword_info').addClass("active");
	//获取所有label
	getLabel();
	
	$('#addKeyword').after('<input type="file" id="load_xls" name="file" style="display:none" onchange ="uploadFile()">');
	$('#addKeyword').on('click',function(){
		$('#load_xls').click();
	});
});
//文件批量上传
function uploadFile(){
	 var myform = new FormData();
    myform.append('file',$('#load_xls')[0].files[0]);
    $.ajax({
   	 url: "/apis/keyInfo/ExcelDataUpload.json",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success:function(res){
       	 if(res.data != null){
       		 layer.msg(res.data, {icon: 1});
       	 }else{
       		 layer.msg(res.message, {icon: 2});
       	 }
       	 /*var param ={type:type,pageSize:pageSize,pageNumber:pageNumber};
			  getType(param);*/
        }
    });
};
//模态框的回显
$('#addKeywordToTable').on('click',function(){
	$('#select').html(option);
	
	$('#myModal').modal('show');
	//添加属性的功能
	$('.my_add').on('click',function(){
		$('#demo-form').append(
				'<div class="form-group add_'+index+'">' +
	            '<label class="col-sm-3 control-label" for="state">关联关系</label>' +
	            '<div class="col-sm-3">' +
	            '<input type="text" name="name_'+index+'" class="form-control" placeholder="添加属性名">' +
	            '</div>' +
	            '<div class="col-md-3">' +
	            '<input type="text" name="value_'+index+'" class="form-control" placeholder="添加属性内容">' +
	            '</div>' +
	            '<button class="btn btn-info btn-xs drop_'+index+'">删除 <i class="fa fa-minus"></i></button>' +
	            '</div>'		
		);
		 $(".drop_"+index+"").on("click",function () {
	            var a = $(this).attr('class');
	            var s =a.substring(25,a.length);
	            $(".add_"+s+"").remove();
	            length--;
	        });
	        index++;
	        length++;
	});
});

//下一页  跳转下一页，同时保存信息
$('.my_nextadd').on('click',function(){
	//属性值
	var arr = new Array();
	var i=1;
    var j=1;
    while (true){
    		//属性名
    	 	var _attributeName = $("input[name='name_"+i+"']").val();
    	 	//属性值
    	 	var _attributeValue = $("input[name='value_"+i+"']").val();
    	 	 arr.push({
    	 		 attributeNames:_attributeName,
    	 		 attributeValues:_attributeValue
             });
             j++;
             i++;
          if(j==length) break;
          if(i>50) break;
    };
  //产业值
    var _typeWord='';
	var _type=$("#select option:selected").val();
	console.log(_type);
	if(_type==0){
		_typeWord=$("input[name=TypeWord]" ).val();
	}
	var _keyword=$("input[name=keyword]" ).val();
	var _describe=$("input[name=describe]" ).val();
	var param={typeId:_type,keyword:_keyword,describe:_describe,typeWord:_typeWord,msg:arr};
	$.ajax({
		url:'/apis/keyInfo/addOrUpAttributeDTO.json',
		type:'POST',
		data:JSON.stringify(param),
		asynyc:false,
		contentType:'application/json',
		success:function(res){
			if(res.data==true){
				$('#myModal').modal('hide');
				$('#secondModal').modal('show');	
			}
		}
	});
	
	
	
	
});
//新增分类
$('#new_add').on('click',function(){
	$('#form3').append(
			'<div class="form-group"><label class="col-md-3 control-label" for="text-input">新增词类</label>'
					+'<div class="col-md-9">'
					+'<input type="text" name="addInfo"  class="form-control" ></div></div>'
	);
	$('#thirdModal').modal('show');
	$('.my_fireadd').on('click',function(){
		var addInfo = $("input[name='addInfo']").val();
		console.log(addInfo);
		$.ajax({
			url:'/apis/keyInfo/updateLabel.json?typeWord='+addInfo,
			type:'GET',
			asynyc:false,
			success:function(res){
				getLabel();
				$('#thirdModal').modal('hide');
			}
		});
	});
});
//获取分类信息
function getLabel(){
	$.ajax({
		url:'/apis/keyInfo/getLabelInfo.json',
		type:'GET',
		asynyc:false,
		success:function(res){
			console.log(res.data);
			if(res.data==null){
				 layer.msg(res.message, {icon: 2});
			}else{
				var label ='';
					label +='<a href="javascript:void(0);" id="0" class="search-item active">全部</a>';
				$.each(res.data,function (i,e){
						label +='<a href="javascript:void(0);" id="'+e.id+'" class="search-item">'+e.typeWord+'</a>';
						option +='<option value="'+e.id+'">'+e.typeWord+'</option>';		                  
				})
				option +='<option value="'+0+'">新增类别</option>';
				$('#labelInfo').html(label);
			}
		}
	});
};


