<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> <%-- HashMap으로 저장해서 넘어온 값들은 이름이 길어 사용하기 불편하므로 <c:set> 태그를 이용해 각 값들을 짧은 변수 이름으로 저장 --%>
<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .cls1 {text-decoration:none;}
   .cls2{text-align:center; font-size:30px;}
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<script>
	function fn_articleForm(isLogOn,articleForm,loginForm){
	  if(isLogOn != '' && isLogOn != 'false'){
	    location.href=articleForm; // 로그인 상태이면 글쓰기창으로 이동
	  }else{
	    alert("로그인 후 글쓰기가 가능합니다.")
	    location.href=loginForm+'?action=/board/articleForm.do'; // 로그아웃 상태면 액션값으로 다음에ㅔ 수행할 url을 전달하면서 로그인창으로 이동
	  }
	}
</script>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${articlesList ==null }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${articlesList !=null }" >
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" > <%-- articlesList로 포워딩된 글 목록을 <forEach>태그를 이용해 표시 --%>
     <tr align="center">
	<td width="5%">${articleNum.count}</td> <%-- <forEach> 태그의 varStatus의 count 속성을 이용해 글 번호를 1부터 자동으로 표시 --%>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	  <span style="padding-right:30px"></span> <%-- 왼쪽으로 30px만큼 여백을 준 후 글 제목들을 표시 --%>
	   <c:choose>
	      <c:when test='${article.level > 1 }'> <%-- level 값이 1보다 큰 경우는 자식 글이므로 level 값만큼 부모 글 밑에 공백으로 들여쓰기하여 자식 글임을 표시(답글) --%>
	         <c:forEach begin="1" end="${article.level }" step="1"> <%-- 부모 글 기준으로 왼쪽 여백을 level 값만큼 채워 답글을 부모 글에 대해 들여쓰기 --%>
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <c:otherwise> <%-- level 값이 1보다 작으면 부모 글을 표시 --%>
	            <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a> <%-- 공백 다음에 자식 글을 표시 --%>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%">${article.writeDate}</td> 
	</tr>
    </c:forEach>
     </c:when>
    </c:choose>
</table>
<!-- <a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a> -->
<a  class="cls1"  href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', 
                                                    '${contextPath}/member/loginForm.do')"><p class="cls2">글쓰기</p></a> 
<!-- 1. 현재 로그인 상태를 함수 인자로 미리 전달하고  
	 2. 로그인 상태일 경우 이동할 글쓰기창 요청 url을 인자로 전달
	 3. 로그인 상태가 아닐경우 로그인창 요청 url을 전달

-->
                                                    
                                                    
                                                    
</body>
</html>