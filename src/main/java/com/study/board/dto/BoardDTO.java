package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long boardId;
    private String category;
    private String writer;
    private String title;
    private String password;
    private String content;
    private Integer views;
    private LocalDateTime generationTimestamp;
    private LocalDateTime modificationTimestamp;
    private List<String> fileNames;
    private Integer fileCounts;
}
