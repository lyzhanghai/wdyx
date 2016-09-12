$(function(){
	$("#check").click(function(){
		if($("#key").val().length>0){
			$("input").blur();
			$.ajax({
	            type: 'GET',
	            dataType: 'text',
	            url: '/TicketServlet',
	            cache: false,
	            data: {
	                openid: $("#openid").val(),
	                key: $("#key").val()
	            },
	            beforeSend: function(xhr, settings) {
	            	$("#check").attr("disabled",true);
	            	$("#check span").html("正在检票,请稍后...");
	            },
	            success: function(data) {
	            	alert(data);
	            },
	            complete: function() {
	            	$("#check span").html("确认检票");
	            	$("#check").removeAttr("disabled");
	            }
	        });
		}else{
			alert("请输入有效密匙");
		}
	});
});