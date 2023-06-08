package com.study.board.response.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardModifyResponse implements BoardResponse {

    private String encryptedPassword;
}
