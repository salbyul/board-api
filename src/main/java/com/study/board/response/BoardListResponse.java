package com.study.board.response;

import com.study.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardListResponse {

    private List<String> categoryNames;
    private List<BoardDTO> boardDTOs;
    private Integer boardCounts;
}
