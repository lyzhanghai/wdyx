var size = 5;// 设置全局样式大小,单位方块大小
var timeflag = false;

var now = new Date();
var till = new Date(2014, 10, 24, 22, 0);


if ((till.getTime() - now.getTime()) <= 0) {
	timeflag = true;
}

setStyle();
function setStyle() {
	$('#time').css({
		'width' : 57 * size,
		'height' : 7 * size
	});
	$('#day1,#day2,#hour1,#hour2,#min1,#min2,#sec1,#po1,#po2,#p').css(
			'margin-right', size);
	$('#day1,#day2,#hour1,#hour2,#min1,#min2,#sec1,#sec2').css({
		'width' : 5 * size,
		'height' : 7 * size
	});
	$('#po1,#po2,#po3').css({
		'width' : size,
		'height' : 7 * size
	});
	$('.potop,.pobottom').css({
		'width' : size - 1,
		'height' : size - 1
	});
	$('.potop').css({
		'top' : 2 * size
	});
	$('.pobottom').css({
		'bottom' : size
	});
	$('.p').css({
		'width' : size - 1,
		'height' : size - 1
	});
}
// 自定义点动画
$.fn.p = function(num, speed) {
	var nr = [];
	// 绘制数字0-9像素点坐标
	nr[0] = [ [ 1, 2 ], [ 1, 3 ], [ 1, 4 ], [ 1, 5 ], [ 1, 6 ], [ 5, 2 ],
			[ 5, 3 ], [ 5, 4 ], [ 5, 5 ], [ 5, 6 ], [ 2, 1 ], [ 3, 1 ],
			[ 4, 1 ], [ 2, 7 ], [ 3, 7 ], [ 4, 7 ], [ 2, 5 ], [ 3, 4 ],
			[ 4, 3 ] ];
	nr[1] = [ [ 3, 1 ], [ 3, 2 ], [ 3, 3 ], [ 3, 4 ], [ 3, 5 ], [ 3, 6 ],
			[ 3, 7 ], [ 2, 2 ], [ 2, 7 ], [ 4, 7 ] ];
	nr[2] = [ [ 5, 7 ], [ 4, 7 ], [ 3, 7 ], [ 2, 7 ], [ 1, 7 ], [ 1, 6 ],
			[ 2, 5 ], [ 3, 4 ], [ 4, 4 ], [ 1, 2 ], [ 2, 1 ], [ 3, 1 ],
			[ 4, 1 ], [ 5, 2 ], [ 5, 3 ] ];
	nr[3] = [ [ 1, 1 ], [ 2, 1 ], [ 3, 1 ], [ 4, 1 ], [ 5, 1 ], [ 1, 6 ],
			[ 2, 7 ], [ 3, 7 ], [ 4, 7 ], [ 4, 2 ], [ 3, 3 ], [ 4, 4 ],
			[ 5, 5 ], [ 5, 6 ] ];
	nr[4] = [ [ 1, 5 ], [ 2, 5 ], [ 3, 5 ], [ 4, 5 ], [ 5, 5 ], [ 1, 4 ],
			[ 2, 3 ], [ 3, 2 ], [ 4, 1 ], [ 4, 2 ], [ 4, 3 ], [ 4, 4 ],
			[ 4, 6 ], [ 4, 7 ] ];
	nr[5] = [ [ 1, 6 ], [ 2, 7 ], [ 3, 7 ], [ 4, 7 ], [ 5, 6 ], [ 5, 5 ],
			[ 5, 4 ], [ 4, 3 ], [ 3, 3 ], [ 2, 3 ], [ 1, 3 ], [ 1, 2 ],
			[ 1, 1 ], [ 2, 1 ], [ 3, 1 ], [ 4, 1 ], [ 5, 1 ] ];
	nr[6] = [ [ 2, 4 ], [ 3, 4 ], [ 4, 4 ], [ 5, 5 ], [ 5, 6 ], [ 4, 7 ],
			[ 3, 7 ], [ 2, 7 ], [ 1, 6 ], [ 1, 5 ], [ 1, 4 ], [ 1, 3 ],
			[ 2, 2 ], [ 3, 1 ], [ 4, 1 ] ];
	nr[7] = [ [ 3, 7 ], [ 3, 6 ], [ 3, 5 ], [ 3, 4 ], [ 4, 3 ], [ 5, 2 ],
			[ 5, 1 ], [ 4, 1 ], [ 3, 1 ], [ 2, 1 ], [ 1, 1 ] ];
	nr[8] = [ [ 5, 5 ], [ 5, 6 ], [ 4, 7 ], [ 3, 7 ], [ 2, 7 ], [ 1, 6 ],
			[ 1, 5 ], [ 2, 4 ], [ 3, 4 ], [ 4, 4 ], [ 5, 3 ], [ 5, 2 ],
			[ 4, 1 ], [ 3, 1 ], [ 2, 1 ], [ 1, 2 ], [ 1, 3 ] ];
	nr[9] = [ [ 2, 7 ], [ 3, 7 ], [ 4, 6 ], [ 5, 5 ], [ 5, 4 ], [ 5, 3 ],
			[ 5, 2 ], [ 4, 1 ], [ 3, 1 ], [ 2, 1 ], [ 1, 2 ], [ 1, 3 ],
			[ 2, 4 ], [ 3, 4 ], [ 4, 4 ] ];
	var po = nr[num].sort(function() {
		return Math.random() > .5 ? -1 : 1
	});
	// 设置动画速度
	if (!speed)
		speed = 2000;
	// 随机取点
	var nn = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
			18, 19 ];
	var nm = nn.sort(function() {
		return Math.random() > .5 ? -1 : 1
	});
	// 清除多余点
	for ( var l = 19; l >= po.length; l--) {
		// 多使用随机数,增加动画的随机性
		$(this).children('.p').eq(nm[l]).animate(
				{
					opacity : 'hide',
					left : Math.floor(Math.random() * 10 * size - Math.random()
							* 6 * size)
							+ 'px',
					top : Math.floor(Math.random() * 10 * size - Math.random()
							* 6 * size)
							+ 'px'
				}, speed);
	}
	// 循环显示点
	for ( var n = 0; n < po.length; n++) {
		var left = (po[n][0] - 1) * size;
		var top = (po[n][1] - 1) * size;
		$(this).children('.p').eq(nm[n]).animate({
			opacity : 'show',
			left : left + 'px',
			top : top + 'px'
		}, speed);
	}
}
$(function() {
	$('.p').each(
			function() {
				$(this).css('top',
						Math.floor(Math.random() * 600 - Math.random() * 600));
				$(this).css('left',
						Math.floor(Math.random() * 500 - Math.random() * 500));
			});
	changetime(1);
	setTimeout("run()", 1000);
});
function run() {
	runtime = setInterval('changetime()', 1000);
}
var d1, d2, h1, h2, m1, m2, s1;
function changetime(isStart) {
	var D = new Date();
	var endD = new Date(2014, 10, 29, 18, 0);
	// 秒
	var date3 = endD.getTime() - D.getTime(); // 时间差的毫秒数

	// 计算出相差天数
	var days = Math.floor(date3 / (24 * 3600 * 1000));

	// 计算出小时数
	var leave1 = date3 % (24 * 3600 * 1000); // 计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000));
	// 计算相差分钟数
	var leave2 = leave1 % (3600 * 1000); // 计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000));

	// 计算相差秒数
	var leave3 = leave2 % (60 * 1000); // 计算分钟数后剩余的毫秒数
	var seconds = Math.round(leave3 / 1000);

	var s2 = seconds % 10;
	if (isStart == 1) {
		$('#sec2').p(s2);// 初始载入个位秒速度很慢
	} else {
		$('#sec2').p(s2, 800);// 循环时个们秒速度变快
	}
	if (Math.floor(seconds / 10) == s1)
		return false;
	s1 = Math.floor(seconds / 10);
	$('#sec1').p(s1);
	// 分钟
	if (minutes % 10 == m2)
		return false;
	m2 = minutes % 10;
	$('#min2').p(m2);
	if (Math.floor(minutes / 10) == m1)
		return false;
	m1 = Math.floor(minutes / 10);
	$('#min1').p(m1);
	// 小时
	if (hours % 10 == h2)
		return false;
	h2 = hours % 10;
	$('#hour2').p(h2);
	if (Math.floor(hours / 10) == h1)
		return false;
	h1 = Math.floor(hours / 10);
	$('#hour1').p(h1);
	// 天数
	if (days % 10 == d2)
		return false;
	d2 = days % 10;
	$('#day2').p(d2);
	if (Math.floor(days / 10) == d1)
		return false;
	d1 = Math.floor(days / 10);
	$('#day1').p(d1);
}