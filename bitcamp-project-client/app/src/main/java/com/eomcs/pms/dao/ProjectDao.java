package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public class ProjectDao {

  Connection con;

  public ProjectDao() throws Exception {
    this.con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
  }

  public int insert(Project p) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_project(title,content,sdt,edt,owner) values(?,?,?,?,?)",
        Statement.RETURN_GENERATED_KEYS)) {
      //        PreparedStatement stmt2 = con.prepareStatement(
      //            "insert into pms_member_project(member_no,project_no) values(?,?)")) {

      con.setAutoCommit(false); 

      stmt.setString(1, p.getTitle());
      stmt.setString(2, p.getContent());
      stmt.setDate(3, p.getStartDate());
      stmt.setDate(4, p.getEndDate());
      stmt.setInt(5, p.getOwner().getNo());
      int count = stmt.executeUpdate();

      try (ResultSet keyRs = stmt.getGeneratedKeys()) {
        keyRs.next();
        p.setNo(keyRs.getInt(1));
      }

      // 2) 프로젝트에 팀원들을 추가한다.
      for (Member member : p.getMembers()) {
        insertMember(project.getNo(), member.getNo());

        //stmt2.setInt(1, member.getNo());
        // stmt2.setInt(2, p.getNo());
        //return stmt2.executeUpdate();
      }
      // 프로젝트 정보 뿐만 아니라 팀원 정보도 정상적으로 입력되었다면,
      // 실제 테이블에 데이터를 적용한다.
      con.commit(); // 의미 : 트랜잭션 종료

      return count;

    } finally {
      con.setAutoCommit(true);
    }
  }

  public List<Project> findAll() throws Exception {
    ArrayList<Project> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select" 
            + "    p.no,"
            + "    p.title,"
            + "    p.sdt,"
            + "    p.edt,"
            + "    m.no as owner_no,"
            + "    m.name as owner_name"
            + "  from pms_project p"
            + "    inner join pms_member m on p.owner=m.no"
            + "  order by title asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        // 1) 프로젝트의 팀원 목록 가져오기
        Project project = new Project();
        //        stmt2.setInt(1, rs.getInt("no"));
        //        String members = "";
        //        try (ResultSet memberRs = stmt2.executeQuery()) {
        //          while (memberRs.next()) {
        //            if (members.length() > 0) {
        //              members += "/";
        //            }
        //            members += memberRs.getString("name");
        //          }
        //        }

        // 2) 프로젝트 정보를 출력
        //  System.out.printf("%d, %s, %s, %s, %s, [%s]\n", 
        project.setNo(rs.getInt("no"));
        project.setTitle(rs.getString("title"));
        project.setStartDate(rs.getDate("sdt"));
        project.setEndDate(rs.getDate("edt"));
        project.setOwner(owner);
        project.setMembers(findAllMembers(project.getNo()));

        list.add(project);

      }
    }
    return list;
  }
}

public Project findByNo(int no) throws Exception {
  try (PreparedStatement stmt = con.prepareStatement(
      "select"
          + "    p.no,"
          + "    p.title,"
          + "    p.content,"
          + "    p.sdt,"
          + "    p.edt,"
          + "    m.no as owner_no,"
          + "    m.name as owner_name"
          + "  from pms_project p"
          + "    inner join pms_member m on p.owner=m.no"
          + " where p.no=?");

}

