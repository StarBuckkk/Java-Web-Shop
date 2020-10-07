package sec03_Board.brd06;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class BoardController
 */
//@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image"; // �ۿ� ÷���� �̹��� ���� ��ġ�� ����� ����
	BoardService boardService;
	ArticleVO articleVO;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		boardService = new BoardService();
		articleVO = new ArticleVO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo(); // ��û���� ������
		System.out.println("action:" + action);
		try {
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
			if (action == null) {
				articlesList = boardService.listArticles();
				request.setAttribute("articlesList", articlesList);
				nextPage = "/board05/listArticles.jsp";
				
			} else if (action.equals("/listArticles.do")) { // action ���� /listArticles.do�̸� ��ü Ŭ�� ��ȸ
				articlesList = boardService.listArticles(); // ��ü ���� ��ȸ
				request.setAttribute("articlesList", articlesList); // ��ȸ�� �� ����� articlesList�� ���ε��� �� listArticles.jsp�� ������
				nextPage = "/board05/listArticles.jsp";
				
			} else if (action.equals("/articleForm.do")) {
				nextPage = "/board05/articleForm.jsp";
				
			} else if (action.equals("/addArticle.do")) { // /addArticle.do�� ��û �� �� �� �߰� �۾��� ����
				int articleNO = 0;
				Map<String, String> articleMap = upload(request, response); // ���� ���ε� ����� ����ϱ� ���� upload()�� ��û ����
				String title = articleMap.get("title"); // articleMap�� ����� �� ������ �ٽ� ������
				String content = articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");

				articleVO.setParentNO(0); // �� ���� �θ� �� ��ȣ�� 0���� ����
				articleVO.setId("hong"); // �� �� �ۼ��� id�� hong���� ����
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName); // �۾���â���� �Էµ� ������ ArticleVO ��ü�� ������ �� addArticle()�� ����
				
				articleNO = boardService.addArticle(articleVO); // ���̺� �� ���� �߰��� �� �� �ۿ� ���� �� ��ȣ�� ������
				
				if (imageFileName != null && imageFileName.length() != 0) { // ������ ������ ��쿡�� ����
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName); // temp ������ �ӽ÷� ���ε�� ���� ��ü�� ����
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO); // CURR_IMAGE_REPO_PATH�� ������ �� ��ȣ�� ������ ����
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true); // temp ������ ������ �� ��ȣ�� �̸����� �ϴ� ������ �̵���Ŵ
				}
				
				PrintWriter pw = response.getWriter(); // �� �� ��� �޽����� ��Ÿ�� �� �ڹٽ�ũ��Ʈ location ��ü�� href �Ӽ��� �̿��� �� ����� ��û
				pw.print("<script>" + "  alert('������ �߰��߽��ϴ�.');" + " location.href='" + request.getContextPath()
						+ "/board/listArticles.do';" + "</script>");

				return;
				
			} else if (action.equals("/viewArticle.do")) { // �� ��â�� ��û�� ��� articleNO���� ������
				String articleNO = request.getParameter("articleNO"); // articleNO ���� �� ������ ��ȸ�ϰ� article �Ӽ����� ���ε�
				articleVO = boardService.viewArticle(Integer.parseInt(articleNO));
				request.setAttribute("article", articleVO);
				nextPage = "/board05/viewArticle.jsp";
				
			} else if (action.equals("/modArticle.do")) {
				Map<String, String> articleMap = upload(request, response);
				int articleNO = Integer.parseInt(articleMap.get("articleNO"));
				articleVO.setArticleNO(articleNO);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");
				articleVO.setParentNO(0);
				articleVO.setId("hong");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				boardService.modArticle(articleVO); // ���۵� �� ������ �̿��� ���� ����
				
				if (imageFileName != null && imageFileName.length() != 0) { // ������ �̹��� ������ ������ �̵�
					String originalFileName = articleMap.get("originalFileName");
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					;
					File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO + "\\" + originalFileName); // ���۵� originalFileName�� �̿��� ������ ������ ����
					oldFile.delete();
				}
				
				PrintWriter pw = response.getWriter(); 
				pw.print("<script>" + "  alert('���� �����߽��ϴ�.');" + " location.href='" + request.getContextPath() // �� ���� �� location ��ü�� href �Ӽ��� �̿��� �� �� ȭ���� ��Ÿ��
						+ "/board/viewArticle.do?articleNO=" + articleNO + "';" + "</script>");
				return;
				
			} else if (action.equals("/removeArticle.do")) {
				int articleNO = Integer.parseInt(request.getParameter("articleNO"));
				List<Integer> articleNOList = boardService.removeArticle(articleNO); // articleNO ���� ���� ���� ������ �� ������ �θ� �۰� �ڽ� ���� articleNO ����� ������
				
				for (int _articleNO : articleNOList) { // ������ �۵��� �̹��� ���� �������� ����
					File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + _articleNO);
					
					if (imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('���� �����߽��ϴ�.');" + " location.href='" + request.getContextPath()
						+ "/board/listArticles.do';" + "</script>");
				return;
			
			}else {
				nextPage = "/board05/listArticles.jsp";
			}

			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ARTICLE_IMAGE_REPO); // �� �̹��� ���� ������ ���� ���� ��ü�� ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
					// �� �۰� ���õ� title, content�� Map�� ����
					
				} else {
					System.out.println("�Ķ���͸�:" + fileItem.getFieldName());
					//System.out.println("���ϸ�:" + fileItem.getName());
					System.out.println("����ũ��:" + fileItem.getSize() + "bytes");
					//articleMap.put(fileItem.getFieldName(), fileItem.getName());
					
					if (fileItem.getSize() > 0) { // ���ε��� ������ �����ϴ� ��� ���ε��� ������ ���� �̸����� ����ҿ� ���ε�
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);
						System.out.println("���ϸ�:" + fileName);
						articleMap.put(fileItem.getFieldName(), fileName);  // ���ε� �� ������ ���� �̸��� Map�� ("imageFileName", "���ε������̸�")�� ����
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName); // ÷���� ������ ���� temp ������ ���ε���
						fileItem.write(uploadFile);

					} // end if
				} // end if
			} // end for
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleMap;
	}

}
