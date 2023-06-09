package com.study.board.controller;

import com.study.board.dto.CommentDTO;
import com.study.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 사용자가 입력한 댓글을 DB에 저장한다.
     *
     * @param commentDTO 사용자가 입력한 댓글의 데이터가 담긴 객체
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDTO commentDTO) {
        commentService.saveComment(commentDTO);
        return ResponseEntity.ok().build();
    }
}
