<%--@elvariable id="statusCode" type="java.lang.String"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pollvite</title>
    <link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <h1>${statusCode} error</h1>
    <h2>${message}</h2>
</body>
</html>