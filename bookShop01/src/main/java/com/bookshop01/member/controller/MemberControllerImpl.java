package com.bookshop01.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.service.MemberService;
import com.bookshop01.member.vo.MemberVO;

/*
 * 로그인창엣 전송된 id와 비밀번호를 Map에 담아 SQL문으로 전달
 * 로그인하지 않은 상태에서 상품을 주문할 경우 로그인창으로 이동하면서 action 값으로
 * 상품 주문 페이지 요청 URL을 저장하여 세션에 바인딩
 * 로그인 후 다시 action 값을 가져와 상품 주문 페이지로 이동하도록 설정
 * */

@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController { // 상속받고 구현
	@Autowired
	private MemberService memberService; // @Autowired를 이용해 id가 memberService인 빈을 자동 주입
	@Autowired
	private MemberVO memberVO; // @Autowired를 이용해 id가 memberVO인 빈을 자동 주입
	
	@Override
	@RequestMapping(value = "/login.do", method = RequestMethod.POST) 
	public ModelAndView login(@RequestParam Map<String, String> loginMap, // id와 비밀번호를 Map에 저장
			                  HttpServletRequest request, HttpServletResponse response) throws Exception { // /bookshop01/login.do로 요청 시 메인 페이지를 보여 줌
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(loginMap); // login() 메서드를 호출면서 로그인 정보를 SQL문으로 전달
		
		if(memberVO != null && memberVO.getMember_id() != null) { // 조회한 회원 정보를 가져와 isLogOn 속성을 true로 설정하고 memberInfo 속성으로 회원 정보를 저장
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberVO);
			
			String action = (String)session.getAttribute("action");
			
			if(action != null && action.equals("/order/orderEachGoods.do") ) { // 상품 주문 과정에서 로그인했으면 로그인 후 
				mav.setViewName("forward:" + action); // 다시 주문 화면으로 진행하고 
				
			}else {
				mav.setViewName("redirect:/main/main.do"); // 그 외에는 메인 페이지를 표시
			}
			
		}else {
			String message = "아이디나  비밀번호가 틀립니다. 다시 로그인해주세요";
			mav.addObject("message", message);
			mav.setViewName("/member/loginForm");
		}
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		mav.setViewName("redirect:/main/main.do");
		
		return mav;
	}
	
	@Override
	@RequestMapping(value ="/addMember.do", method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute("memberVO") MemberVO _memberVO, // 회원 가입창에서 전송된 회원 정보를 _memberVO에 설정
			                HttpServletRequest request, HttpServletResponse response) throws Exception { // 회원 가입창에서 전송된 회원 정보를 바로 MemberVO 객체에 설정
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
		    memberService.addMember(_memberVO); // 설정된_memberVO 객체를 SQL문으로 전달해 회원 등록을 함
		    message  = "<script>";
		    message += " alert('회원 가입을 마쳤습니다.로그인창으로 이동합니다.');";
		    message += " location.href='" + request.getContextPath() + "/member/loginForm.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message += " alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += " location.href='" + request.getContextPath() + "/member/memberForm.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		
		return resEntity;
	}
	
	@Override
	@RequestMapping(value = "/overlapped.do", method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseEntity resEntity = null;
		String result = memberService.overlapped(id); // id 중복 검사를 한다.
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		
		return resEntity;
	}
}
