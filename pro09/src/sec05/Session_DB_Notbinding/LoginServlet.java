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
		String user_id = request.getParameter("user_id"); // �α���â���� ���۵� ID�� ��й�ȣ�� ������
		String user_pwd = request.getParameter("user_pwd");

		MemberVO memberVO = new MemberVO(); // MemberVO ��ü�� �����ϰ� �Ӽ��� id�� ��й�ȣ�� ����
		memberVO.setId(user_id);
		memberVO.setPwd(user_pwd);
		MemberDAO dao = new MemberDAO();
		boolean result = dao.isExisted(memberVO); // MemberDAO�� isExisted �޼��带 ȣ���ϸ鼭 memberVO�� ����

		if (result) {
			HttpSession session = request.getSession();
			session.setAttribute("isLogon", true); // ��ȸ�� ����� true �̸� isLogon �Ӽ��� true�� ���ǿ� ����
			session.setAttribute("login.id", user_id); // ��ȸ�� ����� true�̸� id�� ��й�ȣ�� ���ǿ� ����
			session.setAttribute("login.pwd", user_pwd);

			out.print("<html><body>");
			out.print("�ȳ��ϼ��� " + user_id + "��!!!<br>");
			out.print("<a href='show'>ȸ����������</a>"); // ȸ���������⸦ Ŭ���ϸ� �̹� �α��� �����̹Ƿ� ���ǿ� ����� ȸ�� ������ ǥ�õ� / ShowMember Ŭ������ �̵�
			out.print("</body></html>");
		} else {
			out.print("<html><body>ȸ�� ���̵� Ʋ���ϴ�.");
			out.print("<a href='login3.html'> �ٽ� �α����ϱ�</a>");
			out.print("</body></html>");
		}
	}

}
