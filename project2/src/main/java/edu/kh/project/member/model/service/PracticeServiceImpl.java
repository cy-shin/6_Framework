package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.project.member.model.dao.PracticeDAO;
import edu.kh.project.member.model.vo.Member;

@Service
public class PracticeServiceImpl implements PracticeService{

	@Autowired
	private PracticeDAO dao;
	
	@Override
	public Member memberLogin(Member inputMember) {
		return dao.memberLogin(inputMember);
	}
	
}
