package com.spring.ex01;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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

//	public List<MemberVO> selectAllMemberList() {
//		sqlMapper = getInstance();
//		SqlSession session = sqlMapper.openSession();
//		List<MemberVO> memlist = null;
//		memlist = session.selectList("mapper.member.selectAllMemberList");
//		return memlist;
//	}
	
	 public List<HashMap<String, String>> selectAllMemberList() { 
		sqlMapper = getInstance(); 
     	SqlSession session = sqlMapper.openSession();
		List<HashMap<String, String>> memlist = null; 
		memlist = session.selectList("mapper.member.selectAllMemberList"); // 모든 회원 정보를 조회
		return memlist; 
	 }
	
}
