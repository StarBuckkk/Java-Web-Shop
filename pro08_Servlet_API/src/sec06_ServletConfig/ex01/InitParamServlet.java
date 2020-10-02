package sec06_ServletConfig.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* @WebServlet의 구성 요소들
 * urlPatterns : 웹 브라우저에서 서블릿 요청 시 사용하는 매핑 이름 -> 매핑 이름을 여러 개 설정 가능
 * name : 서블릿 이름
 * loadOnStartup : 컨테이너 실행 시 서블릿이 로드되는 순서 지정
 * initParams : @WebInitParam 애너테이션 이용해 매개변수를 추가하는 기능 -> 여러개의 매개변수 설정 가능
 * description : 서블릿에 대한 설명 */

/**
 * Servlet implementation class InitParamServlet
 */

// @WebServlet 애너테이션을 이용한 서블릿 설정
@WebServlet(name="initParamServlet",
        urlPatterns = { "/sInit", "/sInit2" }, initParams = {
		@WebInitParam(name = "email", value = "admin@jweb.com"), 
		@WebInitParam(name = "tel", value = "010-1111-2222") })

public class InitParamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String email = getInitParameter("email"); // 설정한 매개변수의 name으로 값을 가져옴
		String tel = getInitParameter("tel");
		
		out.print("<html><body>");
		out.print("<table><tr>");
		out.print("<td>email: </td><td>" + email + "</td></tr>");
		out.print("<tr><td>휴대전화: </td><td>" + tel + "</td>");
		out.print("</tr></table></body></html>");

	}

	
}
