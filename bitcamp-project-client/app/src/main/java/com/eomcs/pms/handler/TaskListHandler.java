package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;

public class TaskListHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {

    // 서버에 게시글 목록을 달라고 요청한다.
    Iterator<String> results = stmt.executeQuery("task/selectall");
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
