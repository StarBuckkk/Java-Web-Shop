package sec01.Attribute_Scope;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SetAttribute
 */
@WebServlet("/set")
public class SetAttribute extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String ctxMesg = "context에 바인딩됩니다.";
		String sesMesg = "session에 바인딩됩니다.";
		String reqMesg = "request에 바인딩됩니다.";

		ServletContext ctx = getServletContext(); // ServletContext, HttpSession, HttpServletRequest 객체를 얻은 후 setAttribute으로  바인딩
		HttpSession session = request.getSession();
		
		ctx.setAttribute("context", ctxMesg); // Context 객체에 바인딩 -> 어플리케이션 전체에 대해 접근 가능
		session.setAttribute("session", sesMesg); // HttpSession 객체 바인딩 -> 브라우저에서만 접근 가능
		request.setAttribute("request", reqMesg); // HttpServletRequest 객체에 바인딩 -> 해당 요청/응답 사이클에서만 접근 가능
		out.print("바인딩을 수행합니다.");

	}

}
