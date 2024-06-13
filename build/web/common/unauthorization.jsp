<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Unauthorized Access</title>
</head>
<body>
    <h1>Unauthorized Access</h1>
    <p>You do not have permission to access this page.</p>
    <a href="<%= request.getContextPath() %>/login">Login</a>
</body>
</html>
