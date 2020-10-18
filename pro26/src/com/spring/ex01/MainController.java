package com.spring.ex01; // 애너테이션이 적용되도록 하려면 해당 클래스가 반드시 <component-scan>에서 설정한 패키지나 하위 패키지에 존재해야 한다.

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("mainController") // @Controller 애너테이션을 이용해 MainController 클래스를 빈으로 자동 변환한다. 빈 id는 mainController 이다.
@RequestMapping("/test") // @RequestMapping을 이용해 첫 번째 단계의 URL 요청이 /test이면 mainController 빈을 요청
public class MainController {
	@RequestMapping(value = "/main1.do" , method = RequestMethod.GET)
	public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView(); // @RequestMapping을 이용해 두 번째 단계의 URL 요청이 /main1.do이면 mainController 빈의 main1() 메서드에게 요청
		mav.addObject("msg", "main1"); // method=RequestMethod.GET으로 지정하면 GET 방식으로 요청 시 해당 메서드가 호출
		mav.setViewName("main");
		
		return mav;
   }

	@RequestMapping(value = "/main2.do" , method = RequestMethod.GET)
	public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView(); // @RequestMapping을 이용해 두 번째 단계의 URL 요청이 /main2.do이면 mainController 빈의 main2() 메서드에게 요청
		mav.addObject("msg", "main2"); // method=RequestMethod.GET으로 지정하면 GET 방식으로 요청 시 해당 메서드가 호출
		mav.setViewName("main");
		
		return mav;
	}
}
