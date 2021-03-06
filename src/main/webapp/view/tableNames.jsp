<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SQLCmd</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="container">
    <h2><spring:message code="Tables"/></h2>
    <table class = "table">
        <c:forEach items="${tables}" var="table">
            <tr>
                <td><a href="table?name=${table}">${table}</a><br></td>
            </tr>
        </c:forEach>
    </table>
    <a href="createtable"><spring:message code="Create.table"/></a><br>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>