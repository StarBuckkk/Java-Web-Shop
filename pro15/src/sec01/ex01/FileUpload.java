package sec01.ex01;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/upload.do")
public class FileUpload extends HttpServlet {
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

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
			String encoding = "utf-8";
			File currentDirPath = new File("C:\\file_repo"); // 업로드할 파일 경로를 지정
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(currentDirPath); // 파일 경로를 설정
			factory.setSizeThreshold(1024 * 1024); // 최대 업로드 가능한 파일 크기를 설정

			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request); // request 객체에서 매개변수를 List로 가져옴
				for (int i = 0; i < items.size(); i++) {
					FileItem fileItem = (FileItem) items.get(i); // 파일 업로드창에서 업로드된 항목들을 하나씩 가져옴
	
					if (fileItem.isFormField()) {
						System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding)); // 폼 필드이면 전송된 매개변수 값을 출력
					} else {
						System.out.println("파라미터명:" + fileItem.getFieldName());
						System.out.println("파일명:" + fileItem.getName());
						System.out.println("파일크기:" + fileItem.getSize() + "bytes");
	
						if (fileItem.getSize() > 0) { // 폼 필드가 아니면 파일 업로드 기능 수행
							int idx = fileItem.getName().lastIndexOf("\\");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1); // 업로드한 파일 이름을 가져옴
							File uploadFile = new File(currentDirPath + "\\" + fileName);
							fileItem.write(uploadFile); // 업로드한 파일 이름으로 저장소에 파일 업로드
						} // end if
					} // end if
				} // end for
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
