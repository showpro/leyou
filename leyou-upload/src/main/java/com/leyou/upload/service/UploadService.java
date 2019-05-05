package com.leyou.upload.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhan
 * @create 2019-04-28-21:03
 */
public interface UploadService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
