package com.bookshop01.admin.order.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.admin.order.service.AdminOrderService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.mypage.controller.MyPageController;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.order.vo.OrderVO;

@Controller("adminOrderController")
@RequestMapping(value="/admin/order")
public class AdminOrderControllerImpl extends BaseController  implements AdminOrderController{ // 상속, 구현
	@Autowired
	private AdminOrderService adminOrderService; // @Autowired를 이용해 id가 adminOrderService인 빈을 자동 주입
	
	/*
	 * 고객이 어떤 상품을 맨 처음 주문하면 그 상품의 배송 상태는 '배송 준비 중' 으로 초기화 된다.
	 * 
	 *  쇼핑몰 주문 테이블의 배송 상태를 나타내는 delivery_state 컬럼의 값들
	 * 	배송준비 중 / 배송 중 / 배송 완료 / 주문 취소 / 반품 
	 * 
	 * */
	
	@Override
	@RequestMapping(value = "/adminOrderMain.do", method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView adminOrderMain(@RequestParam Map<String, String> dateMap,
			                          HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		String beginDate = null;
		String endDate = null;
		
		String [] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		
		HashMap<String,Object> condMap = new HashMap<String,Object>();
		
		if(section == null) {
			section = "1";
		}
		
		condMap.put("section",section);
		
		if(pageNum == null) {
			pageNum = "1";
		}
		
		condMap.put("pageNum", pageNum);
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		List<OrderVO> newOrderList = adminOrderService.listNewOrder(condMap);
		mav.addObject("newOrderList", newOrderList);
		
		String beginDate1[] = beginDate.split("-");
		String endDate2[] = endDate.split("-");
		mav.addObject("beginYear", beginDate1[0] );
		mav.addObject("beginMonth", beginDate1[1] );
		mav.addObject("beginDay", beginDate1[2] );
		mav.addObject("endYear", endDate2[0] );
		mav.addObject("endMonth", endDate2[1] );
		mav.addObject("endDay", endDate2[2] );
		
		mav.addObject("section", section);
		mav.addObject("pageNum", pageNum);
		return mav;
		
	}
	
	@Override
	@RequestMapping(value = "/modifyDeliveryState.do", method = {RequestMethod.POST} )
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap, // Ajax로 전달받은 배송 상태를 맵에 저장 
			                        HttpServletRequest request, HttpServletResponse response) throws Exception {
		adminOrderService.modifyDeliveryState(deliveryMap); // 배송 상태를 변경
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message  = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
		
	}
	
	@Override
	@RequestMapping(value = "/orderDetail.do", method = {RequestMethod.GET,RequestMethod.POST} ) // 주문 상품의 상세 정보를 조회
	public ModelAndView orderDetail(@RequestParam("order_id") int order_id, 
			                      HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		Map orderMap = adminOrderService.orderDetail(order_id);
		mav.addObject("orderMap", orderMap);
		return mav;
	}
	
}
