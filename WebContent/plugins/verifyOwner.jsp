
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../error.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>检票</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="keywords" content="武大艺协" />
<meta name="description" content="武大艺协" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="武大艺协">
<meta name="author" content="mobangjack">

<!-- CSS -->
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">
	
</head>
 <body>

<div class="page-container">
    <h1>检票</h1>
    <form action="" method="get">
    	<input type="hidden" name="openid" id="openid" value="${openid }">
        <input type="password" name="key" id="key" placeholder="检票密匙">
        <div>
        <button type="button" id="check"><span>确认检票</span></button>
        </div>
        
    </form>
    <div class="connect">
   		<p>Or visit:</p>
        <p>
            <a class="home" href="/MainServlet?page=main/main.jsp&openid=${openid}"></a>
        </p>
    </div>
</div>

<!-- Javascript -->
<script src="assets/js/jquery-1.8.2.min.js"></script>
<script src="assets/js/supersized.3.2.7.min.js"></script>
<script src="assets/js/supersized-init.js"></script>
<script src="assets/js/verifyOwner.js"></script>

</body>
</html>