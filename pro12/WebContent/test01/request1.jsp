<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="javax.servlet.RequestDispatcher"
    pageEncoding="UTF-8"
    %>
<%
  request.setAttribute("name","이순신"); // RequestDispatcher를 이용해 request 객체에 setAttribute()를 이용해 name과 address 를 바인딩
  request.setAttribute("address","서울시 강남구"); 
%>       

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>첫 번째 JSP</title>
</head>
<body>

<%
  RequestDispatcher dispatch = request.getRequestDispatcher("request2.jsp"); // request 객체를 다른 jsp로 포워딩  
  dispatch.forward(request, response);
%>
</body>
</html>
