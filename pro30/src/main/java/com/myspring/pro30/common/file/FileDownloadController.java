package com.myspring.pro30.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FileDownloadController {
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image"; // ���� ���� ��ġ�� ����
	@RequestMapping("/download.do")
	protected void download(@RequestParam("imageFileName") String imageFileName, // �̹��� ���� �̸��� �ٷ� ����
							@RequestParam("articleNO") String articleNO,
			                 HttpServletResponse response)throws Exception { // �ٿ�ε��� �̹��� ���� �̸��� ����
		OutputStream out = response.getOutputStream();
		String downFile = ARTICLE_IMAGE_REPO + "\\" + articleNO +"\\"+ imageFileName; // �� ��ȣ�� ���� �̸����� �ٿ�ε��� ���� ��θ� ����
		File file = new File(downFile); // �ٿ�ε��� ���� ��ü�� ����

		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName); // ����� ���� �̸��� ����
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 8]; // ���� ����� �̿��� ������ �������� �̹��� ������ ����
		
		while (true) { // ���۸� �̿��� �� ���� 8kbyte�� �������� ����
			int count = in.read(buffer); // ���ۿ� �о���� ���ڰ���
			
			if (count == -1) // ������ �������� �����ߴ��� üũ
				break;
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
	}

}
