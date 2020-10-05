<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="java.util.*"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
  request.setCharacterEncoding("UTF-8");
%>  

<html>
<head>
<meta charset=”UTF-8">
<title>리다이렉트 페이지</title>
</head>
<body>
<c:redirect  url="/test01/member1.jsp"  > <%-- 리다이렉트할 페이지를 설정 --%>
  <c:param  name="id" value="${'hong'}" /> <%-- 리다이렉트할 페이지로 전달할 매개변수를 설정 --%>
  <c:param  name="pwd" value="${'1234'}" />
  <c:param  name="name" value="${'홍길동'}" />
  <c:param  name="email" value="${'hong@test.com'}" />
</c:redirect>
</body>
</html>
