package sec04_binding.ex02_dispatch;

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
		String address = (String) request.getAttribute("address"); // ���޵� request���� getAttribute()�� �̿��� �ּҸ� �޾ƿ�
		out.println("<html><body>");
		out.println("�ּ�:" + address);
		out.println("<br>");
		out.println("dispatch�� �̿��� ���ε� �ǽ��Դϴ�."); // request ��ü�� �������� ��ġ�� �ʰ� �ٷ� ���޵�(�������� second�� �ٲ��� ����) -> Ŭ���̾�Ʈ�� �� �� ����
		out.println("</body></html>");

	}
}
