package com.study.board.repository;

import com.study.board.dto.CommentDTO;

import java.util.List;

public interface CommentRepository {

    /**
     * Board의 Primary Key를 외래키로 갖고 있는 CommentDTO를 리스트 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    List<CommentDTO> findDetailByBoardId(Long boardId);
}
