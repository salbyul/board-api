package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardDTO {

    private Long boardId;
    private String category;
    private Long categoryId;
    private String writer;
    private String title;
    private String password;
    private String content;
    private Integer views;
    private LocalDateTime generationTimestamp;
    private LocalDateTime modificationTimestamp;
    private List<String> fileNames;
    private List<CommentDTO> commentDTOs;
    private Integer fileCounts;

    /**
     * Board를 생성하기 위한 객체
     */
    @Getter
    @Setter
    public static class BoardCreateDTO {
        private Long boardId;

        @Positive(message = "Category Positive")
        @NotNull(message = "Category Null")
        private Long categoryId;

        @Size(min = 3, max = 4, message = "Writer Size")
        @NotNull(message = "Writer Null")
        private String writer;

        @Size(min = 4, max = 99, message = "Title Size")
        @NotNull(message = "Title Null")
        private String title;

        @Size(min = 4, max = 15, message = "Password Size")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{4,16}$", message = "Password Pattern")
        @NotNull(message = "Password Null")
        private String password;

        @Size(min = 4, max = 1999, message = "Content Size")
        @NotNull(message = "Content Null")
        private String content;

        private LocalDateTime generationTimestamp;

        public void setGenerationTimestampToCurrentTime() {
            this.generationTimestamp = LocalDateTime.now();
        }
    }

    /**
     * Board를 수정하기 위한 객체
     */
    @Getter
    @Setter
    public static class BoardModifyDTO {

        @Size(min = 3, max = 4, message = "Writer Size")
        @NotNull(message = "Writer Null")
        private String writer;

        private String password;

        @Size(min = 4, max = 99, message = "Title Size")
        @NotNull(message = "Title Null")
        private String title;

        @Size(min = 4, max = 1999, message = "Content Size")
        @NotNull(message = "Content Null")
        private String content;

        private List<String> fileNames;
        private LocalDateTime modificationTimestamp;
    }
}
