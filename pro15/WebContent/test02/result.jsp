<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>       
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<meta charset=”UTF-8">
<c:set var="file1" value="${param.param1}"  />    
<c:set var="file2" value="${param.param2}"  />
 
<title>이미지 파일 출력하기</title>
</head>
<body>

파라미터 1 :<c:out value="${file1}"  /><br> <!-- 다운로드할 파일 이름을 가져옴  -->
파라미터 2 :<c:out value="${file2}"  /><br>

<c:if test="${not empty file1 }">
<img src="${contextPath}/download.do?fileName=${file1}"  width=300 height=300 /><br> <%-- 파일 이름으로 서블릿에서 이미지를 다운로드해 표시 --%>
</c:if>
<br>
<c:if test="${not empty file2 }">
<img src="${contextPath}/download.do?fileName=${file2}"  width=300 height=300 /><br> <%-- 파일 이름으로 서블릿에서 이미지를 다운로드해 표시 --%>
</c:if>
파일 내려받기 :<br>
<a href="${contextPath}/download.do?fileName=${file1}" >파일 내려받기</a><br> <%-- 이미지를 파일로 다운로드 --%>
<a href="${contextPath}/download.do?fileName=${file2}" >파일 내려받기</a><br> <%-- 이미지를 파일로 다운로드 --%>
</body>
</html>
