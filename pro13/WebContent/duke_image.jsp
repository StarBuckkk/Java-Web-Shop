<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("utf-8"); // param 액션 태그로 전달된 매개변수를 getParameter() 메서드를 이용해 가져옴
   String name = request.getParameter("name" ) ;
   String imgName = request.getParameter("imgName" ) ;
%> 

<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>듀크이미지</title>
</head>
<body>
<br><br>
  <h1>이름은 <%=name%>입니다. </h1><br><br>
  <img src="./image/<%=imgName %>"  />
</body>
</html>
