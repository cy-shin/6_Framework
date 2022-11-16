package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.vo.Member;

@Repository
public class PracticeDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public Member memberLogin(Member inputMember) {
		return sqlSession.selectOne("practiceMapper.memberLogin", inputMember); 
	}

}
