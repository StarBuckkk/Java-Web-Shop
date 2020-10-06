<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,sec01.Ajax_Basic.*"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
<%
	request.setCharacterEncoding("UTF-8");
%>    
<jsp:useBean  id="m1" class="sec01.Ajax_Basic.MemberBean"/>
<jsp:setProperty name="m1" property="*"  />
<jsp:useBean  id="membersList" class="java.util.ArrayList" />
<jsp:useBean  id="membersMap" class="java.util.HashMap" /> <!-- 회원 정보를 저장할 HashMap 객체를 <useBean> 액션 태그를 이용해 생성 -->
<%	
   membersMap.put("id", "park2");
   membersMap.put("pwd", "4321");
   membersMap.put("name","박지성");
   membersMap.put("email","park2@test.com");
   
   MemberBean m2 = new MemberBean("son", "1234", "손흥민", "son@test.com"); 
   membersList.add(m1);
   membersList.add(m2); 
   membersMap.put("membersList",  membersList);  
%>
<html>
<head>
<meta charset=”UTF-8">
<title>회원 정보 출력창</title>
</head>
<body>
<table border=1  align="center" >
    <tr align=center bgcolor="#99ccff">
      <td width="20%"><b>아이디</b></td> 
      <td width="20%"><b>비밀번호</b></td>
      <td width="20%" ><b>이름</b></td>
      <td width="20%"><b>이메일</b></td>
</tr>
<tr align=center>
      <td>${membersMap.id}</td> <!-- HashMap 이름 뒤에 .(마침표) 연산자로 저장 시 사용한 key를 사용하여 value를 가져온다 -->
      <td>${membersMap.pwd}</td>
      <td>${membersMap.name}</td>
      <td>${membersMap.email }</td> 
</tr>
    <tr align=center>
      <td>${membersMap.membersList[0].id}</td> <!-- HashMap에 저장된 ArrayList에 .으로 접근한 뒤 다시 각각의 속성에 .을 이용해 접근하여 첫 번째 회원 정보를 출력 -->
      <td>${membersMap.membersList[0].pwd}</td>
      <td>${membersMap.membersList[0].name}</td>
      <td>${membersMap.membersList[0].email}</td>
   </tr>
   <tr align=center>
      <td>${membersMap.membersList[1].id}</td>
      <td>${membersMap.membersList[1].pwd}</td>
      <td>${membersMap.membersList[1].name}</td>
      <td>${membersMap.membersList[1].email}</td>
</tr>
</table>
</body>
