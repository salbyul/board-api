package com.study.board.response.board;

import com.study.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardDetailResponse implements BoardResponse {

    private BoardDTO boardDTO;
}
