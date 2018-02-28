    var vm=new Vue({
      el:"#app",
      data:{
          ShowTongzhi:false,
          ShowSendTime:false,
          showmodal:false,
          pagesize:5,
          selectednav:0,//选择的类别排序ID
          selectedcateid:'',//选择的模板类别ID
          selectedrow:'',//选择的模板ID
          selectedname:'',//选择的模板名称
          messagetxt:'',//选择的模板内容
          categroylist:[],
          tabconlist:[],
          selectlist:[],
          name:'',
          service:'',
          tel:'',
          messtxt:['','','','']//内容数组，供预览使用
        },
        created:function(){
        	var self = this;
            		$.ajax({
					url: serverroot+'/dictinfo/back/dictinfo/getCategroy.do',
					dataType: "text",
					data: {typeid:4},
					success: function(data){
					var data = JSON.parse(data);
					   if(data.success){
						   self.categroylist=data.obj;
						   //alert(this.categroylist);
					   } else {
						  alert("11");
					   }
					}
				});
				$.ajax({
					url: serverroot+'/messageHelp/back/messagetemplate/getAllTemplate.do',
					dataType: "text",
					data: {},
					success: function(data){
						var data = JSON.parse(data);
					   if(data.success){
						   self.tabconlist=data.obj;
						   self.selectlist=self.tabconlist[self.selectednav].templatelist;
						 //  alert(this.tabconlist);
					   } else {
						  alert("22");
					   }
					}
				});	 
    },
      methods:{
        fn:function(i){
          if(i==0){
            this.ShowTongzhi=true;
          }else{
            this.ShowTongzhi=false;
          }
        },
        sendType:function(i){
          if(i==0){
            this.ShowSendTime=false;
          }else{
            this.ShowSendTime=true;
          }
        },
        openmodal:function(){
        	this.showmodal=true;
        }
      },
      components: {  
            tablist: {    //这个无返回值，不会继续派发  
                template: "#tablist",  
                props: ['pagesize', 'selectednav','selectedrow','selectedname','messagetxt','showmodal','selectedcateid','categroylist','tabconlist','selectlist'],
                data: function () {  
                    
                },  
                methods: {  
					setok:function(){  
                       this.showmodal=false;
                    },
                    setcancel:function(){  
                        this.messagetxt =''  ;
                      this.selectedname ='' ;
                      this.selectedcateid ='' ;
                      this.showmodal = false;
                    },
                    closemodal:function(){  
                       this.messagetxt =''  ;
                      this.selectedname ='' ;
                      this.selectedcateid  = '';
                      this.showmodal = false;
                    }, 
                    selectnav:function(i){  
                       this.selectednav = i;
                       //this.selectedcateid = this.categroylist[i].dictcode;
                       this.selectlist = this.tabconlist[i].templatelist;
                    },
                    selectrow:function(i){  
                    	let data = {a:this.selectlist[i].templatecontent,b:this.selectlist[i].templatename,c:this.selectlist[i].id,d:this.selectednav};
                    	vm.$emit('listenToChildEvent',data);
                    	//alert(this.selectednav);
                    	this.selectlist = this.tabconlist[this.selectednav].templatelist;
                    	//alert(this.selectlist);
                    	//this.selectlist[i].templatecontent,this.selectlist[i].templatename,this.selectlist[i].id
                    },
                    changgroup:function(){  
                       tabconlist[selectednav]=null;
                    }
                }  
            }  
        }  
    });
    
    vm.$on('listenToChildEvent', function (data) {
    	vm.$data.messagetxt=data.a;
    	vm.$data.messtxt=data.a.split("{***}");
    	vm.$data.selectedname=data.b;
    	vm.$data.selectedrow=data.c;
    	vm.$data.selectednav=data.d;
    	vm.$data.selectlist=vm.$data.tabconlist[data.d].templatelist;
    })
 