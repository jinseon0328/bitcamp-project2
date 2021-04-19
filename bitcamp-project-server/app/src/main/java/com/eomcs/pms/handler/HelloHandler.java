package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.util.CommandRequest;
import com.eomcs.pms.util.CommandResponse;
import com.eomcs.stereotype.Component;

@Component("/hello")
public class HelloHandler implements Command {

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    out.println("안녕하세요!");

  }

}
