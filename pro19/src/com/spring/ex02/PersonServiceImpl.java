package com.spring.ex02;

public class PersonServiceImpl implements PersonService {
	private String name;
	private int age;

	public PersonServiceImpl(String name) { // person.xml애서 인자가 한 개인 생성자 설정 시 사용
		this.name = name;
	}

	public PersonServiceImpl(String name, int age) { // person.xml애서 인자가 두 개인 생성자 설정 시 사용
		this.name = name;
		this.age = age;
	}

	@Override
	public void sayHello() { // 추상 메서드 구현
		System.out.println("이름: " + name);
		System.out.println("나이: " + age + "살");
	}
}
