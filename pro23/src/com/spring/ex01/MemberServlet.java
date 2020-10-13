package com.spring.ex01;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mem.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO(); // MemberDAO 객체를 생성하고 selectAllMemberList()를 호출한다.
//		List<MemberVO> membersList = dao.selectAllMemberList();
		List<HashMap<String, String>> membersList = dao.selectAllMemberList(); // 조회한 회원 정보를 List에 저장
		request.setAttribute("membersList", membersList); // 조회한 회원 정보를 바인딩하고  JSP로 포워딩
		RequestDispatcher dispatch = request.getRequestDispatcher("test01/listMembers.jsp"); // test01/listMembers.jsp로 포워딩
		dispatch.forward(request, response);
	}
}
