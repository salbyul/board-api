package com.study.board.repository;

import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;

import java.util.List;

import static com.study.board.dto.BoardDTO.*;

public interface BoardRepository {

    /**
     * DB에서 모든 카테고리의 name 값을 List 형태로 리턴한다.
     *
     * @return 카테고리의 이름이 담긴 리스트
     */
    List<String> findAllCategoryNames();

    /**
     * 검색 조건을 이용해 메인 페이지에 나타낼 데이터가 담긴 BoardDTO 객체를 리턴한다.
     *
     * @param condition 검색 조건이 담긴 객체
     * @return BoardDTO가 담긴 리스트
     */
    List<BoardDTO> findSmallBySearchCondition(SearchCondition condition);

    /**
     * 검색 조건을 이용해 나온 결과의 수를 리턴한다.
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

    /**
     * Board의 Primary Key를 이용해 Board 디테일 페이지에 나타낼 데이터가 담긴 BoardDTO 객체를 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    BoardDTO findDetailByBoardId(Long boardId);

    /**
     * Board의 Primary Key를 이용해 Board의 views 속성을 1 증가시킨다.
     *
     * @param boardId Board의 Primary Key
     */
    void addOneToViews(Long boardId);

    /**
     * Board의 Primary Key를 이용해 Board의 password를 찾아 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    String findPasswordByBoardId(Long boardId);

    /**
     * Board의 Primary Key를 이용해 Board를 DB에서 제거한다.
     *
     * @param boardId Board의 Primary Key
     */
    void deleteByBoardId(Long boardId);
}
