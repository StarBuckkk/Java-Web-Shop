package com.spring.ex02;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	private static SqlSessionFactory sqlMapper = null;

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

	public String  selectName() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession(); // 실제 member.xml 의 SQL문을 호출하는 데 사용되는 sqlSession 객체를 가져옴
		String name = session.selectOne("mapper.member.selectName"); // selectOne() 메서드를 인자로 지정한 SQL문을 실행한 후 한 개의 데이터(문자열)을 반환
		return name;
	} 
		
	public int  selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int pwd = session.selectOne("mapper.member.selectPwd");
		return pwd;
	}


}
