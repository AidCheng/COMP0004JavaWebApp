<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<h1>Error</h1>
<p>Wrong format of date (yyyy-MM-dd) or empty name, returning to the menu and try again</p>
    <%int id = (int) request.getAttribute("id");
      String href = "patientinfo.html?id=" + id;%>
<a href="<%=href%>">Last page</a>
</body>
</html>