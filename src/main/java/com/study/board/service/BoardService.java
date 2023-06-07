package com.study.board.service;

import com.study.board.SHA256Encoder;
import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.FileRepository;
import com.study.board.response.board.BoardListResponse;
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
        List<BoardDTO> boardDTOs = boardRepository.findSmallBySearchCondition(condition);
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

    /**
     * Board의 Primary Key를 이용해 디테일 페이지에 표현할 데이터를 담은 BoardDTO 객체를 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return BoardDTO
     */
    public BoardDTO getBoardDetail(Long boardId) {
        return boardRepository.findDetailByBoardId(boardId);
    }

    /**
     * Board의 views를 1 증가시킨다.
     *
     * @param boardId Board의 Primary Key
     */
    public void updateViews(Long boardId) {
        boardRepository.addOneToViews(boardId);
    }

    /**
     * 비밀번호를 확인해 같은 경우에 Board를 DB에서 제거한다.
     * 같지 않은 경우에는 IllegalArgumentException을 발생시킨다.
     *
     * @param boardId  Board의 Primary Key
     * @param password 사용자가 입력한 password
     * @throws NoSuchAlgorithmException
     */
    public void deleteBoard(Long boardId, String password) throws NoSuchAlgorithmException {
        String encryptedPassword = sha256Encoder.encrypt(password);
        String foundPassword = boardRepository.findPasswordByBoardId(boardId);
        if (!encryptedPassword.equals(foundPassword)) {
            throw new IllegalArgumentException("Password Not Equal");
        }

        boardRepository.deleteByBoardId(boardId);
    }
}
