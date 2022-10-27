package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.vo.Member;

@Repository // 스프링이 bean으로 등록하고 관리(IOC)
public class MyPageDAO {
	
	@Autowired // 스프링으로부터 bean을 주입받음(DI)
	private SqlSessionTemplate sqlSession;

	/** 회원 정보 수정 DAO
	 * @param inputMember
	 * @return
	 */
	public int updateInfo(Member inputMember) {
		return sqlSession.update("myPageMapper.updateInfo", inputMember);
	}
}
