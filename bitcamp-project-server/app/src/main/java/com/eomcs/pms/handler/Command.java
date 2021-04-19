package com.eomcs.pms.handler;

import com.eomcs.pms.util.CommandRequest;
import com.eomcs.pms.util.CommandResponse;

public interface Command {
  void service(CommandRequest request, CommandResponse response) throws Exception;
}
