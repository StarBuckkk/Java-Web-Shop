<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="java.util.*"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<c:url var="url1"  value="/test01/member1.jsp"  > <%-- <c:url> 태그로 이동할 페이지를 설정--%>
  <c:param  name="id" value="hong" /> <%-- 이동할 페이지로 전달할 데이터를 설정 --%>
  <c:param  name="pwd" value="1234" />
  <c:param  name="name" value="홍길동" />
  <c:param  name="email" value="hong@test.com" />
</c:url>
<html>
<head>
<meta charset=”UTF-8">
<title> c:url  태그 실습</title>
</head>
<body>
  <%-- <a href='${contextPath }/test01/member1.jsp'>회원정보출력</a> --%>
<a href='${url1}'>회원정보출력</a> <%-- <c:url> 태그의 변수 이름을 간단하게 설정 --%>
</body>
</html>
