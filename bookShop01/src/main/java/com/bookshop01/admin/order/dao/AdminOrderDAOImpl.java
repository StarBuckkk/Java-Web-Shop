package com.bookshop01.admin.order.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;

@Repository("adminOrderDAO")
public class AdminOrderDAOImpl  implements AdminOrderDAO{
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	public ArrayList<OrderVO>selectNewOrderList(Map condMap) throws DataAccessException {
		ArrayList<OrderVO>  orderList = (ArrayList)sqlSession.selectList("mapper.admin.order.selectNewOrderList", condMap); // 매퍼 파일인 admin.order.xml에서 지정한 id인 selectNewOrderList인 SQL문을 요청
		return orderList;
	}
	public void  updateDeliveryState(Map deliveryMap) throws DataAccessException {
		sqlSession.update("mapper.admin.order.updateDeliveryState", deliveryMap);
	}
	
	public ArrayList<OrderVO> selectOrderDetail(int order_id) throws DataAccessException {
		ArrayList<OrderVO> orderList = (ArrayList)sqlSession.selectList("mapper.admin.order.selectOrderDetail", order_id); // id가 selectOrderDetail인 SQL문을 요청
		return orderList;
	}


	public MemberVO selectOrderer(String member_id) throws DataAccessException {
		MemberVO orderer = (MemberVO)sqlSession.selectOne("mapper.admin.order.selectOrderer", member_id); // id가 selectOrderer인 SQL문을 요청
		return orderer;
		
	}

}
