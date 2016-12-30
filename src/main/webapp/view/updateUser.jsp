<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    <h2>Edit user</h2><br>
    <form action="updateuser" method="post" class="form-horizontal">
        <input type="hidden" name = "name" value="${user.name}">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input class="form-control" id="name" name="name" type="text" value="${user.name}">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input class="form-control" id="password" name="password" type="text" value="${user.password}">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">E-mail</label>
            <div class="col-sm-10">
                <input class="form-control" id="email" name="email" type="text" value="${user.email}">
            </div>
        </div>
        <div class="form-group">
            <div class="checkbox col-sm-offset-2 col-sm-10">
                <label><input id="enabled" name="enabled" type="checkbox" value="${user.enabled}">Enabled</label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btn_save_user" name="btn_save_user" class="btn btn-default">Save</button>
            </div>
        </div>
        <a href="users">Back to users</a>
        <%@include file="footer.jsp" %>
    </form>
</div>
</body>
</html>