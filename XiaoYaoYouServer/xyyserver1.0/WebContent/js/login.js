Ext.onReady(function(){

    Ext.QuickTips.init();
	
 
    var NETSJ_Login = new Ext.form.FormPanel( {
		renderTo:Ext.getBody(),
        bodyStyle:'padding:10px 10px 0',
		bodyBorder:false,
		width:300,
		height:140,
		x:800,
		y:50,
        frame:true,//边框
		resizable:false,
		plain:true,
		//preventHeader:true,
		title:'登录',
        
        defaultType: 'textfield',
        defaults: {
            anchor: '100%'
        },

        items: [{
            fieldLabel: '<b>用户名</b>',
			/* @author fan */
			id: 'log_username',
            name: 'UserName',
            allowBlank:false
        },{
            fieldLabel: '<b>密码</b>',
			/* @author fan */
            id: 'log_passwd',
			name: 'Password',
			inputType:'password',
			allowBlank:false,
			/* @author fan */
			listeners:{
        	   		//按回车键登录
        	   		specialKey:function(field,e){
			   			if (e.getKey() == Ext.EventObject.ENTER && this.getValue().length > 0) {  
			               login();
			            }
			   		}
                }
        }],
		buttonAlign:'center',
        buttons: [{
			xtype:'button',
            text: '<font size="2">登录</font>',
			scope:this,
			width:60,
			height:30,
			
			handler:login
        },{
			xtype:'button',
            text: '<font size="2">重置</font>',
			scope:this,
			width:60,
			height:30,
			handler:reset
        }]
    });
	
	NETSJ_Login.render(document.body);
	
	//load in

	function login() {
		
		/* @author fan */
    	var uname = Ext.getCmp('log_username').getValue();
    	var password = Ext.getCmp('log_passwd').getValue();

		NETSJ_Login.form.submit ({
			clientValidation:true,
			params : {loginData:Ext.encode({
	        			username:uname,
	        			password:password})
	                }, 
			waitMsg:'正在登陆系统请稍候...',
			url:'login.action',//后台地址
			method:'POST',
			success:function(form, action) {
				//Ext.Msg.alert('成功提示', 'success!');
				
				if (action.result.msg=='ok') {
					window.location.href='warn1.html';
				} else {
					Ext.Msg.alert('登陆错误',action.result.msg);
				}
				
				//window.location.href = 'warn1.html';
			},
			failure:function(form, action) {
				//alert('fail!');alert(action.result);
				
				switch (action.failureType) {    
                        case Ext.form.Action.CLIENT_INVALID:    
                            Ext.Msg.alert('错误提示', '表单数据非法请核实后重新输入！');    
                            break;    
                        case Ext.form.Action.CONNECT_FAILURE:    
                            Ext.Msg.alert('错误提示', '网络连接异常！');    
                            break;    
                        case Ext.form.Action.SERVER_INVALID:    
                            Ext.Msg.alert('错误提示', "您的输入用户信息有误，请核实后重新输入！");    

				}
				
				/* @author debug  */
				//Ext.Msg.alert('错误提示', uname);
				//window.location.href = 'warn1.html';
			
			}
		});
		
		
		//NETSJ_Login.form.submit();
		
	}

	// reset
	function reset() {
		
		NETSJ_Login.form.reset();
	}
	
});
