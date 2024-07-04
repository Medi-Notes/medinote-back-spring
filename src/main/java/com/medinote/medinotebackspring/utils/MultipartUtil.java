package com.medinote.medinotebackspring.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MultipartUtil {
    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);

        return file;
    }
}
