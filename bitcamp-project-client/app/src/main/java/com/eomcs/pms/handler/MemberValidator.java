package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;
public class MemberValidator {

  public static void inputMember(Statement stmt) throws Exception{

    while (true) {
      String name = Prompt.inputString(name);
      if (name.length() == 0) {
        return;
      } 

      // 서버에 지정한 번호의 데이터를 요청한다.
      Iterator<String> results = stmt.executeQuery("member/selectByName");

      // 서버의 응답을 받는다.
      if (!results.hasNext()) {
        System.out.println("등록된 회원이 아닙니다.");
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

  public static String inputMembers(Statement statement) throws Exception{
    String members = "";
    while (true) {
      String name = inputMember(statement);
      if (name == null) {
        return members;
      } else {
        if (!members.isEmpty()) {
          members += "/";
        }
        members += name;
      }
    }
  }

}





