package com.eomcs.pms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;

public class BoardDaoImpl implements BoardDao {

  // auto commit 객체 받기
  SqlSession sqlSession;

  public BoardDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;

  }

  // 이제 메서드들은 인스턴스 필드에 들어있는 Connection 객체를 사용해야 하기 때문에
  // 스태틱 메서드가 아닌 인스턴스 메서드로 선언해야 한다.
  @Override
  public int insert(Board board) throws Exception {
    return sqlSession.insert("BoardMappter.insert", board);

  } 

  @Override
  public List<Board> findAll() throws Exception {
    return sqlSession.selectList("BoardMapper.findAll");

  }
  @Override
  public Board findByNo(int no) throws Exception {
    return sqlSession.selectOne("BoardMapper.findByNo", no);
  }

  @Override
  public int update(Board board) throws Exception {
    return sqlSession.update("BoardMapper.update", board);

  }

  @Override
  public int updateViewCount(int no) throws Exception {
    return sqlSession.selectOne("BoardMapper.updateViewCount", no);
  }

  @Override
  public int delete(int no) throws Exception {
    return sqlSession.delete("BoardMapper.delete", no);

  }

  @Override
  public List<Board> findByKeyword(String keyword) throws Exception {
    return sqlSession.selectList("BoardMapper.findByKeyword", keyword);
  }
}

