$(function(){
		$('.t2').html('<canvas id="canvas"></canvas>');
		var startline = $('.t1').attr('id');
		var deadline = $('.t2').attr('id');
		var baseline = new Date();
		var days;
		var hours;
		var minutes;
		var seconds;
		var WINDOW_WIDTH = $(window).width();
		var WINDOW_HEIGHT = $(window).height()*0.2;
		var RADIUS = 1.4; //球半径
		var NUMBER_GAP = 1; //数字之间的间隙
		var u=0.65; //碰撞能量损耗系数
		var context; //Canvas绘制上下文
		var balls = []; //存储彩色的小球
		const colors = ["#33B5E5","#0099CC","#AA66CC","#9933CC","#99CC00","#669900","#FFBB33","#FF8800","#FF4444","#CC0000"]; //彩色小球的颜色
		var currentNums = []; //屏幕显示的字符
		var digit =
                [
                    [
                        [0,0,1,1,1,0,0],
                        [0,1,1,0,1,1,0],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,0,1,1,0],
                        [0,0,1,1,1,0,0]
                    ],//0
                    [
                        [0,0,0,1,1,0,0],
                        [0,1,1,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [1,1,1,1,1,1,1]
                    ],//1
                    [
                        [0,1,1,1,1,1,0],
                        [1,1,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,0,0],
                        [0,0,1,1,0,0,0],
                        [0,1,1,0,0,0,0],
                        [1,1,0,0,0,0,0],
                        [1,1,0,0,0,1,1],
                        [1,1,1,1,1,1,1]
                    ],//2
                    [
                        [1,1,1,1,1,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,0,0],
                        [0,0,1,1,1,0,0],
                        [0,0,0,0,1,1,0],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,1,1,0]
                    ],//3
                    [
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,1,0],
                        [0,0,1,1,1,1,0],
                        [0,1,1,0,1,1,0],
                        [1,1,0,0,1,1,0],
                        [1,1,1,1,1,1,1],
                        [0,0,0,0,1,1,0],
                        [0,0,0,0,1,1,0],
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,1,1]
                    ],//4
                    [
                        [1,1,1,1,1,1,1],
                        [1,1,0,0,0,0,0],
                        [1,1,0,0,0,0,0],
                        [1,1,1,1,1,1,0],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,1,1,0]
                    ],//5
                    [
                        [0,0,0,0,1,1,0],
                        [0,0,1,1,0,0,0],
                        [0,1,1,0,0,0,0],
                        [1,1,0,0,0,0,0],
                        [1,1,0,1,1,1,0],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,1,1,0]
                    ],//6
                    [
                        [1,1,1,1,1,1,1],
                        [1,1,0,0,0,1,1],
                        [0,0,0,0,1,1,0],
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,0,0],
                        [0,0,0,1,1,0,0],
                        [0,0,1,1,0,0,0],
                        [0,0,1,1,0,0,0],
                        [0,0,1,1,0,0,0],
                        [0,0,1,1,0,0,0]
                    ],//7
                    [
                        [0,1,1,1,1,1,0],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,1,1,0],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,1,1,0]
                    ],//8
                    [
                        [0,1,1,1,1,1,0],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [1,1,0,0,0,1,1],
                        [0,1,1,1,0,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,0,1,1],
                        [0,0,0,0,1,1,0],
                        [0,0,0,1,1,0,0],
                        [0,1,1,0,0,0,0]
                    ],//9
                    [
                     [0,0,0,1,1,1,1,1,1,1,0,0,0],
                     [0,0,0,0,0,0,1,0,0,0,0,0,0],
                     [0,0,0,0,0,0,1,0,0,0,0,0,0],
                     [0,0,0,0,0,0,1,0,0,0,0,0,0],
                     [0,1,1,1,1,1,1,1,1,1,1,1,0],
                     [0,0,0,0,0,0,1,0,0,0,0,0,0],
                     [0,0,0,0,0,1,0,1,0,0,0,0,0],
                     [0,0,0,0,1,0,0,0,1,0,0,0,0],
                     [0,0,0,1,0,0,0,0,0,1,0,0,0],
                     [0,1,1,0,0,0,0,0,0,0,1,1,0]
                    ],//10,天
                    [
                     [0,0,0,0,0,0,0,0,0,0,1,0,0],
                     [0,1,1,1,1,0,1,1,1,1,1,1,0],
                     [0,1,0,0,1,0,0,0,0,0,1,0,0],
                     [0,1,0,0,1,0,1,0,0,0,1,0,0],
                     [0,1,1,1,1,0,0,1,0,0,1,0,0],
                     [0,1,0,0,1,0,0,0,1,0,1,0,0],
                     [0,1,0,0,1,0,0,0,0,0,1,0,0],
                     [0,1,1,1,1,0,0,0,0,0,1,0,0],
                     [0,0,0,0,0,0,0,0,1,0,1,0,0],
                     [0,0,0,0,0,0,0,0,0,1,1,0,0]
                    ],//11,时
                    [
                     [0,0,0,0,0,1,0,1,0,0,0,0,0],
                     [0,0,0,0,1,0,0,0,1,0,0,0,0],
                     [0,0,0,1,0,0,0,0,0,1,0,0,0],
                     [0,1,1,0,1,1,1,1,1,0,1,1,0],
                     [0,0,0,0,0,1,0,0,1,0,0,0,0],
                     [0,0,0,0,0,1,0,0,1,0,0,0,0],
                     [0,0,0,0,0,1,0,0,1,0,0,0,0],
                     [0,0,0,0,1,0,0,0,1,0,0,0,0],
                     [0,0,0,1,0,0,1,0,1,0,0,0,0],
                     [0,1,1,0,0,0,0,1,1,0,0,0,0]
                    ],//12,分
                    [
                     [0,0,0,0,1,0,0,0,0,1,0,0,0],
                     [1,1,1,1,0,0,0,0,0,1,0,0,0],
                     [0,0,1,0,0,0,0,0,0,1,0,0,0],
                     [0,0,1,0,0,0,0,1,0,1,0,1,0],
                     [1,1,1,1,1,0,1,0,0,1,0,0,1],
                     [0,1,1,1,0,0,0,0,0,1,0,0,0],
                     [1,0,1,0,1,0,0,0,0,1,1,0,0],
                     [0,0,1,0,0,0,0,0,0,1,0,0,0],
                     [0,0,1,0,0,0,0,0,1,0,0,0,0],
                     [0,0,1,0,0,1,1,1,0,0,0,0,0]
                    ]//13,秒
                ];

		function drawDatetime(cxt){
			var nums = [];

			context.fillStyle="#005eac"
			//var offsetX = 70, offsetY = 30;
			var offsetX = 0, offsetY = 20;
			
			var currentline = new Date().getTime();
			startline += (currentline-baseline);
			
			days = GetDateDiff(startline,deadline, "day");
			var num1 = Math.floor(days/10);
			var num2 = days%10;
			nums.push({num: num1});
			nums.push({num: num2});
			nums.push({num: 10}); //天
			
			hours = GetDateDiff(startline,deadline, "hour");
			var num1 = Math.floor(hours/10);
			var num2 = hours%10;
			nums.push({num: num1});
			nums.push({num: num2});
			nums.push({num: 11}); //时
			
			minutes = GetDateDiff(startline,deadline, "minute");
			var num1 = Math.floor(minutes/10);
			var num2 = minutes%10;
			nums.push({num: num1});
			nums.push({num: num2});
			nums.push({num: 12}); //分
			
			seconds = GetDateDiff(startline,deadline, "second");
			var num1 = Math.floor(seconds/10);
			var num2 = seconds%10;
			nums.push({num: num1});
			nums.push({num: num2});
			nums.push({num: 13}); //秒
			
			for(var x = 0;x<nums.length;x++){
				nums[x].offsetX = offsetX;
				offsetX = drawSingleNumber(offsetX,offsetY, nums[x].num,cxt);
				//两个数字连一块，应该间隔一些距离
				if(x<nums.length-1){
					offsetX+=NUMBER_GAP;
				}
			}

			//说明这是初始化
			if(currentNums.length ==0){
				currentNums = nums;
			}else{
				//进行比较
				for(var index = 0;index<currentNums.length;index++){
					if(currentNums[index].num!=nums[index].num){
						//不一样时，添加彩色小球
						addBalls(nums[index]);
						currentNums[index].num=nums[index].num;
					}
				}
			}
			renderBalls(cxt);
			updateBalls();

		}

		function addBalls (item) {
			var num = item.num;
			var numMatrix = digit[num];
			for(var y = 0;y<numMatrix.length;y++){
				for(var x = 0;x<numMatrix[y].length;x++){
					if(numMatrix[y][x]==1){
						var ball={
							offsetX:item.offsetX+RADIUS+RADIUS*2*x,
							offsetY:30+RADIUS+RADIUS*2*y,
							color:colors[Math.floor(Math.random()*colors.length)],
							g:1.5+Math.random(),
							vx:Math.pow(-1, Math.ceil(Math.random()*10))*4+Math.random(),
							vy:-5
						}
						balls.push(ball);
					}
				}
			}
		}

		function renderBalls(cxt){
			for(var index = 0;index<balls.length;index++){
				cxt.beginPath();
				cxt.fillStyle=balls[index].color;
				cxt.arc(balls[index].offsetX, balls[index].offsetY, RADIUS, 0, 2*Math.PI);
				cxt.fill();
			}
		}

		function updateBalls () {
			var i =0;
			for(var index = 0;index<balls.length;index++){
				var ball = balls[index];
				ball.offsetX += ball.vx;
				ball.offsetY += ball.vy;
				ball.vy+=ball.g;
				if(ball.offsetY > (WINDOW_HEIGHT-RADIUS)){
					ball.offsetY= WINDOW_HEIGHT-RADIUS;
					ball.vy=-ball.vy*u;
				}
				if(ball.offsetX>RADIUS&&ball.offsetX<(WINDOW_WIDTH-RADIUS)){

					balls[i]=balls[index];
					i++;
				}
			}
			//去除出边界的球
			for(;i<balls.length;i++){
				balls.pop();
			}
		}
		function drawSingleNumber(offsetX, offsetY, num, cxt){
			var numMatrix = digit[num];
			for(var y = 0;y<numMatrix.length;y++){
				for(var x = 0;x<numMatrix[y].length;x++){
					if(numMatrix[y][x]==1){
						cxt.beginPath();
						cxt.arc(offsetX+RADIUS+RADIUS*2*x,offsetY+RADIUS+RADIUS*2*y,RADIUS,0,2*Math.PI);
						cxt.fill();
					}
				}
			}
			cxt.beginPath();
			offsetX += numMatrix[0].length*RADIUS*2;
			return offsetX;
		}

		var canvas = document.getElementById("canvas");
		canvas.width=WINDOW_WIDTH;
		canvas.height=WINDOW_HEIGHT;
		context = canvas.getContext("2d");

		setInterval(function(){
			//清空整个Canvas，重新绘制内容
			context.clearRect(0, 0, context.canvas.width, context.canvas.height);
			drawDatetime(context);
		}, 50)
		
		/*
		* 获得时间偏移量,时间格式为 年-月-日 小时:分钟:秒 或者 年/月/日 小时：分钟：秒
		* 其中，年月日为全格式，例如 ： 2010-10-12 01:00:00
		* 返回精度为：秒，分，小时，天
		*/
		function GetDateDiff(startTime,endTime, diffType) {
		    //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
			endTime = endTime.replace(/\-/g, "/");
		    //将计算间隔类性字符转换为小写
		    diffType = diffType.toLowerCase();
		    var sTime = new Date(startTime);      //开始时间
		    var eTime = new Date(endTime);  //结束时间
		    var dif = parseInt((eTime.getTime() - sTime.getTime())/1000);
		    if(dif<=0)return 0;
		    var offset = 0;
		    switch (diffType) {
		        case "second":
		            offset = dif%60;
		            break;
		        case "minute":
		        	offset = (dif/60)%60;
		            break;
		        case "hour":
		        	offset = (dif/3600)%24;
		            break;
		        case "day":
		        	offset = dif/3600/24;
		            break;
		        default:
		            break;
		    }
		    return parseInt(offset);
		}
		
		var openid = $('#openid').val();
		function blink(){
			//先把所有pos隐藏
			for(var i=1;i<101;i++){
				$("#pos"+i).removeClass("pos poss"+i+" posimg uncheck").addClass("pos poss"+i+" posimg opa check");
			}
			//产生20个1-100的随机数，将这些随机数所对应的pos显示
			
			for(var i=0;i<20;i++){
				$("#pos"+Math.floor(Math.random()*100+1)).removeClass("pos poss"+i+" posimg opa check").addClass("pos poss"+i+" posimg uncheck");
			}
		}
		
		var id;
		$("#btn").click(function() {
			if((days+hours+minutes+seconds)!=0){
				alert("抢票还未开始");
			}else{
				$("input").blur();
				$.ajax({
		            type: 'POST',
		            dataType: 'text',
		            url: '/TicketServlet',
		            cache: false,
		            data: {
		                openid: openid
		            },
		            beforeSend: function(xhr, settings) {
		            	id=setInterval(blink,50);
		            	$("#btn").attr("disabled",true);
		            	$("#btn").css({"background-position":"left -100px","color":"#d7bff2"});
		            	$("#btn").val("正在跻身抢票前线...");
		            },
		            success: function(data) {
		                alert(data);
		            },
		            complete: function() {
		            	clearInterval(id);
		            	$("#btn").css({"background-position":"left top","color":"#8bf8ff"});
		            	$("#btn").val("开始抢票");
		            	$("#btn").removeAttr("disabled");
		            }
		        });
			}
			
		});
});