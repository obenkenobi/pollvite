<%@ page import="org.springframework.http.HttpStatus" %>
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
    <%
        int status = response.getStatus();
        String reasonPhrase = HttpStatus.valueOf(status).getReasonPhrase();
    %>
    <h1><% out.println(status); %></h1>
    <h2><% out.println(reasonPhrase); %></h2>
</body>
</html>