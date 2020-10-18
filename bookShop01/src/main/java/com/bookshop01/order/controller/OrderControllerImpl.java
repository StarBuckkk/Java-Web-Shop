package com.bookshop01.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.service.OrderService;
import com.bookshop01.order.vo.OrderVO;

@Controller("orderController")
@RequestMapping(value = "/order")
public class OrderControllerImpl extends BaseController implements OrderController { // 상속받고 구현
	@Autowired
	private OrderService orderService; // @Autowired를 이용해 id가 orderService인 빈을 자동 주입
	@Autowired
	private OrderVO orderVO; // @Autowired를 이용해 id가 orderVO인 빈을 자동 주입
	
	/* 1. 바로 주문하기
	 * 상품 주문은 로그인을 한 상태에서만 가능 만약 로그인하지 않은 상태에서 주문하기를 클릭할 경우 로그인창으로 이동
	 * 주문하기를 클릭하면 브라우저에서 전송된 주문 상품 정보를 ArrayList에 저장해 세션에 바인딩한 후 주문 페이지로 이동
	 * 주문 페이지에서 수령자 정보와 배송지 정보를 입력받음. 최종 주문 시 컨트롤러에서 미리 세션에 저장된 주문 상품을 가져와
	 * 전송된 수령자 정보와 배송지 정보를 합침
	 * 주문 정보를 합친 최종 ArrayList를 SQL문으로 전달하여 주문 처리
	 * */
	
	/* 2. 장바구니 상품 주문하기
	 * 장바구니 페이지를 나타내기 전 장바구니에 추가된 상품 정보를 미리 세션에 저장
	 * 장바구니 페이지에서 주문할 상품을 선택한 후 주문할 상품 번호와 각 상품 주문 수량을 배열에 담아 컨트롤러에 전송
	 * 컨트롤러에서는 전송된 상품 번호와 세션에 저장된 상품들의 상품 번호를 비교해 같으면 상품 정보를 주문 목록의 OrderVO 속성에 설정
	 * 전송된 각 상품별 주문 수량을 OrderVO 속성에 설정
	 * 다시 OrderVO 객체를 myOrderList에 저장한 후 세션에 바인딩 
	 * */
	
	@RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();
		
		Boolean isLogOn = (Boolean)session.getAttribute("isLogOn");
		String action = (String)session.getAttribute("action");
		//로그인 여부 체크
		//이전에 로그인 상태인 경우는 주문과정 진행
		//로그아웃 상태인 경우 로그인 화면으로 이동
		if(isLogOn == null || isLogOn == false) { // 로그인을 하지 않았다면 먼저 로그인 후 주문을 처리하도록
			session.setAttribute("orderInfo", _orderVO);  // 주문정보와
			session.setAttribute("action", "/order/orderEachGoods.do"); // 주문 페이지 요청 URL을 세션에 저장
			
			return new ModelAndView("redirect:/member/loginForm.do");
			
		}else { 
			
			 if(action != null && action.equals("/order/orderEachGoods.do") ) { // 로그인 후 세션에서 주문 정보를 가져와 바로 주문창으로 이동
				orderVO = (OrderVO)session.getAttribute("orderInfo");
				session.removeAttribute("action");
				
			 }else { // 미리 로그인을 했다면 바로 주문 처리
				 orderVO = _orderVO;
			 }
		 }
		
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		List myOrderList = new ArrayList<OrderVO>(); // 주문 정보를 저장할 주문 ArrayList를 생성
		myOrderList.add(orderVO); // 브라우저에서 전달한 주문 정보를 ArrayList에 저장

		MemberVO memberInfo = (MemberVO)session.getAttribute("memberInfo");
		
		session.setAttribute("myOrderList", myOrderList); // 주문정보와
		session.setAttribute("orderer", memberInfo); // 주문자 정보를 세션에 바인딩한 후 주문창으로 전달
		
		return mav;
	}
	
	@RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST) 
	public ModelAndView orderAllCartGoods( @RequestParam("cart_goods_qty")  String[] cart_goods_qty, // 선택한 상품 수량을 배열로 받음
			                 HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		
		Map cartMap = (Map)session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		List<GoodsVO> myGoodsList = (List<GoodsVO>)cartMap.get("myGoodsList"); // 미리 세션에 저장한 장바구니 상품 목록을 가져옴
		
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		
		for(int i = 0; i < cart_goods_qty.length; i++) { // 장바구니 상품 개수만큼 반복
			String[] cart_goods = cart_goods_qty[i].split(":"); // 문자열로 결합되어 전송된 상품 번호와 주문 수량을 split() 메서드를 이용해 분리
			
			for(int j = 0; j < myGoodsList.size(); j++) {
				GoodsVO goodsVO = myGoodsList.get(j); // 장바구니 목록에서 차례로 GoodsVO를 가져옴
				int goods_id = goodsVO.getGoods_id(); // GoodsVO의 상품 번호를 가져옴
				
				if(goods_id == Integer.parseInt(cart_goods[0]) ) {
					OrderVO _orderVO = new OrderVO();
					String goods_title = goodsVO.getGoods_title();
					int goods_sales_price = goodsVO.getGoods_sales_price();
					String goods_fileName = goodsVO.getGoods_fileName();
					
					_orderVO.setGoods_id(goods_id);
					_orderVO.setGoods_title(goods_title);
					_orderVO.setGoods_sales_price(goods_sales_price);
					_orderVO.setGoods_fileName(goods_fileName);
					_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1] ) );
					
					myOrderList.add(_orderVO);
					break;
					// 전송된 상품 번호와 GoodsVO의 상품 번호가 같으면 주문하는 상품이므로 OrderVO 객체를 생성한 후 상품 정보를 OrderVO에 설정. 그리고 다시 myOrderList에 저장
				}
			}
		}
		session.setAttribute("myOrderList", myOrderList); // 장바구니 목록에서 주문하기 위해 선택한 상품만 myOrderList에 저장한 후 세션에 바인딩
		session.setAttribute("orderer", memberVO);
		return mav;
	}	
	
	@RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST) 
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, // 주문창에서 입력한 상품 수령자 정보와 배송지 정보를 맵에 바로 저장
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1() + "-" + memberVO.getHp2() + "-" + memberVO.getHp3();
		List<OrderVO> myOrderList = (List<OrderVO>)session.getAttribute("myOrderList");
		
		for(int i = 0; i < myOrderList.size(); i++) { // 주문창에서 입력한 수령자 정보와 배송지 정보를 주문 상품 정보 목록과 합침
			OrderVO orderVO = (OrderVO)myOrderList.get(i);
			orderVO.setMember_id(member_id); // 각 orderVO에 수령자 정보를 설정한 후 다시 myOrderList에 저장
			orderVO.setOrderer_name(orderer_name);
			orderVO.setReceiver_name(receiverMap.get("receiver_name") );
			
			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1") );
			orderVO.setReceiver_hp2(receiverMap.get("receiver_hp2") );
			orderVO.setReceiver_hp3(receiverMap.get("receiver_hp3") );
			orderVO.setReceiver_tel1(receiverMap.get("receiver_tel1") );
			orderVO.setReceiver_tel2(receiverMap.get("receiver_tel2") );
			orderVO.setReceiver_tel3(receiverMap.get("receiver_tel3") );
			
			orderVO.setDelivery_address(receiverMap.get("delivery_address") );
			orderVO.setDelivery_message(receiverMap.get("delivery_message") );
			orderVO.setDelivery_method(receiverMap.get("delivery_method") );
			orderVO.setGift_wrapping(receiverMap.get("gift_wrapping") );
			orderVO.setPay_method(receiverMap.get("pay_method") );
			orderVO.setCard_com_name(receiverMap.get("card_com_name") );
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month") );
			orderVO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num") );	
			orderVO.setOrderer_hp(orderer_hp);	
			myOrderList.set(i, orderVO);
		}//end for
		
	    orderService.addNewOrder(myOrderList); // 주문 정보를 SQL문으로 전달
		mav.addObject("myOrderInfo", receiverMap); // OrderVO로 주문완료 결과창에  주문자 정보를 표시하도록 전달
		mav.addObject("myOrderList", myOrderList); // OrderVO로 주문완료 결과창에  상품 목록을 표시하도록 전달
		return mav;
	}
	

}
