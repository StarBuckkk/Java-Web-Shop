package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet2
 */
@WebServlet("/login1")
public class LoginServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		System.out.println("init �޼��� ȣ��");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8"); // ������ ������ ������ HTML���� ����
		PrintWriter out = response.getWriter(); // HttpServletResponse ��ü�� getWriter()�� �̿��� ��� ��Ʈ�� PrintWriter ��ü�� �޾ƿ�

		String id = request.getParameter("user_id");
		String pw = request.getParameter("user_pw");

		String data = "<html>"; // �������� ����� �����͸� ���ڿ��� �����ؼ� HTML �±׷� ����
		data += "<body>";
		data += "���̵� : " + id;
		data += "<br>";
		data += "��й�ȣ : " + pw;
		data += "</html>";
		data += "</body>";
		out.print(data); // PrintWriter�� print()�� �̿��� HTML �±� ���ڿ��� �� �������� ���
	}

	public void destroy() {
		System.out.println("destroy �޼��� ȣ��");
	}

}