$(function() {
    $('#submit').click(function() {
    	var name = $('#name').val();
    	var gender = $('#gender').val();
    	var grade = $('#grade').val();
    	var department = $('#department').val();
    	var contact = $('#contact').val();
    	var experience = $('#experience').val();
    	var position = $('#position').val();
    	if(name.length<1){
    		alert('请输入您的姓名');
    	}else if(gender.length<1){
    		alert('请选择您的性别');
    	}else if(grade.length<1){
    		alert('请选择您的年级');
    	}else if(department.length<1){
    		alert('请输入您所在的院系');
    	}else if(contact.length<1){
    		alert('请输入您的QQ/微信或手机号以便我们与您联系');
    	}else if(experience.length<1){
    		alert('请填写您的相关经历');
    	}else if(position.length<1){
    		alert('请选择您所中意的部门');
    	}else if(name.length>10){
    		alert('姓名长度不得超过10个字符');
    	}else if(department.length>16){
    		alert('部门信息长度不得超过16个字符');
    	}else if(contact.length>32){
    		alert('QQ/微信/手机号长度不得超过32个字符');
    	}else if(experience.length>512){
    		alert('个人经历要在512字以内');
    	}else{
    		$('input').blur();
            $.ajax({
                type: 'POST',
                dataType: 'text',
                url: '/JoinUsServlet',
                cache: false,
                data: {
                    openid: $('#openid').val(),
                    name: name,
                    gender: gender,
                    grade: grade,
                    department: department,
                    contact: contact,
                    experience: experience,
                    position: position
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