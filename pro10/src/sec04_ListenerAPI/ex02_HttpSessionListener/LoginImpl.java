package sec04_ListenerAPI.ex02_HttpSessionListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class LoginImpl
 *
 */
@WebListener // HttpSessionListener�� ������ Ŭ������ �ݵ�� �̺�Ʈ �ڵ鷯�� �ֳ����̼��� �̿��ؼ� ����ؾ� �Ѵ�.
public class LoginImpl implements HttpSessionListener { // HttpSessionListener ���� / ���ǿ� ���ε� �� �̺�Ʈ ó��  
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
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("���� ����");
		++total_user; // ���ǿ� ���� �� �����ڼ��� ������Ŵ
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("���� �Ҹ�");
		--total_user; // ���� �Ҹ� �� �����ڼ��� ���ҽ�Ŵ
	}

}
