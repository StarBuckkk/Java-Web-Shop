package com.spring.ex03;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class MemberTest1 { // 라이브러리에서 제공하는 클래스(BeanFactory)를 이용해 person.xml의 설정대로 MemberServiceImpl 빈을 메모리에 생성
	public static void main(String[] args) {
		
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("member.xml")); // 실행 시 member.xml을 읽어 들여 빈을 생성
		MemberService service = (MemberService) factory.getBean("memberService"); // id가 memberService인 빈을 가져옴
		service.listMembers(); // 생성된 빈을 이용해 함수호출
	}
}
