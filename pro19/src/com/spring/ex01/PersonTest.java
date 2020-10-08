package com.spring.ex01;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class PersonTest { // 라이브러리에서 제공하는 클래스(BeanFactory)를 이용해 person.xml의 설정대로 personServiceIMPL 빈을 메모리에 생성

	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("person.xml")); // 실행 시 person.xml을 읽어 들여 빈을 생성
		PersonService person = (PersonService) factory.getBean("personService"); // id가 personService인 빈을 가져옴
		// PersonService person = new PersonServiceImpl(); // 더 이상 자바 코드에서 객체를 직접 생성하지 않아도 되므로 주석 처리
		person.sayHello(); // 생성된 빈을 이용해 name 값을 출력
	}

}
