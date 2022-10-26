package edu.kh.project.member.model.service;

import edu.kh.project.member.model.vo.Member;

public interface MemberService{
	
	/*
	 * * Service Interface 사용하는 이유
	 * 
	 * - 1) 프로젝트에 규칙성을 부여하기 위해서(충돌 등 문제를 방지) 
	 * - 2) 클래스 간의 결합도를 약화시키기 위함????? ---> 유지
	 * 보수성 향상 ( = 객체지향적 설계 ) - 결합도 = 한 객체를 이용하는 코드가 많다 -> 유지 보수가 어렵다 -> 결합도가 높다 -
	 * 
	 * 결합도 예시) - ArrayList<String> list = new ArrayList<String>(); 
	 * 객체 arrayList를 LinkedList로 바꾸면 객체를 이용하는 구문을 전부 바꿔야 함 = 결합도가 높다 
	 * - List<String> list = new ArrayList<String>(); 객체 arrayList를 LinkedLIst로 바꿔도 업캐스팅이 적용되어 있어서 추가 작업이 불필요 = 결합도가 낮다 
	 * - 3) AOP를 사용하기 위함 ---> spring-proxy를 이용하여 AOP코드가 동작하는데 이
	 * spring-proxy는 Service 인터페이스를 상속받아 동작
	 */

	
	
	
	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	public abstract Member login(Member inputMember);

	
	/** 회원 가입 서비스
	 * @param inputMember
	 * @return result
	 */
	public abstract int signUp(Member inputMember);
}
