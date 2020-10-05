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
        HttpSession session =  request.getSession(false); // �̹� ������ �����ϸ� ������ ��ȯ�ϰ�, ������ null ��ȯ	

        if(session != null) { // ���� ������ �����Ǿ� �ִ��� Ȯ��
	    isLogon = (Boolean)session.getAttribute("isLogon");
           
	    	if(isLogon == true) { //  isLogon �Ӽ��� ������ �α��� ���� Ȯ��
	    		id = (String)session.getAttribute("login.id"); // isLogon�� true�̸� �α��� �����̹Ƿ� ȸ�� ������ �������� ǥ��
	    		pwd = (String)session.getAttribute("login.pwd");
	    		out.print("<html><body>");
	    		out.print("���̵�: " + id+"<br>");
	    		out.print("��й�ȣ: " + pwd+"<br>");
	    		out.print("</body></html>");
	    	}else {
	    		response.sendRedirect("login3.html"); // �α��� ���°� �ƴϸ� �α���â���� �̵�
	    	}
        }else {
        	response.sendRedirect("login3.html"); // ������ �������� �ʾ����� �α��� â���� �̵�
        }
	}
}