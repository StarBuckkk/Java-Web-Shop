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
		String address = (String) request.getAttribute("address"); // getAttribute�� request ��ü���� address�� ���� ������ -> null�� ��µȴ� !!! 
		
		/* redirect ������δ� �������� ���ε��� �����͸� �ٸ� �������� ���ۺҰ�(GET ���).
		 * �����ϰ��� �ϴ� �����Ͱ� ���Ȱ� ����� ������, ������ ���� ���ٸ� redirect(GET ���)�� �ᵵ ��������
		 * �뷮�� �����͸� �����ϰ��� �ϸ� ������ �ȴ�.
		 *  */
		
		out.println("<html><body>");
		out.println("�ּ�:" + address);
		out.println("<br>");
		out.println("redirect�� �̿��� ���ε� �ǽ��Դϴ�.");
		out.println("</body></html>");

	}
}
