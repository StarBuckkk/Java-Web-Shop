package sec03_Board.brd08;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image"; // 글에 첨부한 이미지 저장 위치를 상수로 선언
	BoardService boardService;  // BoardService 객체를 코드에서 직접 생성해 사용
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session; // 답글에 대한 부모 글 번호를 저장하기 위해 세션을 요청
		
		String action = request.getPathInfo(); // 요청명을 가져옴
		System.out.println("action:" + action);
		try {
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
			
			if (action == null) {
				String _section = request.getParameter("section"); // 최초 요청 시 또는 /listArticle.do로 요청 시 section 의 값과 pageNum의 값을 구함
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt( ( (_section == null) ? "1" : _section ) ); // 최초 요청 시 section 값과 pageNum 값이 없으면 각각 1로 초기화
				int pageNum = Integer.parseInt( ( (_pageNum == null) ? "1" : _pageNum ) );
				Map<String, Integer> pagingMap = new HashMap<String, Integer>(); // section 값과 pageNum 값을 HashMap에 저장한 후 메서드로 넘김
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = boardService.listArticles(pagingMap); // section 값과 pageNum 값으로 해당 섹션과 페이지에 해당되는 글 목록을 조회
				articlesMap.put("section", section); // 브라우저에서 전송된 section과 pageNum 값을 articlesMap에 저장한 후 listArticles.jsp로 넘김
				articlesMap.put("pageNum", pageNum);
				request.setAttribute("articlesMap", articlesMap); // 조회된 글 목록을 articlesMap으로 바인딩하여 listArticles.jsp로 넘검
				nextPage = "/board07/ listArticles.jsp";
				}
			
			else if(action.equals("/listArticles.do")) { // action 값이 /listArticles.do이면 전체 클을 조회		 	
				String _section = request.getParameter("section"); // 글 목록에서 명시적으로 페이지 번호를 눌러서 요청한 경우 section 값과 pageNum 값을 가져옴 
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt( ( (_section == null) ? "1" : _section ) );
				int pageNum = Integer.parseInt( ( (_pageNum == null) ? "1" : _pageNum)  );
				Map pagingMap = new HashMap();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = boardService.listArticles(pagingMap); // 전체 글을 조회
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);
				request.setAttribute("articlesMap", articlesMap); // 조회한 글 목록을 articlesMap로 바인딩한 후 listArticles.jsp로 포워딩
				nextPage = "/board07/listArticles.jsp";
				
			} else if (action.equals("/articleForm.do")) {
				nextPage = "/board07/articleForm.jsp";
				
			} else if (action.equals("/addArticle.do")) { // /addArticle.do로 요청 시 새 글 추가 작업을 수행
				int articleNO = 0;
				Map<String, String> articleMap = upload(request, response); // 파일 업로드 기능을 사용하기 위해 upload()로 요청 전달
				String title = articleMap.get("title"); // articleMap에 저장된 글 정보를 다시 가져옴
				String content = articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");

				articleVO.setParentNO(0); // 새 글의 부모 글 번호를 0으로 설정
				articleVO.setId("hong"); // 새 글 작성자 id를 hong으로 설정
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName); // 글쓰기창에서 입력된 정보를 ArticleVO 객체에 설정한 후 addArticle()로 전달
				
				articleNO = boardService.addArticle(articleVO); // 테이블에 새 글을 추가한 후 새 글에 대한 글 번호를 가져옴
				
				if (imageFileName != null && imageFileName.length() != 0) { // 파일을 수행한 경우에만 수행
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName); // temp 폴더에 임시로 업로드된 파일 객체를 생성
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO); // CURR_IMAGE_REPO_PATH의 하위에 글 번호로 폴더를 생성
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true); // temp 폴더의 파일을 글 번호를 이름으로 하는 폴더로 이동시킴
				}
				
				PrintWriter pw = response.getWriter(); // 새 글 등록 메시지를 나타낸 후 자바스크립트 location 객체의 href 속성을 이용해 글 목록을 요청
				pw.print("<script>" + "  alert('새글을 추가했습니다.');" + " location.href='" + request.getContextPath()
						+ "/board/listArticles.do';" + "</script>");

				return;
				
			} else if (action.equals("/viewArticle.do")) { // 글 상세창을 요청할 경우 articleNO값을 가져옴
				String articleNO = request.getParameter("articleNO"); // articleNO 대한 글 정보를 조회하고 article 속성으로 바인딩
				articleVO = boardService.viewArticle(Integer.parseInt(articleNO));
				request.setAttribute("article", articleVO);
				nextPage = "/board07/viewArticle.jsp";
				
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
				boardService.modArticle(articleVO); // 전송된 글 정보를 이용해 글을 수정
				
				if (imageFileName != null && imageFileName.length() != 0) { // 수정된 이미지 파일을 폴더로 이동
					String originalFileName = articleMap.get("originalFileName");
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					;
					File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO + "\\" + originalFileName); // 전송된 originalFileName을 이용해 기존의 파일을 삭제
					oldFile.delete();
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글을 수정했습니다.');" + " location.href='" + request.getContextPath() // 글 수정 후 location 객체의 href 속성을 이용해 글 상세 화면을 나타냄
						+ "/board/viewArticle.do?articleNO=" + articleNO + "';" + "</script>");
				return;
			} else if (action.equals("/removeArticle.do")) {
				int articleNO = Integer.parseInt(request.getParameter("articleNO"));
				List<Integer> articleNOList = boardService.removeArticle(articleNO); // articleNO 값에 대한 글을 삭제한 후 삭제된 부모 글과 자식 글의 articleNO 목록을 가져옴
				
				for (int _articleNO : articleNOList) { // 삭제된 글들의 이미지 저장 폴더들을 삭제
					File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + _articleNO);
					if (imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글을 삭제했습니다.');" + " location.href='" + request.getContextPath()
						+ "/board/listArticles.do';" + "</script>");
				return;

			} else if (action.equals("/replyForm.do")) {
				int parentNO = Integer.parseInt(request.getParameter("parentNO")); // 답글창 요청 시 미리 부모 글 번호를 parentNO 속성으로 세션에 저장
				session = request.getSession();
				session.setAttribute("parentNO", parentNO);
				nextPage = "/board06/replyForm.jsp";
				
			} else if (action.equals("/addReply.do")) {
				
				session = request.getSession();// 답근 전송 시 세션에 저장된 parentNO를 가져옴
				int parentNO = (Integer) session.getAttribute("parentNO");
				session.removeAttribute("parentNO");
				Map<String, String> articleMap = upload(request, response);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");
				articleVO.setParentNO(parentNO);// 답글의 부모 글 번호를 설정
				articleVO.setId("lee");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				
				int articleNO = boardService.addReply(articleVO);
				
				if (imageFileName != null && imageFileName.length() != 0) { // 답글에 첨부한 이미지를 temp 폴더에서 답글 번호 폴더로 이동
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('답글을 추가했습니다.');" + " location.href='" + request.getContextPath()
						+ "/board/viewArticle.do?articleNO="+articleNO+"';" + "</script>");
				return;
			
			}else {
				nextPage = "/board06/listArticles.jsp";
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
		File currentDirPath = new File(ARTICLE_IMAGE_REPO); // 글 이미지 저장 폴더에 대해 파일 객체를 생성
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
					// 새 글과 관련된 title, content를 Map에 저장
					
				} else {
					System.out.println("파라미터명:" + fileItem.getFieldName());
					//System.out.println("파일명:" + fileItem.getName());
					System.out.println("파일크기:" + fileItem.getSize() + "bytes");
					//articleMap.put(fileItem.getFieldName(), fileItem.getName());
					
					if (fileItem.getSize() > 0) { // 업로드한 파일이 존재하는 경우 업로드한 파일의 파일 이름으로 저장소에 업로드
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);
						System.out.println("파일명:" + fileName);
								articleMap.put(fileItem.getFieldName(), fileName);  // 업로드 된 파일의 파일 이름을 Map에 ("imageFileName", "업로드파일이름")로 저장
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName); // 첨부한 파일을 먼저 temp 폴더에 업로드함
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
