$.ajaxSetup({contentType:"application/x-www-form-urlencoded; charset=utf-8",type:"post",dataType:"json",async:false,timeout:30000,complete:function(a){},error:function(a,c,b){}});

function checkData(){
	//检查是否选择客服人员
	if(!$('input:radio[name="toopenid"]').is(':checked')){
		showTip("请选择客服人员",1000,function(){},'fydDIV');
		return false;
	}
	if($('#messagetext').val().length<5 || $('#messagetext').val().length>100){
		showTip("留言信息长度应该在5-100位之间",2000,function(){},'fydDIV');
		return false;
	}
	if($('#name').val().length<2 || $('#name').val().length>10){
		showTip("称呼长度应该在2-10位之间",2000,function(){},'fydDIV');
		return false;
	}
	
	if(!(/^1(3|4|5|7|8)\d{9}$/.test($('#mobile').val()))){
		showTip("联系电话格式不正确",2000,function(){},'fydDIV');
		return false;
	}
	//检查手机
	/*if(!checkMobile()){
		return false;	
	}*/
	return true;
}

$(document).ready(function() {
	// 使用 jQuery异步提交表单
	$('#contactForm').submit(function() {
		if(!checkData()) return false;
		jQuery.ajax({
			url:serverroot +'/leavemessage/doAdd.do',
			data:$('#contactForm').serialize(),
			type:"POST",
			datatype : "json",
			beforeSend:function(){

			},
			success:function(data){
				if (data.success) {
					showTip("留言发送成功",2000,function(){window.location.href = serverroot+"/jlb/tovip.html";},'fydDIV');
				}else{
					showTip("留言发送失败",2000,refresh,'fydDIV');
				}
			}
		});
		return false;
	});
});


