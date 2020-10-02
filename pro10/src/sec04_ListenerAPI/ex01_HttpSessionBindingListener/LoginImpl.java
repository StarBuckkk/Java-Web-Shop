package sec04_ListenerAPI.ex01_HttpSessionBindingListener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

// HttpSessionBindingListener�� ������ Ŭ������ �����ʸ� ���� ����� �ʿ䰡 ����
public class LoginImpl implements HttpSessionBindingListener { // HttpSessionBindingListener ���� / ���ǿ� ���ε� �� �̺�Ʈ ó��  
	String user_id;
	String user_pw;
	static int total_user = 0; // ���ǿ� ���ε� �� 1�� ������Ŵ

	public LoginImpl() {
	}

	public LoginImpl(String user_id, String user_pw) {
		this.user_id = user_id;
		this.user_pw = user_pw;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("����� ����");
		++total_user; // ���ǿ� ���� �� �����ڼ��� ������Ŵ
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("����� ���� ����");
		total_user--; // ���ǿ��� �Ҹ� �� �����ڼ��� ���ҽ�Ŵ
	}
}
