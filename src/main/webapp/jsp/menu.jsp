<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SQLCmd</title>
</head>
<body>
<c:forEach items="${commands}" var="command">
    <a href="${command}">${command}</a><br>
</c:forEach>
</body>
</html>