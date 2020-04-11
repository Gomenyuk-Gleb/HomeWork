<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSTL Sample - Results</title>
</head>
<body>
<h1>File</h1>
<p>All file</p>
<form action="watch" method="get">
<table border="1">
    <c:forEach items="${ordersList}" var="a">
        <tr>
            <td><c:out value="${a.name}"/></td>
            <td><p><a href="save?name=${a.name}">Save </a></p></td>
        </tr>
    </c:forEach>
</table>

<a href="save?name=ALL">Save All </a>
</body>
</html>