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

/* @WebServlet의 구성 요소들
 * urlPatterns : 웹 브라우저에서 서블릿 요청 시 사용하는 매핑 이름 -> 매핑 이름을 여러 개 설정 가능
 * name : 서블릿 이름
 * loadOnStartup : 컨테이너 실행 시 서블릿이 로드되는 순서 지정
 * initParams : @WebInitParam 애너테이션 이용해 매개변수를 추가하는 기능 -> 여러개의 매개변수 설정 가능
 * description : 서블릿에 대한 설명 */

/**
 * Servlet implementation class LoadAppConfig
 */

//@WebServlet 애너테이션을 이용한 서블릿 설정
@WebServlet(name = "loadConfig", urlPatterns = { "/loadConfig"},loadOnStartup=1) // loadOnStartup 속성을 추가하고 우선순위를 1로 설정(양의정수, 작은 숫자부터 먼저 초기화됨)
//@WebServlet(name = "loadConfig", urlPatterns = { "/loadConfig"})

/* 톰캣 실행 시 Init() 메서드를 호출하면 getInitParameter() 메서드를 이용해 
 * web.xml의 메뉴정보를 읽어 들인 후 다시 ServletContext 객체에 setAttribute() 메서드로 바인딩한다.
 *  
 * 브라우저에서 요청하면 web.xml이 아니라  ServletContext 객체에서 메뉴 항목을 가져온 후 출력하기 때문에 파일에서 읽어 들여와 출력하는 것보다 빨리 출력 가능
 *  */

public class LoadAppConfig extends HttpServlet {
	private ServletContext context; // 변수 context를 멤버변수로 선언
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("LoadAppConfig의 init 메서드 호출");
		context = config.getServletContext(); // init()메서드에서 ServletContext 객체를 얻음
		String menu_member = context.getInitParameter("menu_member"); // getInitParameter() 메서드로 web.xml의 메뉴 정보를 읽어옴
		String menu_order = context.getInitParameter("menu_order");
		String menu_goods = context.getInitParameter("menu_goods");
		
		context.setAttribute("menu_member", menu_member); // 메뉴 정보를 ServletContext 객체에 바인딩
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
//		ServletContext context = getServletContext(); // doGet() 메서드 호출 시 ServletContext 객체를 얻는 부분은 주석처리함
		
		String menu_member = (String)context.getAttribute("menu_member"); // 브라우저에서 요청 시 ServletContext 객체의 바인딩된 메뉴 항목을 가져옴
		String menu_order = (String)context.getAttribute("menu_order");
		String menu_goods = (String)context.getAttribute("menu_goods");

		out.print("<html><body>");
		out.print("<table border=1 cellspacing=0><tr>메뉴 이름</tr>");
		out.print("<tr><td>" + menu_member + "</td></tr>");
		out.print("<tr><td>" + menu_order + "</td></tr>");
		out.print("<tr><td>" + menu_goods + "</td></tr>");
		out.print("</tr></table></body></html>");
	}

}
