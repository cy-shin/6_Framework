package edu.kh.project.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// JoinPoint : 공통 코드가 삽입될 수 있는 후보군
// Pointcut : 실제로 코드가 삽입될 부분

// Pointcut을 모아둘 클래스
public class CommonPointcut {
	
	// execution([접근제한자] 반환형 패키지명.클래스명.메서드명( [파라미터] ) )
	// * 	: 모든
	// * edu.kh.project : 모든 반환형, edu.kh.project 패키지
	// . 	: 하위 경로
	// .. 	: 하위에 있는 모든 후손 + 0개 이상
	// * edu.kh.project..*ServiceImpl : 모든 반환형, 
	//									edu.kh.project 하위에 있는 모든 후손, 
	//									후손 중 서비스명이 ServiceImpl로 끝나는 서비스, 
	//									해당 서비스의 모든 메서드, 
	//									모든 메서드 중 매개변수가 0개 이상인 메서드를 선택
	@Pointcut("execution(* edu.kh.project..*ServiceImpl.*(..))")
	public void serviceImplPointcut() {}
}
