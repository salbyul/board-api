package com.study.board.repository;

import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;

import java.util.List;

import static com.study.board.dto.BoardDTO.*;

public interface BoardRepository {

    /**
     * DB에서 모든 카테고리를 가져온다.
     *
     * @return 카테고리의 이름이 담긴 리스트
     */
    List<String> findAllCategoryNames();

    /**
     * condition을 이용해 BoardDTO 리스트를 DB에서 가져와 리턴한다.
     *
     * @param condition 검색 조건이 담긴 객체
     * @return BoardDTO가 담긴 리스트
     */
    List<BoardDTO> findBoardDTOsBySearchCondition(SearchCondition condition);

    /**
     * Condition을 이용해 나온 결과의 Counts를 리턴한다.
     *
     * @param condition 검색 조건이 담긴 객체
     * @return 검색 결과
     */
    Integer countBySearchCondition(SearchCondition condition);

    /**
     * BoardCreateDTO를 이용해 DB에 저장한다.
     *
     * @param boardCreateDTO DB에 저장할 데이터가 담긴 객체
     */
    void save(BoardCreateDTO boardCreateDTO);

    /**
     * 전체 카테고리를 리스트 형태로 리턴한다.
     *
     * @return
     */
    List<Category> findAllCategories();
}
