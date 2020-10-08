package com.spring.ex03;

public class MemberServiceImpl implements MemberService {
	private MemberDAO memberDAO; // 주입되는 빈을 저장할 MemberDAO 타입의 속성을 선선

	public void setMemberDAO(MemberDAO memberDAO) { // 설정 파일에서 memberDAO 빈을 생성한 후 setter로 속성 memberDAO에 주입
		this.memberDAO = memberDAO;
	}

	@Override
	public void listMembers() {
		memberDAO.listMembers(); // 주입된 빈을 이용해 listMembers() 메서드 호출
	}
}
