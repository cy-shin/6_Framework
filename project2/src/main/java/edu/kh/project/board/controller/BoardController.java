package edu.kh.project.board.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.board.model.service.BoardService;

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
}
