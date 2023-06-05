package com.study.board.controller;

import com.study.board.dto.SearchCondition;
import com.study.board.response.BoardListResponse;
import com.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    /**
     * 메인 페이지와 연결되어 있다.
     * SearchCondition을 이용해 boardListResponse를 만들어 리턴한다.
     *
     * @param searchCondition
     * @return
     */
    @GetMapping("/list")
    public BoardListResponse getList(SearchCondition searchCondition) {
        return boardService.getBoardList(searchCondition);
    }
}
