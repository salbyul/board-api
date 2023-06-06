package com.study.board.repository;

import com.study.board.dto.FileDTO;

public interface FileRepository {

    /**
     * FileDTO를 받아 DB에 저장한다.
     * @param fileDTO
     */
    void save(FileDTO fileDTO);
}
