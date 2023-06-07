package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {

    private Long commentId;
    private String writer;
    private String content;
    private LocalDateTime generationTimestamp;
    private Long boardId;
}
