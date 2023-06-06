package com.study.board.service;

import com.study.board.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.dir}")
    private String PATH;

    private final FileRepository fileRepository;

    public void save() {

    }


}
