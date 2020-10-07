package sec02_CRUD.UpdateDelete;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI�� �����ϱ� ���� �⺻ ��θ� ����
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // ��Ĺ context.xml�� ������ name���� jdbc/oracle�� �̿��� ��Ĺ�� �̸� ������ DataSource�� �޾ƿ´�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> listMembers() {
		List<MemberVO> membersList = new ArrayList();
		try {
			conn = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String query = "select * from  t_member order by joinDate desc"; // ������ ������ ���� ��ȸ����
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(); // SQL������ ȸ�� ������ ��ȸ
			
			while (rs.next()) {
				String id = rs.getString("id"); // ��ȸ�� ���ڵ��� �� �÷� ���� �޾ƿ�
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate); // �� �÷� ���� �ٽ� MemberVO ��ü�� �Ӽ��� ����
				membersList.add(memberVO);
			}
			
			rs.close(); // ����� ������ �������� ����
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return membersList; // ��ȸ�� ���ڵ��� ������ŭ MemberVO ��ü�� ������ ArrayList�� ��ȯ
	}

	public void addMember(MemberVO m) {
		try {
			conn = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String id = m.getId(); // ���̺� ������ ȸ�� ������ �޾ƿ�
			String pwd = m.getPwd();
			String name = m.getName();
			String email = m.getEmail();
			String query = "INSERT INTO t_member(id, pwd, name, email)" + " VALUES(?, ? ,? ,?)"; // insert���� ���ڿ��� ���� ?�� id, pwd, name, age�� ������� ������
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, id); // insert���� �� '?'�� ������� ȸ�� ������ ���� / ?�� 1���� ����
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MemberVO findMember(String _id) {
		MemberVO memInfo = null;
		try {
			conn = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String query = "select * from  t_member where id=?"; // ���޵� id�� ȸ�� ������ ��ȸ
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, _id);
			System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			String id = rs.getString("id");
			String pwd = rs.getString("pwd");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date joinDate = rs.getDate("joinDate");
			
			memInfo = new MemberVO(id, pwd, name, email, joinDate);
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memInfo;
	}

	public void modMember(MemberVO memberVO) {
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		String name = memberVO.getName();
		String email = memberVO.getEmail();
		try {
			conn = dataFactory.getConnection();
			String query = "update t_member set pwd=?,name=?,email=?  where id=?"; // ���޵� ���� ȸ�� ������ update���� �̿��� ����
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delMember(String id) {
		try {
			conn = dataFactory.getConnection();
			String query = "delete from t_member where id=?"; // delete���� �̿��� ���޵� id�� ȸ�� ������ ����
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,id); // ù��° '?'�� ���޵� id�� ���ڷ� ����
			pstmt.executeUpdate(); // delete ���� ������ ���̺��� �ش� id�� ȸ�� ������ �����Ѵ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
