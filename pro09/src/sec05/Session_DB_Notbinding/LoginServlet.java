package sec05.Session_DB_Notbinding;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String user_id = request.getParameter("user_id"); // 로그인창에서 전송된 ID와 비밀번호를 가져옴
		String user_pwd = request.getParameter("user_pwd");

		MemberVO memberVO = new MemberVO(); // MemberVO 객체를 생성하고 속성에 id와 비밀번호를 설정
		memberVO.setId(user_id);
		memberVO.setPwd(user_pwd);
		MemberDAO dao = new MemberDAO();
		boolean result = dao.isExisted(memberVO); // MemberDAO의 isExisted 메서드를 호출하면서 memberVO를 전달

		if (result) {
			HttpSession session = request.getSession();
			session.setAttribute("isLogon", true); // 조회한 결과가 true 이면 isLogon 속성을 true로 세션에 저장
			session.setAttribute("login.id", user_id); // 조회한 결과가 true이면 id와 비밀번호를 세션에 저장
			session.setAttribute("login.pwd", user_pwd);

			out.print("<html><body>");
			out.print("안녕하세요 " + user_id + "님!!!<br>");
			out.print("<a href='show'>회원정보보기</a>"); // 회원정보보기를 클릭하면 이미 로그인 상태이므로 세션에 저장된 회원 정보가 표시됨 / ShowMember 클래스로 이동
			out.print("</body></html>");
		} else {
			out.print("<html><body>회원 아이디가 틀립니다.");
			out.print("<a href='login3.html'> 다시 로그인하기</a>");
			out.print("</body></html>");
		}
	}

}
