package sec05_ServletContext.ex01;

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

@WebServlet("/cset")
public class SetServletContext extends HttpServlet { //ServletContext Ŭ����
	
	/* ServletContext Ŭ������ Ư¡
	 * javax.servlet.ServletContext�� ����
	 * �������� �����̳� ���� ������ ���� ���
	 * ���ؽ�Ʈ(�� ���ø����̼�)���� �ϳ��� ServletContext�� ������
	 * ���������� �ڿ�(������)�� �����ϴµ� ���
	 * �����̳� ���� �� �����ǰ� �����̳� ���� �� �Ҹ�� */
	
	/* ServletContext�� �����ϴ� ���
	 * ���������� ���� ���� ���
	 * �ڿ� ���ε� ���
	 * �α� ���� ���
	 * ���ؽ�Ʈ���� �����ϴ� ���� ���� ���� */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext(); // ServletContext ��ü�� ������

		List member = new ArrayList();
		member.add("�̼���");
		member.add(30);
		context.setAttribute("member", member); // ServletContext ��ü�� �����͸� ���ε�

		out.print("<html><body>");
		out.print("�̼��Ű� 30 ����");
		out.print("</body></html>");
	}
}