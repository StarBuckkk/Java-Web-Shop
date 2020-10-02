package sec04_ListenerAPI.ex01_HttpSessionBindingListener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginTest
 */
//@WebServlet("/login")
public class LoginTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");

		LoginImpl loginUser = new LoginImpl(user_id, user_pw); // LoginImpl 객체를 생성한 후 전송된 id와 비밀번호를 저장함
		if (session.isNew()) {
			session.setAttribute("loginUser", loginUser); // 세션에 바인딩 시 HttpSessionBindingListener를 구현한 LoginImpl의 valueBound()메서드를 호출
		}

		out.println("<head>");
		out.println("<script  type='text/javascript'>");
		out.println("setTimeout('history.go(0);', 5000)"); // 자바스크립트의 setTimeout() 함수를 이용해 5초마다 서블릿에 재요청하여 현재 접속자수를 표시
		out.println("</script>");
		out.println("</head>");
		out.println("<html><body>");
		out.println("아이디는 " + loginUser.user_id + "<br>"); // 접속자수를 브라우저로 출력
		out.println("총 접속자수는" + LoginImpl.total_user + "<br>");
		out.println("</body></html>");

	}

}
