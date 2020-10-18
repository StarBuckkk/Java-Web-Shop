package com.spring.account;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED) // @Transactional을 이용해 AccountService 클래스의 모든 메서드에 트랜잭션을 적용
public class AccountService {
	private AccountDAO accDAO;

	public void setAccDAO(AccountDAO accDAO) { // 속성 accDAO에 빈을 주입하기 위해 setter를 구현
		this.accDAO = accDAO;
	}

	public void sendMoney() throws Exception { // sendMoney 메서드 호출 시 accDAO의 두 개의 sql문을 실행
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


