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
		MemberDAO dao = new MemberDAO(); // SQL������ ��ȸ�� MemberDAO ��ü�� ����
		PrintWriter out = response.getWriter();
		String command = request.getParameter("command"); // command ���� �޾� ��
      
		if(command != null && command.equals("addMember")) { // ȸ�� ����â���� ���۵� command�� addMember�̸� ���۵� ������ �޾ƿ�
			String _id = request.getParameter("id");	// ȸ�� ����â���� ���۵� ������ ��� �� MemberVO ��ü�� ������ �� SQL���� �̿��� ����
			String _pwd = request.getParameter("pwd");
			String _name = request.getParameter("name");
			String _email = request.getParameter("email");
		 
			MemberVO vo = new MemberVO();
			vo.setId(_id);
			vo.setPwd(_pwd);
			vo.setName(_name);
			vo.setEmail(_email);
			dao.addMember(vo);
	     
		}else if(command != null && command.equals("delMember")) { // command ���� delMember�� ��� id�� ������ SQL������ �����ؼ� ����
			String id = request.getParameter("id");
			dao.delMember(id);
		}
      
		List list = dao.listMembers();
		out.print("<html><body>");
		out.print("<table border=1><tr align='center' bgcolor='lightgreen'>");
		out.print("<td>���̵�</td><td>��й�ȣ</td><td>�̸�</td><td>�̸���</td><td>������</td><td >����</td></tr>");
    
		for (int i = 0; i < list.size(); i++) { // ��ȸ�� ȸ�� ������ for���� <tr> �±׸� �̿��� ����Ʈ�� ���
			MemberVO memberVO = (MemberVO) list.get(i); // MemberVO ��ü�� �Ѿ�ö� � Ÿ�Ե��� �Ѿ���� �𸣹Ƿ� ����ȯ
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
	 		                     + "<a href='/pro07_Servlet_BusinessModel/member3?command=delMember&id=" + id + "'>���� </a></td></tr>"); // ������ Ŭ���ϸ� command ���� ȸ�� id�� ���������� ���� get���

		}
	 	 out.print("</table></body></html>");
	     out.print("<a href='/pro07_Servlet_BusinessModel/memberForm.html'>�� ȸ�� ����ϱ�</a"); // Ŭ���ϸ� �ٽ� ȸ�� ����â���� �̵�
	}
	
}