package com.example.demo.itemDomain.item.infrastructure;

import com.example.demo.config.exceotion.FileException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Repository
public class FileRepository {

    private String serverPath = "C:/Users/parkgw/Desktop/files/";

    public String saveFile(MultipartFile file) {
        String originFileName = file.getOriginalFilename();
        String serverFileName = UUID.randomUUID() + originFileName;

        String serverSavePath = serverPath + serverFileName;
        try {
            file.transferTo(new File(serverSavePath));
        } catch (IOException e) {
            throw new FileException("파일 생성 실패");
        }

        return serverFileName;
    }
}
