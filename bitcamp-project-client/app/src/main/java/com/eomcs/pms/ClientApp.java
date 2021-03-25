package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.MemberAddHandler;
import com.eomcs.pms.handler.MemberDeleteHandler;
import com.eomcs.pms.handler.MemberDetailHandler;
import com.eomcs.pms.handler.MemberListHandler;
import com.eomcs.pms.handler.MemberUpdateHandler;
import com.eomcs.util.Prompt;

public class ClientApp {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비

  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();


  String serverAddress;
  int port;

  public static void main(String[] args) {
    ClientApp app = new ClientApp("localhost", 8888);
    //192.168.0.67

    try {
      app.execute();
    }catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception {


    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler());
    commandMap.put("/board/list", new BoardListHandler());
    commandMap.put("/board/detail", new BoardDetailHandler());
    commandMap.put("/board/update", new BoardUpdateHandler());
    commandMap.put("/board/delete", new BoardDeleteHandler());
    commandMap.put("/board/search", new BoardSearchHandler());

    commandMap.put("/member/add", new MemberAddHandler());
    commandMap.put("/member/list", new MemberListHandler());
    commandMap.put("/member/detail", new MemberDetailHandler());
    commandMap.put("/member/update", new MemberUpdateHandler());
    commandMap.put("/member/delete", new MemberDeleteHandler());

    //    MemberValidator memberValidator = new MemberValidator(stmt);
    //
    //    commandMap.put("/project/add", new ProjectAddHandler(stmt, memberValidator));
    //    commandMap.put("/project/list", new ProjectListHandler(stmt));
    //    commandMap.put("/project/detail", new ProjectDetailHandler(stmt));
    //    commandMap.put("/project/update", new ProjectUpdateHandler(stmt, memberValidator));
    //    commandMap.put("/project/delete", new ProjectDeleteHandler(stmt));
    //
    //    commandMap.put("/task/add", new TaskAddHandler(stmt, memberValidator));
    //    commandMap.put("/task/list", new TaskListHandler(stmt));
    //    commandMap.put("/task/detail", new TaskDetailHandler(stmt));
    //    commandMap.put("/task/update", new TaskUpdateHandler(stmt, memberValidator));
    //    commandMap.put("/task/delete", new TaskDeleteHandler(stmt));


    try {// 서버와 통신하는 것을 대행해 줄 객체를 준비한다.

      while (true) {

        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) {// 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;
        }
        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {
          switch (command) {
            case "history":
              printCommandHistory(commandStack.iterator());
              break;
            case "history2": 
              printCommandHistory(commandQueue.iterator());
              break;
            case "quit":
            case "exit":
              // 서버에게 종료한다고 메시지를 보낸다.

              // 서버의 응답을 읽는다.
              // - 서버가 보낸 응답을 읽지 않으면 프로토콜 위반이다.
              // - 서버가 보낸 데이터를 사용하지 않더라도 프로토콜 규칙에 따라 읽어야 한다.

              System.out.println("안녕!");
              return;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
                // 이제 명령어와 그 명령어를 처리하는 핸들러를 추가할 때 마다
                // case 문을 추가할 필요가 없다.
              }
          }
        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s\n", e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    } catch (Exception e) {
      System.out.println("서버와 통신하는 중에 오류 발생!");
    }
    Prompt.close();
  }

  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }

}


