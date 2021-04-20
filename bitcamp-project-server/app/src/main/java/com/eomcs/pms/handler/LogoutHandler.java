package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.util.CommandRequest;
import com.eomcs.pms.util.CommandResponse;
import com.eomcs.stereotype.Component;

@Component("/logout")
public class LogoutHandler implements Command {

  MemberService memberService;

  public LogoutHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAtrribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }
    request.getSession().invalidate();
    out.printf("% 님 안녕히 가세요!\n", member.getName());

  }
}






