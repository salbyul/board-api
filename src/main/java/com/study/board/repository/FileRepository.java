package com.study.board.repository;

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
     * 인수로 들어온 값을 외래키로 갖고 있는 File들의 real_name 레코드값들을 List 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return real_name List
     */
    List<String> findRealName(Long boardId);

    /**
     * Board의 Primary Key와 File의 real_name 을 이용해 file_name 값을 찾아 리턴한다.
     *
     * @param fileDTO
     * @return
     */
    String findFileNameByFileDTO(FileDTO fileDTO);
}
