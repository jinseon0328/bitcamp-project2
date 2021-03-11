package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[작업 등록]");

    Task t = new Task();

    t.setNo(Prompt.inputInt("번호? "));
    t.setContent(Prompt.inputString("내용? "));
    t.setDeadline(Prompt.inputDate("마감일? "));
    t.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("task/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%d,%s,%s,%s", 
        t.getNo(), t.getContent(), t.getDeadline(),t.getStatus()));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }
    System.out.println("작업을 등록하였습니다.");


  }
}
