package sec01.ex01;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/input2")	
public class InputServlet2 extends HttpServlet{
   
	public void init() throws ServletException {
		System.out.println("init 메서드 호출");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                            throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Enumeration enu = request.getParameterNames(); // 전송되는 데이테가 맣은 경우 name 속성만 Enumeration 타입으로 받아옴
		
		while(enu.hasMoreElements() ) { // 각 name을 하나씩 가져와 전송되어온 값을 출력한다.
			String name=(String)enu.nextElement();
			String [] values = request.getParameterValues(name);		
			
			for(String value:values) {
				System.out.println("name = " + name + ", value = " + value);
			}   
		}
	}
	
	public void destroy() {
      System.out.println("destroy 메서드 호출");
   }
}
