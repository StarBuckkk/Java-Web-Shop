package com.bookshop01.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;


@Controller
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH = "/var/lib/tomcat9/webapps/shopping/file_repo"; // 파일 저장 위치를 지정
	
	@RequestMapping("/download")
	protected void download(@RequestParam("fileName") String fileName, // 이미지 파일 이름과 상품 id를 가져옴
		                 	@RequestParam("goods_id") String goods_id,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "/" + goods_id + "/" + fileName; // 상품 id와  파일 이름으로 다운로드할 파일 경로를 설정
		File image = new File(filePath); // 다운로드할 파일 객체를 생성

		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + fileName);
		FileInputStream in = new FileInputStream(image); 
		byte[] buffer=new byte[1024*8]; // 버퍼 기능을 이용해 빠르게 브라우저로 이미지 파일을 전송
		
		while(true) { // 버퍼를 이용해 한 번에 8kbyte씩 브라우저로 보냄
			int count = in.read(buffer); //버퍼에 읽어들인 문자개수
			
			if(count==-1)  //버퍼의 마지막에 도달했는지 체크
				break;
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
	}
	
	
	@RequestMapping("/thumbnails.do")
	protected void thumbnails(@RequestParam("fileName") String fileName,
                            	@RequestParam("goods_id") String goods_id,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "/" + goods_id + "/" + fileName;
		File image = new File(filePath);
		
		if (image.exists() ) { 
			Thumbnails.of(image).size(121,154).outputFormat("png").toOutputStream(out); // 메인 페이지 이미지를 썸네일로 표시
		}
		
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
	}
}
