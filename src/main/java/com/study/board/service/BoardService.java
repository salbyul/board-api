package com.study.board.service;

import com.study.board.SHA256Encoder;
import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.CommentDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.repository.board.BoardRepository;
import com.study.board.repository.comment.CommentRepository;
import com.study.board.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import static com.study.board.dto.BoardDTO.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;
    private final SHA256Encoder sha256Encoder;

    /**
     * 검색 조건을 이용하여 검색된 레코드들을 리턴한다.
     *
     * @param condition 검색 조건이 담긴 객체
     * @return
     */
    public List<BoardDTO> getBoardList(SearchCondition condition) {
        return boardRepository.findSmallBySearchCondition(condition);
    }

    /**
     * 검색 조건을 이용하여 검색된 레코드들의 수를 리턴한다. (페이징 조건 제외)
     *
     * @param condition 검색 조건이 담긴 객체
     * @return
     */
    public Integer getBoardCounts(SearchCondition condition) {
        return boardRepository.countBySearchCondition(condition);
    }


    /**
     * 모든 카테고리의 이름 목록을 리턴한다.
     *
     * @return 카테고리 이름이 담긴 리스트
     */
    public List<String> getCategoryNames() {
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
     * 저장된 Board의 Primary Key를 리턴한다.
     *
     * @param boardCreateDTO Board 테이블에 저장할 객체
     * @return 저장된 Board의 Primary Key
     * @throws NoSuchAlgorithmException
     */
    public Long saveBoard(BoardCreateDTO boardCreateDTO) throws NoSuchAlgorithmException {
        boardCreateDTO.setGenerationTimestampToCurrentTime();
        boardCreateDTO.setPassword(getEncryptedPassword(boardCreateDTO.getPassword()));

        boardRepository.save(boardCreateDTO);
        return boardCreateDTO.getBoardId();
    }

    /**
     * password를 SHA256 알고리즘으로 암호화한다.
     *
     * @param password password
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String getEncryptedPassword(String password) throws NoSuchAlgorithmException {
        return sha256Encoder.encrypt(password);
    }

    /**
     * Board의 Primary Key를 이용해 디테일 페이지에 표현할 데이터를 담은 BoardDTO 객체를 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return BoardDTO
     */
    public BoardDTO getBoardDetail(Long boardId) {
        BoardDTO boardDetail = boardRepository.findDetailByBoardId(boardId);

        List<String> fileRealNames = fileRepository.findRealNames(boardId);
        boardDetail.setFileNames(fileRealNames);

        List<CommentDTO> commentDTOs = commentRepository.findDTOForDetailByBoardId(boardId);
        boardDetail.setCommentDTOs(commentDTOs);
        return boardDetail;
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
     * 해당 boardId를 Primary Key로 갖고 있는 게시글을 DB에서 제거한다.
     *
     * @param boardId Board의 Primary Key
     * @throws NoSuchAlgorithmException
     */
    public void deleteBoard(Long boardId) {
        boardRepository.deleteByBoardId(boardId);
    }

    /**
     * boardId를 이용해 수정하기 위한 정보가 담긴 boardDTO 객체를 리턴한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    public BoardDTO getDetailForModification(Long boardId) {
        BoardDTO boardDTO = boardRepository.findDTOForModificationByBoardId(boardId);

        List<String> fileNames = fileRepository.findRealNames(boardId);
        boardDTO.setFileNames(fileNames);
        return boardDTO;
    }

    /**
     * 유저가 입력한 비밀번호와 DB에 저장된 비밀번호가 같은지 확인한다.
     *
     * @param password 유저가 입력한 비밀번호
     * @param boardId  해당 글의 Primary Key
     * @throws NoSuchAlgorithmException
     */
    public void checkPassword(Long boardId, String password) throws NoSuchAlgorithmException {
        String encryptedPassword = sha256Encoder.encrypt(password);
        String foundPassword = boardRepository.findPasswordByBoardId(boardId);
        if (!encryptedPassword.equals(foundPassword)) {
            throw new IllegalArgumentException("Password Not Equal");
        }
    }

    /**
     * BoardModifyDTO 객체에 수정 시각을 현재 시각으로 설정하고,
     * boardId를 Primary Key로 갖는 레코드의 값을 boardModifyDTO 객체를 이용하여 변경한다.
     *
     * @param boardModifyDTO 변경할 값이 담긴 객체
     * @param boardId        변경할 레코드를 찾을 Primary Key
     */
    public void modifyBoard(Long boardId, BoardModifyDTO boardModifyDTO) {
        boardModifyDTO.setModificationTimestamp(LocalDateTime.now());
        boardRepository.modifyBoardByBoardModifyDTO(boardId, boardModifyDTO);
    }
}
