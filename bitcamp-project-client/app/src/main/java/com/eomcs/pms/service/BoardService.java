package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Board;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public interface BoardService {

  // 게시글 등록 업무
  int add(Board board) throws Exception;

  // 게시글 목록 조회 업무
  List<Board> list() throws Exception;

  // 게시글 상세 조회 업무
  Board get(int no) throws Exception;

  // 게시글 변경 업무
  int update(Board board) throws Exception;

  // 게시글 삭제 업무
  int delete(int no) throws Exception;

  // 게시글 검색 업무
  List<Board> search(String keyword) throws Exception;
}







