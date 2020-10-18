package com.spring.account;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED) // @Transactional�� �̿��� AccountService Ŭ������ ��� �޼��忡 Ʈ������� ����
public class AccountService {
	private AccountDAO accDAO;

	public void setAccDAO(AccountDAO accDAO) { // �Ӽ� accDAO�� ���� �����ϱ� ���� setter�� ����
		this.accDAO = accDAO;
	}

	public void sendMoney() throws Exception { // sendMoney �޼��� ȣ�� �� accDAO�� �� ���� sql���� ����
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


