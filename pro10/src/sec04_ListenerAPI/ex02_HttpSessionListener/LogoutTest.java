package sec04_ListenerAPI.ex02_HttpSessionListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutTest
 */
@WebServlet("/logout")
public class LogoutTest extends HttpServlet {
	ServletContext context;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 로그아웃 링크를 클릭하면 접속자수를 1 감소시키고 user_list에서 로그아웃한 접속자 id를 삭제한 후 다시 user_list를 ServletContext 객체에 바인딩하도록 설정시킴
		context = getServletContext();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String user_id = request.getParameter("user_id"); // user_list에서 삭제할 id를 가져옴

		session.invalidate(); // 로그아웃 링크를 클릭하면 세션을 소멸시킴

		List user_list = (ArrayList) context.getAttribute("user_list"); // user_list에서 로그아웃한 접속자 id를 삭제한 후 다시 user_list를 컨텍스트에 저장
		user_list.remove(user_id);
		context.removeAttribute("user_list");
		context.setAttribute("user_list", user_list);
		out.println("<br>로그아웃 했습니다.");
	}

}
