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
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException {
		ArrayList<MemberVO>  memberList = (ArrayList)sqlSession.selectList("mapper.admin.member.listMember", condMap); // 매퍼 파일인 admin.member.xml에서 지정한 id인 listMember인 SQL문을 요청
		return memberList;
	}
	
	public MemberVO memberDetail(String member_id) throws DataAccessException {
		MemberVO memberBean = (MemberVO)sqlSession.selectOne("mapper.admin.member.memberDetail", member_id); // id가 memberDetail인 SQL문을 요청
		return memberBean;
	}
	
	public void modifyMemberInfo(HashMap memberMap) throws DataAccessException {
		sqlSession.update("mapper.admin.member.modifyMemberInfo", memberMap); // id가 modifyMemberInfo인 SQL문을 요청
	}
	
	

}
