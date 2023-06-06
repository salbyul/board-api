package com.study.board.service;

import com.study.board.SHA256Encoder;
import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.repository.BoardRepository;
import com.study.board.response.BoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.study.board.dto.BoardDTO.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SHA256Encoder sha256Encoder;

    /**
     * @param condition 검색 조건이 담긴 객체
     * @return
     */
    public BoardListResponse getBoardList(SearchCondition condition) {
        List<String> categoryNames = getCategoryNames();
        List<BoardDTO> boardDTOs = boardRepository.findBoardDTOsBySearchCondition(condition);
        Integer boardCounts = boardRepository.countBySearchCondition(condition);

        return new BoardListResponse(categoryNames, boardDTOs, boardCounts);
    }


    /**
     * 모든 카테고리 목록을 List<String> 형태로 리턴한다.
     *
     * @return 카테고리 이름이 담긴 리스트
     */
    private List<String> getCategoryNames() {
        return boardRepository.findAllCategoryNames();
    }

    /**
     * 모든 카테고리 목록을 리턴한다.
     *
     * @return
     */
    public List<Category> getCategories() {
        return boardRepository.findAllCategories();
    }

    /**
     * BoardCreateDTO에 현재 시각을 주입하고, 비밀번호를 암호화한 후 DB에 저장한다.
     *
     * @param boardCreateDTO Board 테이블에 저장할 객체
     * @return 저장된 Board의 Primary Key
     * @throws NoSuchAlgorithmException
     */
    public Long saveBoard(BoardCreateDTO boardCreateDTO) throws NoSuchAlgorithmException {
        boardCreateDTO.setGenerationTimestampToCurrentTime();
        boardCreateDTO.setPassword(sha256Encoder.encrypt(boardCreateDTO.getPassword()));

        boardRepository.save(boardCreateDTO);
        return boardCreateDTO.getBoardId();
    }
}
