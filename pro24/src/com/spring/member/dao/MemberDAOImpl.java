package com.spring.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.spring.member.vo.MemberVO;


public class MemberDAOImpl implements MemberDAO {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession; // �Ӽ� sqlSession�� sqlSession ���� �����ϱ� ���� setter�� ����
	}

	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList"); // ���Ե� sqlSession ������ select() �޼��带 ȣ���ϸ鼭 SQL���� id�� ����
		return membersList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.member.insertMember", memberVO); // ���Ե� sqlSession ������ insert() �޼��带 ȣ���ϸ鼭 SQL���� id�� memberVO�� ����
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		int result =  sqlSession.delete("mapper.member.deleteMember", id); // ���Ե� sqlSession ������ delete() �޼��带 ȣ���ϸ鼭 SQL���� id�� ȸ�� ID�� ����
		return result;
	}
}
