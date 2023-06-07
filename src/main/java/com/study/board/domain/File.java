package com.study.board.domain;

//TODO java.io.File 과 같은 이름이라 헷갈린다. Rename?

/**
 * Table: file
 */
public class File {

    //    Column name: file_id;
    private Long fileId;

    //    Column name: name
    private String fileName;

    //    Column name: real_name
    private String realName;

    //    Column name: board_id
    private Long boardId;
}
