package edu.kh.project.member.model.service;

import java.util.Map;

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

	/** 비밀번호 변경 서비스
	 * @param paramMap
	 * @return
	 */
	int changePw(Map<String, Object> paramMap);

	/** 뢰원 탈퇴 서비스
	 * @param memberNo
	 * @param memberPw
	 * @return
	 */
	int memberDelete(int memberNo, String memberPw);

	
}
