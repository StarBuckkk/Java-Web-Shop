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
				String resource = "mybatis/SqlMapConfig.xml"; // MemberDAO �� �޼��� ȣ�� �� src/mybatis/SqlMapConfig.xml���� ���� ������ ���� �� ������ ���̽����� ���� �غ� ��
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader); // ���̹�Ƽ���� �̿��ϴ� sqlMapper ��ü�� ������
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;

	}

	public String  selectName() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession(); // ���� member.xml �� SQL���� ȣ���ϴ� �� ���Ǵ� sqlSession ��ü�� ������
		String name = session.selectOne("mapper.member.selectName"); // selectOne() �޼��带 ���ڷ� ������ SQL���� ������ �� �� ���� ������(���ڿ�)�� ��ȯ
		return name;
	} 
		
	public int  selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int pwd = session.selectOne("mapper.member.selectPwd");
		return pwd;
	}


}
