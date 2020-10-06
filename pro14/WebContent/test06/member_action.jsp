<%@ page language="java" contentType="text/html; charset=UTF-8"
     import=" java.util.*,sec02_member_AddUpdateDelete.ID_overlap.*"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>    
<html>
<head>
<meta charset="TF-8">
<jsp:useBean  id="m" class="sec02.ID_overlap.MemberBean" />
<jsp:setProperty name="m" property="*"  />
<%
   MemberDAO memDAO = new MemberDAO();
   memDAO.addMember(m); 
   List membersList = memDAO.listMembers();
   request.setAttribute("membersList", membersList);
%> <%-- 조회한 회원 정보를 request에 바인딩 --%> 
</head>
<body>
<jsp:forward  page="membersList.jsp" /> <%-- 다시 memberList.jsp로 포워딩 --%>
</body>
</html>
