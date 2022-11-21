package edu.kh.project.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.board.model.vo.Board;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// @PathVariable 사용
	// URL 경로에 있는 값을 파라미터(변수)로 사용할 수 있게 하는 어노테이션
	// + 자동으로 request scope로 등록되어, EL 구문으로 jsp에서 출력 가능
	
	@GetMapping("/board/{boardCode}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
								Model model,
								@RequestParam(value="cp", required=false, defaultValue="1") int cp
								) {
		// PathVaribale을 이용, 주소에서 boardCode를 꺼내서 int boardCode에 저장
		
		// Model : 값 전달용 객체
		// model.addAttribute("k", v) : request scope에 세팅 -> forward 시 유지
		// forward 시에는 굳이 session에 올릴 필요 없음
		
		// 쿼리 스트링(QueryString) ?K=V&K=V&K=V...
		// -> 요청주소에 값을 담아서 전달할 때 사용하는 문자열
		
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		model.addAttribute("map", map);
		
		return "board/boardList";
	}
	
	// 게시글 상세조회
	@GetMapping("/board/{boardCode}/{boardNo}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			@PathVariable("boardCode") int boardCode,
			Model model,
			HttpServletRequest req, // 쿠기 보낼 때
			HttpServletResponse resp // 쿠키 만들 때
			) throws ParseException {
		
		
		// 게시글 상세 조회 서비스 호출
		Board board = service.selectBoardDetail(boardNo);
		// 좋아요 수, 좋아요 여부
		// 쿠키를 이용해서 해당 IP에서 하루 1회 조회수 증가
		
		// 게시글 상세 조회 성공 시 조회 수의 증가
		if(board != null) {
			
			// 컴퓨터 1대당 게시글마다 1일 1번씩만 조회수 증가
			// -> 쿠키 이용
			
			// Cookie
			// - 사용되는 경로, 수명
			// -> 경로 지정 시, 해당 경로 또는 경로 이하의 요청을 보낼 때 
			//    쿠키 파일을 서버로 같이 보냄
			
			// 쿠키에 "readBoardNo"를 얻어와, 현재 조회하려는 게시글 번호가 없으면 조회수 1 증가 후 쿠키에 게시글 번호 추가
			// 만약에 있으면 조회수 증가 X
			
			// 쿠키 얻어오기
			Cookie[] cookies = req.getCookies(); // 쿠키를 여러개 사용하는 경우, 쿠키를 배열 형태로 얻어오기
			
			// 쿠키 중 "readBoardNo"가 있는지 확인
			Cookie c = null;
			
			if(cookies != null) { // 쿠키가 있는 경우
				for(Cookie temp : cookies) {
					
					// 얻어온 쿠키의 이름이 "readBoardNo"인 경우
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
					}
				}
			}
			
			int result = 0; // 조회 수 증가 service 호출 결과 저장
			
			if(c == null) { // Cookies 배열에 "readBoardNo" 쿠키가 없다 즉, 상세조회를 하지 않았다
				result = service.updateReadCount(boardNo);
				
				// boardNo 게시글을 상세조회 했음을 쿠키에 기록
				
				c = new Cookie("readBoardNo", "|" + boardNo + "|");
				// 톰캣 8.5 이상 부터 쿠키의 값으로 세미콜론; 콤마, 등호= 공백( )을 사용할 수 없게 되었음
			} else { // "readBoardNo" 쿠키가 있는 경우
				// c.getValue() : "readBoardNo" 쿠키에 저장된 값(|1995|)
				
				// 쿠키에 저장된 값 중에서, "|게시글번호|"가 존재하는지 확인
				if(c.getValue().indexOf("|" + boardNo + "|") == -1 ) {
					// 존재하지 않는 경우
					// == 오늘 처음 조회하는 게시글 번호
					result = service.updateReadCount(boardNo);
					
					// 현재 조회한 게시글 번호를 쿠키에 값으로 추가
					c.setValue( c.getValue() + "|" + boardNo + "|" );
					// |1995||2000||2132|....누적
					
				}   
			}
			
			
			
			if(result > 0) { // 조회 수 증가 성공 시
							 // DB와 조회된 Board 조회 수 동기화
				board.setReadCount(board.getReadCount() + 1);
				
				// 쿠키 적용 경로, 수명 설정 후 클라이언트에게 전달
				c.setPath("/"); // localhost("/")이하로 적용

				// Date : 날짜용 객체
				
				// Calender : Date 업그레이드 객체
				
				// SimpleDateFormat : 날짜를 원하는 형태의 문자열로 변환
				
				Date a = new Date(); // 현재 시간
				
				// new Date(0)  자바 기준 시간(1970.01.01 09:00:00)
				// new Date(ms) 기준 시간 + ms만큼 지난 시간
				
				Calendar cal = Calendar.getInstance();
				// cal.add(단위, 추가할 값);
				
				cal.add(cal.DATE, 1); // 날짜에 1 추가해서 내일 값을 얻어냄
				
				// simpleDateFormat을 이용해서 cal 날짜 중 시, 분, 초를 0:0:0으로 바꿈
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date temp = new Date( cal.getTimeInMillis() );
				
				// TimeInMillis() : ms값을 얻어냄
				Date b = sdf.parse(sdf.format(temp));
				// parse : 여기서는 날자 형식 String을 Date로 변환
				
				// 내일 자정 시간 - 현재 시간 = 
				long diff = b.getTime() - a.getTime();
				
				// System.out.println(diff / 1000 - 1); // 자정 - 1초 - 현재시각
				// (diff - 1000) / 1000
				c.setMaxAge((int)(diff/1000)); // 10분(임시)
				
				
				
				resp.addCookie(c); // 쿠키를 클라이언트에게 전달
				                   // 기존에 동일한 이름의 쿠키가 있던 경우, 덮어쓰기됨
			}
		}
		
		model.addAttribute("board", board);
		
		return "board/boardDetail";
	}
}
