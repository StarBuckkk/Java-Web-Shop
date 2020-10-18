package com.spring.ex01; // �ֳ����̼��� ����ǵ��� �Ϸ��� �ش� Ŭ������ �ݵ�� <component-scan>���� ������ ��Ű���� ���� ��Ű���� �����ؾ� �Ѵ�.

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("mainController") // @Controller �ֳ����̼��� �̿��� MainController Ŭ������ ������ �ڵ� ��ȯ�Ѵ�. �� id�� mainController �̴�.
@RequestMapping("/test") // @RequestMapping�� �̿��� ù ��° �ܰ��� URL ��û�� /test�̸� mainController ���� ��û
public class MainController {
	@RequestMapping(value = "/main1.do" , method = RequestMethod.GET)
	public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView(); // @RequestMapping�� �̿��� �� ��° �ܰ��� URL ��û�� /main1.do�̸� mainController ���� main1() �޼��忡�� ��û
		mav.addObject("msg", "main1"); // method=RequestMethod.GET���� �����ϸ� GET ������� ��û �� �ش� �޼��尡 ȣ��
		mav.setViewName("main");
		
		return mav;
   }

	@RequestMapping(value = "/main2.do" , method = RequestMethod.GET)
	public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView(); // @RequestMapping�� �̿��� �� ��° �ܰ��� URL ��û�� /main2.do�̸� mainController ���� main2() �޼��忡�� ��û
		mav.addObject("msg", "main2"); // method=RequestMethod.GET���� �����ϸ� GET ������� ��û �� �ش� �޼��尡 ȣ��
		mav.setViewName("main");
		
		return mav;
	}
}
