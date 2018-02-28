function showTip(content,time,func,tipId){
	var html = ' <div class="fwmyd" ><p class="bgyj">'+content+'</p> </div>';
	$("#"+tipId).html(html);
	$("#"+tipId).show();

	if(!func){
		func = function(){};
	}

	setTimeout("hideTip("+func+",'"+tipId+"')",time);
}

function hideTip(func,tipId){	
	$("#"+tipId).hide();
	if(func){
		func();
	}
}

function refresh(){
	location.replace(location);
}

