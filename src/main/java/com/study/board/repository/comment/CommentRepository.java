package com.study.board.repository.comment;

import com.study.board.dto.CommentDTO;

import java.util.List;

public interface CommentRepository {

    /**
     * Board의 Primary Key를 외래키로 갖고 있는 CommentDTO를 리스트 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    List<CommentDTO> findDTOForDetailByBoardId(Long boardId);

    /**
     * CommentDTO에 담긴 데이터를 이용하여 Comment 레코드를 생성한다.
     *
     * @param commentDTO DB에 저장할 데이터가 담긴 객체
     */
    void saveComment(CommentDTO commentDTO);
}
