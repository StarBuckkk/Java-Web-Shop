package com.spring.ex04;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

public class MemberDAO {
	private static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
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

	public int insertMember(MemberVO memberVO) { 
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		result = session.insert("mapper.member.insertMember", memberVO); // ������ id�� SQL���� memberVO�� ���� �����Ͽ� ȸ�� ������ ���̺� �߰�
		session.commit(); // ���� Ŀ���̹Ƿ� �ݵ�� commit() �޼��带 ȣ���Ͽ� ���� �ݿ�
		
		return result;
	}
	public int insertMember2(Map<String,String> memberMap){ // HashMap�� �̿��� �߰�
        sqlMapper = getInstance();
        SqlSession session = sqlMapper.openSession();
        int result = session.insert("mapper.member.insertMember2", memberMap); // �޼���� ���޵� HashMap�� �ٽ� SQL������ ����
        session.commit();
        
        return result;		
	}

	public int updateMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = session.update("mapper.member.updateMember", memberVO); // update�� ȣ�� �� SqlSession�� update() �޼��带 �̿�
		session.commit();
		
		return result;
	}   

	
    public int deleteMember(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		result = session.delete("mapper.member.deleteMember", id); // delete���� �����Ϸ��� SqlSession�� delete() �޼��带 �̿�
		session.commit(); // �ݵ�� Ŀ��
		
		return result;
    } 
    
    public List<MemberVO>  searchMember(MemberVO  memberVO){
        sqlMapper = getInstance();
        SqlSession session=sqlMapper.openSession();
        //List list = session.selectList("mapper.member.searchMember", memberVO);
        List<MemberVO> list = session.selectList("mapper.member.searchMember", memberVO); // ȸ�� �˻�â���� ���޵� �̸��� ���� ���� memberVO�� �����Ͽ� SQL������ ����
        
        return list;		
    } 

    public List<MemberVO>  foreachSelect(List nameList){
        sqlMapper = getInstance();
        SqlSession session = sqlMapper.openSession();
        List list = session.selectList("mapper.member.foreachSelect", nameList); // �˻� �̸��� ����� nameList�� SQL������ ����
        
        return list;		
    }
    
    public int  foreachInsert(List memList){
        sqlMapper = getInstance();
        SqlSession session = sqlMapper.openSession();
        int result = session.insert("mapper.member.foreachInsert", memList); // insert���� ���������� ����Ǹ� ����� ��ȯ
        session.commit(); // �ݵ�� Ŀ��
        
        return result ;		
     }
    
    
    public List<MemberVO>  selectLike(String name){
        sqlMapper = getInstance();
        SqlSession session = sqlMapper.openSession();
        List list = session.selectList("mapper.member.selectLike", name);
        
        return list;		
    }


}
