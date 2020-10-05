package sec06_ServletConfig.ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* @WebServlet�� ���� ��ҵ�
 * urlPatterns : �� ���������� ������ ��û �� ����ϴ� ���� �̸� -> ���� �̸��� ���� �� ���� ����
 * name : ������ �̸�
 * loadOnStartup : �����̳� ���� �� �������� �ε�Ǵ� ���� ����
 * initParams : @WebInitParam �ֳ����̼� �̿��� �Ű������� �߰��ϴ� ��� -> �������� �Ű����� ���� ����
 * description : �������� ���� ���� */

/**
 * Servlet implementation class LoadAppConfig
 */

//@WebServlet �ֳ����̼��� �̿��� ������ ����
@WebServlet(name = "loadConfig", urlPatterns = { "/loadConfig"},loadOnStartup=1) // loadOnStartup �Ӽ��� �߰��ϰ� �켱������ 1�� ����(��������, ���� ���ں��� ���� �ʱ�ȭ��)
//@WebServlet(name = "loadConfig", urlPatterns = { "/loadConfig"})

/* ��Ĺ ���� �� Init() �޼��带 ȣ���ϸ� getInitParameter() �޼��带 �̿��� 
 * web.xml�� �޴������� �о� ���� �� �ٽ� ServletContext ��ü�� setAttribute() �޼���� ���ε��Ѵ�.
 *  
 * ���������� ��û�ϸ� web.xml�� �ƴ϶�  ServletContext ��ü���� �޴� �׸��� ������ �� ����ϱ� ������ ���Ͽ��� �о� �鿩�� ����ϴ� �ͺ��� ���� ��� ����
 *  */

public class LoadAppConfig extends HttpServlet {
	private ServletContext context; // ���� context�� ��������� ����
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("LoadAppConfig�� init �޼��� ȣ��");
		context = config.getServletContext(); // init()�޼��忡�� ServletContext ��ü�� ����
		String menu_member = context.getInitParameter("menu_member"); // getInitParameter() �޼���� web.xml�� �޴� ������ �о��
		String menu_order = context.getInitParameter("menu_order");
		String menu_goods = context.getInitParameter("menu_goods");
		
		context.setAttribute("menu_member", menu_member); // �޴� ������ ServletContext ��ü�� ���ε�
		context.setAttribute("menu_order", menu_order);
		context.setAttribute("menu_goods", menu_goods);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
//		ServletContext context = getServletContext(); // doGet() �޼��� ȣ�� �� ServletContext ��ü�� ��� �κ��� �ּ�ó����
		
		String menu_member = (String)context.getAttribute("menu_member"); // ���������� ��û �� ServletContext ��ü�� ���ε��� �޴� �׸��� ������
		String menu_order = (String)context.getAttribute("menu_order");
		String menu_goods = (String)context.getAttribute("menu_goods");

		out.print("<html><body>");
		out.print("<table border=1 cellspacing=0><tr>�޴� �̸�</tr>");
		out.print("<tr><td>" + menu_member + "</td></tr>");
		out.print("<tr><td>" + menu_order + "</td></tr>");
		out.print("<tr><td>" + menu_goods + "</td></tr>");
		out.print("</tr></table></body></html>");
	}

}