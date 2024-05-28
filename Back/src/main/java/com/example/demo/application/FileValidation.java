package com.example.demo.application;

import com.example.demo.config.exceotion.FileException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileValidation {
    public void checkImage(MultipartFile file) {
        List<String> checkExtensionList = new ArrayList<>();
        checkExtensionList.add("png");
        checkExtensionList.add("jpg");

        String filename = file.getOriginalFilename();
        if(filename.equals("")) throw new FileException("파일이 존재하지 않습니다.");
        String extension = StringUtils.getFilenameExtension(filename);
        
        for(String checkExtenstionLoop : checkExtensionList) {
            if(extension == null) break;
            if(extension.equals(checkExtenstionLoop)) return;
        }

        throw new FileException("파일 형식이 잘못되었습니다. [" + filename + "]");
    }
}
