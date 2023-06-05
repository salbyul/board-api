package com.study.board.repository;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;

import java.util.List;

public interface BoardRepository {

    /**
     * DB에서 모든 카테고리를 가져온다.
     *
     * @return
     */
    List<String> findAllCategoryNames();

    /**
     * condition을 이용해 BoardDTO 리스트를 DB에서 가져와 리턴한다.
     *
     * @param condition 검색조건이 담긴 객체
     * @return
     */
    List<BoardDTO> findBoardDTOsBySearchCondition(SearchCondition condition);

    /**
     * condition을 이용해 나온 결과의 count를 리턴한다.
     *
     * @param condition 검색조건이 담긴 객체
     * @return
     */
    Integer countBySearchCondition(SearchCondition condition);
}
