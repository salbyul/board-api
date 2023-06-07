package com.study.board.controller;

import com.study.board.dto.FileDTO;
import com.study.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    /**
     * FileDTO를 받아 해당 File을 1024 버퍼의 크기로 전송한다.
     *
     * @param response
     * @param fileDTO
     * @throws IOException
     */
    @GetMapping("/download")
    public void fileDownload(HttpServletResponse response, FileDTO fileDTO) throws IOException {
        FileInputStream fileInputStream = fileService.getFileInputStream(fileDTO);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileDTO.getRealName());

        ServletOutputStream out = response.getOutputStream();
        int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }

        out.flush();
        out.close();
        fileInputStream.close();
    }
}
