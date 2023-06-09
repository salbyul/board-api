package com.study.board.response.board;

import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardResponse {

    private List<String> categoryNames;
    private List<BoardDTO> boardDTOsForList;
    private Integer boardCounts;
    private BoardDTO boardDetail;
    private Long boardId;
    private List<Category> categories;
}
