package edu.kh.project.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.service.MemberServiceImpl;
import edu.kh.project.member.model.vo.Member;

// 회원 관련 요청을 받는 컨트롤러
// Controller : 프레젠테이션 레이어
//				웹 애플리케이션으로 전달 받은 클라이언트의 요청을 
//				알맞은 서비스로 연결하고 
//				서비스에서 반환된 결과에 따라 
//				알맞은 화면으로 응답하는 방법을 '제어'하는 역할


//@Controller 어노테이션 : 컴파일러에게 현재클래스가 Controller임을 알려줌
//+ bean 등록 (Spring이 객체로 만들어서 관리함)
@Controller
public class MemberController {
	
	// * 공용으로 사용할 service 객체 생성
	
	// @Autowired
	// bean 스캐닝을 통해 bean으로 등록된 객체 중 알맞은 객체를 DI(의존성 주입) 해주는 어노테이션
	
	// 자동 연결 규칙 : 타입이 같거나 상속 관계인 bean을 자동으로 DI
	
	// @Qualifier
	// @Autowired와 함께 쓰이며, 한 프로젝트 내에 @Autowired로 의존성을
	// 주입하고자 하는 객체가 여러개 있을 경우, @Qualifier(“name”)를 통해 원하는
	// 객체를 지정하여 주입할 수 있다
	
	@Autowired
	private MemberService service;
	
//	private MemberService service = new MemberServiceImpl();

	// @RequestMapping : 클라이언트의 요청을 처리할 클래스 / 메서드를 지정하는 어노테이션
	// == Handler Mapping
	// 로그인 요청(POST)
	// @RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req)  {
		// *** 파라미터를 전달받는 방법 ***
		// 1.HttpServletRequest를 이용하는 방법
		String inputEmail = req.getParameter("inputEmail");
		String inputPw = req.getParameter("inputPw");
		System.out.println(inputEmail);
		System.out.println(inputPw);
		
		// * forward 방법 : prefix/suffix를 제외한 나머지 jsp 경로
		// * redirect 방법 : "redirect:요청주소";
		
		return "redirect:/";
		// 최상위 경로 "/"로 redirect
	}
	
	   // 2. @RequestParam 어노테이션 사용
	   // - 메서드 매개변수에 전달받은 파라미터를 주입하는 어노테이션
	   // - .으로 구별
	
	   // [속성]
	   // value : 전달 받은 input 태그의 name 속성값 (값이 하나만 있을 땐 value값으로 자동 지정됨)
	   
	   // required : 입력된 name 속성값 파라미터 필수 여부 지정(기본값 true)
	   // -> required = true인 파라미터가 존재하지 않는다면 400 Bad Request 에러 발생
	   // -> required = true인 파라미터가 null인 경우에도 400 Bad Request
	   // -> required = false인 경우 전달된 파라미터가 없으면 null......

	   // defaultValue : 파라미터 중 일치하는 name 속성 값이 없을 경우에 대입할 값 지정.
	   // -> required = false인 경우 사용
	
	   // * @RequestParam 생략하기 *
	   // 조건 : 매개변수 이름 == input name 속성 값
	
	// @RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(@RequestParam("inputEmail") String email,
			@RequestParam(value="inputPw2", required=false, defaultValue = "1234") String pw,
			String inputPw) {
		
		System.out.println(email);
		System.out.println(pw);
		System.out.println(inputPw);
		
		return "redirect:/";
	}
	
	// @RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(String inputEmail, String inputPw) {
		System.out.println(inputEmail);
		System.out.println(inputPw);
		
		return "redirect:/";
	}
	
	// ***** @RepuestMapping... 방식 포함해서 간단히 적기
	// @RequestMapping(value="/member/login" , method = RequestMethod.POST
	// @PostMapping("/member/login") // POST 방식의 /member/login 요청을 연결
	// @GETMapping("/member/login")  // GET  방식의 /member/login 요청을 연결

	/*    
	 * 3. @ModelAttribute 어노테이션 사용
	 * 
	 * [작성법]
	 * @ModeAttribute VO타입 매개변수명
	 * -> 파라미터의 name 속성 값이
	 * 	  지정된 VO의 필드명과 같아면
	 *    해당 VO 객체의 필드에 파라미터를 세팅
	 *    
	 * [조건]
	 * 1. name 속성 값과 필드 명이 같아야 함.
	 * 2. VO에 반드시 기본 생성자가 존재해야 함.
	 * 3. VO에 반드시 setter가 존재해야 함.
	 * 
	 **/
	
	// *@ModelAttribute 어노테이션 생략도 가능
	// == 커맨드 객체
	
	@PostMapping("/member/login")
	public String login(/*@ModelAttribute*/ Member inputMember) {
		// System.out.println(inputMember);
		
		// Servlet 프로젝트
		// Service 객체 생성
		// try ~ catch 내부에 코드를 작성
		
		// Spring 프로젝트
		// 1. try ~ catch
		// 2. 메서드 별로 주소를 할당할 수 있다 -> 서비스를 멤버변수로 선언해서 사용하기
		
		// 서비스 호출 후 결과 반환 받기
		Member loginMember = service.login(inputMember);
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
}