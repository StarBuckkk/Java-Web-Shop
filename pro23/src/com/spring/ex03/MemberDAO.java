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
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession(); // ���� member.xml �� SQL���� ȣ���ϴ� �� ���Ǵ� sqlSession ��ü�� ������
		List<MemberVO> memlist = null;
		memlist = session.selectList("mapper.member.selectAllMemberList"); // ���� ���� ���ڵ带 ��ȸ�ϹǷ� selectList() �޼��忡 �����ϰ��� �ϴ� SQL���� id�� ���ڷ� ����
		
		return memlist;
	}

	public MemberVO selectMemberById(String id){
	      sqlMapper = getInstance();
	      SqlSession session = sqlMapper.openSession();
	      MemberVO memberVO = session.selectOne("mapper.member.selectMemberById", id); // selectOne�� ���ڵ� �� ���� ��ȸ�� �� ���(���̵�� �ߺ�x�̹Ƿ�) / �������� �Ѿ�� id�� ���� selectOne() �޼��忡 �����ϰ��� �ϴ� SQL���� ���� ���ڷ� ����
	      
	      return memberVO;		 
	   }

	public List<MemberVO> selectMemberByPwd(int pwd) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList= session.selectList("mapper.member.selectMemberByPwd", pwd); // ��й�ȣ�� ���� ȸ���� ���� ���� ���� �� �����Ƿ� selectList() �޼���� ��ȸ / ���� �������� pwd�� SQL���� ���� ������ ����
	
		return membersList;
	}



}
