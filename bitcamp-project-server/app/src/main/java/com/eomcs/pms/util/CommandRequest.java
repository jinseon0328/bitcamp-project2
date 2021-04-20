package com.eomcs.pms.util;

public class CommandRequest {

  private String commandPath;
  private String remoteAddr;
  private int remotePort;
  private Prompt prompt;
  private Session session;

  public CommandRequest (
      String commandPath, 
      String remoteAddr, 
      int remotePort, 
      Prompt prompt, 
      Session session) {

    this.commandPath = commandPath;
    this.remoteAddr = remoteAddr;
    this.remotePort = remotePort;
    this.prompt = prompt;
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

  public Prompt getPrompt() {
    return prompt;
  }

  public Session getSession() {
    return session;
  }
}



