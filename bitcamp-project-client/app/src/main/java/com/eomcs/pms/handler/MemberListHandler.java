package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;

public class MemberListHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[회원 목록]");

    // 서버에 게시글 목록을 달라고 요청한다.
    Iterator<String> results = stmt.executeQuery("member/selectall");
    while (results.hasNext()) {
      String[] fields = results.next().split(",");

      System.out.printf("%s, %s, %s, %s, %s\n", 
          fields[0], 
          fields[1], 
          fields[2],
          fields[3],
          fields[4]);
    }
  }
}






