package sec01.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionTest
 */
@WebServlet("/sess")
public class SessionTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(); // session ��ü�� ������
		session.setAttribute("name", "�̼���"); // session ��ü�� name�� ���ε�
		
		pw.println("<html><body>");
		pw.println("<h1>���ǿ� �̸��� ���ε��մϴ�.</h1>");
		pw.println("<a href='/pro12_JSP_Script/test01/session1.jsp'>ù��° �������� �̵��ϱ� </a>");
		pw.println("</body></html>"); 
		
	}

}
