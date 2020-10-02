<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true" %>

<!DOCTYPE html> <!-- 페이지 디렉티브 태그의 isErrorPage 속성을 true로 설정해 exception 내장 객체를 이용해서 발생한 예외를 처리한다. 이때 exception 내장 객체는 자바의 Exception 클래스의 인스턴스이다. -->
<html>
<head>
   <title>에러 페이지</title>
</head>
<body>
    ====== toString() 내용 ======= <br>
   <h1><%= exception.toString()  %> </h1> <!-- exception 내장 객체를 사용해 예외 처리를 한다. -->
=============== getMessage()내용 ==========<br>
   <h1><%=exception.getMessage()%> </h1>
   ============= printStackTrace() 내용 =======<br> <!-- ;를 붙이니까 이클립스 콘솔로 예외 메세지를 출력함 -->
   <h1><% exception.printStackTrace(); %> </h1>
   <h3>
   숫자만 입력 가능합니다.다시 시도 하세요.
   <a href='add.html'>다시하기</a>
   </h3>
</body>
</html>
