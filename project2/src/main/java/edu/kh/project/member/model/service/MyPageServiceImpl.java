package edu.kh.project.member.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.common.Util;
import edu.kh.project.member.model.dao.MyPageDAO;
import edu.kh.project.member.model.vo.Member;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private MyPageDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 회원 정보 수정 서비스
	@Transactional
	@Override
	public int updateInfo(Member inputMember) {
		int result = dao.updateInfo(inputMember);

		return result;
	}
	
	// 비밀번호 변경 서비스
	@Transactional
	@Override
	public int changePw(Map<String, Object> paramMap) {
		// 현재 비밀번호 일치 시 새 비밀번호로 변경
		// bCrypt를 이용해 암호화를 해두었기 때문에, 변경 작업이 두 단계로 나뉨
		
		// 1. 회원 번호를 이용해서 DB에서 암호화된 비밀번호를 조회
		String encPw = dao.selectEncPw( (int)paramMap.get("memberNo") );
		
		// 2. matches(입력PW, 암호화PW) 결과가 true인 경우
		//    새 비밀번호로 UPDATE하는 DAO 코드를 호출
		if (bcrypt.matches((String)paramMap.get("currentPw"), encPw)) {
			
			// 새 비밀번호 암호화
			String newPw = bcrypt.encode( (String)paramMap.get("newPw"));
			
			paramMap.put("newPw", newPw);
			// paramMap에 존재하는 기존 "newPw"를 덮어쓰기

			int result = dao.changePw(paramMap);
			
			return result;
		}
		
		return 0; // 현재 비밀번호 불일치 시 반환
	}
	
	// 회원 탈퇴 서비스
	@Transactional(rollbackFor = Exception.class) // 모든 종류의 예외(Exception.class 및 자식 예외) 발생 시 롤백을 시킴
	@Override
	public int memberDelete(int memberNo, String memberPw) {
		String encPw = dao.selectEncPw(memberNo);
		
		if(bcrypt.matches(memberPw, encPw)) {
			int result = dao.memberDelete(memberNo);

			return result;
		}
		
		return 0;
	}
	// 프로필 이미지 수정
//	@Transactional // 기본적으로 RuntimeException 오류 발생 시 롤백을 수행함
	@Transactional(rollbackFor = Exception.class) // 모든 종류의 예외(Exception.class 및 자식 예외) 발생 시 롤백을 함
	// 업데이트를 했으므로 트랜잭션 처리가 필요함
	@Override
	public int updateProfile(String webPath, String filePath, MultipartFile profileImage, Member loginMember) throws Exception {
		// Exception Exception is not compatible with throws clause in MyPageService.updateProfile(String, String, MultipartFile, Member)
		// 부모 클래스에는 작성이 안되어있어서 오류 발생
		
		// 실패를 대비해, 이전 이미지의 경로를 저장
		String temp = loginMember.getProfileImage();
		
		// 중복 파일명 업로드를 대비하기 위해, 파일명 변경 작업이 필요
		String rename = null;
		
		if(profileImage.getSize() == 0) { // 업로드된 파일이 없는 경우...?
			loginMember.setProfileImage(null);
		} else { // 업로드된 파일이 있는 경우!
			// 원본 파일명을 이용해서 새로운 파일명을 생성
			rename = Util.fileRename(profileImage.getOriginalFilename() );
			
			loginMember.setProfileImage(webPath + rename);
			// resources/images/memberProfile/변경된 파일명
			
		}
		
		int result = dao.updateProfile(loginMember); // 0 또는 1
		
		if(result > 0) { // DB 수정 성공 시 -> 실제로 서버에 파일을 저장
			
			if(rename != null) {
				// 변경된 이미지명이 있다 == 새로운 파일이 업로드되었다
				profileImage.transferTo(new File(filePath + rename));
				// 메모리에 임시 저장된 파일을 지정된 경로에 파일 형태로 변환
				// == 서버 파일 업로드
			}
			
		} else {
			// 실패 시 다시 이전 이미지를 세팅
			loginMember.setProfileImage(temp);
			throw new Exception(); // 롤백을 발생시키려고 예외를 강제 발생
			
		}
		
		return result; // 결과 반환
		
	}

}
