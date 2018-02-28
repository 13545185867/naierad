
$.ajaxSetup({contentType:"application/x-www-form-urlencoded; charset=utf-8",type:"post",dataType:"json",async:false,timeout:30000,complete:function(a){},error:function(a,c,b){}});

var salesman = '';
$(function(){
	//获得推荐人
	//var params = getUrlVars();
	//salesman = decodeURI(params[params[0]]);
	var url = window.location.href;
	if(url != 'http://club.meifang.com/register.html'){
		if(url.charAt(url.length-1) == '/'){
			url = url.substr(0,url.length-1);
		}
		var lastIndex = url.lastIndexOf("/");
		salesman = url.substr(lastIndex+1);

		//$(".gengduo2").hide();  //隐藏登录		
	}else{
		salesman = '';
	}

	$.formValidator.initConfig({autotip:true,formid:"myform",onerror:function(msg){}});

	$("#username").formValidator({onshow:"请输入姓名",onfocus:"姓名应该为2-10位之间"}).inputValidator({min:2,max:10,onerror:"姓名应该为2-10位之间"});
	
	$("#mobile").formValidator({onshow:"请输入手机号码",onfocus:"请输入手机号码"}).inputValidator({min:1,max:11,onerror:"请输入正确的手机号码"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机格式不正确"}).ajaxValidator({
	    type : "post",
		url : serverroot+"/jlb/checkMobile.html",
		datatype : "json",
		success : function(data){
            if( data.success) {
                $("#GetVerify").show();
                return true;
			} else {
					$("#GetVerify").hide();
                return false;
			}
		},
		buttons: $("#dosubmit"),
		onerror : "手机号码已经注册",
		onwait : "查询中..."
	});

	$(":checkbox[name='protocol']").formValidator({tipid:"protocoltip",onshow:"请阅读协议",onfocus:"请阅读协议"}).inputValidator({min:1,onerror:"请阅读协议"});

	$('#myform').submit(function(){
				
		var username = $('#username').val();
		var mobile = $("#mobile").val();
		var mobile_verify = $("#mobile_verify").val();
		
		if(username == '' || mobile == '' || mobile_verify == ''){
			return false;
		}	

		if(!$(":checkbox").is(':checked')) {
			showTip("请阅读协议",2000,function(){},'fydDIV');
			return false;
		}

        ajaxSubmit(username,mobile,mobile_verify);
        return false;
    });

});

function show_protocol() {
	art.dialog({lock:false,title:'会员注册协议',id:'protocoliframe', iframe:'http://club.meifang.com/protocol.php',width:'350',height:'410',yesText:'同意'}, function(){
		$("#protocol").attr("checked",true);
	});
}

function ajaxSubmit(username,mobile,mobile_verify){
	$("#dosubmit").attr("disabled",true);   //
	$("#dosubmit").attr("value","注册中"); 
	var devicetype = getDeviceType(); 
	$.ajax({ 
        type : "post", 
        timeout:30000,
        url : serverroot + "/jlb/doreg.html", 
        data : {username:username,mobile:mobile,verify:mobile_verify,salesman:salesman,devicetype:devicetype}, 
        dataType: "json",
        async : false, 
        success : function(data,textStatus){
        	if(data.success){
    			showTip("恭喜您，注册成功",3000,function(){window.location.href = serverroot+"/jlb/tomember.html";},'fydDIV');			
    		}else{
    			showTip("很抱歉，注册失败",3000,function(){},'fydDIV');
    		}
    		$("#dosubmit").attr("disabled",false);
    		$("#dosubmit").attr("value","注册"); 
        } 
    }); 
};

function getUrlVars(){
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?')+1).split('&');
	for(var i = 0; i < hashes.length; i++) {
	hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}

function getDeviceType(){   
	var devicetype = 'pc'; 
	var userAgentInfo = navigator.userAgent;  
	var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");    
	var flag = true;    
	for (var v = 0; v < Agents.length; v++) {    
		if (userAgentInfo.indexOf(Agents[v]) > 0) { devicetype = Agents[v]; break; }    
	} 	
	return devicetype;    
}  

var times;
var isinerval;

function get_verify() {
	$("#GetVerify").attr("disabled", true);
	var mobile = $("#mobile").val();
	var messageuuid = $("#messageuuid").val();
	var partten = /^1[3-9]\d{9}$/;
	if(!partten.test(mobile)){
		showTip("请输入正确的手机号码",2000,function(){},'fydDIV');
		$('#mobile').focus();
		$("#GetVerify").attr("disabled", false);
		return false;
	}
	$.post(serverroot+"/messageLog/sendMessge.html",{phonenum:mobile,messageuuid:messageuuid},function(data){if(data.success) {times = 60;$("#GetVerify").attr("disabled", true);isinerval = setInterval("CountDown()", 1000);} else{showTip("短信验证码发送失败",2000,function(){},'fydDIV');$("#GetVerify").attr("disabled", false);}});
}
function CountDown() {
	if (times < 1) {
		$("#GetVerify").html("获取验证码").attr("disabled", false);
		clearInterval(isinerval);
		return;
	}
	$("#GetVerify").html(times+"秒后重发");
	times--;
}

function setCookie(c_name,value,expiredays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate()+expiredays);
document.cookie=c_name+ "=" +escape(value)+
((expiredays==null) ? "" : ";expires="+exdate.toGMTString()+";path=/");
}
if(window.location.href != 'http://club.meifang.com/register.html'){
setCookie('registerUrl',window.location.href,365);
}

