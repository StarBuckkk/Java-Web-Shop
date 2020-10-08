package com.spring.ex01;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingAdvice implements MethodInterceptor { // �������̽� MethodInterceptor�� ������ �����̽� Ŭ������ ����
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("[�޼��� ȣ�� �� : LogginAdvice");
		System.out.println(invocation.getMethod() + "�޼��� ȣ�� ��"); // �޼��� ȣ�� ���� �����ϴ� ����

		Object object = invocation.proceed(); // invocation�� �̿��� �޼��带 ȣ��

		System.out.println("[�޼��� ȣ�� �� : loggingAdvice");
		System.out.println(invocation.getMethod() + "�޼��� ȣ�� ��"); // �޼��� ȣ�� �Ŀ� �����ϴ� ����
		return object;
	}
}
