package com.study.board.service;

import com.study.board.dto.FileDTO;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.dir}")
    private String PATH;

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    /**
     * MultipartFile List를 받아 로컬과 DB에 저장한다.
     *
     * @param files   파일 리스트
     * @param boardId 게시글의 Primary Key
     * @throws IOException
     */
    public void saveFiles(List<MultipartFile> files, Long boardId) throws IOException {
        if (isFilesEmpty(files)) return;

        for (MultipartFile file : files) {
            saveFile(file, boardId);
        }
    }

    /**
     * File List Null 체크
     *
     * @param files 파일리스트
     * @return Null값이면 False, 아니면 True
     */
    private boolean isFilesEmpty(List<MultipartFile> files) {
        return files == null;
    }

    /**
     * 파일의 이름을 체크 후 알맞은 형태로 변경, 로컬에 저장한 후 DB에 파일 정보를 저장한다.
     * <p>
     * 파일의 이름이 Null, Empty, Blank 일 경우 IllegalArgumentException 발생
     *
     * @param file    파일
     * @param boardId 게시글의 Primary Key
     * @throws IOException
     */
    private void saveFile(MultipartFile file, Long boardId) throws IOException {
        String fileName = file.getOriginalFilename();

        if (StringUtils.hasText(fileName)) {
            String renamedName = fileRename(fileName);
            saveToLocal(file, renamedName);
            FileDTO fileDTO = new FileDTO(renamedName, file.getOriginalFilename(), boardId);
            fileRepository.save(fileDTO);
        } else {
//            파일의 이름이 체크에 실패하면 생성된 Board를 DB에서 제거하고 IllegalArgumentException 발생
            boardRepository.deleteByBoardId(boardId);
            throw new IllegalArgumentException("File Null Name");
        }
    }

    /**
     * UUID + 현재 날짜 시각 + fileName 형태의 String을 리턴한다.
     *
     * @param fileName 파일 이름
     * @return 변경된 파일 이름
     */
    private String fileRename(String fileName) {
        String body;
        String ext;
        int dot = fileName.lastIndexOf(".");
        if (dot != -1) {
            body = fileName.substring(0, dot);
            ext = fileName.substring(dot);
        } else {
            body = fileName;
            ext = "";
        }

        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        return uuid.toString() + now + "[" + body + "]" + ext;
    }

    /**
     * 로컬에 파일을 저장한다.
     *
     * @param file        파일
     * @param renamedName 저장할 파일의 이름
     * @throws IOException
     */
    private void saveToLocal(MultipartFile file, String renamedName) throws IOException {
        file.transferTo(new File(PATH + renamedName));
    }

    /**
     * Board의 Primary Key를 외래키로 갖고 있는 레코드들의 real_name 값을 List 형태로 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return real_name List
     */
    public List<String> getFileRealNames(Long boardId) {
        return fileRepository.findRealName(boardId);
    }

    /**
     * FileDTO에 담긴 값을 이용해 name을 찾아 FileInputStream 객체를 생성해 리턴한다.
     *
     * @param fileDTO name값을 찾을 때 사용될 데이터가 담긴 객체
     * @return 해당 파일의 InputStream
     * @throws IOException
     */
    public FileInputStream getFileInputStream(FileDTO fileDTO) throws IOException {
        String fileName = fileRepository.findFileNameByFileDTO(fileDTO);
        if (fileName == null) throw new IllegalArgumentException("File Null");

        return new FileInputStream(PATH + fileName);
    }

    /**
     * 제거될 File의 real_name들을 추리고 제거한다.
     *
     * @param fileNames 제거되지 않을 real_name 목록
     * @param boardId   외래키로 갖는 값
     */
    public void removeFileByFileNames(List<String> fileNames, Long boardId) {
        List<String> existFileNames = fileRepository.findRealName(boardId);
        if (existFileNames.isEmpty()) return;

        if (!fileNames.isEmpty()) {
            List<String> listToRemove = selectToRemove(fileNames, existFileNames);
            if (!listToRemove.isEmpty()) {
                fileRepository.deleteFileByRealNameAndBoardId(listToRemove, boardId);
            }
        } else {
            fileRepository.deleteByBoardId(boardId);
        }
    }

    /**
     * existFileNames에는 존재하지만 fileNames에는 존재하지 않는 이름들의 목록을 리턴한다.
     *
     * @param fileNames      제거되지 않을 이름 목록
     * @param existFileNames 기존에 존재하는 이름 목록
     * @return
     */
    private List<String> selectToRemove(List<String> fileNames, List<String> existFileNames) {
        List<String> result = existFileNames.stream().collect(Collectors.toList());
        for (String fileName : fileNames) {
            for (String existFileName : result) {
                if (existFileName.equals(fileName)) {
                    result.remove(existFileName);
                    break;
                }
            }
        }
        return result;
    }
}
