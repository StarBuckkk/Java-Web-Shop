package com.myspring.pro30.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;


@Repository("memberDAO") // id�� memberDAO�� ���� �ڵ� ����
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession; // XML ���� ���Ͽ��� ������ ID�� sqlSession�� ���� �ڵ� ����

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
		int result = sqlSession.delete("mapper.member.deleteMember", id); // ���Ե� sqlSession ������ delete() �޼��带 ȣ���ϸ鼭 SQL���� id�� ȸ�� ID�� ����
		return result;
	}
	
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException{
		  MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO); // �޼��� ȣ�� �� ���޵� memberVO�� SQL������ ������ ID�� ��й�ȣ�� ���� ȸ�� ������ MemberVO ��ü�� ��ȯ
		  return vo;
	}

}
