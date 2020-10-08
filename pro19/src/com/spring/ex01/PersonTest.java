package com.spring.ex01;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class PersonTest { // ���̺귯������ �����ϴ� Ŭ����(BeanFactory)�� �̿��� person.xml�� ������� personServiceIMPL ���� �޸𸮿� ����

	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("person.xml")); // ���� �� person.xml�� �о� �鿩 ���� ����
		PersonService person = (PersonService) factory.getBean("personService"); // id�� personService�� ���� ������
		// PersonService person = new PersonServiceImpl(); // �� �̻� �ڹ� �ڵ忡�� ��ü�� ���� �������� �ʾƵ� �ǹǷ� �ּ� ó��
		person.sayHello(); // ������ ���� �̿��� name ���� ���
	}

}
