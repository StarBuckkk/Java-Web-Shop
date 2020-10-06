<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSON 테스트</title>
   <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
      $(function() {
          $("#checkJson").click(function() {
             var jsonStr = '{"age": [22, 33, 44]}'; // 정수형 데이터를 가지는 이름이 age인 배열을 선언
             var jsonInfo = JSON.parse(jsonStr); // parse() 메서드로 배열을 구함
             var output ="회원 나이<br>";
             output += "=======<br>";
        
             for(var i in jsonInfo.age) { // 배열요소(나이)를 차례대로 출력
                output += jsonInfo.age[i]+"<br>";
             }
             $("#output").html(output);
        });
     });
</script>
</head>
<body>
    <a id="checkJson" style="cursor:pointer">출력</a><br><br>
    <div id="output"></div>
</body>
</html>
