package sec05.Session_DB_Notbinding;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowMember
 */
@WebServlet("/show")
public class ShowMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String id ="", pwd="" ;
        Boolean isLogon=false;
        HttpSession session =  request.getSession(false); // 이미 세션이 존재하면 세션을 반환하고, 없으면 null 반환	

        if(session != null) { // 먼저 세션이 생성되어 있는지 확인
	    isLogon = (Boolean)session.getAttribute("isLogon");
           
	    	if(isLogon == true) { //  isLogon 속성을 가져와 로그인 상태 확인
	    		id = (String)session.getAttribute("login.id"); // isLogon이 true이면 로그인 상태이므로 회원 정보를 브라우저에 표시
	    		pwd = (String)session.getAttribute("login.pwd");
	    		out.print("<html><body>");
	    		out.print("아이디: " + id+"<br>");
	    		out.print("비밀번호: " + pwd+"<br>");
	    		out.print("</body></html>");
	    	}else {
	    		response.sendRedirect("login3.html"); // 로그인 상태가 아니면 로그인창으로 이동
	    	}
        }else {
        	response.sendRedirect("login3.html"); // 세션이 생성되지 않았으면 로그인 창으로 이동
        }
	}
}
