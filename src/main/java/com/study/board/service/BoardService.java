package com.study.board.service;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.repository.BoardRepository;
import com.study.board.response.BoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

//    TODO Clean Code
    public BoardListResponse getBoardList(SearchCondition condition) {
        List<String> categoryNames = getCategoryNames();
        List<BoardDTO> boardDTOs = boardRepository.findBoardDTOsBySearchCondition(condition);
        BoardListResponse boardListResponse = new BoardListResponse();
        boardListResponse.setBoardDTOs(boardDTOs);
        boardListResponse.setCategoryNames(categoryNames);
        Integer integer = boardRepository.countBySearchCondition(condition);
        boardListResponse.setBoardCounts(integer);
        return boardListResponse;
    }


    /**
     * 모든 카테고리 목록을 List<String> 형태로 리턴한다.
     * @return
     */
    private List<String> getCategoryNames() {
        return boardRepository.findAllCategoryNames();
    }
}
