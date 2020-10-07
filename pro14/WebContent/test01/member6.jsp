<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
<%
  request.setCharacterEncoding("UTF-8");
%>    
<jsp:useBean  id="m" class="sec01.ex01.ex02.MemberBean" />
<jsp:setProperty  name="m" property="*" />
<jsp:useBean   id="addr" class="sec01.ex01.ex02.Address"/>
<jsp:setProperty   name="addr" property="city" value="서울"/> <!-- Address 빈을 생성한 후 도시(city)와 우편번호(zipcode)를 설정 -->
<jsp:setProperty   name="addr" property="zipcode" value="07654"/>
<%
  m.setAddr(addr);
%> 
<html>
<head>
<meta charset=”UTF-8">
<title>회원 정보 출력창</title>
</head>
<body>
<table border=1 align="center"   >
   <tr align="center"  bgcolor="#99ccff" >
      <td width="7%"><b>아이디</b></td>
      <td width="7%"><b>비밀번호</b></td>
      <td width="5%" ><b>이름</b></td>
      <td width="5%"><b>이메일</b></td>
<td width="5%" ><b>도시</b></td>
      <td width="5%" ><b>우편번호</b></td>
   </tr>
   <tr align="center">
      <td>${m.id } </td>
      <td>${m.pwd } </td>
      <td>${m.name } </td>
      <td>${m.email}</td>
      <td><%=m.getAddr().getCity() %></td> <!-- 표현식을 이용해 속성들의 getter를 두 번 호출해서 주소를 출력 -> 이 방식은 불편 -->
      <td><%=m.getAddr().getZipcode() %></td>   
   </tr>
   <tr align="center">
      <td>${m.id } </td>
      <td>${m.pwd } </td>
      <td>${m.name} </td>
      <td>${m.email}</td>
<td>${m.addr.city}</td> <!-- 자바 빈의 속성 이름과 .연산자를 이용해 주소를 출력 -->
      <td>${m.addr.zipcode}</td>
   </tr>
</table>
</body>
</html>
