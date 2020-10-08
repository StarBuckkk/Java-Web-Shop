package com.spring.ex01;

public class PersonServiceImpl implements PersonService {
	private String name;
	private int age;

	public void setName(String name) { // person.xml에서 <value> 태그로 설정한 값을 name 속성에 주입 / age 속성은 setter가 없으므로 빈이 생성되더라도 값이 초기화되지 않는다.
		this.name = name;
	}

	@Override
	public void sayHello() { // 추상 메서드 구현
		System.out.println("이름: " + name);
		System.out.println("나이: " + age);
	}
}
