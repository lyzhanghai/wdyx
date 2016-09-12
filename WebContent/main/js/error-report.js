$(function(){
	$('#send').click(function(){
		if($('#remark').val().length<6){
			alert('您至少得输入5个字符');
		}else{
			$.ajax({
		        type: 'POST',
		        dataType: 'text',
		        url: '/ErrorReportServlet',
		        cache: false,
		        data: {
		        	openid: $("#openid").val().length==28?$("#openid").val():returnCitySN["cip"],
		        	code: $('#code').val(),
		        	remark: $('#remark').val()
		        },
		        beforeSend: function(xhr, settings) {
		        	$('#send').attr('disabled',true);
		        	$('#send').attr('value','Sending...');
		        },
		        success: function(data) {
		        	alert(data);
		        },
		        complete: function() {
		        	$('ul.default').prepend('<li>'+$('#remark').val()+'('+new Date().Format("yyyy-MM-dd hh:mm:ss")+')</li>');
		        	$('#send').attr('value','Send');
		        	$('#send').removeAttr('disabled');
		        }
		    });
		}
	});
});


