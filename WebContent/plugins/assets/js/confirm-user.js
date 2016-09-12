$(function() {
    $('#bind').click(function() {
    	if($('input[name=username]').val().length!=13){
    		alert('学号长度不对');
    	}else if($('input[name=EISPassword]').val().length<1){
    		alert('教务密码不能为空');
    	}else if($('input[name=EISPassword]').val().length>16){
    		alert('教务密码不能超过16位');
    	}else if($('input[name=IPSPassword]').val().length<1){
    		alert('信息门户密码不能为空');
    	}else if($('input[name=IPSPassword]').val().length>16){
    		alert('信息门户密码不能超过16位');
    	}else if($('input[name=DLSPassword]').val().length<1){
    		alert('图书馆密码不能为空');
    	}else if($('input[name=DLSPassword]').val().length>16){
    		alert('图书馆密码不能超过16位');
    	}else{
    		$('input').blur();
            $.ajax({
                type: 'POST',
                dataType: 'text',
                url: '/BindServlet',
                cache: false,
                data: {
                    openid: $('#openid').val(),
                    username: $('input[name=username]').val(),
                    EISPassword: $('input[name=EISPassword]').val(),
                    IPSPassword: $('input[name=IPSPassword]').val(),
                    DLSPassword: $('input[name=DLSPassword]').val()
                },
                beforeSend: function(xhr, settings) {
                    $('#bind').attr('disabled', true);
                    $('#bind span').html('绑定中,请稍后...');
                    $('#bind label').css('display', 'inline');
                },
                success: function(data) {
                    alert(data);
                    if(data=='绑定成功'){
                    	WeixinJSBridge.invoke('closeWindow',{},function(res){});
                    }
                },
                complete: function() {
                    $('#bind span').html('绑定');
                    $('#bind').removeAttr('disabled');
                }
            });
    	}	
    });
});