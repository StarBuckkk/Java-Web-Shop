package sec02.ID_overlap;

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

	public boolean overlappedID(String id){
		boolean result = false;
		try {
			con = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
			String query = "select decode(count(*),1,'true','false') as result from t_member"; // ����Ŭ�� decode()�Լ��� �̿��� id�� �����ϸ�(1�̸�) true, �������� ������ false�� ���ڿ��� ��ȸ
			query += " where id=?";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();// SQL������ ȸ�� ������ ��ȸ 
			
			rs.next();
			result = Boolean.parseBoolean(rs.getString("result")); // ���ڿ��� Boolean �ڷ������� ��ȯ
			pstmt.close(); // ����� ������ �������� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
