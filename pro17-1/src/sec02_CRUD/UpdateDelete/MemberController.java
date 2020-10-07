package sec02_CRUD.UpdateDelete;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class MemberController
 */
@WebServlet("/member/*") // ���������� ��û �� �� �ܰ�� ��û �Ͼ
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;

	public void init() throws ServletException {
		memberDAO = new MemberDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo(); // URL ���� ��û���� ������
		System.out.println("action:" + action);
		
		if (action == null || action.equals("/listMembers.do")) { // ���� ��û�̰ų� action���� /memberList.do�� ȸ�� ����� ���
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp"; // test03 ������ listMember.jsp�� ������
			
		} else if (action.equals("/addMember.do")) { // action ���� /addMember.do�� ���۵� ȸ�� ������ �����ͼ� ���̺� �߰�
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			request.setAttribute("msg", "addMember");
			nextPage = "/member/listMembers.do"; // ȸ�� ��� �� �ٽ� ȸ�� ����� ���
			
		} else if (action.equals("/memberForm.do")) { // action ���� /memberForm.do�� ȸ�� ����â�� ȭ�鿡 ���
			nextPage = "/test03/memberForm.jsp"; // test03 ������ memberForm.jsp�� ������
			
		}else if(action.equals("/modMemberForm.do")) { // ȸ�� ����â ��û �� id�� ȸ�� ������ ��ȸ�� �� ����â���� ������
			
		     String id=request.getParameter("id");
		     MemberVO memInfo = memberDAO.findMember(id); // ȸ�� ���� ����â�� ��û�ϸ鼭 ���۵� id�� �̿��� ���� �� ȸ�� ������ ��ȸ
		     request.setAttribute("memInfo", memInfo); // request�� ���ε��Ͽ� ȸ�� ���� ����â�� �����ϱ� �� ȸ�� ������ ����
		     nextPage="/test03/modMemberForm.jsp";
		     
		}else if(action.equals("/modMember.do")) { // ���̺��� ȸ�� ������ ����
		     String id=request.getParameter("id"); // ȸ�� ���� ����â���� ���۵� ���� ȸ�� ������ ������ �� MemberVo ��ü �Ӽ��� ����
		     String pwd=request.getParameter("pwd");
		     String name= request.getParameter("name");
	         String email= request.getParameter("email");
		     MemberVO memberVO = new MemberVO(id, pwd, name, email);
		     memberDAO.modMember(memberVO);
		     request.setAttribute("msg", "modified"); // ȸ�� ���â���� ���� �۾� �Ϸ� �޼����� ����
		     nextPage="/member/listMembers.do";
		     
		}else if(action.equals("/delMember.do")) { // ȸ�� id�� sql������ ������ ���̺��� ȸ�� ������ ����
		     String id=request.getParameter("id"); // ������ ȸ�� id�� �޾ƿ�
		     memberDAO.delMember(id);
		     request.setAttribute("msg", "deleted"); // ȸ�� ���â���� ���� �۾� �Ϸ� �޽����� ����
		     nextPage="/member/listMembers.do";
		     
		}else {
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp";
			
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

}
