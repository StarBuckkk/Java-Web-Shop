package com.spring.ex02;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class PersonTest2 { // ���̺귯������ �����ϴ� Ŭ����(BeanFactory)�� �̿��� person.xml�� ������� personServiceImpl ���� �޸𸮿� ����
	
	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("person.xml")); // ���� �� person.xml�� �о� �鿩 ���� ����
		PersonService person1 = (PersonService) factory.getBean("personService1"); // id�� personService1�� ���� ������
		person1.sayHello(); // ������ ���� �̿��� name ���� ���
		System.out.println();

		PersonService person2 = (PersonService) factory.getBean("personService2"); // id�� personService2�� ���� ������
		person2.sayHello(); // ������ ���� �̿��� name�� age �� ���
	}
}
