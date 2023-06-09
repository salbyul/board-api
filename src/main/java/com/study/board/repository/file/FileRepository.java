package com.study.board.repository.file;

import com.study.board.dto.FileDTO;

import java.util.List;

public interface FileRepository {

    /**
     * FileDTO를 받아 DB에 저장한다.
     *
     * @param fileDTO
     */
    void save(FileDTO fileDTO);

    /**
     * boardId를 외래키로 갖고 있는 File들의 real_name 레코드값들을 List 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return real_name List
     */
    List<String> findRealNames(Long boardId);

    /**
     * FileDTO에 담긴 boardId를 외래키로, realName을 real_name으로 갖고 있는 레코드를 찾아,
     * 해당 레코드의 fileName을 리턴한다.
     *
     * @param fileDTO
     * @return
     */
    String findFileNameByFileDTO(FileDTO fileDTO);

    /**
     * boardId를 외래키로 갖고, real_name이 fileNames에 속한 레코드들을 제거한다.
     *
     * @param fileNames 제거될 real_name 목록
     * @param boardId   제거될 레코드들이 외래키로 갖는 값
     */
    void deleteFileByRealNameAndBoardId(List<String> fileNames, Long boardId);

    /**
     * boardId를 외래키로 갖는 모든 레코드를 DB에서 제거한다.
     *
     * @param boardId 외래키
     */
    void deleteByBoardId(Long boardId);
}
