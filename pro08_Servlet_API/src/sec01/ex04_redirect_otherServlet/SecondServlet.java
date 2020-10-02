package sec01.ex04_redirect_otherServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/second")
public class SecondServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws  ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		String name=request.getParameter("name"); // getParameter() 메서드로 name으로 이전 서블릿에서 전달된 lee를 받음
		out.println("</body></html>");
		out.println("이름:" + name);
		out.println("<br>");
		out.println("</body></html>");

	}
}
