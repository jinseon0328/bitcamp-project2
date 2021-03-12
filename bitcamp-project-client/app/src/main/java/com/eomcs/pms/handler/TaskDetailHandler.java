package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class TaskDetailHandler implements Command {
  Statement stmt;
  public  TaskDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[작업 상세보기]");

    int no = Prompt.inputInt("번호? ");

    // 서버에 지정한 번호의 게시글을 요청한다.

    Iterator<String> results = stmt.executeQuery("task/select", 
        Integer.toString(no));

    String[] fields = results.next().split(",");

    System.out.printf("내용: %s\n", fields[1]);
    System.out.printf("마감일: %s\n", fields[2]);
    System.out.printf("상태: %s\n", fields[3]);
    System.out.printf("담당자: %s\n", fields[4]);
  }
}

