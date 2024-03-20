<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirect to updated patient</title>
    <%String url = "patientinfo.html?id=" + request.getAttribute("id");%>
    <meta http-equiv="refresh" content="1;url=<%=url%>">
</head>
<body>
    <p>edit successful, redirect to updated patient in 1 seconds</p>
    <a href = "<%=url%>">press here if you are not automatically directed</a>
</body>
</html>
