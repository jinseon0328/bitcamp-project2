package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class BoardSearchHandler implements Command {

  Statement stmt;

  public BoardSearchHandler(Statement stmt) {
    this.stmt = stmt;
  }
  @Override
  public void service() throws Exception {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }
    //결과를 리턴할 때만 이터레이터
    Iterator<String> results = stmt.executeQuery("board/selectByKeyword", keyword);
    // 서버에 지정한 번호의 게시글의 요청한다.


    // 서버의 응답을 받는다.
    if (!results.hasNext()) {
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

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







