package com.spring.ex02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserController extends MultiActionController { // MultiActionController를 이용하면 여러 요청명에 대해 한 개의 컨트롤러에 구현된 각 메서드로 처리 할 수 있다.
	// 설정 파일의 userMethodNameResolver 프로퍼티를 사용하려면 반드시 MultiActionController를 상속받아야 한다.
	
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String userID = "";
//		String passwd = "";
//		ModelAndView mav = new ModelAndView();
//		request.setCharacterEncoding("utf-8");
//		userID = request.getParameter("userID");
//		passwd = request.getParameter("passwd");
//
//		mav.addObject("userID", userID); // ModelAndView에 로그인 정보를 바인딩
//		mav.addObject("passwd", passwd);
//		mav.setViewName("result"); // ModelAndView 객체에 포워딩할 JSP 이름을 설정
//		return mav;
//	}
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = "";
		String passwd = "";
		ModelAndView mav = new ModelAndView();
		request.setCharacterEncoding("utf-8");
		userID = request.getParameter("userID");
		passwd = request.getParameter("passwd");
		String viewName = getViewName(request); // getViewName() 메서드를 호출해 요청명에서 확장명 .do를 제외한 뷰이름을 가져옴
		
		mav.addObject("userID", userID); // ModelAndView에 로그인 정보를 바인딩
		mav.addObject("passwd", passwd);
		//mav.setViewName("result");
		mav.setViewName(viewName); // 뷰이름을 지정
	    System.out.println("ViewName:" + viewName);
		return mav;
	}

	public ModelAndView memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
	    ModelAndView mav = new ModelAndView();
	    String id = request.getParameter("id");
	    String pwd = request.getParameter("pwd");
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");

	    mav.addObject("id", id); // 회원 가입창에서 전송된 회원 정보를 addObject() 메서드를 이용해 ModelAndView 객체에 바인딩
	    mav.addObject("pwd", pwd);
	    mav.addObject("name", name);
	    mav.addObject("email", email);
	    mav.setViewName("memberInfo"); // memberInfo로 포워딩
	    return mav;
	}
	
	private  String getViewName(HttpServletRequest request) throws Exception { // request 객체에서 URL 요청명을 가져와 .do를 제외한 요청명을 구함
	      String contextPath = request.getContextPath();
	      String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
	      
	      if(uri == null || uri.trim().equals("") ) {
	         uri = request.getRequestURI();
	      }

	      int begin = 0;
	      
	      if(! ( (contextPath == null) || ("".equals(contextPath) ) ) ) {
	         begin = contextPath.length();
	      }

	      int end;
	      
	      if(uri.indexOf(";") != -1) {
	         end = uri.indexOf(";");
	         
	      }else if(uri.indexOf("?") != -1) {
	         end = uri.indexOf("?");
	         
	      }else {
	         end = uri.length();
	      }

	      String fileName = uri.substring(begin,end);
	      
	      if(fileName.indexOf(".") != -1) {
	         fileName = fileName.substring(0,fileName.lastIndexOf(".") );
	      }
	      
	      if(fileName.lastIndexOf("/") != -1) {
	         fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length() );
	      }
	      return fileName;
	   }

	
	
}
