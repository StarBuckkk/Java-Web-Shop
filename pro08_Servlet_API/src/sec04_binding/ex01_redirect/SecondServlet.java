package sec04_binding.ex01_redirect;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/second")
public class SecondServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws  ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String address = (String) request.getAttribute("address"); // getAttribute로 request 객체에서 address의 값을 가져옴 -> null이 출력된다 !!! 
		
		/* redirect 방식으로는 서블릿에서 바인딩한 데이터를 다른 서블릿으로 전송불가(GET 방식).
		 * 전달하고자 하는 데이터가 보안과 상관이 없으며, 데이터 양이 적다면 redirect(GET 방식)을 써도 괜찮지만
		 * 대량의 데이터를 전달하고자 하면 문제가 된다.
		 *  */
		
		out.println("<html><body>");
		out.println("주소:" + address);
		out.println("<br>");
		out.println("redirect를 이용한 바인딩 실습입니다.");
		out.println("</body></html>");

	}
}
