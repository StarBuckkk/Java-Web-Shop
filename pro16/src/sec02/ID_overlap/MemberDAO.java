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
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI에 접근하기 위해 기본 경로를 지정
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // 톰캣 context.xml에 설정한 name값인 jdbc/oracle을 이용해 톰캣이 미리 연결한 DataSource를 받아온다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean overlappedID(String id){
		boolean result = false;
		try {
			con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select decode(count(*),1,'true','false') as result from t_member"; // 오라클의 decode()함수를 이용해 id가 존재하면(1이면) true, 존재하지 않으면 false를 문자열로 조회
			query += " where id=?";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();// SQL문으로 회원 정보를 조회 
			
			rs.next();
			result = Boolean.parseBoolean(rs.getString("result")); // 문자열을 Boolean 자료형으로 변환
			pstmt.close(); // 사용한 순서의 역순으로 종료
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
