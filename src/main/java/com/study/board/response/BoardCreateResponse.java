package com.study.board.response;

import com.study.board.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardCreateResponse {

    private List<Category> categories;
}
