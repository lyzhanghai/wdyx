$(function(){
	$('input[type=submit]').click(function(){
		var obj = $(this);
		$('input').blur();
		$.ajax({
	        type: 'POST',
	        dataType: 'json',
	        url: '/VoteServlet',
	        cache: false,
	        data: {
	            openid: $("#openid").val().length==28?$("#openid").val():returnCitySN["cip"],
	            candidate: obj.closest('.actions').children().children('input[type=hidden]').val()
	        },
	        beforeSend: function(xhr, settings) {
	        	obj.attr('disabled',true);
	        	obj.val("投票中...");
	        },
	        success: function(data) {
	        	alert(data['msg']);
	        	if(data['state']=="1"){
	        		var tag = obj.parents('article').children('div.inner').children('p').children('span');
	        		var pre = parseInt(tag.text());
	        		tag.text(pre+1);
	        	}
	        },
	        complete: function() {
	        	obj.val("给我投票");
	        	obj.removeAttr('disabled');
	        }
	    });
	});
});