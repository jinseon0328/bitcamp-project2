package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class BoardDeleteHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {

    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    stmt.executeQuery("board/select", Integer.toString(no));

    //    // 서버에서 보낸 게시글을 사용할 일이 없기 때문에 리턴 값을 무시한다.
    //    // - 중요한 것은 서버가 출력했으면 클라이언트는 필요가 없더라도 읽어야 한다.
    //    // - 그것이 통신 프로토콜이다.
    //    in.readUTF();
    //    

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 삭제를 취소하였습니다.");
      return;
    }
    stmt.executeUpdate("board/delete",Integer.toString(no));


    System.out.println("게시글을 삭제하였습니다.");

  }
}




