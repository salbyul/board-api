package com.study.board.controller;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.CommentDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.response.board.BoardCreateResponse;
import com.study.board.response.board.BoardDetailResponse;
import com.study.board.response.board.BoardResponse;
import com.study.board.service.BoardService;
import com.study.board.service.CommentService;
import com.study.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CommentService commentService;

    /**
     * 메인 페이지와 연결되어 있다.
     * SearchCondition을 이용해 BoardListResponse를 만들어 전달한다.
     *
     * @param searchCondition 검색 조건이 담긴 객체
     * @return
     */
    @GetMapping("/list")
    public BoardResponse getList(SearchCondition searchCondition) {
        return boardService.getBoardList(searchCondition);
    }

    /**
     * 게시글을 작성하기 위한 페이지에 카테고리 목록을 전달한다.
     *
     * @return
     */
    @GetMapping("/create")
    public BoardResponse getCategories() {
        return new BoardCreateResponse(boardService.getCategories());
    }

    @PutMapping("/create")
    public BoardResponse createBoard(@Validated @RequestPart("boardCreateDTO") BoardCreateDTO boardCreateDTO, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws NoSuchAlgorithmException, IOException {
        Long boardId = boardService.saveBoard(boardCreateDTO);
        fileService.saveFiles(files, boardId);
        return new BoardCreateResponse(boardId);
    }

    /**
     * 해당 boardId를 Primary Key로 갖는 게시글의 정보를 담은 BoardDTO 객체를 전달한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    @GetMapping("/detail/{boardId}")
    public BoardResponse detail(@PathVariable Long boardId) {
        BoardDTO boardDetail = boardService.getBoardDetail(boardId);
        List<String> fileRealNames = fileService.getFileRealNames(boardId);
        List<CommentDTO> commentDetailDTOs = commentService.getCommentDetailDTOs(boardId);

        boardDetail.setFileNames(fileRealNames);
        boardDetail.setCommentDTOs(commentDetailDTOs);
        boardService.updateViews(boardId);
        return new BoardDetailResponse(boardDetail);
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId, String password) throws NoSuchAlgorithmException {
        boardService.deleteBoard(boardId, password);
        return ResponseEntity.ok().build();
    }
}
