package com.study.board.response.board;

import com.study.board.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardCreateResponse implements BoardResponse {

    private Long boardId;
    private List<Category> categories;

    public BoardCreateResponse(List<Category> categories) {
        this.categories = categories;
    }

    public BoardCreateResponse(Long boardId) {
        this.boardId = boardId;
    }
}
