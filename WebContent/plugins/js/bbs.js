//scroll refresh
var minId = $('#minId').val();
var maxId = $('#maxId').val();
var openid = $('#openid').val();
var uid = $('#uid').val();
var isFetching = false;
var isNotOver = true;
$(window).scroll(function () { 
	var winH = $(window).height();
    var pageH = $(document.body).height(); 
    var scrollT = $(window).scrollTop();
    var aa = (pageH-winH-scrollT)/winH; 
    if(aa<0.002&&isNotOver&&!isFetching){ 
    	fetchBack();
    }
}); 


//auto refresh
var id;
function autoRefresh(interval) {
	clearInterval(id);
	id = setInterval("fetchFor();",interval);
}
function disableAutoRefresh() {
	id = clearInterval(id);
}
function fetchFor(){
	isFetching = true;
	fetchData('20',maxId,'gt','');
	isFetching = false;
}
function fetchBack(){
	isFetching = true;
	fetchData('20',minId,'lt','');
	isFetching = false;
}
function fetchData(count,lastId,ori,openid){
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/BbsServlet',
        cache: false,
        data: {
            count: count,
            lastId: lastId,
            ori: ori,
            openid: openid
        },
        beforeSend: function(xhr, settings) {
        	if(ori=='lt'){
        		$('#no-more').hide();
        		$('#loading-img').show();
        	}
        },
        success: function(data) {
        	if(data[0]){
        		isNotOver = ori=='lt'?true:isNotOver;
        		if(ori=='gt'){
                  	 maxId = data[data.length-1]['id'];
                   }else{
                  	 minId = data[data.length-1]['id'];
                   }
        		 
        		$.each(data,function(index,array){ 
        			 var str = "";
        			 str+= "<div class=\"msg-body\" id=\""+array['openid']+"\">";
                     str+= "<div class=\"item\" id=\""+array['id']+"\">";
                     str+= "<img src=\""+array['headimgurl']+"\" class=\"online\"/>";
                     str+= "<p class=\"message\">";
                     str+= "<a href=\"javascript:void(0)\" class=\"name\" id=\""+array['openid']+"\">";
                     str+= "<small class=\"text-muted pull-right\"><i class=\"fa fa-clock-o\"></i> "+array['timestamp']+"</small>";
                     str+= "<span>"+array['spokesman']+"</span>";
                     str+= "</a>";
                     str+= replace_em(array['msg']);
                     str+= "</p>";
                     if(uid=='admin'){
                    	 str+= "<div class=\"operation\" align=\"right\"><a class=\"delete\" href=\"javascript:void(0)\">删除</a>&nbsp;|&nbsp;<a class=\"forbid\" href=\"javascript:void(0)\">禁言</a></div>";
                     }
                     if(array['review']){
                    	 $.each(array['review'],function(i,arr){
                        	 str+="<div class=\"attachment\">";
                        	 str+= "<div class=\"item\" id=\""+arr['id']+"\">";
                             str+= "<img src=\""+arr['headimgurl']+"\" class=\"online\"/>";
                             str+= "<p class=\"message\">";
                             str+= "<a href=\"javascript:void(0)\" class=\"name\" id=\""+arr['openid']+"\">";
                             str+= "<small class=\"text-muted pull-right\"><i class=\"fa fa-clock-o\"></i> "+arr['timestamp']+"</small>";
                             str+= "<span>"+arr['spokesman']+"</span>";
                             str+= "</a>";
                             str+= replace_em(arr['msg']);
                             str+= "</p>";
                             if(uid=='admin'){
                            	 str+= "<div class=\"operation\" align=\"right\"><a class=\"delete\" href=\"javascript:void(0)\">删除</a>&nbsp;|&nbsp;<a class=\"forbid\" href=\"javascript:void(0)\">禁言</a></div>";
                             }
                             str+= "</div>";
                        	 str+= "</div>";
                    	 }); 
                     }
                     str+= "</div>";
                     str+= "</div>";
                     ori=='gt'?$('#chat-box').prepend(str):$('#chat-box').append(str);
                });
        	}else{
        		isNotOver = ori=='lt'?false:isNotOver;
        		$('#no-more').show();
        	}
        },
        complete: function() {
        	$('#loading-img').hide();
        }
    });
}

//leave msg
$('#send-msg').click(function(){
	if(openid){
		var msg = $('#msg').val();
		if(msg){
			if(msg.length>512){
				alert('输入内容长度不能超过512个字符');
			}else{
				$('input').blur();
				$.ajax({
		            type: 'POST',
		            dataType: 'json',
		            url: '/BbsServlet',
		            cache: false,
		            data: {
		            	operation: 'add',
		                openid: openid,
		                msg: msg,
		                msgid: '0'
		            },
		            beforeSend: function(xhr, settings) {
		                $('#send-msg').attr('disabled', true);
		                $('#send-msg i').hide();
		                $('#send-msg span').show();
		            },
		            success: function(data) {
		            	//alert(data);
		                if(data){
		                	fetchFor();
		                }
		            },
		            complete: function() {
		            	$('#send-msg span').hide();
		            	$('#send-msg i').show();
		                $('#send-msg').removeAttr('disabled');
		                $('#msg').val('');
		            }
		        });
			}
		}
	}else{
		alert('请在武大艺协微信公众平台发表留言与评论');
	}
	
});

//review
var msgid;
var msgParentId;
var msgChildId;
var msgParent;
var msgChild;
$(document).on('click','.name',function(){
	if(openid){
		msgid = $(this).closest('.msg-body').children('.item').attr('id');
		msgParentId = $(this).closest('.msg-body').attr('id');
		msgChildId = $(this).attr('id');
		msgParent = $(this).closest('.msg-body').children('.item').children('p').contents().filter(function(){return this.nodeType==3}).text().trim();
		msgChild = $(this).closest('p').contents().filter(function(){return this.nodeType==3}).text().trim();
		//alert('msgParent:'+msgParent+',msgChild:'+msgChild);
		$('#review').attr('placeholder','回复 '+$(this).children('span').text())
		$('#box-review').toggle();
	}else{
		alert('请在武大艺协微信公众平台回复'+$(this).children('span').text()+'说的话');
	}
});

//on input blur
$(document).on('blur','#review',function(){
	$('#box-review').css('display','none');
});

$('#send-review').click(function(){
	if(openid){
		var msg = $('#review').val();
		if(msg){
			if(msg.length>512){
				alert('回复内容长度不能超过512个字符');
			}else{
				$('input').blur();
				$.ajax({
		            type: 'POST',
		            dataType: 'json',
		            url: '/BbsServlet',
		            cache: false,
		            data: {
		            	operation: 'add',
		                openid: openid,
		                msgid: msgid,
		                msg: msg,
		                msgParentId: msgParentId,
		                msgChildId: msgChildId,
		                msgParent: msgParent,
		                msgChild: msgChild
		            },
		            beforeSend: function(xhr, settings) {
		                $('#send-review').attr('disabled', true);
		                $('#send-review i').hide();
		                $('#send-review span').show();
		            },
		            success: function(data) {
		            	if(data){
		            		insertReview(data['headimgurl'],data['spokesman'],msg);
		            	}
		            },
		            complete: function() {
		            	$('#send-review span').hide();
		            	$('#send-review i').show();
		                $('#send-review').removeAttr('disabled');
		                $('#box-review').css('display','none');
		                $('#review').val('');
		            }
		        });
			}
		}
	}else{
		alert('请在武大艺协微信公众平台发表留言与评论');
	}
	
});

function insertReview(headimgurl,spokesman,msg){
	var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
	var str = "";
	str+= "<div class=\"attachment\">";
	str+= "<div class=\"item\">";
	str+= "<img src=\""+headimgurl+" class=\"online\"/>";
	str+= "<p class=\"message\">";
	str+= "<a href=\"javascript:void(0)\" class=\"name\" id=\""+openid+"\">";
	str+= "<small class=\"text-muted pull-right\"><i class=\"fa fa-clock-o\"></i> "+time+"</small>";
	str+= "<span>"+spokesman+"</span>";
	str+= "</a>";
	str+= replace_em(msg);
	str+= "</p>";
	if(uid=='admin'){
   	 str+= "<div class=\"operation\" align=\"right\"><a class=\"delete\" href=\"javascript:void(0)\">删除</a>&nbsp;|&nbsp;<a class=\"forbid\" href=\"javascript:void(0)\">禁言</a></div>";
    }
	str+= "</div>";
	str+= "</div>";
	$('div#'+msgid+'.item').append(str);
	
}

//me or home
$('#atme').click(function(){
	$('.msg-body').each(function(){
		if($(this).attr('id')!=openid){
			$(this).hide();
		}
	});
});
$('#home').click(function(){
	$('.msg-body').show();
});


