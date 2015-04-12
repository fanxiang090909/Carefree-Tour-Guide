
Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', '../examples/ux');

Ext.require('Ext.chart.*');
Ext.require(['Ext.Window', 'Ext.fx.target.Sprite', 'Ext.layout.container.Fit', 'Ext.window.MessageBox']);

Ext.onReady(function(){
    
	Ext.QuickTips.init();
	
	  Ext.define("MyData", {
        extend : "Ext.data.Model",
        fields : ["id", {
            name : "time",
            mapping : "time",
			type : "date",
            dateFormat : 'Y-m-dTH:i:s'
        }, {
            name : "spot_id",
            mapping : "spot_id",
			type: 'int'
        }, {
            name : "spot_name",
			mapping : "spot_name",
            type : "string"
        }]
    });
    
    var store = Ext.create("Ext.data.Store", {
       		model : "MyData",
			
       	 	proxy : {
            	type : "ajax",
            	url : "Search_traceroute2.action",
            	reader : {
                	type : "json",
                	root : "route"
            	}
        	}
    	});

   
    Ext.define("tournum", {
        extend : "Ext.data.Model",
        fields : ["id1",{
            name : "spot_id",
            mapping : "spot_id",
			type : "int"
        }, {
            name : "spot_name",
            mapping : "spot_name",
			type: 'string'
        }, {
            name : "num",
			mapping : "num",
            type : "int"
        }, , {
            name : "maxnum",
			mapping : "maxnum",
            type : "int"
        }
		]
    });
    
    var store1 = Ext.create("Ext.data.Store", {
        model : "tournum",
        proxy : {
            type : "ajax",
            url : "refresh_touristnum.action",
            reader : {
                type : "json",
                root : "touristnum_list"
            }
        },
        autoLoad : true
    });
    var store1;
    function refresh() {
    	
    	var storeTemp = Ext.create("Ext.data.Store", {
            model : "tournum",
            proxy : {
                type : "ajax",
                url : "refresh_touristnum.action",
                reader : {
                    type : "json",
                    root : "touristnum_list"
                }
            }
            //autoLoad : true
        });
    	
    	store1.loadData(storeTemp);
    	store1.load();
    	
    }
    
    // every 10s to request the num and the maxnum of spot.
	setInterval(refresh, 30000);
	//store1.load();
	/*
	// 这段是用于静态内容（景点和人数）的显示
	var touristNum = [
		['五龙桥', 1,200],
	    ['鱼石', 12,150],
		['五里关', 165,250],
		['石门', 87,250],
		['莎萝坪', 91,250],
		['毛女洞', 32,250],
		['青柯坪', 54,250],
		['回心石', 65,250],
		['千尺幢', 11,250],
		['百尺峡', 90,250],
		['群仙观', 111,250],
		['北峰（云台峰）', 72,150],
		['索道上站', 18,150],
		['索道下站', 44,150],
		['智取华山路', 22,150],
		['东山门', 33,150],
		['聚仙台', 77,150],
		['天梯', 138,150],
		['苍龙岭', 11,150],
		['五云峰', 22,150],
		['金锁关', 13,150],
		['三峰路口', 55,150],
		['中锋（玉女峰）', 66,150],
		['鹞子翻身', 77,150],
		['下棋亭', 88,150],
		['东峰（朝阳峰）', 99,150],
		['南天门', 100,150],
		['长空栈道', 33,150],
		['南峰（落雁峰）', 45,150],
		['震岳宫', 66,150],
		['莲花坪', 98,150],
		['西峰顶（莲花峰）', 87,150],
		['舍身崖', 111,150]
	];
	
	
	
	var store1 = Ext.create('Ext.data.ArrayStore', {
        fields: [
           {name: 'spot', type:'string'},
		   {name: 'tourNum', type:'int'},
		   {name: 'maxNum', type:'int'}
        ],
        data: touristNum
    });
	*/
	
	
	// 弹出框
	Ext.define('Ext.ux.window.Notification', {
		extend: 'Ext.window.Window',
		alias: 'widget.uxNotification',

		cls: 'ux-notification-window',
		autoHide: true,
		autoHeight: true,
		plain: false,
		draggable: false,
		shadow: false,
		focus: Ext.emptyFn,

		// For alignment and to store array of rendered notifications. Defaults to document if not set.
		manager: null,

		useXAxis: false,

		// Options: br, bl, tr, tl, t, l, b, r
		position: 'br',

		// Pixels between each notification
		spacing: 6,

		// Pixels from the managers borders to start the first notification
		paddingX: 30,
		paddingY: 10,

		slideInAnimation: 'easeIn',
		slideBackAnimation: 'bounceOut',
		slideInDuration: 1500,
		slideBackDuration: 1000,
		hideDuration: 500,
		autoHideDelay: 7000,
		stickOnClick: true,
		stickWhileHover: true,

		// Private. Do not override!
		isHiding: false,
		readyToHide: false,

		// Caching coordinates to be able to align to final position of siblings being animated
		xPos: 0,
		yPos: 0,

		statics: {
			defaultManager: {
				el: null
			}
		},
		
		
		getXposAlignedToManager: function () {
		var me = this;

		var xPos = 0;

		// Avoid error messages if the manager does not have a dom element
		if (me.manager && me.manager.el && me.manager.el.dom) {
			if (!me.useXAxis) {
				// Element should already be aligned verticaly
				return me.el.getLeft();
			} else {
				// Using getAnchorXY instead of getTop/getBottom should give a correct placement when document is used
				// as the manager but is still 0 px high. Before rendering the viewport.
				if (me.position == 'br' || me.position == 'tr' || me.position == 'r') {
					xPos += me.manager.el.getAnchorXY('r')[0];
					xPos -= (me.el.getWidth() + me.paddingX);
				} else {
					xPos += me.manager.el.getAnchorXY('l')[0];
					xPos += me.paddingX;
				}
			}
		}

		return xPos;
	},

	getYposAlignedToManager: function () {
		var me = this;

		var yPos = 0;

		// Avoid error messages if the manager does not have a dom element
		if (me.manager && me.manager.el && me.manager.el.dom) {
			if (me.useXAxis) {
				// Element should already be aligned horizontaly
				return me.el.getTop();
			} else {
				// Using getAnchorXY instead of getTop/getBottom should give a correct placement when document is used
				// as the manager but is still 0 px high. Before rendering the viewport.
				if (me.position == 'br' || me.position == 'bl' || me.position == 'b') {
					yPos += me.manager.el.getAnchorXY('b')[1];
					yPos -= (me.el.getHeight() + me.paddingY);
				} else {
					yPos += me.manager.el.getAnchorXY('t')[1];
					yPos += me.paddingY;
				}
			}
		}

		return yPos;
	},

	getXposAlignedToSibling: function (sibling) {
		var me = this;

		if (me.useXAxis) {
			if (me.position == 'tl' || me.position == 'bl' || me.position == 'l') {
				// Using sibling's width when adding
				return (sibling.xPos + sibling.el.getWidth() + sibling.spacing);
			} else {
				// Using own width when subtracting
				return (sibling.xPos - me.el.getWidth() - me.spacing);
			}
		} else {
			return me.el.getLeft();
		}

	},

	getYposAlignedToSibling: function (sibling) {
		var me = this;

		if (me.useXAxis) {
			return me.el.getTop();
		} else {
			if (me.position == 'tr' || me.position == 'tl' || me.position == 't') {
				// Using sibling's width when adding
				return (sibling.yPos + sibling.el.getHeight() + sibling.spacing);				
			} else {
				// Using own width when subtracting
				return (sibling.yPos - me.el.getHeight() - sibling.spacing);
			}
		}
	},

	getNotifications: function (alignment) {
		var me = this;

		if (!me.manager.notifications[alignment]) {
			me.manager.notifications[alignment] = [];
		}

		return me.manager.notifications[alignment];
	},

	beforeShow: function () {
		var me = this;

		// 1.x backwards compatibility
		if (Ext.isDefined(me.corner)) {
			me.position = me.corner;
		}
		if (Ext.isDefined(me.slideDownAnimation)) {
			me.slideBackAnimation = me.slideDownAnimation;
		}
		if (Ext.isDefined(me.autoDestroyDelay)) {
			me.autoHideDelay = me.autoDestroyDelay;
		}
		if (Ext.isDefined(me.slideInDelay)) {
			me.slideInDuration = me.slideInDelay;
		}
		if (Ext.isDefined(me.slideDownDelay)) {
			me.slideBackDuration = me.slideDownDelay;
		}
		if (Ext.isDefined(me.fadeDelay)) {
			me.hideDuration = me.fadeDelay;
		}

		// 'bc', lc', 'rc', 'tc' compatibility
		me.position = me.position.replace(/c/, '');

		switch (me.position) {
			case 'br':
				me.paddingFactorX = -1;
				me.paddingFactorY = -1;
				me.siblingAlignment = "br-br";
				if (me.useXAxis) {
					me.managerAlignment = "bl-br";
				} else {
					me.managerAlignment = "tr-br";
				}
				break;
			case 'bl':
				me.paddingFactorX = 1;
				me.paddingFactorY = -1;
				me.siblingAlignment = "bl-bl";
				if (me.useXAxis) {
					me.managerAlignment = "br-bl";
				} else {
					me.managerAlignment = "tl-bl";
				}
				break;
			case 'tr':
				me.paddingFactorX = -1;
				me.paddingFactorY = 1;
				me.siblingAlignment = "tr-tr";
				if (me.useXAxis) {
					me.managerAlignment = "tl-tr";
				} else {
					me.managerAlignment = "br-tr";
				}
				break;
			case 'tl':
				me.paddingFactorX = 1;
				me.paddingFactorY = 1;
				me.siblingAlignment = "tl-tl";
				if (me.useXAxis) {
					me.managerAlignment = "tr-tl";
				} else {
					me.managerAlignment = "bl-tl";
				}
				break;
			case 'b':
				me.paddingFactorX = 0;
				me.paddingFactorY = -1;
				me.siblingAlignment = "b-b";
				me.useXAxis = 0;
				me.managerAlignment = "t-b";
				break;
			case 't':
				me.paddingFactorX = 0;
				me.paddingFactorY = 1;
				me.siblingAlignment = "t-t";
				me.useXAxis = 0;
				me.managerAlignment = "b-t";
				break;
			case 'l':
				me.paddingFactorX = 1;
				me.paddingFactorY = 0;
				me.siblingAlignment = "l-l";
				me.useXAxis = 1;
				me.managerAlignment = "r-l";
				break;
			case 'r':
				me.paddingFactorX = -1;
				me.paddingFactorY = 0;
				me.siblingAlignment = "r-r";
				me.useXAxis = 1;
				me.managerAlignment = "l-r";
				break;
			}

		if (typeof me.manager == 'string') {
			me.manager = Ext.getCmp(me.manager);
		}

		// If no manager is provided or found, then the static object is used and the el property pointed to the body document.
		if (!me.manager) {
			me.manager = me.statics().defaultManager;

			if (!me.manager.el) {
				me.manager.el = Ext.getBody();
			}
		}
		
		if (typeof me.manager.notifications == 'undefined') {
			me.manager.notifications = {};
		}

		if (me.stickOnClick) {
			if (me.body && me.body.dom) {
				Ext.fly(me.body.dom).on('click', function () {
					me.cancelAutoHide();
					me.addCls('notification-fixed');
				}, me);
			}
		}

		me.el.hover(
			function () {
				me.mouseIsOver = true;
			},
			function () {
				me.mouseIsOver = false;
			},
			me
		);
		
		if (me.autoHide) {
			me.task = new Ext.util.DelayedTask(me.doAutoHide, me);
			me.task.delay(me.autoHideDelay);
		}

		var notifications = me.getNotifications(me.managerAlignment);

		if (notifications.length) {
			me.el.alignTo(notifications[notifications.length - 1].el, me.siblingAlignment, [0, 0]);
			me.xPos = me.getXposAlignedToSibling(notifications[notifications.length - 1]);
			me.yPos = me.getYposAlignedToSibling(notifications[notifications.length - 1]);
		} else {
			me.el.alignTo(me.manager.el, me.managerAlignment, [(me.paddingX * me.paddingFactorX), (me.paddingY * me.paddingFactorY)]);
			me.xPos = me.getXposAlignedToManager();
			me.yPos = me.getYposAlignedToManager();
		}

		Ext.Array.include(notifications, me);

		me.stopAnimation();
		
		me.el.animate({
			to: {
				x: me.xPos,
				y: me.yPos,
				opacity: 1
			},
			easing: me.slideInAnimation,
			duration: me.slideInDuration,
			dynamic: true
		});

	},

	slideBack: function () {
		var me = this;

		var notifications = me.getNotifications(me.managerAlignment);
		var index = Ext.Array.indexOf(notifications, me)

		// Not animating the element if it already started to hide itself or if the manager is not present in the dom
		if (!me.isHiding && me.el && me.manager && me.manager.el && me.manager.el.dom && me.manager.el.isVisible()) {

			if (index) {
				me.xPos = me.getXposAlignedToSibling(notifications[index - 1]);
				me.yPos = me.getYposAlignedToSibling(notifications[index - 1]);
			} else {
				me.xPos = me.getXposAlignedToManager();
				me.yPos = me.getYposAlignedToManager();
			}

			me.stopAnimation();

			me.el.animate({
				to: {
					x: me.xPos,
					y: me.yPos
				},
				easing: me.slideBackAnimation,
				duration: me.slideBackDuration,
				dynamic: true
			});
		}
	},

	cancelAutoHide: function() {
		var me = this;

		if (me.autoHide) {
			me.task.cancel();
			me.autoHide = false;
		}
	},

	doAutoHide: function () {
		var me = this;

		/* Delayed hiding when mouse leaves the component.
		   Doing this before me.mouseIsOver is checked below to avoid a race condition while resetting event handlers */
		me.el.hover(
			function () {
			},
			function () {
				me.hide();
			},
			me
		);
		
		if (!(me.stickWhileHover && me.mouseIsOver)) {
			// Hide immediately
			me.hide();
		}
	},


	hide: function () {
		var me = this;

		// Avoids restarting the last animation on an element already underway with its hide animation
		if (!me.isHiding && me.el) {

			me.isHiding = true;

			me.cancelAutoHide();
			me.stopAnimation();

			me.el.animate({
				to: {
					opacity: 0
				},
				easing: 'easeIn',
				duration: me.hideDuration,
				dynamic: false,
				listeners: {
					afteranimate: function () {
						if (me.manager) {
							var notifications = me.getNotifications(me.managerAlignment);
							var index = Ext.Array.indexOf(notifications, me);
							if (index != -1) {
								Ext.Array.erase(notifications, index, 1);

								// Slide "down" all notifications "above" the hidden one
								for (;index < notifications.length; index++) {
									notifications[index].slideBack();
								}
							}
						}

						me.readyToHide = true;
						me.hide();
					}
				}
			});
		}

		// Calling parents hide function to complete hiding
		if (me.readyToHide) {
			me.isHiding = false;
			me.readyToHide = false;
			me.removeCls('notification-fixed');
			this.callParent(arguments);
		}
	}

});

	
	
function createWin() {
		
		new Ext.create('widget.uxNotification', {
	
			title: '求救信息',
			corner: 'tr',
			height: 150,
			width: 200,
			closable: true,
			stickOnClick: true,
			autoHide:false,
					
			items: [{
				xtype:'displayfield',
				id: 'helpName',
				fieldLabel: '用户名',
				labelWidth: 60,
			},{
				xtype:'displayfield',
				id: 'helpPhone',
				fieldLabel: '手机号',
				labelWidth: 60,
			},{
				xtype:'displayfield',
				id: 'helpTime',
				type : "date",
				dateFormat : 'Y-m-d H:i:s',
				fieldLabel: '求救时间',
				labelWidth: 60,
			}]
		}).show();
	
	
	}

	setInterval(fresh, 10000);

	function fresh() {
	Ext.Ajax.request ({
			clientValidation:true,
			url:'refresh_touristnum2.action',//后台地址
			method:'POST',
			success : function(request,options) {
		
				var json = Ext.JSON.decode(request.responseText);
				var helpInfo = json.alarm_msgs;
				
				for(var i = 0; i < helpInfo.length; i++){
					var jsonobj = helpInfo[i];
		
					var name = jsonobj.username;
					var phone = jsonobj.phone;
					var time = jsonobj.time;
					
					createWin();
					
					Ext.getCmp('helpName').setValue(name);
					Ext.getCmp('helpPhone').setValue(phone);
					Ext.getCmp('helpTime').setValue(time);
				
				}
				
			}	
	});
	}

	

    var info = new Ext.form.FormPanel( {
	
	name:'mainPanel',
	renderTo:Ext.getBody(),
	frame:true,
	//layout: 'column',
	bodyPadding: 5, 
	border: true,
	width: 550,
    height: 700,
	items:[
	{
		xtype:'tabpanel',
		bodyPadding: 5, 
		height:685,
		activeTab: 3,
		layoutOnTabChange:true ,
		items: [
		{
        
			xtype: 'panel',
			title: '发布信息',
			
			bodyStyle:'padding:10px 10px 0',
			items: [
			{
				name: 'rangeInfo',
				fieldLabel: '<font size = "2">请选择接受信息的游客范围</font>',
				id: 'range',
				labelWidth : 200, 
				xtype:'checkboxgroup',
				width:500,
				height: 140,
				columns: 2,
				//vertical: true,
				allowBlank: false, 			
				items: [  
				{
					boxLabel: '西山门到五里关路段', 
					inputValue: '01'
				},    
				{
					boxLabel: '五里关到回心石路段', 
					inputValue: '02'
				},    
				{
					boxLabel: '回心石到群仙观路段', 
					inputValue: '03'
				},    
				{
					boxLabel: '北峰区域', 
					inputValue: '04'
				},
				{
					boxLabel: '智取华山路上段', 
					inputValue: '05'
				},
				{
					boxLabel: '智取华山路下段', 
					inputValue: '06'
				},
				{
					boxLabel: '黄甫峪进山公路路段', 
					inputValue: '07'
				},
				{
					boxLabel: '北峰到五云峰路段', 
					inputValue: '08'
				},
				{
					boxLabel: '五云峰到三峰路口路段', 
					inputValue: '09'
				},
				{
					boxLabel: '中峰区域',  
					inputValue: '10'
				},
				{
					boxLabel: '南峰区域', 
					inputValue: '11'
				},
				{
					boxLabel: '东峰区域', 
					inputValue: '12'
				},
				{
					boxLabel: '西峰区域', 
					inputValue: '13'
				}] 
				
			},
			
			{
				xtype: 'displayfield',
				html: '<br><br>&nbsp;<br>'
			},
			
			{
				xtype:'textarea',
				name: 'warnInfo',
				id: 'warnContent',
				fieldLabel: '<font size = "2">请输入发布的信息</font>',
				allowBlank: false, 
				labelWidth : 200, 
				width:500,
				height:250,
				regex:/[\u4e00-\u9fa5]/,
				regexText:'请输入长度0-1024的中文字符',
				maxLength:64,
				emptyText:'请输入长度0-1024的中文字符'
			}],
			buttonAlign:'right',
			buttons: [{
				text: '发布',
				cls: 'buttonCss',
				height: 20,
				handler:publish
			},{
				text: '清除',
				scope : this,
				handler:cancel
			}],
			html:'<br><font size = "2">点击"发布"后，信息将发送至园区内所选择区域的游客!</font>'
			
		},
		{
        
			title: '搜救定位',
			xtype: 'panel',
			bodyStyle:'padding:20px 10px 0',
		
			items: [{
				xtype: 'panel',
				//bodyStyle:'padding:20px 10px 0',
				border:false,
				items: [{
					xtype:'fieldset',
					title: '查询',
					collapsible: true,
					defaultType: 'textfield',
					layout: 'anchor',
					defaults: {
						anchor: '100%'
					},
					items :[{
						fieldLabel: '请输入游客手机号',
						labelSeparator: '',
						name: 'phoneNum',
						id: 'phoneNum',
						maxLength:11,
						minLength:11,
						emptyText:'请输入11位数的电话号码'
					}]
				
				}],
				buttons: [{
					text: '查询踪迹',
					handler: findPosition
				},{
					text: '地图显示',
					handler:showTrace
					
				}]
				
			},{
			
				xtype:'fieldset',
				title: '游客信息',
				
				collapsible: true,
				collapsed: true,
				id: 'infoTour',
				//defaultType: 'textfield',
				/*
				layout: 'anchor',
				*/
				defaults: {
					anchor: '100%'
				},
				
				items :[{
					xtype:'displayfield',
					name: 'tourName',
					id: 'tourName',
					fieldLabel: '姓名'
					
				},{
					xtype:'displayfield',
					name: 'tourPhone',
					id: 'tourPhone',
					fieldLabel: '电话'
				
				},{
					xtype:'displayfield',
					name: 'tourSex',
					id: 'tourSex',
					fieldLabel: '性别'
				
				},{
					xtype:'displayfield',
					name: 'tourBelong',
					id: 'tourBelong',
					fieldLabel: '籍贯'
				},{
					xtype: 'displayfield',
					html: '<br>该游客所经过的位置',
				},
				{
				
					xtype:'gridpanel',
					name: 'trace',
					id: 'trace',
					labelWidth : 200, 
					store: store,
					columnLines: true,
					width: 400,
					layout:'column',
					stateId: 'stateGrid',
					columns: [
					{
						text     : '时间',
						//flex     : 1,
						width    : 200,
						sortable : true,
						//dataIndex: 'lastTime',
						dataIndex: 'time',
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
					},
					{
						text     : '位置',
						width    : 200,
						sortable : true,
						// dataIndex: 'position'
						dataIndex: 'spot_name'
					}]
        
				}]
			}],
			buttonAlign:'left',
			buttons: [{
				text: '清除标记',
				handler:erase
			}]
		
		},
		
		{
			title: '客流管理',
			xtype: 'panel',
			height:700,
			width: 500,
			bodyStyle:'padding:5px 5px 0',
			layout: 'fit',
			items: [
			{
				
				id: 'chartNum',
				xtype: 'chart',
				animate: true,
				shadow: true,
				hidden: false,
				store: store1,
				  
			
				axes: [{
					type: 'Numeric',
					position: 'bottom',
					fields: ['num'],
					/*
					label: {
						renderer: Ext.util.Format.numberRenderer('0,0')
					},
					*/
					title: '游客人数',
					grid: true,
					minimum: 0
				}, {
					type: 'Category',
					position: 'left',
					fields: ['spot_name'],
					title: '景点'
				}],
				//theme: 'White',
				background: {
					gradient: {
						id: 'backgroundGradient',
						angle: 45,
						stops: {
							0: {
								color: '#ffffff'
							},
							100: {
								color: '#eaf1f8'
							}
						}
					}
				},
				series: [
				{
					
					type: 'bar',
					axis: 'bottom',
					
					highlight: true,
					
					tips: {
						trackMouse: true,
						width: 140,
						height: 28,
						renderer: function (storeItem, item) {
                            //this.setTitle(storeItem.get('spot') + ': ' + (storeItem.get(maxNum) - storeItem.get('tourNum')));
							this.setTitle(storeItem.get('spot_name') + ': ' + storeItem.get('maxnum'));
							//this.setTitle(item.value.x + ': ' + item.value);    
                        } 
					},
					
					label: {
						display: 'insideEnd',
						field: 'num',
						renderer: Ext.util.Format.numberRenderer('0'),
						orientation: 'horizontal',
						color: '#333',
					'text-anchor': 'middle'
                },
					xField: 'spot_name',
					yField: ['num']
					
				}]
				
				
				
			}]

		},
		{
			title: '反馈信息',
			xtype: 'panel',
			name: 'feedbackPanel',
			id: 'feedbackPanel',
			bodyStyle:'padding:5px 5px 0'
		}]
		

	}]
	});	

	
	info.render(document.body);
	//info.show();
	
	
	Ext.Ajax.request ({
		clientValidation:true,
		url:'load_feedbacks.action',//后台地址
		method:'POST',
		success : function(request,options) {
						
			var json = Ext.JSON.decode(request.responseText);
			var feedbacks = json.feedbacks;
			
			for(var i = 0; i < feedbacks.length; i++) {
				
				var backs = feedbacks[i];
				
				var panel = new Ext.Panel({
					
					layout: 'fit',
					frame: true,
					border : false,
					items: [{
						xtype:'displayfield',
						html: backs.username
						
					},{
						xtype:'displayfield',
						html: backs.comment,
						cls: 'commentCss'
						
					},{
						xtype:'displayfield',
						html: backs.time,
						cls: 'timeCss'
						
					}]
				});
				Ext.getCmp('feedbackPanel').add(panel);
			}
			Ext.getCmp('feedbackPanel').doLayout();		
		},
		failure : function() {
			Ext.Msg.show({
				title : 'Wrong',
				msg : 'something wrong!',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR
			});
		}
		
	});
		
	
	function findPosition() {
	
		var tel = Ext.getCmp('phoneNum').getValue();
	
		Ext.Ajax.request ({
			clientValidation:true,
			params : {
	        			phone:tel
	                }, 
			
			url:'Search_traceroute.action',//后台地址
			method:'POST',
			success : function(request,options) {
							//success:true,msg:'success to delete'}
								var json = Ext.JSON.decode(request.responseText);
								
								Ext.getCmp('infoTour').expand();
								Ext.getCmp('tourName').setValue(json.username);
								Ext.getCmp('tourPhone').setValue(json.phone);
								Ext.getCmp('tourSex').setValue(json.sex);
								Ext.getCmp('tourBelong').setValue(json.province);
								
			},
			failure : function() {
				Ext.Msg.show({
					title : 'Wrong',
					msg : 'something wrong!',
					buttons : Ext.Msg.OK,
					icon : Ext.Msg.ERROR
				});
			}
			
		});
		
		store.load({params:{phone:tel}});
	
	}
    
	 function showTrace() {
		  var tel = Ext.getCmp('phoneNum').getValue();
			
		  
		  Ext.Ajax.request ({
				clientValidation:true,
				params : {
		        			phone:tel
		                }, 
				
				url:'tourist_route.action',//后台地址
				method:'POST',
				success : function(request,options) {
								
					var json = Ext.JSON.decode(request.responseText);
					var position = json.routePointList;
					for(var i = 0; i < position.length; i++){
						var jsonobj = position[i];
			
							var lat = jsonobj.lat;
							var lng = jsonobj.lng;
							marker = new google.maps.Marker({
								position: new google.maps.LatLng(lat, lng),		
							
								draggable: false,
								map: map
							});
							markersArray.push(marker);
							
					
					}
									
				},
				failure : function() {
					Ext.Msg.show({
						title : 'Wrong',
						msg : 'something wrong!',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.ERROR
					});
				}
				
			});	
		}

	
	
	//发布预警信息
	function publish() {
	
		var region = "";
		
		Ext.getCmp('range').items.each(function(f){ 
		//this.range.items.each(function(f){  
            if(f.getValue() == true){  
                region += f.inputValue + ",";  
            }  
        });  
		
		var uname = "";
		var content = Ext.getCmp('warnContent').getValue();
		
		Ext.Ajax.request ({
			clientValidation:true,
			params : {warnInfo:Ext.encode({
	        			username:uname,
						content:content,
	        			region_list:region})
	                }, 
			waitMsg:'正在发布...',
			url:'send_warning.action',
			method:'POST',
			success:function(form, action) {
				Ext.Msg.alert('提示','预警信息已经成功发布');
				//window.location.reload(); 
			},
			failure:function(form, action) {
				Ext.Msg.alert('提示','信息发布失败');
				//window.location.reload(); 
			}
		});

	}
	
	function erase() {
		
		if (markersArray) {
			 
		      for (var i in markersArray) {
		        markersArray[i].setMap(null);
		      }
		      markersArray.length = 0;
		}
		
	}
	
	//取消发布信息
	function cancel() {
		
		Ext.Msg.confirm('系统提示','你确定要取消发布吗', function(btn){
			if(btn == 'yes')
			{
				info.form.reset();
				
			}
		},this);
	
	}
	
 });
