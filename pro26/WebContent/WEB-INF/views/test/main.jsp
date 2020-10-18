<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<%
  request.setCharacterEncoding("UTF-8");
%>    


<html> <!-- 컨트롤러에서 ModelAndView에 뷰이름으로 설정한 main.jsp -->
<head>
<meta charset=UTF-8">
<title>결과창</title>
</head>
<body>
<h1>안녕하세요!!</h1>
<h1>${msg} 페이지입니다!!</h1> <!-- 컨트롤러에서 넘긴 메세지를 출력 -->
</body>
</html>
