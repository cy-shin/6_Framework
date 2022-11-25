package edu.kh.project.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.vo.Board;
import edu.kh.project.board.model.vo.BoardImage;
import edu.kh.project.board.model.vo.Pagination;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	/** 게시판 이름 목록 조회
	 * @return boardTypeList
	 */
	public List<Map<String, Object>> selectBoardType() {
		return sqlSession.selectList("boardMapper.selectBoardType");
	}
	
	
	/** 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	public int getListCount(int boardCode) {
		return sqlSession.selectOne("boardMapper.getListCount", boardCode);
	}


	/** 특정 게시판 목록 조회
	 * @param pagination
	 * @param boardCode
	 * @return
	 */
	public List<Board> selectBoardList(Pagination pagination, int boardCode) {
		
		// RowBounds 객체 (마이바티스)
		// - 여러 행 조회 결과 중 특정 위치부터 지정된 행의 개수만 조회
		//                    (몇 행을 건너 뛸 것인가?)
		
		int offset = ( pagination.getCurrentPage() - 1 ) * pagination.getLimit();
	
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		return sqlSession.selectList("boardMapper.selectBoardList", boardCode, rowBounds);
																// 파라미터가 없는 경우 null을 직접 대입
																// rowBounds는 반드시 3번쨰 자리에 위치
	}


	/** 게시글 상세 조회 + 이미지 목록 조회 + 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	public Board selectBoardDetail(int boardNo) {
		return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNo);
	}


	/** 게시글 상세 조회 성공 시 조회 수의 증가
	 * @param boardNo
	 * @return
	 */
	public int updateReadCount(int boardNo) {
		return sqlSession.update("boardMapper.updateReadCount", boardNo);
	}


	/** 좋아요 여부 체크
	 * @param map
	 * @return
	 */
	public int boardLikeCheck(Map<String, Object> map) {
		return sqlSession.selectOne("boardMapper.boardLikeCheck", map);
	}


	/** 좋아요 수 증가(INSERT)
	 * @param paramMap
	 * @return
	 */
	public int boardLikeUp(Map<String, Object> paramMap) {
		return sqlSession.insert("boardMapper.boardLikeUp", paramMap);
	}


	/** 좋아요 수 감소(DELETE)
	 * @param paramMap
	 * @return
	 */
	public int boardLikeDown(Map<String, Object> paramMap) {
		return sqlSession.delete("boardMapper.boardLikeDown", paramMap);
	}


	/** 게시글 삭제(UPDATE)
	 * @param boardCode
	 * @param boardNo
	 * @return
	 */
	public int boardDelete(int boardNo) {
		return sqlSession.update("boardMapper.boardDelete", boardNo);
	}


	/** 게시글 삽입
	 * @param board
	 * @return boardNo
	 */
	public int boardWrite(Board board) {
		int result = sqlSession.insert("boardMapper.boardWrite", board);
		// board의 boardNo 필드
		// -> <selectKey>로 인해서 생성된 시퀀스값이 세팅되어 있음.
		
		// 메인 쿼리(INSERT) 성공 시
		if(result > 0) result = board.getBoardNo();
		
		return result; // 0 또는 삽입된 게시글 번호
		
	}


	/** 이미지 전송
	 * @param boardImageList
	 * @return result(INSERT된 행의 개수
	 */
	public int insertBoardImageList(List<BoardImage> boardImageList) {
		return sqlSession.insert("boardMapper.insertBoardImageList", boardImageList);
	}


	/** 게시글 수정
	 * @param board
	 * @return result
	 */
	public int boardUpdate(Board board) {
		return sqlSession.update("boardMapper.boardUpdate", board);
	}


	/** 게시글 이미지 삭제
	 * @param condition
	 * @return result
	 */
	public int boardImageDelete(String condition) {
		return sqlSession.delete("boardMapper.boardImageDelete", condition);
	}


	/** 이미지 수정
	 * @param img
	 * @return result
	 */
	public int boardImageUpdate(BoardImage img) {
		return sqlSession.update("boardMapper.boardImageUpdate", img);
	}


	/** 이미지 삽입
	 * @param img
	 * @return result
	 */
	public int boardImageInsert(BoardImage img) {
		return sqlSession.insert("boardMapper.boardImageInsert", img);
	}


	/** 검색 조건이 일치하는 게시글 수 조회
	 * @param pm
	 * @return listCount
	 */
	public int getListCount(Map<String, Object> pm) {
		return sqlSession.selectOne("boardMapper.getListCount_search", pm);
	}


	/** 검색 조건이 일치하는 게시글 목록 조회
	 * @param pagination
	 * @param pm
	 * @return boardList
	 */
	public List<Board> selectBoardList(Pagination pagination, Map<String, Object> pm) {
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
				// offset : 건너뛸 행 개수
				// limit : 
		
		return sqlSession.selectList("boardMapper.selectBoardList_search", pm, rowBounds);
	}

}
