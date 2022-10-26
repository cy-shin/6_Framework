package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dao.MemberDAO;
import edu.kh.project.member.model.vo.Member;

// @Service			: bean 등록 시 이름을 클래스명으로 등록(=memberServiceImpl)
// @Service("이름") : bean 등록 시 이름을 지정된 이름으로 등록


// @Transactional 클래스 위에 작성할 경우, dql인데도 트랜잭션 처리가 될 수 있어 비효율적임
@Service // Service 레이어, 비즈니스 로직을 가진 클래스임을 명시 + bean 등록
public class MemberServiceImpl implements MemberService {
	
	// MemberDAO bean을 의존성 주입
	@Autowired
	private MemberDAO dao;
	
	// spring - security.xml에서 등록한 bean을 의존성 주입(DI)
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
//		System.out.println("입력한 비밀번호 : " + inputMember.getMemberPw());
//		System.out.println("암호화 비밀번호 : " + bcrypt.encode(inputMember.getMemberPw()));
		
		// bcrypt 이용 시 로그인 방법
		// 1. 이메일이 일치하는 회원 정보를 DB에서 조회해옴
		//    반드시 비밀번호(MEMBER_PW)가 포함되어 있어야 함
		// 기존에는 트랜잭션 제어를 하기 위해 connection을 사용했는데 이제 aop를 사용해 트랜잭션을 제어하므로 필요 없음
		Member loginMember = dao.login(inputMember.getMemberEmail());
		
		// 2. 입력 받은 비밀번호(평문) vs 조회한 암호화된 비밀번호(암호문) 비교
		//    BCryptPasswordEncode.matches(평문, 암호문) -> 일치하면 true, 다르면 false
		if(loginMember != null) { // 아이디 정상 입력
			
			// 3-1. 비밀번호가 일치하면 조회된 회원 정보를 반환
			//      단, 비밀번호는 제거(반환X)
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) { 
				loginMember.setMemberPw(null);
			} else{
				loginMember = null;
			}
			
		}
		
		// 3-2. 비밀번호가 일치하지 않으면 null을 반환
		
		return loginMember;
	}
	
	/* ** 스프링에서 트랜잭션을 처리하는 방법
	 * - 선언적 트랜잭션 처리
	 * 
	 * 1) <tx:advice> 를 이용한 XML 작성 방법
	 * 
	 * 2) @Transactional 어노테이션 작성 방법
	 *    - 클래스, 메서드 위에 작성 가능
	 *    - 조건 : AOP를 이용한 기술 -> Service Interface 필요
	 *    		   트랜잭션 매니저가 bean으로 등록되어 있어야 함
	 *
	 * @Transactional 어노테이션 특징
	 * - 예외가 발생한 경우 rollback 자동 진행
	 * - 예외의 기본값은 RuntimeException
	 * 			-> SQL 진행 시 발생하는 예외 == SQLException
	 * 											SQLException은 RuntimeException와 형제관계여서 다형성 적용 불가
	 * 			-> 다른 예외에도 rollback이 진행되고 싶다면 rollbackFor = 예외 클래스를 작성하면 된다.
	 */
	
	@Transactional(rollbackFor = Exception.class) // 모든 예외 상황 발생 시 롤백
	@Override
	public int signUp(Member inputMember) {
		
		// 비밀번호 암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		// DAO 호출 후 결과 반환 받기
		int result = dao.signUp(inputMember);
		
		return result;
	}

	
}
