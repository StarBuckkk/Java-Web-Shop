package com.bookshop01.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.member.vo.MemberVO;

@Repository("adminMemberDao")
public class AdminMemberDAOImpl  implements AdminMemberDAO{
	@Autowired
	private SqlSession sqlSession; // XML ���� ���Ͽ��� ������ ID�� sqlSession�� ���� �ڵ� ����
	
	
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException {
		ArrayList<MemberVO>  memberList = (ArrayList)sqlSession.selectList("mapper.admin.member.listMember", condMap); // ���� ������ admin.member.xml���� ������ id�� listMember�� SQL���� ��û
		return memberList;
	}
	
	public MemberVO memberDetail(String member_id) throws DataAccessException {
		MemberVO memberBean = (MemberVO)sqlSession.selectOne("mapper.admin.member.memberDetail", member_id); // id�� memberDetail�� SQL���� ��û
		return memberBean;
	}
	
	public void modifyMemberInfo(HashMap memberMap) throws DataAccessException {
		sqlSession.update("mapper.admin.member.modifyMemberInfo", memberMap); // id�� modifyMemberInfo�� SQL���� ��û
	}
	
	

}
