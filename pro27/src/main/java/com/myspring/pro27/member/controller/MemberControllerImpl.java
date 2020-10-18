package com.myspring.pro27.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.service.MemberService;
import com.myspring.pro27.member.vo.MemberVO;



@Controller("memberController") // @Controller를 이용해 MemberControllerimpl 클래스에 대해 id가 memberController인 빈을 자동 생성
//@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class); // LoggerFactory 클래스를 이용해 Logger 클래스 객체를 가져옴
	@Autowired
	private MemberService memberService; // @Autowired를 이용해 id가 memberService인 빈을 자동 주입
	@Autowired
	private MemberVO memberVO ; // @Autowired를 이용해 id가 memberVO인 빈을 자동 주입
	
	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET) // 두 단계로 요청 시 바로 해당 메서드를 호출하도록 매핑
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
//		String viewName = (String)request.getAttribute("viewName");
		//System.out.println("viewName: " +viewName);
		logger.info("viewName: " + viewName); // Logger 클래스의 info() 메서드로 ㄹ그 메시지 레벨을 info로 설정
		logger.debug("viewName: " + viewName); // Logger 클래스의 debug() 메서드로 로그 메시지 레벨을 debug로 설정
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName); // veiwName이 <definition> 태그에 설정한 뷰이름과 일치
		mav.addObject("membersList", membersList);
		
		return mav; // ModelAndView 객체에 설정한 뷰이름을 타일즈 뷰리졸버로 반환
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception { // 회원 가입창에서 전송된 회원 정보를 바로 MemberVO 객체에 설정
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member); // 설정된 memberVO 객체를 SQL문으로 전달해 회원 등록을 합니다.
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{ // 전송된 id를 변수 id에 설정
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
//	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception { // 정규식을 이용해 요청명이 Form.do로 끝나면 form() 메서드를 호출
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		return mav;
	}
	
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
		    mav.setViewName("redirect:/member/listMembers.do");
		    
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
	private ModelAndView form(@RequestParam(value = "result", required = false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception { // 로그인창 요청 시 매개변수 result가 전송되면 변수 result에 값을 저장 / 최초로 로그인창을 요청할 때는 매개변수 result가 전송되지 않으므로 무시
		//String viewName = getViewName(request);
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		
		return mav;
	}
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		
		if (uri == null || uri.trim().equals("") ) {
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
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length() );
		}
		
		return viewName;
	}


}
