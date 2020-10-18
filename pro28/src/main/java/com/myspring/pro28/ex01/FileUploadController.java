package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/*@Controller*/
public class FileUploadController {
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	@RequestMapping(value="/form")
	public String form() { // ���ε�â�� uploadForm.jsp�� ��ȯ
	    return "uploadForm";
	  }
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
	  throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		Map map = new HashMap(); // ���ϰ� �Ű������� ���� �����ϹǷ� Map�� ���
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements() ) { // ���۵� �Ű����� ���� key/value�� map�� ����
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			//System.out.println(name+", "+value);
			map.put(name,value);
		}
		
		List fileList = fileProcess(multipartRequest); // ������ ���ε��� �� ��ȯ�� ���� �̸��� ����� fileList�� �ٽ� map�� ����
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // map�� ���â���� ������
		mav.setViewName("result");
		
		return mav;
	}
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList= new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames(); // ÷�ε� ���� �̸��� ������
		
		while(fileNames.hasNext() ){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName); // ���� �̸��� ���� MultipartFile ��ü�� ������
			String originalFileName = mFile.getOriginalFilename(); // ���� ���� �̸��� ������
			fileList.add(originalFileName); // ���� �̸��� �ϳ��� fileList�� ����
			File file = new File(CURR_IMAGE_REPO_PATH + "\\" + fileName);
			
			if(mFile.getSize() !=0) { //File Null Check
				if(! file.exists() ) { //��λ� ������ �������� ���� ���
					if(file.getParentFile().mkdirs() ) { //��ο� �ش��ϴ� ���丮���� ����
						file.createNewFile(); //���� ���� ����
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + "\\" + originalFileName) ); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
			}
		}
		return fileList; // ÷���� ���� �̸��� ����� fileList�� ��ȯ
	}
}
