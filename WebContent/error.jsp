<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>错误页</title>
 <style>
            body {
                background-color: #ececec;
            }
      
     		 .info {
                text-align: center;
                text-color: #ffaaaa
            }
        </style>
</head>
<body>
<div class="info"><img alt="出错了！" src="${baseViewPicUrl}error.gif"></div>
<div class="info"><span>粗大事了~${info}</span></div>
</body>
</html>