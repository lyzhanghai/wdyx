$(function() {
    $('#submit').click(function() {
    	var name = $('#name').val();
    	var gender = $('#gender').val();
    	var email = $('#email').val();
    	var department = $('#department').val();
    	var grade = $('#grade').val();
    	var contact = $('#contact').val();
    	var remark = $('#remark').val();
    	if(name.length<1){
    		alert('请输入您的姓名');
    	}else if(gender.length<1){
    		alert('请选择您的性别');
    	}else if(email.length<1){
    		alert('请输入您的邮箱');
    	}else if(department.length<1){
    		alert('请输入您所在的院系');
    	}else if(grade.length<1){
    		alert('请选择您的年级');
    	}else if(contact.length<1){
    		alert('请输入您的微信号或QQ号以便我们与您联系');
    	}else if(remark.length<1){
    		alert('请输入参赛方式');
    	}else if(name.length>10){
    		alert('姓名长度不得超过10个字符');
    	}else if(email.length>32){
    		alert('邮箱信息长度不得超过32个字符');
    	}else if(department.length>16){
    		alert('部门信息长度不得超过16个字符');
    	}else if(contact.length>32){
    		alert('QQ/微信/手机号长度不得超过32个字符');
    	}else if(remark.length>32){
    		alert('参赛方式长度不得超过32个字符');
    	}else{
    		$('input').blur();
            $.ajax({
                type: 'POST',
                dataType: 'text',
                url: '/ActivityServlet',
                cache: false,
                data: {
                    openid: $('#openid').val(),
                    title: $('#title').val(),
                    name: name,
                    gender: gender,
                    email: email,
                    department: department,
                    grade: grade,
                    contact: contact,
                    remark: remark
                },
                beforeSend: function(xhr, settings) {
                	$('#submit').attr('disabled', true);
                    $('#submit').attr('value', '正在处理...');
                },
                success: function(data) {
                    alert(data);
                    WeixinJSBridge.invoke('closeWindow',{},function(res){});
                },
                complete: function() {
                	$('#send').attr('value','确认');
		        	$('#send').removeAttr('disabled');
                }
            });
    	}	
    });
});