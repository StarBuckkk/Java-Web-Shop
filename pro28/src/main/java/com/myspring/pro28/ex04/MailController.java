package com.myspring.pro28.ex04;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAsync // @EnableAsync를 지정해서 메서드를 호출할 경우 비동기로 동작
public class MailController {
    @Autowired
    private MailService mailService;
 
    @RequestMapping(value = "/sendMail.do", method = RequestMethod.GET)
    public void sendSimpleMail(HttpServletRequest request, HttpServletResponse response) 
                                                          throws Exception{
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	StringBuffer sb = new StringBuffer(); // StringBuffer 변수 sb를 선언
       
    	sb.append("<html><body>"); // 문자열로 HTML 태그를 작성한 후 sb에 저장
 		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
 		sb.append("<h1>" + "제품소개"+"<h1><br>");
 		sb.append("신간 도서를 소개합니다.<br><br>");
 		sb.append("<a href='http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788956746425&orderClick=LAG&Kc=#N'>");
 		sb.append("<img  src='http://image.kyobobook.co.kr/images/book/xlarge/425/x9788956746425.jpg' /> </a><br>");
 		sb.append("</a>");
 		sb.append("<a href='http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788956746425&orderClick=LAG&Kc=#N'>상품보기</a>");
 		sb.append("</body></html>");
 		String str = sb.toString(); // 문자열로 반환
 		mailService.sendMail("수신자@naver.com","신상품을 소개합니다.", str); // html 형식의 내용을 메일로 보냄
      
        out.print("메일을 보냈습니다!!");
    }
}


