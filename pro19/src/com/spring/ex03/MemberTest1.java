package com.spring.ex03;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class MemberTest1 { // ���̺귯������ �����ϴ� Ŭ����(BeanFactory)�� �̿��� person.xml�� ������� MemberServiceImpl ���� �޸𸮿� ����
	public static void main(String[] args) {
		
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("member.xml")); // ���� �� member.xml�� �о� �鿩 ���� ����
		MemberService service = (MemberService) factory.getBean("memberService"); // id�� memberService�� ���� ������
		service.listMembers(); // ������ ���� �̿��� �Լ�ȣ��
	}
}
