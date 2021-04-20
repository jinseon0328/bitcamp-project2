package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.util.CommandRequest;
import com.eomcs.pms.util.CommandResponse;
import com.eomcs.stereotype.Component;

@Component("/userInfo")
public class UserInfoHandler {

  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAtrribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다");
      return;
    }
    // 로그인 성공한다면, 로그이니 사용자 정볼르 세션 객체에 보관한다.
    out.printf("사용자번호: %d\n", member.getNo());
    out.printf("이름: %s\n", member.getName());
    out.printf("메일: %s\n", member.getEmail());
    out.printf("사진: %s\n", member.getPhoto());


  }
}

