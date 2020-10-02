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
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI에 접근하기 위해 기본 경로를 지정
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // 톰캣 context.xml에 설정한 name값인 jdbc/oracle을 이용해 톰캣이 미리 연결한 DataSource를 받아온다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List listMembers() {
		List list = new ArrayList();
//		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			// connDB();
			con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select * from t_member ";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
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
				list.add(vo); // 설정된 MemberVO 객체의 속성에 설정
			}
			
			rs.close(); // 사용한 순서의 역순으로 종료
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; // 조회한 레코드의 개수만큼 MemberVO 객체를 저장한 ArrayList를 반환
	}

	public void addMember(MemberVO memberVO) { // PreparedStatement의 insert문은 회원정보를 저장하기 위해 ?를 사용
		try {
			Connection con = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스와 연결
			String id = memberVO.getId(); // 테이블에 저장할 회원 정보를 받아옴
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			
			String query = "insert into t_member"; // insert문을 문자열로 만듬
			query += " (id,pwd,name,email)";
			query += " values(?,?,?,?)"; // ?는 id, pwd, name, age에 순서대로 대응됨
			
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

	public void delMember(String id) {
		try {
			Connection con = dataFactory.getConnection();
			Statement stmt = con.createStatement();
			String query = "delete from t_member" + " where id=?"; // delete문을 문자열로 만듬
			System.out.println("prepareStatememt:" + query);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id); // 첫번째 '?'에 전달된 id를 인자로 넣음
			pstmt.executeUpdate(); // delete 문을 실행해 테이블에서 해당 id의 회원 정보를 삭제한다.
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
			String query = "select decode(count(*),1,'true','false') as result from t_member"; // 오라클의 decode()함수를 이용해 조회하여 id와 비밀번호가 테이블에 존재하면 true, 존재하지 않으면 false 조회
			query += " where id=? and pwd=?";
			System.out.println(query);
			
			pstmt = con.prepareStatement(query); 
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery(); // 메서드로 전달된 id와 비밀번호를 이용해 SQL문을 작성한 후 데이터베이스에 조회
			rs.next(); //커서를 첫번째 레코드로 위치시킴
			result = Boolean.parseBoolean(rs.getString("result"));
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
