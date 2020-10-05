package sec05.Session_DB_Notbinding;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI�� �����ϱ� ���� �⺻ ��θ� ����
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // ��Ĺ context.xml�� ������ name���� jdbc/oracle�� �̿��� ��Ĺ�� �̸� ������ DataSource�� �޾ƿ´�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List listMembers() {
		List list = new ArrayList();
//		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			// connDB();
			con = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String query = "select * from t_member ";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(); // SQL������ ȸ�� ������ ��ȸ
			
			while (rs.next()) {
				String id = rs.getString("id"); // ��ȸ�� ���ڵ��� �� �÷� ���� �޾ƿ�
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO(); // �� �÷� ���� �ٽ� MemberVO ��ü�� �Ӽ��� ����
				
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo); // ������ MemberVO ��ü�� �Ӽ��� ����
			}
			
			rs.close(); // ����� ������ �������� ����
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; // ��ȸ�� ���ڵ��� ������ŭ MemberVO ��ü�� ������ ArrayList�� ��ȯ
	}

	public void addMember(MemberVO memberVO) { // PreparedStatement�� insert���� ȸ�������� �����ϱ� ���� ?�� ���
		try {
			Connection con = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String id = memberVO.getId(); // ���̺��� ������ ȸ�� ������ �޾ƿ�
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			
			String query = "insert into t_member"; // insert���� ���ڿ��� ����
			query += " (id,pwd,name,email)";
			query += " values(?,?,?,?)"; // ?�� id, pwd, name, age�� ������� ������
			
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id); // insert���� �� '?'�� ������� ȸ�� ������ ���� / ?�� 1���� ����
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delMember(String id) {
		try {
			Connection con = dataFactory.getConnection();
			Statement stmt = con.createStatement();
			String query = "delete from t_member" + " where id=?"; // delete���� ���ڿ��� ����
			System.out.println("prepareStatememt:" + query);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id); // ù��° '?'�� ���޵� id�� ���ڷ� ����
			pstmt.executeUpdate(); // delete ���� ������ ���̺����� �ش� id�� ȸ�� ������ �����Ѵ�.
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExisted(MemberVO memberVO) {
		boolean result = false;
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		try {
			con = dataFactory.getConnection();
			String query = "select decode(count(*),1,'true','false') as result from t_member"; // ����Ŭ�� decode()�Լ��� �̿��� ��ȸ�Ͽ� id�� ��й�ȣ�� ���̺��� �����ϸ� true, �������� ������ false ��ȸ
			query += " where id=? and pwd=?";
			System.out.println(query);
			
			pstmt = con.prepareStatement(query); 
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery(); // �޼���� ���޵� id�� ��й�ȣ�� �̿��� SQL���� �ۼ��� �� �����ͺ��̽��� ��ȸ
			rs.next(); //Ŀ���� ù��° ���ڵ�� ��ġ��Ŵ
			result = Boolean.parseBoolean(rs.getString("result"));
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}