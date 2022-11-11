package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.project.member.model.dao.AjaxDAO;
import edu.kh.project.member.model.vo.Member;

@Service // 비즈니스 로직 처리 역할 + bean 등록
public class AjaxServiceImpl implements AjaxService {
	
	@Autowired // (의존성 주입)
	private AjaxDAO dao;


	
	// 이메일 중복 검사
	@Override
	public int emailDupCheck(String memberEmail) {
		
		return dao.emailDupCheck(memberEmail);
	}


	// 닉네임 중복 검사
	@Override
	public int nicknameDupCheck(String memberNickName) {
		return dao.nicknameDupCheck(memberNickName);
	}


	@Override
	public Member selectEmail(String email) {
		return dao.selectEmail(email);
	}
	
}
