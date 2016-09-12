$(function() {
    $('#login').click(function() {
    	
    	if($('input[name=username]').val().length<1){
    		alert('用户名不能为空');
    	}else if($('input[name=password]').val().length<1){
    		alert('密码不能为空');
    	}else{
    		$('input').blur();
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: '/AdminServlet',
                cache: false,
                data: {
                	openid: $('input[name=openid]').val(),
                    username: $('input[name=username]').val(),
                    password: $('input[name=password]').val()
                },
                beforeSend: function(xhr, settings) {
                    $('#login').attr('disabled', true);
                    $('#login span').html('login...');
                },
                success: function(data) {
                	alert(data['msg']);
                    if(data['openid']){
                    	location.href="/MainServlet?page=plugins/bbs.jsp&openid="+data['openid'];
                    }
                },
                complete: function() {
                    $('#login span').html('login');
                    $('#login').removeAttr('disabled');
                }
            });
    	}
    	
    });
});