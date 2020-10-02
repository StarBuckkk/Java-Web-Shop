package sec05_ServletContext.ex03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cfile")
public class ContextFileServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext(); // getServletContext() 메서드로 ServletContext에 접근하여 getResourceAsStream() 메서드에 읽어들일 파일 위치를 지정한 후 파일에서 데이터를 입력받음
		InputStream is = context.getResourceAsStream("/WEB-INF/bin/init.txt"); // 해당 위치의 파일을 읽어들임
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is) ); // 버퍼로 읽음

		String menu = null;
		String menu_member = null;
		String menu_order = null;
		String menu_goods = null;
		
		while ((menu = buffer.readLine()) != null) { // readLind() 메서드로 콤마(,)를 구분자로 하여 메뉴 항목 분리
			StringTokenizer tokens = new StringTokenizer(menu, ","); // 회원등록 회원조회 회원수정, 주문조회 주문수정 주문취소, 상품조회 상품등록 상품수정 상품삭제 이므로 
			menu_member = tokens.nextToken();
			menu_order = tokens.nextToken();
			menu_goods = tokens.nextToken();
		}
		
		out.print("<html><body>");
		out.print(menu_member + "<br>"); // 회원
		out.print(menu_order + "<br>"); // 주문
		out.print(menu_goods + "<br>"); // 상품
		out.print("</body></html>");
		out.close();
	}
}
