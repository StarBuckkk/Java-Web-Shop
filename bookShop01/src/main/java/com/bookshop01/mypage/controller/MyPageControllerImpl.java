package com.bookshop01.mypage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.order.vo.OrderVO;

@Controller("myPageController")
@RequestMapping(value = "/mypage")
public class MyPageControllerImpl extends BaseController  implements MyPageController { // 상속받고 구현
	@Autowired
	private MyPageService myPageService; // @Autowired를 이용해 id가 myPageService인 빈을 자동 주입
	
	@Autowired
	private MemberVO memberVO; // @Autowired를 이용해 id가 memberVO인 빈을 자동 주입
	
	@Override
	@RequestMapping(value = "/myPageMain.do", method = RequestMethod.GET) // 마이페이지 최초 화면을 요청
	public ModelAndView myPageMain(@RequestParam(required = false, value = "message")  String message, // 주문 취소 시 결과 메세지를 받음
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu", "my_page"); // 마이페이지 사이드 메뉴로 설정한다.
		
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		
		String member_id = memberVO.getMember_id();
		List<OrderVO> myOrderList = myPageService.listMyOrderGoods(member_id); // 회원 id를 이용해 주문 상품을 조회
		
		mav.addObject("message", message); // 주문 취소 시 결과 메시지를 JSP로 전달
		mav.addObject("myOrderList", myOrderList); // 주문 상품 목록을 JSP로 전달

		return mav;
	}
	
	@Override
	@RequestMapping(value = "/myOrderDetail.do", method = RequestMethod.GET)
	public ModelAndView myOrderDetail(@RequestParam("order_id")  String order_id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		MemberVO orderer=(MemberVO)session.getAttribute("memberInfo");
		
		List<OrderVO> myOrderList = myPageService.findMyOrderInfo(order_id);
		mav.addObject("orderer", orderer);
		mav.addObject("myOrderList", myOrderList);
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/listMyOrderHistory.do", method = RequestMethod.GET)
	public ModelAndView listMyOrderHistory(@RequestParam Map<String, String> dateMap,
			                               HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		String  member_id = memberVO.getMember_id();
		
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		String beginDate = null, endDate = null;
		
		String [] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		dateMap.put("member_id", member_id);
		List<OrderVO> myOrderHistList = myPageService.listMyOrderHistory(dateMap);
		
		String beginDate1[] = beginDate.split("-"); //검색일자를 년,월,일로 분리해서 화면에 전달합니다.
		String endDate1[] = endDate.split("-");
		mav.addObject("beginYear", beginDate1[0] );
		mav.addObject("beginMonth", beginDate1[1] );
		mav.addObject("beginDay", beginDate1[2] );
		mav.addObject("endYear", endDate1[0] );
		mav.addObject("endMonth", endDate1[1] );
		mav.addObject("endDay", endDate1[2] );
		mav.addObject("myOrderHistList", myOrderHistList);
		return mav;
	}	
	
	@Override
	@RequestMapping(value = "/cancelMyOrder.do", method = RequestMethod.POST) // 주문취소 클릭 시 수행
	public ModelAndView cancelMyOrder(@RequestParam("order_id")  String order_id, // 취소할 주문번호를 전달
			                         HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		myPageService.cancelOrder(order_id); // 주문을 취소
		mav.addObject("message", "cancel_order"); // 주문 메시지를 다시 마이페이지 최초 화면으로 전달
		mav.setViewName("redirect:/mypage/myPageMain.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/myDetailInfo.do" ,method = RequestMethod.GET)
	public ModelAndView myDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}	
	
	@Override
	@RequestMapping(value="/modifyMyInfo.do" ,method = RequestMethod.POST)
	public ResponseEntity modifyMyInfo(@RequestParam("attribute")  String attribute, // 수정할 회원 정보 속성을 저장
			                 @RequestParam("value")  String value, // 회원 정보의 속성 값을 저장
			               HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> memberMap = new HashMap<String,String>();
		String val[] = null;
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		String  member_id = memberVO.getMember_id();
		
		if(attribute.equals("member_birth") ){
			val=value.split(",");
			memberMap.put("member_birth_y", val[0] );
			memberMap.put("member_birth_m", val[1] );
			memberMap.put("member_birth_d", val[2] );
			memberMap.put("member_birth_gn", val[3] );
			
		}else if(attribute.equals("tel") ){
			val = value.split(",");
			memberMap.put("tel1", val[0] );
			memberMap.put("tel2", val[1] );
			memberMap.put("tel3", val[2] );
			
		}else if(attribute.equals("hp") ){
			val = value.split(",");
			memberMap.put("hp1", val[0] );
			memberMap.put("hp2", val[1] );
			memberMap.put("hp3", val[2] );
			memberMap.put("smssts_yn", val[3] );
			
		}else if(attribute.equals("email") ){
			val = value.split(",");
			memberMap.put("email1", val[0] );
			memberMap.put("email2", val[1] );
			memberMap.put("emailsts_yn", val[2] );
			
		}else if(attribute.equals("address") ){
			val = value.split(",");
			memberMap.put("zipcode", val[0] );
			memberMap.put("roadAddress",val[1] );
			memberMap.put("jibunAddress", val[2] );
			memberMap.put("namujiAddress", val[3] );
		}else {
			memberMap.put(attribute,value);	
		}
		
		memberMap.put("member_id", member_id);
		
		//수정된 회원 정보를 다시 세션에 저장한다.
		memberVO = (MemberVO)myPageService.modifyMyInfo(memberMap); // 회원 정보 수정 후 다시 갱신된 회원 정보를 조회
		session.removeAttribute("memberInfo"); // 세션에 저장된 기존 회원 정보를 삭제한 후 갱신된 회원 정보를 저장
		session.setAttribute("memberInfo", memberVO);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message  = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}	
	
}
