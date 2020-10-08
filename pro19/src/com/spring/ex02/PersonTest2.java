package com.spring.ex02;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class PersonTest2 { // 라이브러리에서 제공하는 클래스(BeanFactory)를 이용해 person.xml의 설정대로 personServiceImpl 빈을 메모리에 생성
	
	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("person.xml")); // 실행 시 person.xml을 읽어 들여 빈을 생성
		PersonService person1 = (PersonService) factory.getBean("personService1"); // id가 personService1인 빈을 가져옴
		person1.sayHello(); // 생성된 빈을 이용해 name 값을 출력
		System.out.println();

		PersonService person2 = (PersonService) factory.getBean("personService2"); // id가 personService2인 빈을 가져옴
		person2.sayHello(); // 생성된 빈을 이용해 name과 age 를 출력
	}
}
