<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%>   

<html>
<head>
<meta charset=”UTF-8">
   <title>학점 변환 결과</title>
</head>
<body>
   <c:set var="score" value="${param.score }" /> <%-- param.score를 score 변수에 할당해서 사용 --%>
   <h1>시험점수  <c:out  value="${score}" /></h1><br>
   <c:choose> <%-- 조건이 여러 개인 경우 <c:choose>안의 <c:when> 태그를 이용해 점수를 해당하는 학점으로 변환 --%>
      <c:when test="${score>=90 && score<100 }">
          <h1>A학점입니다.</h1>
      </c:when>    
      <c:when test="${score>=80 && score<90 }">
          <h1>B학점입니다.</h1>
      </c:when> 
      <c:when test="${score>=70 && score<80 }">
          <h1>C학점입니다.</h1>
      </c:when>
      <c:when test="${score>=60 && score<70 }">
          <h1>D학점입니다.</h1>
      </c:when>    
      <c:otherwise>	
          <h1>F학점입니다.</h1>
      </c:otherwise>    
   </c:choose>    
</body>
</html>
