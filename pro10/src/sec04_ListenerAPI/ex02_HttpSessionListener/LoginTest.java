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
 * Servlet implementation class LoginTest
 */
@WebServlet("/login")
public class LoginTest extends HttpServlet {
	ServletContext context = null;
	List user_list = new ArrayList(); // �α����� ������ id�� �����ϴ� ArrayList

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		context = getServletContext();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		LoginImpl loginUser = new LoginImpl(user_id, user_pw); // LoginImpl ��ü�� ������ �� ���۵� id�� ��й�ȣ�� ������
		if (session.isNew()) { // ���� �α��� �� ������ id�� ArrayList�� ���ʷ� ������ �� �ٽ� context ��ü�� �Ӽ����� ����
			session.setAttribute("loginUser", loginUser); 
			user_list.add(user_id);
			context.setAttribute("user_list", user_list);
		}

		out.println("<html><body>");
		out.println("���̵�� " + loginUser.user_id + "<br>");
		out.println("�� �����ڼ���" + LoginImpl.total_user + "<br><br>"); // ���ǿ� ���ε� �̺�Ʈ ó�� �� �� �����ڼ��� ǥ��
		out.println("���� ���̵�:<br>");
		List list = (ArrayList) context.getAttribute("user_list"); // context ��ü�� ArrayList�� ������ ������ id�� ���ʷ� �������� ���
		for (int i = 0; i < list.size(); i++) {
			out.println(list.get(i) + "<br>");
		}
		out.println("<a href='logout?user_id=" + user_id + "'>�α׾ƿ� </a>"); // �α׾ƿ� Ŭ�� �� ������ logout���� ������ id�� ������ �α׾ƿ�
		out.println("</body></html>");
	}

}