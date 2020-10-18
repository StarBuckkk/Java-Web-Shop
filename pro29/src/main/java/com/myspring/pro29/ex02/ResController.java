package com.myspring.pro29.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResController { // ResController로 지정되지 않음
	@RequestMapping(value = "/res1")
	@ResponseBody // 메서드 호출 시 데이터를 전송하도록 설정
	public Map<String, Object> res1() { // Map 데이터를 브라우저로 전송
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "hong");
		map.put("name", "홍길동");
		return map;
	}
	
	@RequestMapping(value = "/res2") // 메서드 호출 시 home.jsp를 브라우저로 전송
	public ModelAndView res2() {
		return new ModelAndView("home");
	}
	
}
