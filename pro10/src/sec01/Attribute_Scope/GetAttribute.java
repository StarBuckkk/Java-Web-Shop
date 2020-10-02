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
 * Servlet implementation class GetAttribute
 */
@WebServlet("/get")
public class GetAttribute extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ServletContext ctx = getServletContext();
		HttpSession sess = request.getSession();

		// 각 서블릿 API에서 getAttribute로 바인딩된 속성의 값을 가져온다.
		String ctxMesg = (String) ctx.getAttribute("context"); // Context 객체에 바인딩된 데이터는 모든 브라우저에서 같은 결과를 출력한다.
		String sesMesg = (String) sess.getAttribute("session"); 
		String reqMesg = (String) request.getAttribute("request"); // 기존에 바인딩괸 request 객체는 /get으로 요청하여 생성된 request 객체와 다르므로 null이 출력된다.

		out.print("context값 : " + ctxMesg + "<br>"); 
		out.print("session값 : " + sesMesg + "<br>");
		out.print("request값 : " + reqMesg + "<br>");

	}

}
