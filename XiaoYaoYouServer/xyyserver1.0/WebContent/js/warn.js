Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', '../examples/ux');
Ext.require([
    'Ext.panel.Panel.*',
    'Ext.ux.GMapPanel'
]);


Ext.require('Ext.chart.*');
Ext.require(['Ext.Window', 'Ext.fx.target.Sprite', 'Ext.layout.container.Fit', 'Ext.window.MessageBox']);

Ext.onReady(function(){
    
	Ext.QuickTips.init();
	
	
	// 这段是用于静态内容(游客时间位置)的显示
	var myData = [
        ['2012-07-24 12:00:00', '五龙桥'],
        ['2012-07-24 12:30:00', '石门'],
        ['2012-07-24 14:00:00', '青柯坪']
    ];
	
	// create the data store
    var store = Ext.create('Ext.data.ArrayStore', {
        fields: [
           {name: 'lastTime', type:'date', dateFormat:'Y-m-d H:i:s'},
		   {name: 'position', type:'string'}
        ],
        data: myData
    });
	
	
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
	
	
	// 实现求救信号的弹出
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
	
	Ext.create('widget.uxNotification', {
		title: '求救信号',
		corner: 'tr',
		closable: true,
		stickOnClick: true,
		autoHide:false,
		html: '游客18702900487求救'
	}).show();
	
	Ext.create('widget.uxNotification', {
		title: '求救信号',
		corner: 'tr',
		closable: true,
		stickOnClick: true,
		autoHide:false,
		html: '游客18702912365求救'
	}).show();
	

    var info = new Ext.form.FormPanel( {
	
	name:'mainPanel',
	renderTo:Ext.getBody(),
	frame:true,
	layout:'column',
	layoutOnTabChange:true ,
	bodyPadding: 5, 
	border: true,
	y:30,
	width:1200,
    height:700,
	
	items:[{
		
		xtype:'panel',
		name:'map',
        title: '查看地图',
        width:590,
        height:700,
        border: true,
		columnWidth:.5,
   
		items: {
            xtype: 'gmappanel',
            zoomLevel: 13,
            gmapType: 'map', //'map'
			id: 'mapOfSpot',
            mapConfOpts: ['enableScrollWheelZoom','enableDoubleClickZoom','enableDragging'],
            mapControls: ['GSmallMapControl','GMapTypeControl','NonExistantControl'],
            setCenter: {
                
				lat: 34.49424873578246, //北纬
				lng: 110.10395050048828 //东经
                //marker: {title: 'huashan'}
			},
			
			markers: [{
			
				lat: 34.5239896823904, //北纬
				lng: 110.077257156372, //东经

				marker: 
				{
					title: 'huashan',
					draggable:true
				},
				listeners: {
					click: function(e){
					
						//initialize();
						alert("经度" + e.Vd + "  纬度" + e.Ha);
					}
				}
			}]
        }
	},
	{
		xtype:'tabpanel',
		columnWidth:.5,
		bodyPadding: 5, 
		height:685,
		items: [
		{
        // Explicitly define the xtype of this Component configuration.
        // This tells the Container (the tab panel in this case)
        // to instantiate a Ext.panel.Panel when it deems necessary
			xtype: 'panel',
			title: '发布预警信息',
			bodyStyle:'padding:10px 10px 0',
       
			items: [
			{
				name: 'rangeInfo',
				fieldLabel: '<font size = "2">请选择接受信息的游客范围</font>',
				
				labelWidth : 200, 
				xtype:'checkboxgroup',
				width:500,
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
					inputValue: '05'
				},
				{
					boxLabel: '北峰到五云峰路段', 
					inputValue: '05'
				},
				{
					boxLabel: '五云峰到三峰路口路段', 
					inputValue: '05'
				},
				{
					boxLabel: '中峰区域', 
					inputValue: '05'
				},
				{
					boxLabel: '南峰区域', 
					inputValue: '05'
				},
				{
					boxLabel: '东峰区域', 
					inputValue: '05'
				},
				{
					boxLabel: '西峰区域', 
					inputValue: '05'
				}] 
				
			},
			
			{
				xtype: 'displayfield',
				html: '<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>',
			},
			
			{
				xtype:'textarea',
				name: 'warnInfo',
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
			html:'<br><font size = "2" align:"center">点击"发布"后，预警信息将发送至园区内所选择区域的游客!</font>',
			buttonAlign:'right',
			buttons: [{
				text: '发布',
				handler:publish
			},{
				text: '取消',
				handler:cancel
			}]	
		
	
		
		/*
        listeners: {
            render: function() {
                
            }
        }
		*/
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
						maxLength:11,
						minLength:11,
						emptyText:'请输入11位数的电话号码'
					}]
				
				}],
				buttons: [{
					text: '查询踪迹',
					handler: function(){
						Ext.getCmp('infoTour').expand();
					}
					
				
				},{
					text: '地图显示',
					handler:function() {
						var point1 = new GLatLng(34.523401862237,110.077192783355);//点击按钮时在地图上增加个标记
						var point2 = new GLatLng(34.504823724647,110.0755995512);
						var point3 = new GLatLng(34.4915428793187,110.075862407684);
						var map=Ext.getCmp("mapOfSpot");  
						map.addMarker(point1);
						map.addMarker(point2);
						map.addMarker(point3);
					}
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
					fieldLabel: '姓名'
					
				},{
					xtype:'displayfield',
					name: 'tourSex',
					fieldLabel: '性别'
				
				},{
					xtype:'displayfield',
					name: 'tourAge',
					fieldLabel: '年龄'
				},{
					xtype:'displayfield',
					name: 'tourBelong',
					fieldLabel: '籍贯'
				},{
					xtype: 'displayfield',
					html: '<br>该游客所经过的位置:',
				},
				{
				
					xtype:'gridpanel',
					name: 'trace',
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
						dataIndex: 'lastTime',
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
					},
					{
						text     : '位置',
						width    : 200,
						sortable : true,
						dataIndex: 'position'
					}]
        
				}]
			}]
		
		},
		
		{
			title: '客流管理',
			xtype: 'panel',
			bodyStyle:'padding:5px 5px 0',
			layout: 'fit',
			
			items: [
			{
				
				id: 'chartNum',
				xtype: 'chart',
				animate: true,
				shadow: true,
				store: store1,
				  
			
				axes: [{
					type: 'Numeric',
					position: 'bottom',
					fields: ['tourNum'],
					label: {
						renderer: Ext.util.Format.numberRenderer('0,0')
					},
					title: '游客人数',
					grid: true,
					minimum: 0
				}, {
					type: 'Category',
					position: 'left',
					fields: ['spot'],
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
							this.setTitle(storeItem.get('spot') + ': ' + storeItem.get('maxNum'));
							//this.setTitle(item.value.x + ': ' + item.value);    
                        } 
					},
					
					label: {
						display: 'insideEnd',
						field: 'tourNum',
						renderer: Ext.util.Format.numberRenderer('0'),
						orientation: 'horizontal',
						color: '#333',
					'text-anchor': 'middle'
                },
					xField: 'spot',
					yField: ['tourNum']
					
				}]
				
				
				
			}]
			
		},
		{
			title: '反馈信息',
			xtype: 'panel',
			bodyStyle:'padding:5px 5px 0',
			//layout: 'fit',
			
			items: [
			{
				
				xtype: 'panel',
				layout: 'fit',
				frame: true,
				border : false, 
				items: [{
					xtype:'displayfield',
					name: 'nickname1',
					html: 'sophie：'
				
				},{
					xtype:'displayfield',
					//width: 300,
					//height: 100,
					name: 'comment1',
					html: 'goodaaaaa',
					cls: 'commentCss'
				
				},{
					xtype:'displayfield',
					name: 'com_time1',
					html: '2012-07-27',
					cls: 'timeCss'
				
				}]
			  
			},
			{
				
				xtype: 'panel',
				layout: 'fit',
				frame: true,
				border : false, 
				items: [{
					xtype:'displayfield',
					name: 'nickname1',
					html: 'sophie：'
				
				},{
					xtype:'displayfield',
					//width: 300,
					//height: 100,
					name: 'comment1',
					html: 'goodaaaaa',
					cls: 'commentCss'
				
				},{
					xtype:'displayfield',
					name: 'com_time1',
					html: '2012-07-27',
					cls: 'timeCss'
				
				}]
			  
			},
			{
				
				xtype: 'panel',
				layout: 'fit',
				frame: true,
				border : false, 
				items: [{
					xtype:'displayfield',
					name: 'nickname1',
					html: 'sophie：'
				
				},{
					xtype:'displayfield',
					//width: 300,
					//height: 100,
					name: 'comment1',
					html: 'goodaaaaa',
					cls: 'commentCss'
				
				},{
					xtype:'displayfield',
					name: 'com_time1',
					html: '2012-07-27',
					cls: 'timeCss'
				
				}]
			  
			}]
			
		}]
		

	}]
	});	
	
	info.show();

	
    
	//预警信息发布成功
	function publish() {
		info.form.submit ({
			clientValidation:true,
			waitMsg:'正在发布...',
			url:'10.128.53.126:8000',//后台地址
			method:'POST',
			success:function(form, action) {
				Ext.Msg.alert('提示','预警信息已经成功发布，系统5秒后返回');
				window.location.reload(); 
			},
			failure:function(form, action) {
				//Ext.Msg.alert('提示','信息发布失败');
				Ext.Msg.alert('提示','预警信息已经成功发布，系统5秒后返回');
				window.location.reload(); 
			}
		});

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
