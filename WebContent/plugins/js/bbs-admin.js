$(document).on('click','.delete',function(){
	var obj = $(this);
	if(window.confirm('确定删除该条状态？')){
		var id = obj.closest('.item').attr('id');
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: '/BbsServlet',
			cache: false,
			data: {
				operation: 'delete',
				id: id,
				openid: $('input[name=openid]').val()
			},
			beforeSend: function(xhr,settings){
				obj.html('正在删除...');
			},
			success: function(data){
				alert(data['msg']);
				if(data['state']=='1'){
					if(obj.closest('.attachment') == undefined){
						obj.closest('.msg-body').remove();
					}else{
						obj.closest('.attachment').remove();
					}
				}
			},
			complete: function(){
				if(obj!=undefined){
					obj.html('删除');
				}
			}
		});
	}
});
	
$(document).on('click','.forbid',function(){
	var obj = $(this);
	var time = prompt('禁言时长（h）','1');
	var ok = false;
	if(parseInt(time)>0){
		var id = obj.closest('.item').attr('id');
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: '/BbsServlet',
			cache: false,
			data: {
				operation: 'forbid',
				id: id,
				openid: $('input[name=openid]').val(),
				user: obj.parent().prev().children('.name').attr('id')
			},
			beforeSend: function(xhr,settings){
				obj.html('正在禁言...');
			},
			success: function(data){
				alert(data['msg']);
				if(data['state']=='1'){
					ok = true;
					obj.html('已禁言');
				}
			},
			complete: function(){
				if(!ok){
					obj.html('禁言');
				}
			}
		});
	}
});