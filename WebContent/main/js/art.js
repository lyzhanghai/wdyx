$(function(){
	$('input[name=publish]').click(function(){
		var obj = $(this);
		var openid = $('input[name=openid]').val();
		var artid = $('input[name=artid]').val();
		var title = $('input[name=title]').val();
		var review = $('textarea[name=review]').val();
		if(review.length<1){
			alert('您不能提交空评论');
		}else if(review.length>512){
			alert('评论内容不能超过512字');
		}else{
			$('input').blur();
			$.ajax({
				type: 'post',
				dataType: 'text',
				url: '/ArtServlet',
				cache: false,
				data: {
					openid: openid,
					artid: artid,
					title: title,
					review: review
				},
				beforeSend: function(xhr,settings){
					obj.attr("disabled",true);
					obj.val('正在发表...');
				},
				success: function(data){
					alert(data);
					if(data=='评论成功'){
						var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
						$('#review-list').children('ul').prepend('<li>'+review+'('+time+')</li>');
					}
				},
				complete: function(){
					obj.val('发表');
					obj.removeAttr("disabled");
				}
			});
		}
	});
});