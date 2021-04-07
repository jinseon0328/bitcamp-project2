package com.eomcs.pms.dao.mariadb;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

//한 번에 4번째 단계까지 가지말고 일단 3번째와 4번째 단계 사이에 있는 정도로 구현을 해보자.
//- 각 DAO 클래스는 Connection 객체를 공유하기 위해 인스턴스 필드로 선언한다.
//- 각 DAO 클래스는 DAO 인스턴스가 생성될 때 Connection 객체를 만든다.
public class ProjectDaoImpl implements ProjectDao{

  SqlSession sqlSession;

  public ProjectDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(Project project) throws Exception {
    int count = sqlSession.insert("ProjectMapper.insert", project);

    for (Member member : project.getMembers()) {
      insertMember(project.getNo(), member.getNo());
    }
    return count;
  }

  @Override
  public List<Project> findAll() throws Exception {
    return sqlSession.selectList("ProjectMapper.findAll");
    //    List<Project> projects = sqlSession.selectList("ProjectMapper.findAll");
    //    for (Project p : projects) {
    //      p.setMembers(findAllMembers(p.getNo()));
    //    }
    //    return projects;
  }

  @Override
  public Project findByNo(int no) throws Exception {
    return sqlSession.selectOne("ProjectMapper.findAll", no);
    //    Project project = sqlSession.selectOne("ProjectMapper.findByNo", no);
    //    project.setMembers(findAllMembers(no));
    //    return project;
  }

  @Override
  public int update(Project project) throws Exception {
    int count = sqlSession.update("Project.update", project);

    deleteMembers(project.getNo());

    for (Member member : project.getMembers()) {
      insertMember(project.getNo(), member.getNo());
    }
    return count;
  }

  @Override
  public int delete(int no) throws Exception {

    deleteMembers(no);

    return sqlSession.delete("ProjectMapper.delete", no);
  }

  @Override
  public int insertMember(int projectNo, int memberNo) throws Exception {
    HashMap<String, Object> params = new HashMap<>();
    params.put("projectNo", projectNo);
    params.put("memberNo", memberNo);
    return sqlSession.insert("ProjectMapper.insertMember", params);
  }

  public List<Member> findAllMembers(int projectNo) throws Exception {
    return sqlSession.selectList("ProjectMapper.findAllMembers", projectNo);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    return sqlSession.delete("ProjectMapper.deletMembers", projectNo);
  }
}


