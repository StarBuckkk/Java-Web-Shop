package sec02.ex01;

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

	public List listMembers(MemberVO memberVO) {
		List membersList = new ArrayList();
		String _name = memberVO.getName();
		try {
			con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select * from t_member ";
			
			if( (_name != null && _name.length() != 0) ) { // _name 값이 존재하면 SQL문에 where절을 추가하여 해당 이름으로 조회
				 query += "where name=?";
				 pstmt = con.prepareStatement(query);
				 pstmt.setString(1, _name); // 첫번째 '?'에 전달된 이름을 지정
			}else {
				pstmt = con.prepareStatement(query); // _name값이 없으면 모든 회원 정보를 조회
			}
			
			
			System.out.println("prepareStatememt: " + query);
			ResultSet rs = pstmt.executeQuery(); // SQL문으로 회원 정보를 조회
			
			while (rs.next()) {
				String id = rs.getString("id"); // 조회할 레코드의 각 컬럼 값을 받아옴
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO(); // 각 컬럼 값을 다시 MemberVO 객체의 속성에 설정
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				membersList.add(vo); // 설정된 MemberVO 객체의 속성에 설정
			}
			rs.close(); // 사용한 순서의 역순으로 종료
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membersList; // 조회한 레코드의 개수만큼 MemberVO 객체를 저장한 ArrayList를 반환
	}

}
