package com.spring.ex03;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "mybatis/SqlMapConfig.xml"; // MemberDAO 각 메서드 호출 시 src/mybatis/SqlMapConfig.xml에서 설정 정보를 읽은 후 데이터 베이스와의 연동 준비를 함
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader); // 마이바티스를 이용하는 sqlMapper 객체를 가져옴
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession(); // 실제 member.xml 의 SQL문을 호출하는 데 사용되는 sqlSession 객체를 가져옴
		List<MemberVO> memlist = null;
		memlist = session.selectList("mapper.member.selectAllMemberList"); // 여러 개의 레코드를 조회하므로 selectList() 메서드에 실행하고자 하는 SQL문의 id를 인자로 전달
		
		return memlist;
	}

	public MemberVO selectMemberById(String id){
	      sqlMapper = getInstance();
	      SqlSession session = sqlMapper.openSession();
	      MemberVO memberVO = session.selectOne("mapper.member.selectMemberById", id); // selectOne은 레코드 한 개만 조회할 때 사용(아이디는 중복x이므로) / 서블릿에서 넘어온 id의 값을 selectOne() 메서드에 실행하고자 하는 SQL문의 조건 인자로 전달
	      
	      return memberVO;		 
	   }

	public List<MemberVO> selectMemberByPwd(int pwd) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList= session.selectList("mapper.member.selectMemberByPwd", pwd); // 비밀번호가 같은 회원은 여러 명이 있을 수 있으므로 selectList() 메서드로 조회 / 정수 데이터인 pwd를 SQL문의 조건 값으로 전달
	
		return membersList;
	}



}
