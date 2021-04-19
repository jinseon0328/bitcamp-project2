package com.eomcs.pms.util;

public class CommandRequest {

  private String commandPath;
  private String remoteAddr;
  private int remotePort;

  public CommandRequest (String commandPath, String remoteAddr, int remotePort) {
    this.commandPath = commandPath;
    this.remoteAddr = remoteAddr;
    this.remotePort = remotePort;
  }

  public String getCommandPath() {
    return commandPath;
  }

  public String getRemoteAddr() {
    return remoteAddr;
  }

  public int getRemotePort() {
    return remotePort;
  }

}



