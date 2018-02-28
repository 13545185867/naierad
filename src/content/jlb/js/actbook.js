$.ajaxSetup({contentType:"application/x-www-form-urlencoded; charset=utf-8",type:"post",dataType:"json",async:false,timeout:30000,complete:function(a){},error:function(a,c,b){}});

$(document).ready(function() {
	$('#baomingForm').submit(function() {
		if(!checkdata()) return false;
		jQuery.ajax({
			url:serverroot + '/jlb/dobook.html',
			data:$('#baomingForm').serialize(),
			type:"POST",
			datatype : "json",
			beforeSend:function(){

			},
			success:function(data){
				if (data.success) {
					parent.showTip("报名成功",2000,refresh,'fydDIV');
				}else{
					parent.showTip("报名失败",2000,refresh,'fydDIV');
				}
			}
		});
		return false;
	});
	
	$(".jlbhd-grxx img").click(function(){
		$(".footer").css("display","none");
		openPhotoSwipe();
    });
	

	$('#baomingForm1').submit(function() {
		if(nomember == '1'){
			showTip("非正式会员不能报名活动",2000,refresh,'fydDIV');
			return false;
		}
		var submitType = $("#dosubmit").attr("data");
		if(submitType == 'ok'){
			
			inputActivityInfo();
			
		}else if(submitType == 'cancel'){
			if(window.confirm('你确定要取消报名吗？')){	
				jQuery.ajax({
					url:serverroot+'/jlb/cancelbook.html',
					data:$('#baomingForm1').serialize(),
					type:"POST",
					datatype : "json",
					beforeSend:function(){ 
						
					},
					success:function(data){
						if (data.success) {
							showTip("取消报名成功",3000,refresh,'fydDIV');
						}else{
							showTip("取消报名失败",3000,refresh,'fydDIV');
						}
					}
				});
			}
		}
		return false;
	});
	

});
function checkdata(){
	var familynum = $("#familynum").val();
	var reg = /^(?!0\d+)\d+$/;
	if(!reg.test(familynum)){
		parent.showTip("参加总人数错误",1000,function(){},'fydDIV');
		return false;
	}

	if(familynum < 1){
		parent.showTip("参加总人数至少1人",1000,function(){},'fydDIV');
		return false;
	}

	if(familynum > 5){
		parent.showTip("参加总人数不能超过5人",1000,function(){},'fydDIV');
		return false;
	}

	return true;
}

 
function show_dialog(title,id,iframeUrl,func) {
	art.dialog({lock:false,title:title,id:id, iframe:iframeUrl,width:'300',height:'190',yesText:'确定'}, func);
}

/*输入报名活动的附加信息*/
function  inputActivityInfo(){
	show_dialog('报名活动','baomingForm',serverroot+'/jlb/book.html?id='+id);
}

jQuery.ajax({
	url:serverroot + '/jlb/addViewCount.html',
	data:{"count":count,"id":id},
	type:"POST",
	datatype : "json",
	beforeSend:function(){

	},
	success:function(data){
		//if (data.success) {
		//	alert(1);
		//}else{
		//	alert(0);
		//}
	}
});
