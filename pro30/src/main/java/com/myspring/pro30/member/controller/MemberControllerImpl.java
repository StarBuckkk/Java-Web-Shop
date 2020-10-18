package com.myspring.pro30.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;




@Controller("memberController") // @Controller를 이용해 MemberControllerimpl 클래스에 대해 id가 memberController인 빈을 자동 생성
//@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
	@Autowired
	private MemberService memberService; // @Autowired를 이용해 id가 memberService인 빈을 자동 주입
	@Autowired
	private MemberVO memberVO ; // @Autowired를 이용해 id가 memberVO인 빈을 자동 주입
	 
	@RequestMapping(value = { "/","/main.do"}, method = RequestMethod.GET) // /pro30/main.do로 요청 시 메인 페이지를 보여 줌
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET) // 두 단계로 요청 시 바로 해당 메서드를 호출하도록 매핑
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String)request.getAttribute("viewName");
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName); // veiwName이 <definition> 태그에 설정한 뷰이름과 일치
		mav.addObject("membersList", membersList);
		
		return mav;
	}

	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception { // 회원 가입창에서 전송된 회원 정보를 바로 MemberVO 객체에 설정
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		int result = 0;
		result = memberService.addMember(member); // 설정된 memberVO 객체를 SQL문으로 전달해 회원 등록을 합니다.
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception { // 전송된 id를 변수 id에 설정
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	/*
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception { // 정규식을 이용해 요청명이 Form.do로 끝나면 form() 메서드를 호출
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	/*
	 * 1. 글목록창(listArticle.jsp) 요청 시 미리 세션의 isLogOn 속성을 자바스크립트이 함수의 인자로 저장
	 * 2. 글쓰기를 클릭하면 자바스크립트 함수에서 isLogOn 속성 값을 체크하여 true 아니면 memberController로 로그인창을 요청하면서 다음에 수행항 url을 action 값으로 전송
	 * 3. memberController는 action 값을 세션에 저장
	 * 4. 로그인창에서 id와 비밀번호를 입력하여 memberController로 전송한 후 로그인에 성공하면 세션의 action 속성 값을 가져와서 글쓰기창으로 바로 이동
	 * 5. isLogOn 속성이 true 이면 바로 글쓰기 창으로 이동
	 * */
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              RedirectAttributes rAttr, // RedirectAttributes 클래스를 이용해 로그인 실패 시 다시 로그인창으로 리다이렉트하여 실패 메시지를 전달
		                       HttpServletRequest request, HttpServletResponse response) throws Exception { // 로그인창에서 전송된 id와 비밀번호를 MemberVO객체인 member에 저장
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member); // login() 메서드를 호출면서 로그인 정보를 전달
	
	if(memberVO != null) { // memberVO로 반환된 값이 있으면 세션을 이용해 로그인 상태를 true로 한다.
	    HttpSession session = request.getSession();
	    session.setAttribute("member", memberVO); // 세션에 히원 정보를 저장
	    session.setAttribute("isLogOn", true); // 세션에 로그인 상태를 true로 설정
	    //mav.setViewName("redirect:/member/listMembers.do");
	    String action = (String)session.getAttribute("action"); // 로그인 성공 시 세션에 저장된 action 값을 가져옴
	    session.removeAttribute("action");
	    
	    if(action!= null) { // 액션이 null이 아니면 액션값을 뷰이름으로 지정해 글쓰기창으로 이동
	       mav.setViewName("redirect:" + action); 
	       
	    }else { 
	       mav.setViewName("redirect:/member/listMembers.do"); 
	    }

	}else {
	   rAttr.addAttribute("result","loginFailed");
	   mav.setViewName("redirect:/member/loginForm.do"); // 로그인 실패 시 다시 로그인창으로 리다이렉트
	}
	return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member"); // 로그아웃 요청 시 세션에 저장된 로그인 정보와 회원 정보를 삭제
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							  @RequestParam(value= "action", required=false) String action,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception { // 로그인창 요청 시 매개변수 result가 전송되면 변수 result에 값을 저장 / 최초로 로그인창을 요청할 때는 매개변수 result가 전송되지 않으므로 무시
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);  
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		
		return mav;
	}
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		
		if (! ( (contextPath == null) || ("".equals(contextPath) ) ) ) {
			begin = contextPath.length();
		}

		int end;
		
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
			
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
			
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf(".") );
		}
		
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length() ); // /member/listMembers.do로 요청할 경우 member/listMember를 파일 이름으로 가져옴
		}
		return viewName;
	}


}
