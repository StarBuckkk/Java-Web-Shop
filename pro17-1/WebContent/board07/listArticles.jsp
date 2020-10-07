<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> <%-- HashMap으로 저장해서 넘어온 값들은 이름이 길어 사용하기 불편하므로 <c:set> 태그를 이용해 각 값들을 짧은 변수 이름으로 저장 --%>
<c:set  var="articlesList"  value="${articlesMap.articlesList}" />
<c:set  var="totArticles"  value="${articlesMap.totArticles}" />
<c:set  var="section"  value="${articlesMap.section}" />
<c:set  var="pageNum"  value="${articlesMap.pageNum}" />

<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .no-uline {text-decoration:none;}
   .sel-page{text-decoration:none;color:red;}
   .cls1 {text-decoration:none;}
   .cls2{text-align:center; font-size:30px;}
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${empty articlesList}" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${!empty articlesList}" >
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" > <%-- articlesList로 포워딩된 글 목록을 <forEach>태그를 이용해 표시 --%>
     <tr align="center">
	<td width="5%">${articleNum.count}</td> <%-- <forEach> 태그의 varStatus의 count 속성을 이용해 글 번호를 1부터 자동으로 표시 --%>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	    <span style="padding-right:30px"></span> <%-- 왼쪽으로 30px만큼 여백을 준 후 글 제목들을 표시 --%>    
	   <c:choose>
	      <c:when test='${article.level > 1 }'> <%-- level 값이 1보다 큰 경우는 자식 글이므로 level 값만큼 부모 글 밑에 공백으로 들여쓰기하여 자식 글임을 표시 --%>  
	         <c:forEach begin="1" end="${article.level }" step="1"> <%-- 부모 글 기준으로 왼쪽 여백을 level 값만큼 채워 답글을 부모 글에 대해 들여쓰기 --%>
	             <span style="padding-left:10px"></span> 
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <c:otherwise>
	            <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a> <%-- 공백 다음에 자식 글을 표시 --%>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%"><fmt:formatDate value="${article.writeDate}" /></td> 
	</tr>
    </c:forEach>
     </c:when>
    </c:choose>
</table>

<div class="cls2">
 <c:if test="${totArticles != null }" > <%-- 전체 클 수에 따라 페이징 표시를 다르게 함 --%>
      <c:choose>
        <c:when test="${totArticles >100 }">  <!-- 글 개수가 100 초과인경우 -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	         <c:if test="${section >1 && page==1 }">
	          <a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; pre </a> <%-- 섹션 값 2부터는 앞 섹션으로 이동할 수 있는 pre를 표시 --%>
	         </c:if>
	          <a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
	         <c:if test="${page ==10 }">
	          <a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section+1}&pageNum=${section*10+1}">&nbsp; next</a> <%-- 페이지 번호 10 오른쪽에는 다음 섹션으로 이동할 수 있는 next를 표시 --%>
	         </c:if>
	      </c:forEach>
        </c:when>
        <c:when test="${totArticles ==100 }" >  <!--등록된 글 개수가 100개인경우는 첫 번째 섹션의 10개 페이지만 표시하면 됨  -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	        <a class="no-uline"  href="#">${page } </a>
	      </c:forEach>
        </c:when>
        
        <c:when test="${totArticles< 100 }" >   <!--등록된 글 개수가 100개 미만인 경우  -->
	      <c:forEach   var="page" begin="1" end="${totArticles/10 +1}" step="1" > <%-- 글 수가 100개가 되지 않으므로 표시되는 페이지는 10개가 되지 않고, 전체 클 수를 10으로 나누어 구한 몫에 1을 더한 페이지까지 표시 --%>
	         <c:choose>
	           <c:when test="${page==pageNum }">
	            <a class="sel-page"  href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${page } </a> <%-- 페이지 번호와 컨트롤러에서 넘어온 pageNum이 같은 경우 페이지 번호를 빨간색으로 표시하여 현재 사용자가 보고 있는 페이지임을 알림 --%>
	          </c:when>
	          <c:otherwise>
	            <a class="no-uline"  href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${page } </a> <%-- 페이지 번호를 클릭하면 section 값과 pageNum 값을 컨트롤러로 전송 --%>
	          </c:otherwise>
	        </c:choose>
	      </c:forEach>
        </c:when>
      </c:choose>
    </c:if>
</div>    
<br><br>
<a  class="cls1"  href="${contextPath}/board/articleForm.do"><p class="cls2">글쓰기</p></a> <%-- 글쓰기를 클릭하면 글쓰기창인  articleForm.do가 나타남 --%>
</body>
</html>