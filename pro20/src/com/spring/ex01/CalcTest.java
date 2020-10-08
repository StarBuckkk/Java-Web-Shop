package com.spring.ex01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcTest { // ���̺귯������ �����ϴ� Ŭ����(ApplicationContext)�� �̿��� AOPTest.xml�� ������� personServiceIMPL ���� �޸𸮿� ����
	
	   public static void main(String[] args){
	      ApplicationContext context = new ClassPathXmlApplicationContext("AOPTest.xml"); // AOPTest.xml�� �о� �鿩 ���� ����
	      Calculator cal = (Calculator)context.getBean("proxyCal"); // id�� proxyCal�� �� ����

	      cal.add(100,20); // �޼��� ȣ�� ���Ŀ� �����̽� ���� �����
	      System.out.println();
	      cal.subtract(100,20);
	      System.out.println();
	      cal.multiply(100,20);
	      System.out.println();
	      cal.divide(100,20);
	   }
	}

