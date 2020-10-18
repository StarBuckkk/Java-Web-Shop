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
@Transactional(propagation = Propagation.REQUIRED) // OrderServiceImpl Ŭ������ �̿��� id�� orderService�� ���� �ڵ� ����
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO; // OrderServiceImpl Ŭ������ �̿��� id�� orderDAO�� ���� �ڵ� ����
	
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception {
		List<OrderVO> orderGoodsList;
		orderGoodsList = orderDAO.listMyOrderGoods(orderVO);
		return orderGoodsList;
	}
	
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception {
		orderDAO.insertNewOrder(myOrderList); // �ֹ� ��ǰ ����� ���̺� �߰�
		orderDAO.removeGoodsFromCart(myOrderList); // ��ٱ��Ͽ��� �ֹ��� ��� �ش� ��ǰ�� ��ٱ��Ͽ��� ����
	}	
	
	public OrderVO findMyOrder(String order_id) throws Exception {
		return orderDAO.findMyOrder(order_id);
	}

}
