package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Task;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public interface TaskService {

  // 등록 업무
  int add(Task task) throws Exception;

  // 목록 조회 업무
  List<Task> list() throws Exception;

  List<Task> listOfProject(int projectNo) throws Exception;

  //상세 조회 업무
  Task get(int no) throws Exception;

  // 변경 업무
  int update(Task task) throws Exception;

  // 삭제 업무
  int delete(int no) throws Exception;

}







