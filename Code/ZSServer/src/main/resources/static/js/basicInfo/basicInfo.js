$(function(){
	$("#basicinfoItem").addClass("active");
	$.ajax({  
        url: "/user/findMyInformation.json",  
        async: false,  
        success: function (result) {  
        	if(result.success){
        		$("#userAccount").html(result.data.userAccount);
        		$("#createTime").html(result.data.createTime);
        		$("#name").val(result.data.realName);
        		$("#park").val(result.data.userPark);
        		$("#department").val(result.data.userDepartment);
        		$("#position").val(result.data.userJob);
        		$("#phone").val(result.data.telphone);
        		$("#email").val(result.data.userEmail);
        	}else{
        		new Alert({flag:false,text:result.message,timer:2000}).show();
        	}
        }  
    }); 
});
String.prototype.compare = function(str){
	//不区分大小写
	if(this.toLowerCase() == str.toLowerCase()){
	   return true; // 正确
	}else{
	   return false; // 错误
	}
}
function expandMenu(){
	$("#followItem").addClass("active");
}
function changeInformation(){
	var _name=$("#name").val();
	var _park=$("#park").val();
	var _department=$("#department").val();
	var _position=$("#position").val();
	var _phone=$("#phone").val();
	var _email=$("#email").val();
	if(checkString(_name,"name")&&checkString(_park,"park")&&checkString(_department,"department")
			&&checkString(_position,"position")&&checkEmail(_email)){
		var param={realName:_name,userPark:_park,userDepartment:_department,userJob:_position,telphone:_phone,userEmail:_email};
		$.ajax({  
			url:'/user/modifyInformation.json',
			type:'POST',
			async: false,
		    contentType: 'application/json',
			data:JSON.stringify(param),
			success: function (result) {  
				if(result.data){
					new Alert({flag:true,text:result.message,timer:2000}).show();
	    		}else{
	    			new Alert({flag:false,text:result.message,timer:2000}).show();
	    		}
			}
		});
	}
}
function checkString(str,type){
	reg=/^[a-zA-Z\u4e00-\u9fa5]+$/;
	 if(!reg.test(str)){
	 $("."+type+"status").html("<b>只能输入汉字与英文</b>");
	  	return false;
	 }else{
	 $("."+type+"status").html("");
	  	return true;
	 }
}
function checkEmail(str){
	reg=/^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
	 if(!reg.test(str)){
	 $(".emailstatus").html("<b>请输入有效的邮箱号</b>");
	  	return false;
	 }else{
	 $(".emailstatus").html("");
	  	return true;
	 }
}
function checkPhone(str){
	reg=/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$/;
	 if(!reg.test(str)){
	 $(".phonestatus").html("<b>请输入有效的手机号</b>");
	  	return false;
	 }else{
	 $(".phonestatus").html("");
	  	return true;
	 }
}
function changePassword(){
	$(".beforstatus").html("");
	$(".newstatus").html("");
	$(".restatus").html("");
	$(".codestatus").html("");
	if($("#beforPassword").val().length==0){
		$(".beforstatus").html("<b>请输入原始密码！</b>");
		return false;
	}else if($("#newPassword").val().length==0){
		$(".newstatus").html("<b>请输入新密码！</b>");
		return false;
	}else if($("#rePassword").val().length==0){
		$(".restatus").html("<b>请输入确认密码！</b>");
		return false;
	}else if($("#newPassword").val()!=$("#rePassword").val()){
		$(".restatus").html("您两次输入的密码不一致请重新确认！");
		return false;
	}else if($("#code").val().length==0){
		$(".codestatus").html("<b>请输入验证码！</b>");
		return false;
	}else if(!$("#code").val().compare(getCode())){
		$(".codestatus").html("<b>验证码错误！</b>");
		return false;
	}
	$.ajax({  
        url: "/user/modifyPassword.json",  
        data:{beforPassword:$("#beforPassword").val(),newPassword:$("#newPassword").val()},
        async: false,  
        success: function (result) {  
    		if(result.data){
    			new Alert({flag:true,text:result.message}).show();
    			setTimeout("window.location.href = '/logout'",2000);
    		}else{
    			new Alert({flag:false,text:result.message,timer:2000}).show();
    		}
        }  
    }); 
}