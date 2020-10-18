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



@Controller("memberController") // @Controller�� �̿��� MemberControllerimpl Ŭ������ ���� id�� memberController�� ���� �ڵ� ����
//@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class); // LoggerFactory Ŭ������ �̿��� Logger Ŭ���� ��ü�� ������
	@Autowired
	private MemberService memberService; // @Autowired�� �̿��� id�� memberService�� ���� �ڵ� ����
	@Autowired
	private MemberVO memberVO ; // @Autowired�� �̿��� id�� memberVO�� ���� �ڵ� ����
	
	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET) // �� �ܰ�� ��û �� �ٷ� �ش� �޼��带 ȣ���ϵ��� ����
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
//		String viewName = (String)request.getAttribute("viewName");
		//System.out.println("viewName: " +viewName);
		logger.info("viewName: " + viewName); // Logger Ŭ������ info() �޼���� ���� �޽��� ������ info�� ����
		logger.debug("viewName: " + viewName); // Logger Ŭ������ debug() �޼���� �α� �޽��� ������ debug�� ����
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName); // veiwName�� <definition> �±׿� ������ ���̸��� ��ġ
		mav.addObject("membersList", membersList);
		
		return mav; // ModelAndView ��ü�� ������ ���̸��� Ÿ���� �丮������ ��ȯ
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception { // ȸ�� ����â���� ���۵� ȸ�� ������ �ٷ� MemberVO ��ü�� ����
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member); // ������ memberVO ��ü�� SQL������ ������ ȸ�� ����� �մϴ�.
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{ // ���۵� id�� ���� id�� ����
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		
		return mav;
	}
	
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
//	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception { // ���Խ��� �̿��� ��û���� Form.do�� ������ form() �޼��带 ȣ��
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              RedirectAttributes rAttr, // RedirectAttributes Ŭ������ �̿��� �α��� ���� �� �ٽ� �α���â���� �����̷�Ʈ�Ͽ� ���� �޽����� ����
		                       HttpServletRequest request, HttpServletResponse response) throws Exception { // �α���â���� ���۵� id�� ��й�ȣ�� MemberVO��ü�� member�� ����
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member); // login() �޼��带 ȣ��鼭 �α��� ������ ����
	
	if(memberVO != null) { // memberVO�� ��ȯ�� ���� ������ ������ �̿��� �α��� ���¸� true�� �Ѵ�.
		    HttpSession session = request.getSession();
		    session.setAttribute("member", memberVO); // ���ǿ� ���� ������ ����
		    session.setAttribute("isLogOn", true); // ���ǿ� �α��� ���¸� true�� ����
		    mav.setViewName("redirect:/member/listMembers.do");
		    
	}else {
		    rAttr.addAttribute("result","loginFailed");
		    mav.setViewName("redirect:/member/loginForm.do"); // �α��� ���� �� �ٽ� �α���â���� �����̷�Ʈ
	}
	
	return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member"); // �α׾ƿ� ��û �� ���ǿ� ����� �α��� ������ ȸ�� ������ ����
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value = "result", required = false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception { // �α���â ��û �� �Ű����� result�� ���۵Ǹ� ���� result�� ���� ���� / ���ʷ� �α���â�� ��û�� ���� �Ű����� result�� ���۵��� �����Ƿ� ����
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
