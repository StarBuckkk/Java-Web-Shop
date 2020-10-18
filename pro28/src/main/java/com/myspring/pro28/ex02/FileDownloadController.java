package com.myspring.pro28.ex02;

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

/*@Controller*/
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	
//	@RequestMapping("/download")
//	protected void download(@RequestParam("imageFileName") String imageFileName,
//			                 HttpServletResponse response) throws Exception {
//		OutputStream out = response.getOutputStream();
//		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
//		File image = new File(filePath); // 다운로드할 파일 객체를 생성
//		int lastIndex = imageFileName.lastIndexOf("."); // 확장자를 제외한 원본 이미지 파일의 이름을 가져옴
//		String fileName = imageFileName.substring(0, lastIndex);
//		File thumbnail = new File(CURR_IMAGE_REPO_PATH + "\\" + "thumbnail" + "\\" + fileName + ".png"); // 원본 이미지 파일 이름과 같은 이름의 썸네일 파일에 대한 File 객체를 생성
//		
//		if (image.exists() ) { // 원본 이미지 파일을 가로세로가 50픽셀인 png 형식의 썸네일 이미지 파일로 생성
//			thumbnail.getParentFile().mkdirs();
//		    Thumbnails.of(image).size(50,50).outputFormat("png").toFile(thumbnail);
//		}
//
//		FileInputStream in = new FileInputStream(thumbnail);
//		byte[] buffer = new byte[1024 * 8]; // 버퍼 기능을 이용해 빠르게 브라우저로 이미지 파일을 전송
//		
//		while (true) { // 버퍼를 이용해 한 번에 8kbyte씩 브라우저로 보냄
//			int count = in.read(buffer); // 버퍼에 읽어들인 문자개수
//			if (count == -1) // 버퍼의 마지막에 도달했는지 체크
//				break;
//			out.write(buffer, 0, count);
//		} // 생성된 썸네일 파일을 브라우저로 전송
//		in.close();
//		out.close();
//	}
	
	/*
	 * 쇼핑몰의 상품 목록 이미지 같은 경우 썸네일 이미지 파일을 따로 생성할 필요 없이 썸네일 이미지로 바로 다운로드하면 훨씬 빨리 표시가능
	 * ㅂ,라우저에 표시되는 결과는 앞에서와 같지만 해당 경로의 폴더를 보면 썸네일 이미지 파일이 따로 생성되지 않음 
	 * */

	@RequestMapping("/download")
	protected void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File image = new File(filePath); // 다운로드할 파일 객체를 생성
		int lastIndex = imageFileName.lastIndexOf("."); // 확장자를 제외한 원본 이미지 파일의 이름을 가져옴
		String fileName = imageFileName.substring(0,lastIndex);
		File thumbnail = new File(CURR_IMAGE_REPO_PATH+"\\"+"thumbnail"+"\\"+fileName+".png"); // 원본 이미지 파일 이름과 같은 이름의 썸네일 파일에 대한 File 객체를 생성
		if (image.exists()) { 
			Thumbnails.of(image).size(50,50).outputFormat("png").toOutputStream(out);
			// 원본 이미지에 대한 썸네일 이미지를 생성한 후 OutputStream 객체에 할당
		}else {
			return;
		}
		byte[] buffer = new byte[1024 * 8]; // 썸네일 이미지를 OutputStream 객체를 이용해 브라우저로 전송
		out.write(buffer);
		out.close();
	}
	
}
