package com.bookshop01.mypage.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;

@Repository("myPageDAO")
public class MyPageDAOImpl implements MyPageDAO {
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	public List<OrderVO> selectMyOrderGoodsList(String member_id) throws DataAccessException {
		List<OrderVO> orderGoodsList = (List)sqlSession.selectList("mapper.mypage.selectMyOrderGoodsList", member_id); // 매퍼 파일인 mypage.xml에서 지정한 id인 selectMyOrderGoodsList인 SQL문을 요청
		return orderGoodsList;
	}
	
	public List selectMyOrderInfo(String order_id) throws DataAccessException {
		List myOrderList = (List)sqlSession.selectList("mapper.mypage.selectMyOrderInfo", order_id); // id가 selectMyOrderInfo인 SQL문을 요청
		return myOrderList;
	}	

	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException {
		List<OrderVO> myOrderHistList = (List)sqlSession.selectList("mapper.mypage.selectMyOrderHistoryList", dateMap); // id가 selectMyOrderHistoryList인 SQL문을 요청
		return myOrderHistList;
	}
	
	public void updateMyInfo(Map memberMap) throws DataAccessException {
		sqlSession.update("mapper.mypage.updateMyInfo", memberMap); // id가 updateMyInfo인 SQL문을 요청
	}
	
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException {
		MemberVO memberVO = (MemberVO)sqlSession.selectOne("mapper.mypage.selectMyDetailInfo", member_id); // id가 selectMyDetailInfo인 SQL문을 요청
		return memberVO;
		
	}
	
	public void updateMyOrderCancel(String order_id) throws DataAccessException {
		sqlSession.update("mapper.mypage.updateMyOrderCancel", order_id); // id가 updateMyOrderCancel인 SQL문을 요청
	}
}
