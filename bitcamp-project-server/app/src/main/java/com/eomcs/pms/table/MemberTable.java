package com.eomcs.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.JsonFileHandler;
import com.eomcs.util.Request;
import com.eomcs.util.Response;

public class MemberTable implements DataTable {

  File jsonFile = new File("members.json");
  List<Member> list;

  public MemberTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Member.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Member member = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "member/insert":

        // data에서 CSV 형식으로 표현된 문자열을 꺼내 각 필드 값으로 분리한다.
        fields = request.getData().get(0).split(",");

        // CSV 데이터를 Board 객체에 저장한다.
        member = new Member();

        // 새 게시글의 번호
        if (list.size() > 0) {
          member.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          member.setNo(1);
        }

        member.setName(fields[1]);
        member.setEmail(fields[2]);
        member.setPassword(fields[3]);
        member.setPhoto(fields[4]);
        member.setTel(fields[5]);
        member.setRegisteredDate(Date.valueOf(fields[6]));

        // 새 게시글을 담은 Board 객체를 목록에 저장한다.
        list.add(member);

        // 게시글을 목록에 추가하는 즉시 List 컬렉션의 전체 데이터를 파일에 저장한다.
        // - 매번 전체 데이터를 파일에 저장하는 것은 비효율적이다.
        // - 효율성에 대한 부분은 무시한다.
        // - 현재 중요한 것은 서버 애플리케이션에서 데이터 파일을 관리한다는 점이다.
        // - 어차피 이 애플리케이션은 진정한 성능을 제공하는 DBMS으로 교체할 것이다.
        // 
        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "member/selectall":
        for (Member m : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s,%s", 
              m.getNo(),
              m.getName(),
              m.getEmail(),
              m.getPassword(),
              m.getPhoto(),
              m.getTel(),
              m.getRegisteredDate()));
        }
        break;
      case "member/select":
        int no = Integer.parseInt(request.getData().get(0));

        member = getMember(no);
        if (member != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s,%s", 
              member.getNo(), 
              member.getName(),
              member.getEmail(),
              member.getPassword(),
              member.getPhoto(),
              member.getTel(),
              member.getRegisteredDate()));
        } else {
          throw new Exception("해당 번호의 회원이 없습니다.");
        }
        break;
      case "member/selectByMember":
        String name = request.getData().get(0);
        break;
      case "member/update":
        fields = request.getData().get(0).split(",");

        member = getMember(Integer.parseInt(fields[0]));
        if (member == null) {
          throw new Exception("해당 번호의 회원이 없습니다.");
        }

        // 해당 게시물의 제목과 내용을 변경한다.
        // - List 에 보관된 객체를 꺼낸 것이기 때문에 
        //   그냥 그 객체의 값을 변경하면 된다.
        member.setName(fields[1]);
        member.setEmail(fields[2]);
        member.setPassword(fields[3]);
        member.setPhoto(fields[4]);
        member.setTel(fields[5]);
        member.setRegisteredDate(Date.valueOf(fields[6]));

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "member/delete":
        no = Integer.parseInt(request.getData().get(0));
        member = getMember(no);
        if (member == null) {
          throw new Exception("해당 번호의 회원이 없습니다.");
        }

        list.remove(member);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Member getMember(int memberNo) {
    for (Member m : list) {
      if (m.getNo() == memberNo) {
        return m;
      }
    }
    return null;
  }

  private Member getMemberByName(String name) {
    for (Member m : list) {
      if (m.getName().equals(name)) {
        return m;
      }
    }
    return null;
  }
}
