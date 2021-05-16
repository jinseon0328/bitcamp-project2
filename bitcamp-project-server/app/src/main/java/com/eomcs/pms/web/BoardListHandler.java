package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;


@SuppressWarnings("serial")
@WebServlet("/board/list")
public class BoardListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 클라이언트가 /board/list 를 요청하면 톰캣 서버가 이 메서드를 호출한다. 

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    try {
      // JSP가 게시글 목록을 출력할 때 사용할 데이터를 준비한다. 
      List<Board> boards = boardService.list();

      // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
      request.setAttribute("list", boards);

      // 목록 출력을 JSP에게 맡긴다.
      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/board/list.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }

  }
}






