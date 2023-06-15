<%@ page import="org.springframework.http.HttpStatus" %>
<%@ include file = "meta/index.jsp" %>
<html>
<%@include file="fragments/head.jsp" %>
<body>
    <%
        int status = response.getStatus();
        String reasonPhrase = HttpStatus.valueOf(status).getReasonPhrase();
    %>
    <h1><% out.println(status); %></h1>
    <h2><% out.println(reasonPhrase); %></h2>
</body>
</html>