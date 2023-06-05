package com.study.board.domain;

import java.time.LocalDateTime;

/**
 * Table: board
 */
public class Board {

    //    Column name: board_id
    private Long boardId;

    //    Column name: category_id
    private Long categoryId;

    //    Column name: writer
    private String writer;

    //    Column name: password
    private String password;

    //    Column name: title
    private String title;

    //    Column name: content
    private String content;

    //    Column name: views
    private Integer views;

    //    Column name: created_date
    private LocalDateTime generationTimestamp;

    //    Column name: modified_date
    private LocalDateTime modificationTimestamp;
}
