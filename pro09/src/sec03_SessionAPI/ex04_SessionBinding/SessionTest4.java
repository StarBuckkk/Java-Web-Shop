package sec03_SessionAPI.ex04_SessionBinding;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionTest4
 */
//@WebServlet("/login")
public class SessionTest4 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		if (session.isNew() ) { // 최초 요청시 수행
			if(user_id != "") { // 원본 소스코드는 user_id != null -> 아이디에 아무것도 입력하지 않으면 String의 기본값인 null 이 아니라 빈 문자열이 넘어간다. 따라서 ""로 변경해줘야 한다. 
				session.setAttribute("user_id", user_id);
				out.println("<a href='login'>로그인 상태 확인</a>");
			}else {
				out.print("<a href='login2.html'>다시 로그인 하세요!!</a>");
				session.invalidate();
			}
		}else { 
			user_id = (String) session.getAttribute("user_id"); 
			
			if (user_id != null && user_id.length() != 0) { // 재요청시 세션에서 id를 가져와 이전에 로그인했는지 여부를 확인
				out.print("안녕하세요 " + user_id + "님!!!");
			} else {
				out.print("<a href='login2.html'>다시 로그인 하세요!!</a>");
				session.invalidate();
			}
		}
	}

}
