package sec05_ServletContext.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cset")
public class SetServletContext extends HttpServlet { //ServletContext 클래스
	
	/* ServletContext 클래스의 특징
	 * javax.servlet.ServletContext로 정의
	 * 서블릿과 컨테이너 간의 연동을 위해 사용
	 * 컨텍스트(웹 어플리케이션)마다 하나의 ServletContext가 생성됨
	 * 서블릿끼리 자원(데이터)를 공유하는데 사용
	 * 컨테이너 실행 시 생성되고 컨테이너 종료 시 소멸됨 */
	
	/* ServletContext가 제공하는 기능
	 * 서블릿에서 파일 접근 기능
	 * 자원 바인딩 기능
	 * 로그 파일 기능
	 * 컨텍스트에서 제공하는 설정 정보 제공 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext(); // ServletContext 객체를 가져옴

		List member = new ArrayList();
		member.add("이순신");
		member.add(30);
		context.setAttribute("member", member); // ServletContext 객체에 대이터를 바인딩

		out.print("<html><body>");
		out.print("이순신과 30 설정");
		out.print("</body></html>");
	}
}
