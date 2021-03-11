package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberValidatorHandler {

  public static String inputMember(String promptTitle, DataInputStream in, DataOutputStream out) {
    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      }
      // 서버에 지정한 번호의 게시글의 요청한다.
      out.writeUTF("board/MemberByName");
      out.writeInt(1);
      out.writeUTF(name);
      out.flush();

      // 서버의 응답을 받는다.
      String status = in.readUTF();
      int length = in.readInt();

      if(staus)

        if (status.equals("error")) {
          System.out.println(in.readUTF());
          return;
        }

      public static String inputMembers(String promptTitle,
          DataInputStream in, DataOutputStream out) {
        String members = "";
        while (true) {
          String name = inputMember(promptTitle, in, out);
          if (name == null) {
            return members;
          } else {
            if (!members.isEmpty()) {
              members += ",";
            }
            members += name;
          }
        }
      }

    }






