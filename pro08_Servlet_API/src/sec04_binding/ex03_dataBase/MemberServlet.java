package sec04_binding.ex03_dataBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MemberDAO dao = new MemberDAO(); // SQL문으로 조회할 MemberDAO 객체를 생성
		
		// ---------- toDo ----------
		String command = request.getParameter("command"); // command 값을 받아 옴
	      
		if(command != null && command.equals("addMember")) { // 회원 가입창에서 전송된 command가 addMember이면 전송된 값들을 받아옴
			String _id = request.getParameter("id");	// 회원 가입창에서 전송된 값들을 얻어 와 MemberVO 객체에 저장한 후 SQL문을 이용해 전달
			String _pwd = request.getParameter("pwd");
			String _name = request.getParameter("name");
			String _email = request.getParameter("email");
		 
			MemberVO vo = new MemberVO();
			vo.setId(_id);
			vo.setPwd(_pwd);
			vo.setName(_name);
			vo.setEmail(_email);
			dao.addMember(vo);
	     
		}else if(command != null && command.equals("delMember")) { // command 값이 delMember인 경우 id를 가져와 SQL문으로 전달해서 삭제
			String id = request.getParameter("id");
			dao.delMember(id);
		}		
		// ---------------------------
		
		List membersList = dao.listMembers();
		request.setAttribute("membersList", membersList); // 조회된 회원 정보를 ArrayList 객체에 저장한 후 request에 바인딩
		RequestDispatcher dispatch = request.getRequestDispatcher("viewMembers"); // 바인딩한 request를 viewMembers 서블릿으로 포워딩
		dispatch.forward(request, response);
	}
	
}
