<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirecting...</title>
    <%String url = "menu.html";%>
    <meta http-equiv="refresh" content="3;url=<%=url%>">
</head>
<body>
    <p>operation success, redirecting to the menu in 3 seconds</p>
    <a href = "<%=url%>">press here if you are not automatically directed</a>
</body>
</html>
