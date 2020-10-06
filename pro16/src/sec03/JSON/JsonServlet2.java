package sec03.JSON;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class JsonServlet2
 */
@WebServlet("/json2")
public class JsonServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();

		JSONObject totalObject = new JSONObject(); // �迭�� ������ totalObject�� ����
		JSONArray membersArray = new JSONArray(); // memberInfo json ��ü�� ������ membersArray �迭 ����
		JSONObject memberInfo = new JSONObject(); // ȸ�� �� ���� ������ �� memberinfo json ��ü ����

		memberInfo.put("name", "������"); // ȸ�� ������ name / value ������ ����
		memberInfo.put("age", "25");
		memberInfo.put("gender", "����");
		memberInfo.put("nickname", "��������");
        //  �迭�� �Է�
		membersArray.add(memberInfo); // ȸ�� ������ �ٽ� membersArray �迭�� ����

		memberInfo = new JSONObject(); // �ٸ� ȸ�� ������ name / value ������ ���� �� �� membersArray�� �ٽ� ����
		memberInfo.put("name", "�迬��");
		memberInfo.put("age", "21");
		memberInfo.put("gender", "����");
		memberInfo.put("nickname", "Įġ");
		membersArray.add(memberInfo);

		totalObject.put("members", membersArray); // totalObject�� members��� name���� membersArray�� value�� ����

		String jsonInfo = totalObject.toJSONString(); // JSONObject�� ���ڿ��� ��ȯ
		System.out.print(jsonInfo); 
		writer.print(jsonInfo); // JSON �����͸� �������� ����
	}

}