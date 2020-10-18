package com.bookshop01.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.bookshop01.order.dao.OrderDAO;
import com.bookshop01.order.vo.OrderVO;


@Service("orderService")
@Transactional(propagation = Propagation.REQUIRED) // OrderServiceImpl 클래스를 이용해 id가 orderService인 빈을 자동 생성
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO; // OrderServiceImpl 클래스를 이용해 id가 orderDAO인 빈을 자동 생성
	
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception {
		List<OrderVO> orderGoodsList;
		orderGoodsList = orderDAO.listMyOrderGoods(orderVO);
		return orderGoodsList;
	}
	
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception {
		orderDAO.insertNewOrder(myOrderList); // 주문 상품 목록을 테이블에 추가
		orderDAO.removeGoodsFromCart(myOrderList); // 장바구니에서 주문한 경우 해당 상품을 장바구니에서 삭제
	}	
	
	public OrderVO findMyOrder(String order_id) throws Exception {
		return orderDAO.findMyOrder(order_id);
	}

}
