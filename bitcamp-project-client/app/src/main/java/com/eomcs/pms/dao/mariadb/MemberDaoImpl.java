package com.eomcs.pms.dao.mariadb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;

public class MemberDaoImpl implements MemberDao {

  SqlSession sqlSession;

  // Connection 객체를 자체적으로 생성하지 않고 외부에서 주입받는다.
  // - Connection 객체를 여러 DAO가 공유할 수 있다.
  // - 교체하기도 쉽다.
  public MemberDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(Member member) throws Exception {
    return sqlSession.insert("MemberMapper.insert", member);
  }

  @Override
  public List<Member> findAll() throws Exception {
    return sqlSession.selectList("BoardMapper.findAll");
  }

  @Override
  public Member findByNo(int no) throws Exception {
    return sqlSession.selectOne("MemberMaper.findByNo", no);
  }

  @Override
  public int update(Member member) throws Exception {
    return sqlSession.update("MemberMapper.update", member);
  }

  @Override
  public int delete(int no) throws Exception {
    return sqlSession.delete("MemberMapper.delete", no);
  }

  @Override
  public Member findByName(String name) throws Exception {
    return sqlSession.selectList("MemberMapper.findByName", name);
  }
