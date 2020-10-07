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
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI에 접근하기 위해 기본 경로를 지정
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // 톰캣 context.xml에 설정한 name값인 jdbc/oracle을 이용해 톰캣이 미리 연결한 DataSource를 받아온다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> listMembers() {
		List<MemberVO> membersList = new ArrayList();
		try {
			conn = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select * from  t_member order by joinDate desc"; // 가입일 순으로 정렬 조회쿼리
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(); // SQL문으로 회원 정보를 조회
			
			while (rs.next()) {
				String id = rs.getString("id"); // 조회할 레코드의 각 컬럼 값을 받아옴
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate); // 각 컬럼 값을 다시 MemberVO 객체의 속성에 설정
				membersList.add(memberVO);
			}
			
			rs.close(); // 사용한 순서의 역순으로 종료
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return membersList; // 조회한 레코드의 개수만큼 MemberVO 객체를 저장한 ArrayList를 반환
	}

	public void addMember(MemberVO m) {
		try {
			conn = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스와 연결
			String id = m.getId(); // 테이블에 저장할 회원 정보를 받아옴
			String pwd = m.getPwd();
			String name = m.getName();
			String email = m.getEmail();
			String query = "INSERT INTO t_member(id, pwd, name, email)" + " VALUES(?, ? ,? ,?)"; // insert문을 문자열로 만듬 ?는 id, pwd, name, age에 순서대로 대응됨
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, id); // insert문의 각 '?'에 순서대로 회원 정보를 세팅 / ?은 1부터 시작
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
			conn = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
			String query = "select * from  t_member where id=?"; // 전달된 id로 회원 정보를 조회
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
			String query = "update t_member set pwd=?,name=?,email=?  where id=?"; // 전달된 수정 회원 정보를 update문을 이용해 수정
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
			String query = "delete from t_member where id=?"; // delete문을 이용해 전달된 id의 회원 정보를 삭제
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,id); // 첫번째 '?'에 전달된 id를 인자로 넣음
			pstmt.executeUpdate(); // delete 문을 실행해 테이블에서 해당 id의 회원 정보를 삭제한다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
