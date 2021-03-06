<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    <h2><spring:message code="Update.user"/></h2><br>
    <form:form action="updateuser" method="post" commandName="userForm" class="form-horizontal">
        <input type="hidden" name = "actionType" value="${userForm.actionType}">
        <input type="hidden" name = "oldPassword" value="${userForm.oldPassword}">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label"><spring:message code="Name"/></label>
            <div class="col-sm-10">
                <c:choose>
                    <c:when test="${user.username==null}">
                        <input class="form-control" id="username" name="username" type="text"
                               value="${userForm.username}">
                    </c:when>
                    <c:otherwise>
                        <input class="form-control" id="username" name="username" type="text"
                               value="${userForm.username}" disabled>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label"><spring:message code="Password"/></label>
            <div class="col-sm-10">
                <input class="form-control" id="password" name="password" type="password" value="${userForm.password}"
                       placeholder="<spring:message code="Not.changed"/>">
            </div>
        </div>
        <div class="form-group">
            <label for="confirmpassword" class="col-sm-2 control-label"><spring:message code="Password.confirm"/></label>
            <div class="col-sm-10">
                <input class="form-control" id="confirmpassword" name="confirmpassword" type="password"
                       value="${userForm.confirmPassword}" placeholder="<spring:message code="Not.changed"/>">
            </div>
        </div>
        <div class="form-group">

            <label for="email" class="col-sm-2 control-label">E-mail</label>
            <div class="col-sm-10">
                <input class="form-control" id="email" name="email" type="text" value="${userForm.email}">
            </div>

            <%--<spring:bind path="email">--%>
                <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
                    <%--<form:input type="text" path="email" class="form-control" placeholder="E-mail"--%>
                                <%--autofocus="true"></form:input>--%>
                    <%--<form:errors path="email"></form:errors>--%>
                <%--</div>--%>
            <%--</spring:bind>--%>



        </div>
        <div class="form-group">
            <div class="check-box-table-cell col-sm-offset-2 col-sm-10">
                <form:checkbox id="enabled" path="enabled" label="Enabled"/>
            </div>
        </div>
        <div class="form-group">
            <div class="check-box-table-cell col-sm-offset-2 col-sm-10">
                <form:checkboxes path="roleNames" items="${roleNames}"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btn_save_user" class="btn btn-default"><spring:message code="Update"/></button>
            </div>
        </div>
        <a href="users"><spring:message code="Back.to.users"/></a>
        <%@include file="footer.jsp" %>
    </form:form>
</div>
</body>
</html>