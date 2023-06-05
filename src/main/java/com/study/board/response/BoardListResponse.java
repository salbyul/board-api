package com.study.board.response;

import com.study.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardListResponse {

//    카테고리 목록
    private List<String> categoryNames;

    private List<BoardDTO> boardDTOs;

//    전체 게시글 개수
    private Integer boardCounts;
}
