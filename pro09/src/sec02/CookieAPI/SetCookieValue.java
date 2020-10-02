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
		Cookie c = new Cookie("cookieTest", URLEncoder.encode("JSP���α׷����Դϴ�.", "utf-8")); // Cookie ��ü�� ������ cookieTest �̸����� �ѱ� ������ URLEncoder.encode�� ���ڵ��ؼ� ��Ű�� ���� 
//		c.setMaxAge(24 * 60 * 60); // ��ȿ�Ⱓ : ���ڰ��� ����ϰ��  ���Ͽ� ����Ǵ� Persistence ��Ű 
		c.setMaxAge(-1);  // ���ڰ��� �����̰ų� setMaxAge�� ������� ������� Session ���
		response.addCookie(c); // ������ ��Ű�� �������� ����
		out.println("����ð� : " + d);
		out.println("<br> ���ڿ��� Cookie�� �����մϴ�.");

	}

}
