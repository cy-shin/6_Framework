package edu.kh.project.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.service.MyPageService;
import edu.kh.project.member.model.vo.Member;

// 클래스 레벨에 작성된 @RequestMapping
// -> 요청주소 중 앞에 공통된 부분을 작성하여
//    해당 유형의 요청을 모두 받아들인다고 알림
@RequestMapping("/member/myPage")
@SessionAttributes("loginMember") // 탈퇴 성공 시 로그아웃에 사용
@Controller
public class MyPageController {
	
	@Autowired
	private MyPageService service;
	
	// 내 정보 페이지 이동
	@GetMapping("/info") // 나머지 주소 작성
	public String info() {
		return "member/myPage-info";
	}
	
	// 내 정보 수정
	@PostMapping("/info")
	public String updateInfo(Member inputMember, 
							 String[] memberAddress,
							 @SessionAttribute("loginMember") Member loginMember,
							 RedirectAttributes ra
							) {
		// inputMember : 입력 받은 memberNickName / memberTel / memberAddress(가공 필요)
		// memberAddress(배열) : 입력된 우편번호, 주소, 상대주소가 담긴 배열
		
		// @SessionAttribute("loginMember") Member loginMember
		// -> session의 속성 중 "loginMember"를 키로 가지는 값을 매개변수에 대입
		
	    // 기존 방법
	    //HttpSession session = req.getSession();
	    //Member loginMember = (Member)session.getAttribute("loginMember")
		
		// 1. 로그인된 회원 정보에서 회원 번호를 얻어와 inputMember에 저장
		inputMember.setMemberNo(loginMember.getMemberNo());
		
		// 2. inputMember.memberAddres의 값에 따라 변경
		if(inputMember.getMemberAddress().equals(",,") ) { // 주소 미작성
			inputMember.setMemberAddress(null);
			
		} else {
			String address = String.join(",,", memberAddress);
			
			inputMember.setMemberAddress(address);
		}

		// 회원 정보 수정 서비스 호출 후 결과 반환 받기
		int result = service.updateInfo(inputMember);
		
		String message = null;
		
		if(result>0) {
			message = "회원 정보가 수정되었습니다.";
			
			// DB - session 동기화 작업
			loginMember.setMemberNickName(inputMember.getMemberNickName() );
			loginMember.setMemberTel(inputMember.getMemberTel() );
			loginMember.setMemberAddress(inputMember.getMemberAddress() );
			
			
		} else {
			message = "회원 정보 수정 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:info"; // 내 정보 페이지 재요청
	}


	// 비밀번호 변경 페이지 이동
	@GetMapping("/changePw")
	public String changePw() {
		return "member/myPage-changePw";
	}
	
	// 비밀번호 변경
	
	@PostMapping("/changePw")
	public String changePw( @SessionAttribute("loginMember") Member loginMember,
							@RequestParam Map<String, Object> paramMap,
							RedirectAttributes ra
						  ) {
		
		// 1. loginMember에서 회원 번호를 얻어와 paramMap에 추가
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		// 2. 서비스 호출 후 결과 반환 받기
		int result = service.changePw(paramMap);

		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "info";
			message = "비밀번호가 변경되었습니다.";
		} else {
			path="changePw";
			message = "현재 비밀번호가 일치하지 않습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	// 회원 탈퇴 페이지 이동
	@GetMapping("/delete")
	public String memberDelete() {
		return "member/myPage-delete";
	}
	
	// 회원 탈퇴
	@PostMapping("/delete")
	public String memberDelete( @SessionAttribute("loginMember") Member loginMember, 
								String memberPw, 
								SessionStatus status,
								RedirectAttributes ra) {
		int result = service.memberDelete(loginMember.getMemberNo(), memberPw);
							 // MEMBER_DEL_FL = 'Y'으로 UPDATE
		String message = null;
		String path = null;
		
		if(result > 0) { // 성공
			message = "탈퇴 되었습니다.";
			path ="/";
			status.setComplete();
			
		} else { // 실패
			message = "비밀번호가 일치하지 않습니다.";
			path = "delete"; 
		}
		
		// message 전달 코드 작성
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
		
		
		// status.setComplete(); // 세션 무효화
		// -> 클래스 레벨에 작성된 @SessionAttributes("key") 에 작성된
		//    key가 일치하는 값만 무효화함
		
		// ex) session에서 "loginMember"를 없애야 한다면 이를 클래스 레벨의 @SessionAttributes에 작성
		// == @SessionAttributes("loginMember")
		//    ...
		//    status.complete(); // "loginMember" 무효화
		
	}
	
	// 마이페이지 이동
	@GetMapping("/profile")
	public String profile() {
		return "member/myPage-profile";
	}
	
	// 프로필 이미지 수정
	@PostMapping("/updateProfile")
	public String updateProfile(
			@RequestParam(value="profileImage") MultipartFile profileImage,  /* 업로드된 파일 */
			@SessionAttribute("loginMember") Member loginMember, /* 로그인 회원 정보 */
			RedirectAttributes ra, /* 메세지 전달용 */
			HttpServletRequest req /* 저장할 서버 경로 */
			) throws Exception {
	// RequestParam(value = 제출된 값 name)
	// MultipartFile
	// - MultipartResolver에 의해서 반환된 input type="file"의 값을 저장한 객체
	
	// * 관련 메서드
	// 1) getOriginalFilename() : 파일 원본명
	// 2) getSize() : 파일 크기
	// 3) transferTo() : 메모리에 임시 저장된 파일을 지정된 경로에 저장
		
		// ** 업로드된 이미지를 프로젝트 폴더 내부에 저장하는 방법 **
		// 1) server -> 지정된 서버 설정 -> Serve modules without publishing 체크
		// 2) 파일을 저장할 폴더 생성
		// 3) HttpServletRequest를 이용해서 저장 폴더 절대 경로 얻어오기
		// 4) MultipartFile.transferTo()를 이용해서 지정된 경로에 파일 저장
		
		// 인터넷 주소로 접근할 수 있는 경로
		String webPath = "/resources/images/memberProfile/";
		
		String filePath = req.getSession().getServletContext().getRealPath(webPath);
						// getSession().getServletContext() : 어플리케이션 스코프 객체를 얻어옴
						// 어플리케이션 스코프는 하나, 즉 프로그램 자체를 가리키는 것과 마찬가지
						// 프로그램 내 경로를 찾는 코드
		
		int result = service.updateProfile(webPath, filePath, profileImage, loginMember);
		
		String message = null;
		if(result > 0) message = "프로필 이미지가 변경되었습니다.";
		else		   message = "프로필 이미지 변경 실패";
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:profile";
		// return : view Resolver로 결과를 보내서 view를 요청함
		// redirect : HandlerMapping으로 컨트롤러를 재요청
//		return "redirect:/member/myPage/profile";
	}
}
