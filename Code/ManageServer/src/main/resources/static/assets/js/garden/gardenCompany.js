$(function(){
	showCompany(pageNum,pageSize);
	 $(".search-box").on("click",".search-item-content>a",function(){
        $(this).addClass("active").siblings().removeClass("active");
        showCompany(pageNum,pageSize);
	 });
});
var options={
	    "id":"page",//显示页码的元素
	    "data":null,//显示数据
	    "maxshowpageitem":5,//最多显示的页码个数
	    "pagelistcount":20,//每页显示数据个数
	    "callBack":function(){}
	};
var pageSize = 20;
var pageNum = 0;
var searchName
function showCompany(_pageNum,_pageSize){
	var msg = new Array();
    var arr = $(".search-box").find(".active");
    arr.each(function(){
        msg.push($(this).html());
    });
	var req = {
			"pageNum" : _pageNum,
			"pageSize" : _pageSize,
			"msg":msg
		};
	$.ajax({
		type:'post',
		url:'/apis/company/findGardenCompany.json',
		contentType:'application/json',
		data:JSON.stringify(req),
		async:false,
		success:function(res){
			console.log(res.data);
			var arr = res.data.content;
			var html = '';
			for (var i = 0; i < arr.length; i++) {
				html += '<tr class="gradeX"><input type="hidden" class="form-control input-block" value="'+arr[i].id+'"/><td>' + arr[i].companyName + '</td><td>' + arr[i].address + '</td><td>' + arr[i].boss + '</td><td>' + arr[i].park
				+ '<td>' + arr[i].registerCapital + '</td><td>' + arr[i].registerDate + '</td></td><td class="actions"><a href="javascript:void(0);" class="hidden on-editing save-row"><i class="fa fa-save"></i></a>'
				+ '<a href="javascript:void(0);" class="hidden on-editing cancel-row"><i class="fa fa-times"></i></a>' + '<a href="javascript:void(0);" class="on-default edit-row"><i class="fa fa-pencil"></i></a>'
				+ '<a href="javascript:void(0);" class="on-default remove-row"><i class="fa fa-trash-o"></i></a></td></tr>';
			}
			$("#company").html(html);
			if(res.data.totalPages>1){
                page.init(res.data.totalElements,res.data.number+1,options);
                $("#"+page.pageId +">li[class='pageItem']").on("click",function(){
                	pageNum = $(this).attr("page-data")-1;
                	showCondition($(this).attr("page-data")-1,pageSize,_serarchName);
                });
            }else{
                $('#page').html("");
            }
		}
	});
	initPage();
}
function initPage(){
	$(".edit-row").on("click",function(){
		$(this).parents('.gradeX').children( 'td' ).each(function( i ) {
			var $this = $( this );
			if ( $this.hasClass('actions') ) {
				$this.find("a").each(function(){
					if($(this).hasClass("hidden")){
						$(this).removeClass("hidden");
					}else{
						$(this).addClass("hidden");
					}
				})
			} else {
				$this.html( '<input type="text" class="form-control input-block" value="' + $this.text() + '"/>' );
			}
		});
	})
	$(".save-row").on("click",function(i){
		var _input = $(this).parents('.gradeX').find( '.input-block' );
		$(this).parents('.gradeX').find(".actions").find("a").each(function(){
			if($(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else{
				$(this).addClass("hidden");
			}
		});
		var indusArr = new Array();
		_input.each(function( i ) {
			var $this = $( this );
			indusArr.push($this.val());
		});
		var indus = {"id":indusArr[0],"industry":indusArr[1],"company":indusArr[2],"companyName":indusArr[3],"industryLabel":indusArr[4],"induszero":indusArr[5]}
		insertIndus(indus);
	})
	$("#addToTable").on("click",function(i){
		$(this).attr("disabled",true);
		var html = '<tr role="row" class="adding odd"><td class="sorting_1"><input class="form-control input-block" value="" type="text"></td>' +
			'<td><input class="form-control input-block" value="" type="text"></td>' +
			'<td><input class="form-control input-block" value="" type="text"></td>' +
			'<td><input class="form-control input-block" value="" type="text"></td>' +
			'<td><input class="form-control input-block" value="" type="text"></td>' +
			'<td class="actions"><a href="#" class="on-editing save-row">' +
				'<i class="fa fa-save"></i></a> <a href="#" class="on-editing cancel-row">' +
				'<i class="fa fa-times"></i></a> <a href="#" class="on-default edit-row hidden">'+
				'<i class="fa fa-pencil"></i></a> <a href="#" class="on-default remove-row hidden">'+
				'<i class="fa fa-trash-o"></i></a></td></tr>';
		$("#indusCompany").children().eq(0).before(html);
		$(".save-row").on("click",function(i){
			var _input = $(this).parents('.adding').find( 'input' );
			$(this).parents('.adding').find(".actions").find("a").each(function(){
				if($(this).hasClass("hidden")){
					$(this).removeClass("hidden");
				}else{
					$(this).addClass("hidden");
				}
			});
			var indusArr = new Array();
			_input.each(function( i ) {
				var $this = $( this );
				indusArr.push($this.val());
			});
			var indus = {"industry":indusArr[0],"company":indusArr[1],"companyName":indusArr[2],"industryLabel":indusArr[3],"induszero":indusArr[4]}
			insertIndus(indus);
		})
		$(".cancel-row").on("click",function(){
			window.location.reload();
		})
	})
	$(".remove-row").on("click",function(i){
		var id = $(this).parents('.gradeX').find( 'input' ).val();
		delIndus(id);
	})
	$(".cancel-row").on("click",function(){
		window.location.reload();
	})
}