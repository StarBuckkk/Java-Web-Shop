<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
  request.setCharacterEncoding("utf-8");
  int score=Integer.parseInt(request.getParameter("score")); // 전송된 시험 점수를 가져옴
%> 

<!DOCTYPE html>
<html>
	<head>
		<title>점수 출력창</title>
		<meta charset="UTF-8">
	</head>
	<body>
		<h1>시험점수  <%=score %>점</h1>
		<br>
			<%
 			if(score >= 90) { // 90점 이상이면 A를 출력
			%>
		
   		<h1>A학점입니다.</h1>
		
			<%
 			}else if(score >= 80 && score < 90) { // B출력
			%>
		
    	<h1> B학점입니다.</h1>
		
			<%
   			}else if(score >= 70 && score < 80) { // C출력
			%>
   
   		<h1> C학점입니다.</h1>
		
			<%
   			}else if(score >= 60 && score < 70) { // D출력
			%>
   		
   		<h1> D학점입니다.</h1>
		
			<%
   			}else {
			%>
   		
   		<h1> F학점입니다.</h1> <!-- 그 외 점수 -->
		
			<%
   			}
			%>
			
		<br>
			<a href="scoreTest.html">시험점수입력</a>
	</body>
</html>
