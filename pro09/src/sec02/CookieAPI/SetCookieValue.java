package sec02.CookieAPI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetCookieValue
 */
@WebServlet("/set")
public class SetCookieValue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		Date d = new Date();
		Cookie c = new Cookie("cookieTest", URLEncoder.encode("JSP프로그래밍입니다.", "utf-8")); // Cookie 객체를 생성후 cookieTest 이름으로 한글 정보를 URLEncoder.encode로 인코딩해서 쿠키에 저장 
//		c.setMaxAge(24 * 60 * 60); // 유효기간 : 인자값이 양수일경우  파일에 저장되는 Persistence 쿠키 
		c.setMaxAge(-1);  // 인자값이 음수이거나 setMaxAge를 사용하지 않을경우 Session 쿠기
		response.addCookie(c); // 생성된 쿠키를 브라우저로 전송
		out.println("현재시간 : " + d);
		out.println("<br> 문자열을 Cookie에 저장합니다.");

	}

}
