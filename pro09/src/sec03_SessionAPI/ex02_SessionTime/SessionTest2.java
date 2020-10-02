package sec03_SessionAPI.ex02_SessionTime;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionTest2
 */
@WebServlet("/sess2")
public class SessionTest2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(); // getSession()�� ȣ���Ͽ� ���� ��û �� ���� ��ü�� ���� �����ϰų� ���� ������ ��ȯ��
		
		out.println("���� ���̵� : " + session.getId() + "<br>"); // ������ ��ü�� ���� Id�� ������
		out.println("���� ���� ���� �ð� : " + new Date(session.getCreationTime()) + "<br>"); // ���� ���� ��ü ���� �ð��� ������
		out.println("�ֱ� ���� ���� �ð� : " + new Date(session.getLastAccessedTime()) + "<br>"); // ���� ��ü�� ���� �ֱٿ� ������ �ð��� ������
		
		out.println("�⺻ ���� ��ȿ �ð� : " + session.getMaxInactiveInterval() + "<br>"); // ������ �⺻ ���� ��ȿ�ð� ��� : 1800��(30��)
		session.setMaxInactiveInterval(5); // ���� ��ȿ�ð� 5�ʷ� ����
		out.println("���� ��ȿ �ð� : " + session.getMaxInactiveInterval() + "<br>");
		
		if (session.isNew()) { // ���� ������ �������� �Ǻ�
			out.print("�� ������ ����������ϴ�.");
		}
	}

}
