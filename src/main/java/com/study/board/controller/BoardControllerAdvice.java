package com.study.board.controller;

import com.study.board.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.study.board.response.ErrorResponse.*;

@Slf4j
@RestControllerAdvice
public class BoardControllerAdvice {

    /**
     * Validation 과정에서 발생한 Exception
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationFailed(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.error("ERROR: [{}]", bindingResult.getFieldError().getDefaultMessage());
        if (bindingResult.hasErrors()) {
            return new ErrorResponse(bindingResult.getFieldError().getDefaultMessage());
        }

//        파악 안된 Exception
        e.printStackTrace();
        return new ErrorResponse();
    }

    /**
     * File의 이름이 Null, Empty, Blank일 경우 발생한 Exception
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        if (e.getMessage().equals("File Null Name")) {
            return new ErrorResponse(FILE_NAME_NULL);
        } else if (e.getMessage().equals("File Null Name")) {
            return new ErrorResponse(FILE_NULL);
        } else if (e.getMessage().equals("Password Not Equal")) {
            return new ErrorResponse(PASSWORD_PASSWORD_NOT_EQUAL);
        }

//        파악 안된 Exception
        e.printStackTrace();
        return new ErrorResponse();
    }

    /**
     * IOException
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ErrorResponse IOExceptionHandler(IOException e) {
        e.printStackTrace();
        return new ErrorResponse();
    }

    /**
     * 비밀번호 암호화 과정에서 발생한 Exception
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ErrorResponse NoSuchAlgorithmExceptionHandler(NoSuchAlgorithmException e) {
        e.printStackTrace();
        return new ErrorResponse();
    }
}
