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
public class OrderControllerImpl extends BaseController implements OrderController { // ��ӹް� ����
	@Autowired
	private OrderService orderService; // @Autowired�� �̿��� id�� orderService�� ���� �ڵ� ����
	@Autowired
	private OrderVO orderVO; // @Autowired�� �̿��� id�� orderVO�� ���� �ڵ� ����
	
	/* 1. �ٷ� �ֹ��ϱ�
	 * ��ǰ �ֹ��� �α����� �� ���¿����� ���� ���� �α������� ���� ���¿��� �ֹ��ϱ⸦ Ŭ���� ��� �α���â���� �̵�
	 * �ֹ��ϱ⸦ Ŭ���ϸ� ���������� ���۵� �ֹ� ��ǰ ������ ArrayList�� ������ ���ǿ� ���ε��� �� �ֹ� �������� �̵�
	 * �ֹ� ���������� ������ ������ ����� ������ �Է¹���. ���� �ֹ� �� ��Ʈ�ѷ����� �̸� ���ǿ� ����� �ֹ� ��ǰ�� ������
	 * ���۵� ������ ������ ����� ������ ��ħ
	 * �ֹ� ������ ��ģ ���� ArrayList�� SQL������ �����Ͽ� �ֹ� ó��
	 * */
	
	/* 2. ��ٱ��� ��ǰ �ֹ��ϱ�
	 * ��ٱ��� �������� ��Ÿ���� �� ��ٱ��Ͽ� �߰��� ��ǰ ������ �̸� ���ǿ� ����
	 * ��ٱ��� ���������� �ֹ��� ��ǰ�� ������ �� �ֹ��� ��ǰ ��ȣ�� �� ��ǰ �ֹ� ������ �迭�� ��� ��Ʈ�ѷ��� ����
	 * ��Ʈ�ѷ������� ���۵� ��ǰ ��ȣ�� ���ǿ� ����� ��ǰ���� ��ǰ ��ȣ�� ���� ������ ��ǰ ������ �ֹ� ����� OrderVO �Ӽ��� ����
	 * ���۵� �� ��ǰ�� �ֹ� ������ OrderVO �Ӽ��� ����
	 * �ٽ� OrderVO ��ü�� myOrderList�� ������ �� ���ǿ� ���ε� 
	 * */
	
	@RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();
		
		Boolean isLogOn = (Boolean)session.getAttribute("isLogOn");
		String action = (String)session.getAttribute("action");
		//�α��� ���� üũ
		//������ �α��� ������ ���� �ֹ����� ����
		//�α׾ƿ� ������ ��� �α��� ȭ������ �̵�
		if(isLogOn == null || isLogOn == false) { // �α����� ���� �ʾҴٸ� ���� �α��� �� �ֹ��� ó���ϵ���
			session.setAttribute("orderInfo", _orderVO);  // �ֹ�������
			session.setAttribute("action", "/order/orderEachGoods.do"); // �ֹ� ������ ��û URL�� ���ǿ� ����
			
			return new ModelAndView("redirect:/member/loginForm.do");
			
		}else { 
			
			 if(action != null && action.equals("/order/orderEachGoods.do") ) { // �α��� �� ���ǿ��� �ֹ� ������ ������ �ٷ� �ֹ�â���� �̵�
				orderVO = (OrderVO)session.getAttribute("orderInfo");
				session.removeAttribute("action");
				
			 }else { // �̸� �α����� �ߴٸ� �ٷ� �ֹ� ó��
				 orderVO = _orderVO;
			 }
		 }
		
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		List myOrderList = new ArrayList<OrderVO>(); // �ֹ� ������ ������ �ֹ� ArrayList�� ����
		myOrderList.add(orderVO); // ���������� ������ �ֹ� ������ ArrayList�� ����

		MemberVO memberInfo = (MemberVO)session.getAttribute("memberInfo");
		
		session.setAttribute("myOrderList", myOrderList); // �ֹ�������
		session.setAttribute("orderer", memberInfo); // �ֹ��� ������ ���ǿ� ���ε��� �� �ֹ�â���� ����
		
		return mav;
	}
	
	@RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST) 
	public ModelAndView orderAllCartGoods( @RequestParam("cart_goods_qty")  String[] cart_goods_qty, // ������ ��ǰ ������ �迭�� ����
			                 HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		
		Map cartMap = (Map)session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		List<GoodsVO> myGoodsList = (List<GoodsVO>)cartMap.get("myGoodsList"); // �̸� ���ǿ� ������ ��ٱ��� ��ǰ ����� ������
		
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		
		for(int i = 0; i < cart_goods_qty.length; i++) { // ��ٱ��� ��ǰ ������ŭ �ݺ�
			String[] cart_goods = cart_goods_qty[i].split(":"); // ���ڿ��� ���յǾ� ���۵� ��ǰ ��ȣ�� �ֹ� ������ split() �޼��带 �̿��� �и�
			
			for(int j = 0; j < myGoodsList.size(); j++) {
				GoodsVO goodsVO = myGoodsList.get(j); // ��ٱ��� ��Ͽ��� ���ʷ� GoodsVO�� ������
				int goods_id = goodsVO.getGoods_id(); // GoodsVO�� ��ǰ ��ȣ�� ������
				
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
					// ���۵� ��ǰ ��ȣ�� GoodsVO�� ��ǰ ��ȣ�� ������ �ֹ��ϴ� ��ǰ�̹Ƿ� OrderVO ��ü�� ������ �� ��ǰ ������ OrderVO�� ����. �׸��� �ٽ� myOrderList�� ����
				}
			}
		}
		session.setAttribute("myOrderList", myOrderList); // ��ٱ��� ��Ͽ��� �ֹ��ϱ� ���� ������ ��ǰ�� myOrderList�� ������ �� ���ǿ� ���ε�
		session.setAttribute("orderer", memberVO);
		return mav;
	}	
	
	@RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST) 
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, // �ֹ�â���� �Է��� ��ǰ ������ ������ ����� ������ �ʿ� �ٷ� ����
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1() + "-" + memberVO.getHp2() + "-" + memberVO.getHp3();
		List<OrderVO> myOrderList = (List<OrderVO>)session.getAttribute("myOrderList");
		
		for(int i = 0; i < myOrderList.size(); i++) { // �ֹ�â���� �Է��� ������ ������ ����� ������ �ֹ� ��ǰ ���� ��ϰ� ��ħ
			OrderVO orderVO = (OrderVO)myOrderList.get(i);
			orderVO.setMember_id(member_id); // �� orderVO�� ������ ������ ������ �� �ٽ� myOrderList�� ����
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
		
	    orderService.addNewOrder(myOrderList); // �ֹ� ������ SQL������ ����
		mav.addObject("myOrderInfo", receiverMap); // OrderVO�� �ֹ��Ϸ� ���â��  �ֹ��� ������ ǥ���ϵ��� ����
		mav.addObject("myOrderList", myOrderList); // OrderVO�� �ֹ��Ϸ� ���â��  ��ǰ ����� ǥ���ϵ��� ����
		return mav;
	}
	

}
