package com.spring.ex01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcTest { // 라이브러리에서 제공하는 클래스(ApplicationContext)를 이용해 AOPTest.xml의 설정대로 personServiceIMPL 빈을 메모리에 생성
	
	   public static void main(String[] args){
	      ApplicationContext context = new ClassPathXmlApplicationContext("AOPTest.xml"); // AOPTest.xml을 읽어 들여 빈을 생성
	      Calculator cal = (Calculator)context.getBean("proxyCal"); // id가 proxyCal인 빈에 접근

	      cal.add(100,20); // 메서드 호출 전후에 어드바이스 빈이 적용됨
	      System.out.println();
	      cal.subtract(100,20);
	      System.out.println();
	      cal.multiply(100,20);
	      System.out.println();
	      cal.divide(100,20);
	   }
	}

