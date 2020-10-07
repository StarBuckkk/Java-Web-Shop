package sec01.Ajax_Basic;

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

	public List listMembers() {
		List list = new ArrayList();
		
		try {
			con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select * from t_member order by joinDate desc "; // 회원 정보를 가입일 순으로 정렬 조회
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(); // SQL문으로 회원 정보를 조회
			
			while (rs.next()) {
				String id = rs.getString("id"); // 조회할 레코드의 각 컬럼 값을 받아옴
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberBean vo = new MemberBean(); // 각 컬럼 값을 다시 MemberBean 객체의 속성에 설정
				
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo); // 설정된 MemberBean 객체의 속성에 설정
			}
			
			rs.close(); // 사용한 순서의 역순으로 종료
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; // 조회한 레코드의 개수만큼 MemberBean 객체를 저장한 ArrayList를 반환
	}

	public void addMember(MemberBean memberBean) { // PreparedStatement의 insert문은 회원정보를 저장하기 위해 ?를 사용
		try {
			Connection con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스와 연결
			String id = memberBean.getId(); // 테이블에 저장할 회원 정보를 받아옴
			String pwd = memberBean.getPwd();
			String name = memberBean.getName();
			String email = memberBean.getEmail();
			
			String query = "insert into t_member"; // insert문을 문자열로 만듬
			query += " (id,pwd,name,email)";
			query += " values(?,?,?,?)";
			
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id); // insert문의 각 '?'에 순서대로 회원 정보를 세팅 / ?은 1부터 시작
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
