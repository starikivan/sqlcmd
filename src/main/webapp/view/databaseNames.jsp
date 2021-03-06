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
    <h2><spring:message code="Databases"/></h2>
    <table class="table">
        <c:forEach items="${databases}" var="database">
            <tr>
                <c:choose>
                    <c:when test ="${database == current}">
                        <td><a href="tables?name=${database}">${database}</a><br></td>
                        <td><spring:message code="Connected"/></td>
                        <td></td>
                    </c:when>
                    <c:otherwise>
                        <td>${database}<br></td>
                        <td></td>
                        <td><a href="dropdatabase?name=${database}"><spring:message code="Drop"/></a><br></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
    <table class="table">
        <tr>
            <td><a href="connect"><spring:message code="Connect"/></a></td>
            <td><a href="createdatabase"><spring:message code="Create.database"/></a></td>
        </tr>
    </table>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>