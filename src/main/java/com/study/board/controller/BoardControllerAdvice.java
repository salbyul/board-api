package com.study.board.controller;

import com.study.board.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {BoardController.class})
public class BoardControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationFailed(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.error("ERROR: [{}]", bindingResult.getFieldError().getDefaultMessage());
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ErrorResponse(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}
