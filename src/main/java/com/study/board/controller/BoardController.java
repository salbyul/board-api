package com.study.board.controller;

import com.study.board.domain.Category;
import com.study.board.dto.BoardDTO;
import com.study.board.dto.SearchCondition;
import com.study.board.response.board.BoardResponse;
import com.study.board.service.BoardService;
import com.study.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.study.board.dto.BoardDTO.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    /**
     * SearchCondition을 이용해 BoardResponse를 만들어 전달한다.
     * 페이징에 따른 목록과 전체 목록 수, 카테고리 이름 목록이 담겨있다.
     *
     * @param condition 검색 조건이 담긴 객체
     * @return
     */
    @GetMapping("/list")
    public BoardResponse getList(SearchCondition condition) {
        condition.transformPageToOffset();

        List<BoardDTO> boardDTOs = boardService.getBoardList(condition);
        Integer boardCounts = boardService.getBoardCounts(condition);
        List<String> categoryNames = boardService.getCategoryNames();
        return BoardResponse.builder()
                .boardDTOsForList(boardDTOs)
                .boardCounts(boardCounts)
                .categoryNames(categoryNames)
                .build();
    }

    /**
     * 카테고리 목록을 BoardResponse에 담아 전달한다.
     *
     * @return
     */
    @GetMapping("/create")
    public BoardResponse getCategories() {
        List<Category> categories = boardService.getCategories();
        return BoardResponse.builder()
                .categories(categories)
                .build();
    }

    /**
     * boardCreateDTO를 이용해 Board를 DB에 저장, files를 이용해 파일들을 로컬에 저장, DB에 저장한다.
     *
     * @param boardCreateDTO 사용자가 입력한 Board 데이터가 담긴 객체
     * @param files          사용자가 업로드할 파일들이 담긴 객체
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @PostMapping("/create")
    public BoardResponse createBoard(@Validated @RequestPart("boardCreateDTO") BoardCreateDTO boardCreateDTO, @RequestPart(required = false) List<MultipartFile> files) throws NoSuchAlgorithmException, IOException {
        Long boardId = boardService.saveBoard(boardCreateDTO);
        fileService.saveFiles(files, boardId);
        return BoardResponse.builder()
                .boardId(boardId)
                .build();
    }

    /**
     * 해당 boardId를 Primary Key로 갖는 게시글의 정보를 담은 BoardDTO 객체를 BoardResponse에 담아 전달한다.
     *
     * @param boardId Board의 Primary Key
     * @return
     */
    @GetMapping("/detail/{boardId}")
    public BoardResponse getBoardDetail(@PathVariable Long boardId) {
        BoardDTO boardDetail = boardService.getBoardDetail(boardId);
        boardService.updateViews(boardId);
        return BoardResponse.builder()
                .boardDetail(boardDetail)
                .build();
    }

    /**
     * password를 확인 후 맞으면 boardId를 Primary Key로 갖고 있는 게시글을 삭제한다.
     *
     * @param boardId  Board의 Primary Key
     * @param password 게시글 삭제를 위해 입력한 비밀번호
     * @return
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable Long boardId, String password) throws NoSuchAlgorithmException {
        boardService.checkPassword(boardId, password);
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    /**
     * 게시글 수정을 위해 입력한 비밀번호가 맞는지 확인한다.
     *
     * @param boardId  수정될 게시글의 Primary Key
     * @param password 사용자가 입력한 비밀번호
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PutMapping("/modify")
    public ResponseEntity checkPasswordForModification(Long boardId, String password) throws NoSuchAlgorithmException {
        boardService.checkPassword(boardId, password);
        return ResponseEntity.ok().build();
    }

    /**
     * 수정을 위해 기존의 데이터들을 갖고 있는 BoardDTO를 BoardResponse에 담아 전달한다.
     *
     * @param boardId 수정될 게시글의 Primary Key
     * @return
     */
    @GetMapping("/modify/{boardId}")
    public BoardResponse getDTOForModification(@PathVariable Long boardId) {
        BoardDTO boardDTO = boardService.getDetailForModification(boardId);
        return BoardResponse.builder()
                .boardDetail(boardDTO)
                .build();
    }

    /**
     * 수정된 게시글의 데이터가 담긴 BoardModifyDTO를 받아 해당 게시글을 수정한다.
     * 마찬가지로 파일의 변경도 확인 후 수정한다.
     *
     * @param boardId        수정된 게시글의 Primary Key
     * @param boardModifyDTO 수정된 게시글의 데이터가 담긴 객체
     * @param files          새로 등록된 파일목록
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @PutMapping("/modify/{boardId}")
    public ResponseEntity modifyBoard(@PathVariable Long boardId, @Validated @RequestPart BoardModifyDTO boardModifyDTO, @RequestPart(required = false) List<MultipartFile> files) throws NoSuchAlgorithmException, IOException {
        boardService.checkPassword(boardId, boardModifyDTO.getPassword());
        boardService.modifyBoard(boardId, boardModifyDTO);
        fileService.removeFileByFileNames(boardModifyDTO.getFileNames(), boardId);
        fileService.saveFiles(files, boardId);
        return ResponseEntity.ok().build();
    }
}
