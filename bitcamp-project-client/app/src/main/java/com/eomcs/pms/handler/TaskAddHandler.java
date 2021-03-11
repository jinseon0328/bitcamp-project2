package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[작업 등록]");

    Task t = new Task();

    t.setNo(Prompt.inputInt("번호? "));
    t.setContent(Prompt.inputString("내용? "));
    t.setDeadline(Prompt.inputDate("마감일? "));
    t.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));


    stmt.executeUpdate("task/insert", 
        String.format("%d,%s,%s,%s", 
            t.getNo(), t.getContent(), t.getDeadline(),t.getStatus()));

    System.out.println("작업을 등록하였습니다.");


  }
}
