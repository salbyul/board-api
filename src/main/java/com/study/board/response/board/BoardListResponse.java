package com.study.board.response.board;

import com.study.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardListResponse implements BoardResponse {

    private List<String> categoryNames;
    private List<BoardDTO> boardDTOs;
    private Integer boardCounts;
}
