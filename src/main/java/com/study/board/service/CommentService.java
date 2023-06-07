package com.study.board.service;

import com.study.board.dto.CommentDTO;
import com.study.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * Board의 Primary Key를 외래키로 갖고 있는 CommentDTO를 리스트 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    public List<CommentDTO> getCommentDetailDTOs(Long boardId) {
        return commentRepository.findDetailByBoardId(boardId);
    }
}
