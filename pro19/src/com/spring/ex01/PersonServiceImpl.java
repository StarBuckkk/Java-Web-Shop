package com.spring.ex01;

public class PersonServiceImpl implements PersonService {
	private String name;
	private int age;

	public void setName(String name) { // person.xml���� <value> �±׷� ������ ���� name �Ӽ��� ���� / age �Ӽ��� setter�� �����Ƿ� ���� �����Ǵ��� ���� �ʱ�ȭ���� �ʴ´�.
		this.name = name;
	}

	@Override
	public void sayHello() { // �߻� �޼��� ����
		System.out.println("�̸�: " + name);
		System.out.println("����: " + age);
	}
}
