package edu.kh.project.member.model.service;

import edu.kh.project.member.model.vo.Member;

// 서비스 인터페이스 : 설계, 유지보수성 향상, AOP
public interface AjaxService {


	/**  이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int emailDupCheck(String memberEmail);

	
	/** 닉네임 중복 검사
	 * @param memberNickName
	 * @return
	 */
	int nicknameDupCheck(String memberNickName);


	/** 이메일로 검색
	 * @param email
	 * @return
	 */
	Member selectEmail(String email);
	
	
	
}
