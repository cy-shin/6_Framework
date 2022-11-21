package edu.kh.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dao.BoardDAO;
import edu.kh.project.board.model.vo.Board;
import edu.kh.project.board.model.vo.Pagination;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO dao;
	
	// 게시판 이름 목록 조회
	@Override
	public List<Map<String, Object>> selectBoardType() {
		return dao.selectBoardType();
	}
	
	
	// 게시글 수 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		// 1. 특정 게시판의 전체 게시글 수를 조회(단, 삭제된 경우는 제외함)
		int listCount = dao.getListCount(boardCode);
		
		
		// 2. 전체 게시글 수 + cp(현재 페이지)를 이용해서 페이징 처리 객체 생성
		Pagination pagination = new Pagination(listCount, cp);
		
		
		// 3. 페이징 처리 객체를 이용해서 게시글 목록 조회
		List<Board> boardList = dao.selectBoardList(pagination, boardCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}

	// 게시글 상세 조회 + 이미지 목록 조회 + 댓글 목록 조회
	@Override
	public Board selectBoardDetail(int boardNo) {
		return dao.selectBoardDetail(boardNo);
	}

	// 게시글 상세 조회 성공 시 조회 수의 증가
	// @Transactional : 단일 DML에는 꼭 적을 필요 없다! 
	// 왜? 단일 DML구문의 경우, 실행 중 오류가 발생하면 자동으로 취소됨
	
	// 반면 DML구문이 여러 개로 이루어진 경우에는 반드시 작성 
	// 왜? 예를 들어, DML구문이 1번부터 5번까지 있는데 4번에서 문제가 있는 경우
	// 1번부터 3번 DML 구문은 정상적으로 처리되고, 4번 DML구문부터 처리되지 않아서 의도한 결과가 나오지 않음
	// 그래서 중간에 오류가 발생했을 경우 롤백이 될 수 있게 @Transactional이 필요함
	@Override
	public int updateReadCount(int boardNo) {
		return dao.updateReadCount(boardNo);
	}

	
	// 좋아요 여부 체크
	@Override
	public int boardLikeCheck(Map<String, Object> map) {
		return dao.boardLikeCheck(map);
	}

	// 좋아요 수 증가(INSERT)
	@Override
	public int boardLikeUp(Map<String, Object> paramMap) {
		return dao.boardLikeUp(paramMap);
	}

	// 좋아요 수 감소(DELETE)
	@Override
	public int boardLikeDown(Map<String, Object> paramMap) {
		return dao.boardLikeDown(paramMap);
	}

}
