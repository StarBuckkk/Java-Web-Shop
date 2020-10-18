<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="java.io.*"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 다국어들을 표시하기 위해 <spring:message> 태그를 이용할 수 있도록 설정 --%>
<%
  request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="site.title" text="Member Info" /></title> <!-- code 속서에 프로퍼티 파일의 site.tile 값을 표시 -->
</head>
<body>
<a href="${pageContext.request.contextPath }/test/locale.do?locale=ko">한국어</a> <!-- 한국어를 요청 -->
<a href="${pageContext.request.contextPath }/test/locale.do?locale=en">ENGLISH</a> <!-- 영어를 요청 -->
 <h1><spring:message code="site.title" text="Member Info" /></h1>
 <p><spring:message code="site.name" text="no name" /> : <spring:message code="name" text="no name" /></p> <!-- 프로퍼티 site.name에 해당하는 값을 표시 / 프로퍼티 name에 해당하는 값을 표시 -->
 <p><spring:message code="site.job" text="no job" />   : <spring:message code="job" text="no job" /></p>


<input type=button value="<spring:message code='btn.send' />" /> <!-- 태그를 이용해 프로퍼티 btn.send를 버튼 이름으로 설정 -->
<input type=button value="<spring:message code='btn.cancel' />" />
<input type=button value="<spring:message code='btn.finish' />" />

</body>
</html>