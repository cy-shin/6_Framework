package edu.kh.project.member.model.service;

import edu.kh.project.member.model.vo.Member;

// 설계 + 유지보수 + AOP
public interface MyPageService {

	/* public abstract int updateInfo(Member inputMember); */
	/* int updateInfo(Member inputMember); */

	/** 회원 정보 수정 서비스
	 * @param inputMember
	 * @return
	 */
	int updateInfo(Member inputMember);

	
}
