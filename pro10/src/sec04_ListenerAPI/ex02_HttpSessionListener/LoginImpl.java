package sec04_ListenerAPI.ex02_HttpSessionListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class LoginImpl
 *
 */
@WebListener // HttpSessionListener를 구현한 클래스는 반드시 이벤트 핸들러를 애너테이션을 이용해서 등록해야 한다.
public class LoginImpl implements HttpSessionListener { // HttpSessionListener 구현 / 세션에 바인딩 시 이벤트 처리  
	String user_id;
	String user_pw;
	static int total_user = 0; // 세션에 바인딩 시 1씩 증가시킴

	public LoginImpl() {
	}

	public LoginImpl(String user_id, String user_pw) {
		this.user_id = user_id;
		this.user_pw = user_pw;
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("세션 생성");
		++total_user; // 세션에 생성 시 접속자수를 증가시킴
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("세션 소멸");
		--total_user; // 세션 소멸 시 접속자수를 감소시킴
	}

}
