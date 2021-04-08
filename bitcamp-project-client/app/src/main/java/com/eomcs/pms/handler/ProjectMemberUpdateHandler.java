package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectMemberUpdateHandler implements Command {

  ProjectDao projectDao;
  MemberValidator memberValidator;

  public ProjectMemberUpdateHandler(ProjectDao projectDao, MemberValidator memberValidator) {
    this.projectDao = projectDao;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 멤버 변경]");

    int no = Prompt.inputInt("프로젝트 번호? ");

    Project project = projectDao.findByNo(no);

    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    System.out.printf("프로젝트 명: %s\n", no);
    System.out.println("멤버: ");
    for (Member m : project.getMembers()) {
      System.out.printf(" %s(%d)\n", m.getName(), m.getNo());
    }
    System.out.println();

    // 프로젝트 팀원 정보를 입력 받는다.
    System.out.println("프로젝트의 멤버를 새로 등록하세요");
    List<Member> members = memberValidator.inputMembers("팀원(%s)?(완료: 빈 문자열) ");

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트의 멤버 변경을 취소하였습니다.");
      return;
    }

    // 프로젝트의 기존 멤버를 모두 삭제한다
    projectDao.deleteMembers(no);

    // 새 프로젝트 멤버를 등록한다.
    projectDao.insertMembers(no, members);

    System.out.println("프로젝트 멤버를 변경하였습니다.");
  }
}






