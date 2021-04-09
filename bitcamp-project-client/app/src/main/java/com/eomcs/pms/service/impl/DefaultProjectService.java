package com.eomcs.pms.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public class DefaultProjectService implements ProjectService {

  // 서비스 객체는 트랜잭션을 제어해야 하기 때문에
  // DAO가 사용하는 SqlSession 객체를 주입 받아야 한다.
  SqlSession sqlSession;

  // 비즈니스 로직을 수행하는 동안 데이터 처리를 위해 사용할 DAO 를 주입 받아야 한다.
  ProjectDao projectDao;
  TaskDao taskDao;

  public DefaultProjectService(SqlSession sqlSession, ProjectDao projectDao, TaskDao taskDao) {
    this.sqlSession = sqlSession;
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  // 등록 업무
  @Override
  public int add(Project project) throws Exception {
    try {
      // 1) 프로젝트 정보를 입력한다.
      int count = projectDao.insert(project);

      // 일부러 예외를 발생시킨다.
      // => 그러면 프로젝트 멤버는 등록되지 않을 것이다.
      // => 자동 커밋 모드 -> 프로젝트 정보만 등록될 것이다.
      // => 수동 커밋 모드 -> 이전에 수행한 작업을 취소하기 때문에 프로젝트 정보는 등록되지 않는다.
      projectDao.insertMembers(project.getNo(), project.getMembers());
      sqlSession.commit();
      return count;
    } catch(Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }
  // 목록 조회 업무
  @Override
  public List<Project> list() throws Exception {
    return projectDao.findByKeyword(null, null);
  }

  // 상세 조회 업무
  @Override
  public Project get(int no) throws Exception {
    return projectDao.findByNo(no);
  }

  // 변경 업무
  @Override
  public int update(Project project) throws Exception {
    try {
      // 1) 프로젝트 정보를 변경한다.
      int count = projectDao.update(project);

      // 2) 프로젝트의 기존 멤버를 모두 삭제한다.
      projectDao.deleteMembers(project.getNo());

      // 3) 프로젝트 멤버를 추가한다.
      //    for (Member member : project.getMembers()) {
      //      insertMember(project.getNo(), member.getNo());
      //    }
      projectDao.insertMembers(project.getNo(), project.getMembers());
      sqlSession.commit();
      return count;
    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    try {
      // 1) 프로젝트의 모든 업무 삭제
      taskDao.deleteByProjectNo(no);

      // 2) 프로젝트 멤버 삭제
      projectDao.deleteMembers(no);

      // 2) 프로젝트 삭제
      int count = projectDao.delete(no);
      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  // 검색 업무
  @Override
  public List<Project> search(String title, String owner, String member) throws Exception {
    return projectDao.findByKeywords(title, owner, member);
  }

  @Override
  public List<Project> search(String item, String keyword) throws Exception {
    return projectDao.findByKeyword(item, keyword);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    int count = projectDao.deleteMembers(projectNo);
    sqlSession.commit();
    return count;
  }

  @Override
  public int updateMembers(int projectNo, List<Member> members) throws Exception {
    try {
      projectDao.delete(projectNo);
      int count = projectDao.insertMembers(projectNo, members);
      sqlSession.commit();
      return count;
    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }
}







