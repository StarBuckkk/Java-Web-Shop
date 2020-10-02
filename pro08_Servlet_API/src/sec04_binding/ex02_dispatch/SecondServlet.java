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
		String address = (String) request.getAttribute("address"); // 전달된 request에서 getAttribute()를 이용해 주소를 받아옴
		out.println("<html><body>");
		out.println("주소:" + address);
		out.println("<br>");
		out.println("dispatch를 이용한 바인딩 실습입니다."); // request 객체가 브라우저를 거치지 않고 바로 전달됨(브라우저가 second로 바뀌지 않음) -> 클라이언트는 알 수 없음
		out.println("</body></html>");

	}
}
