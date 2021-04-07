package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Task;

public interface TaskDao {

  int insert(Task task) throws Exception;

  List<Task> findAll() throws Exception;

  List<Task> findByProjectNo(int projectNo) throws Exception;

  Task findByNo(int no) throws Exception;

  int update(Task task) throws Exception;

  int delete(int no) throws Exception;

  int deleteByProjectNo(int projectNo) throws Exception;

  // 기존에 구현한 프로젝트에 영향을 끼칠 경우 사용
  // default int deleteByProjectNo(int projectNo) throws Exception {return 0;};
}
