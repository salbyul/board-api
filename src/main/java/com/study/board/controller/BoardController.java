package com.study.board.controller;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.response.BoardCreateResponse;
import com.study.board.response.BoardListResponse;
import com.study.board.service.BoardService;
import com.study.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.study.board.dto.BoardDTO.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 메인 페이지와 연결되어 있다.
     * SearchCondition을 이용해 BoardListResponse를 만들어 리턴한다.
     *
     * @param searchCondition 검색 조건이 담긴 객체
     * @return
     */
    @GetMapping("/list")
    public BoardListResponse getList(SearchCondition searchCondition) {
        return boardService.getBoardList(searchCondition);
    }

    @GetMapping("/create")
    public BoardCreateResponse getCategories() {
        return new BoardCreateResponse(boardService.getCategories());
    }

    @PutMapping("/create")
    public ResponseEntity<String> createBoard(@Validated @RequestPart("boardCreateDTO") BoardCreateDTO boardCreateDTO, @RequestPart(value = "files", required = false)List<MultipartFile> files) throws NoSuchAlgorithmException {
        System.out.println(boardCreateDTO);
//        Long boardId = boardService.saveBoard(boardCreateDTO);

        return ResponseEntity.ok("Good");
    }
}
