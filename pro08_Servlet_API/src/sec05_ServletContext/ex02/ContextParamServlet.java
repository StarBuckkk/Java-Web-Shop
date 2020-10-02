package sec05_ServletContext.ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/initMenu")
public class ContextParamServlet extends HttpServlet {	
	protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,  IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext(); // ServletContext의 객체를 가져옴

		String menu_member = context.getInitParameter("menu_member"); // web.xml에서 작성한 <param-name>태그의 이름으로 <param-value> 태그의 값인 메뉴 이름들을 가져옴
		String menu_order = context.getInitParameter("menu_order");
		String menu_goods = context.getInitParameter("menu_goods");

		out.print("<html><body>"); // 크롬, 익스플로어등 모든 브라우저에서 같은 메뉴를 출력 -> 메뉴는 ContextServlet 객체를 통해 접근하므로 모든 웹 브라우저에서 공유하면서 접근 가능
		out.print("<table border=1 cellspacing=0><tr>메뉴 이름</tr>");
		out.print("<tr><td>" + menu_member + "</td></tr>");
		out.print("<tr><td>" + menu_order + "</td></tr>");
		out.print("<tr><td>" + menu_goods + "</td></tr>");
		out.print("</tr></table></body></html>");	
	}
}
