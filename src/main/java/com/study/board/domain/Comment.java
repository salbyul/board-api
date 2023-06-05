package com.study.board.domain;

import java.time.LocalDateTime;

/**
 * Table: comment
 */
public class Comment {

    //    Column name: comment_id
    private Long commentId;

    //    Column name: writer
    private String writer;

    //    Column name: content
    private String content;

    //    Column name: created_date
    private LocalDateTime generationTimestamp;

    //    Column name: board_id
    private Long boardId;
}
