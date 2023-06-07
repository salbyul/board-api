package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 검색 조건을 담은 DTO
 */
@Getter
@Setter
@ToString
public class SearchCondition {

    private Integer page;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String category;
    private String search;
    private Integer limit;
}
