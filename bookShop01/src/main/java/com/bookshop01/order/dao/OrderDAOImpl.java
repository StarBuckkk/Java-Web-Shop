package com.bookshop01.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.order.vo.OrderVO;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws DataAccessException {
		List<OrderVO> orderGoodsList=new ArrayList<OrderVO>();
		orderGoodsList = (ArrayList)sqlSession.selectList("mapper.order.selectMyOrderList", orderVO); // 매퍼 파일인 order.xml에서 지정한 id인 selectMyOrderList인 SQL문을 요청
		return orderGoodsList;
	}
	
	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException {
		int order_id = selectOrderID(); // 각 irderVO에 설정할 주문 번호를 가져옴
		
		for(int i = 0; i < myOrderList.size(); i++) { // 주문 목록에서 차례대로 orderVO를 가져와 주문 번호를 설정
			OrderVO orderVO = (OrderVO)myOrderList.get(i);
			orderVO.setOrder_id(order_id);
			sqlSession.insert("mapper.order.insertNewOrder", orderVO); // id가 insertNewOrder인 SQL문을 요청
		}
		
	}	
	
	public OrderVO findMyOrder(String order_id) throws DataAccessException {
		OrderVO orderVO = (OrderVO)sqlSession.selectOne("mapper.order.selectMyOrder", order_id); // id가 selectMyOrder인 SQL문을 요청	
		return orderVO;
	}
	
	public void removeGoodsFromCart(OrderVO orderVO) throws DataAccessException {
		sqlSession.delete("mapper.order.deleteGoodsFromCart", orderVO); // id가 deleteGoodsFromCart인 SQL문을 요청
	} 
	
	public void removeGoodsFromCart(List<OrderVO> myOrderList) throws DataAccessException {
		for(int i = 0; i < myOrderList.size(); i++) {
			OrderVO orderVO = (OrderVO)myOrderList.get(i);
			sqlSession.delete("mapper.order.deleteGoodsFromCart", orderVO);	// id가 deleteGoodsFromCart인 SQL문을 요청
		} // 장바구니에서 주문한 경우 해당 상품을 장바구니에서 삭제 
	}	
	private int selectOrderID() throws DataAccessException {
		return sqlSession.selectOne("mapper.order.selectOrderID"); // id가 selectOrderID인 SQL문을 요청
		// 테이블에 저정할 주문 번호를 가져옴
	}
}

