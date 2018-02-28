$.ajaxSetup({contentType:"application/x-www-form-urlencoded; charset=utf-8",type:"post",dataType:"json",async:false,timeout:30000,complete:function(a){},error:function(a,c,b){}});

$(document).ready(function() {
	// 使用 jQuery异步提交表单
	$('#regForm').submit(function() {
		if(!checkData()) return false;
		$.ajax({
			url:serverroot +'/jlb/doupdateInfo.html',
			data:$('#regForm').serialize(),
			type:"POST",
			datatype : "json",
			async:false,
			beforeSend:function(){ 
			},
			success:function(data){
				if (data.success) {
					showTip("修改信息成功",2000,function(){window.location.href = serverroot+"/jlb/tovip.html";},'fydDIV');
				}else{
					showTip("修改信息失败",2000,refresh,'fydDIV');
				}
			}
		});
		return false;
	});
});

function checkData(){
	//检查性别
	if(!$('input:radio[name="sex"]').is(':checked')){
		showTip("请选择性别",1000,function(){},'fydDIV');
		return false;
	}
	
	//检查手机
	/*if(!checkMobile()){
		return false;	
	}*/
	return true;
}
