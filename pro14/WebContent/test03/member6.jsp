<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="java.util.*"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  List dataList = new ArrayList();
  dataList.add("hello");
  dataList.add("world");
  dataList.add("안녕하세요!!");
%>
<c:set  var="list"  value="<%=dataList  %>" /> <%-- 표현 언어에서 사용할 수 있도록 <c:set> 태그를 이용해 변수에 dataList를 할당 --%>
<html>
<head>
<meta charset=”UTF-8">
<title>반복문 실습</title>
</head>
<body>
   <c:forEach  var="i" begin="1" end="10" step="1"  varStatus="loop">
    i=  ${i}   &nbsp;&nbsp;&nbsp;  반복횟수: ${loop.count} <br>
   </c:forEach>
 <br>
   <c:forEach  var="i" begin="1" end="10" step="2" >
        5 * ${i} = ${5*i}<br>
   </c:forEach>
<br>
   <c:forEach  var="data" items="${list}" > <%-- ArrayList같은 컬렉션 객체에 저장된 객체(데이터)를 반복해서 반복 변수 data에 하나씩 가져와 처리 --%>
       ${data } <br> 
   </c:forEach>
<br>
<c:set var="fruits" value="사과, 파인애플, 바나나, 망고, 귤"  />
<c:forTokens  var="token" items="${fruits}" delims="," > <%-- 구분자 ,(콤마)를 이용해 문자열을 분리해서 출력 --%>
    ${token} <br> 
</c:forTokens>
</body>
</html>
