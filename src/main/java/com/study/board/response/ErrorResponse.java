package com.study.board.response;

import lombok.Getter;

/**
 * TODO CLEAN CODE: 더럽다, 실제로 ErrorResponse를 전송할 때 static Field까지 전부 전송된다.
 */
@Getter
public class ErrorResponse {

    /**
     * Error Message Field
     * xx1 : Positive
     * xx2 : Null
     * xx3 : Size
     * xx4 : Pattern
     */
    public static final String CATEGORY_POSITIVE = "Category Positive";
    private final Integer CATEGORY_POSITIVE_CODE = 101;
    public static final String CATEGORY_NULL = "Category Null";
    private final Integer CATEGORY_NULL_CODE = 102;
    public static final String WRITER_NULL = "Writer Null";
    private final Integer WRITER_NULL_CODE = 202;
    public static final String WRITER_SIZE = "Writer Size";
    private final Integer WRITER_SIZE_CODE = 203;
    public static final String TITLE_NULL = "Title Null";
    private final Integer TITLE_NULL_CODE = 302;
    public static final String TITLE_SIZE = "Title Size";
    private final Integer TITLE_SIZE_CODE = 303;
    public static final String PASSWORD_NULL = "Password Null";
    private final Integer PASSWORD_NULL_CODE = 402;
    public static final String PASSWORD_SIZE = "Password Size";
    private final Integer PASSWORD_SIZE_CODE = 403;
    public static final String PASSWORD_PATTERN = "Password Pattern";
    private final Integer PASSWORD_PATTERN_CODE = 404;
    public static final String CONTENT_NULL = "Content Null";
    private final Integer CONTENT_NULL_CODE = 502;
    public static final String CONTENT_SIZE = "Content Size";
    private final Integer CONTENT_SIZE_CODE = 503;

    private final int errorCode;
    private final String errorMessage;

    public ErrorResponse() {
        this.errorCode = -1;
        this.errorMessage = "Unknown Error";
    }

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        switch (errorMessage) {
            case CATEGORY_POSITIVE:
                this.errorCode = CATEGORY_POSITIVE_CODE;
                break;
            case CATEGORY_NULL:
                this.errorCode = CATEGORY_NULL_CODE;
                break;
            case WRITER_NULL:
                this.errorCode = WRITER_NULL_CODE;
                break;
            case WRITER_SIZE:
                this.errorCode = WRITER_SIZE_CODE;
                break;
            case TITLE_NULL:
                this.errorCode = TITLE_NULL_CODE;
                break;
            case TITLE_SIZE:
                this.errorCode = TITLE_SIZE_CODE;
                break;
            case PASSWORD_NULL:
                this.errorCode = PASSWORD_NULL_CODE;
                break;
            case PASSWORD_SIZE:
                this.errorCode = PASSWORD_SIZE_CODE;
                break;
            case PASSWORD_PATTERN:
                this.errorCode = PASSWORD_PATTERN_CODE;
                break;
            case CONTENT_NULL:
                this.errorCode = CONTENT_NULL_CODE;
                break;
            case CONTENT_SIZE:
                this.errorCode = CONTENT_SIZE_CODE;
                break;
            default:
                errorCode = 0;
        }
    }
}
