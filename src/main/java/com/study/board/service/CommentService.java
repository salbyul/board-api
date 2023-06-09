package com.study.board.service;

import com.study.board.dto.CommentDTO;
import com.study.board.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * CommentDTO의 생성 시각을 현재 시각으로 설정해준 뒤,
     * CommentDTO에 담긴 데이터를 DB에 저장한다.
     *
     * @param commentDTO 사용자가 입력한 값이 담긴 객체
     */
    public void saveComment(CommentDTO commentDTO) {
        commentDTO.setGenerationTimestampToNow();
        commentRepository.saveComment(commentDTO);
    }
}
