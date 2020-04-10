<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSTL Sample - Results</title>
</head>
<body>
<h1>File</h1>
<p>HELP</p>
<table border="1">
    <c:forEach items="${filePost}" var="a">
        <tr>
            <td><c:out value="${a.fileName}"/></td>
            <td><c:out value="${a.size}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>