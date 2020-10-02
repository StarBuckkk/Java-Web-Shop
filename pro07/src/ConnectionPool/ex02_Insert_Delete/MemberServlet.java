package ConnectionPool.ex02_Insert_Delete;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member3")
public class MemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO(); // SQL문으로 조회할 MemberDAO 객체를 생성
		PrintWriter out = response.getWriter();
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
      
		List list = dao.listMembers();
		out.print("<html><body>");
		out.print("<table border=1><tr align='center' bgcolor='lightgreen'>");
		out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td><td >삭제</td></tr>");
    
		for (int i = 0; i < list.size(); i++) { // 조회한 회원 정보를 for문과 <tr> 태그를 이용해 리스트로 출력
			MemberVO memberVO = (MemberVO) list.get(i); // MemberVO 객체가 넘어올때 어떤 타입들이 넘어올지 모르므로 형변환
			String id = memberVO.getId();
	 		String pwd = memberVO.getPwd();
	 		String name = memberVO.getName();
	 		String email =memberVO.getEmail();
	 		Date joinDate = memberVO.getJoinDate();
	 		
	 		out.print("<tr><td>" + id + "</td><td>"
	 			                 + pwd + "</td><td>"
	 			                 + name + "</td><td>"
	 			                 + email + "</td><td>"
	 			                 + joinDate + "</td><td>"
	 		                     + "<a href='/pro07_Servlet_BusinessModel/member3?command=delMember&id=" + id + "'>삭제 </a></td></tr>"); // 삭제를 클릭하면 command 값과 회원 id룰 서블릿으로 전송 get방식

		}
	 	 out.print("</table></body></html>");
	     out.print("<a href='/pro07_Servlet_BusinessModel/memberForm.html'>새 회원 등록하기</a"); // 클릭하면 다시 회원 가입창으로 이동
	}
	
}
