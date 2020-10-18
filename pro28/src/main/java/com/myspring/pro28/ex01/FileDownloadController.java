package com.myspring.pro28.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*@Controller*/
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo"; // 파일 저장 위치를 지정

	@RequestMapping("/download")
	public void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response)throws Exception { // 다운로드할 이미지 파일 이름을 전달
		OutputStream out = response.getOutputStream();
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File file = new File(downFile); // 다운로드할 파일 객체를 생성

		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName); // 헤더에 파일 이름을 설정
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 8]; // 버퍼 기능을 이용해 빠르게 브라우저로 이미지 파일을 전송
		
		while (true) { // 버퍼를 이용해 한 번에 8kbyte씩 브라우저로 보냄
			int count = in.read(buffer); // 버퍼에 읽어들인 문자개수
			if (count == -1) // 버퍼의 마지막에 도달했는지 체크
				break;
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
	}

}
