package sec04_ListenerAPI.ex01_HttpSessionBindingListener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

// HttpSessionBindingListener를 구현한 클래스는 리스너를 따로 등록할 필요가 없음
public class LoginImpl implements HttpSessionBindingListener { // HttpSessionBindingListener 구현 / 세션에 바인딩 시 이벤트 처리  
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
	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("사용자 접속");
		++total_user; // 세션에 저장 시 접속자수를 증가시킴
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("사용자 접속 해제");
		total_user--; // 세션에서 소멸 시 접속자수를 감소시킴
	}
}
