<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
</head>
<body>
错误信息：<h4>${(msg)!''}</h4>
<form action="/login" method="post">
    <p>账号：<input type="text" name="username" value="zhangsan"/></p>
    <p>密码：<input type="text" name="password" value="111111"/></p>
    <p><input type="submit" value="登录"/></p>
</form>
</body>
</html>