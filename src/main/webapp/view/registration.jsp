<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>SQLCmd</title>
</head>
<body>
    <h2>New user</h2><br>
    <form action="registration" method="post">
        <table>
            <tr>
                <td>Login</td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Registration"></td>
            </tr>
        </table>
    </form><br>
    <%@include file="footer.jsp" %>
</body>
</html>