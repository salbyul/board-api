package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 검색 조건을 담은 DTO
 */
@Getter
@Setter
public class SearchCondition {

    private Integer offset;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate endDate;
    private String category;
    private String search;
    private Integer limit;
}
