package com.Dealpf.demo.Service;

import com.Dealpf.demo.Util.Uploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileUploadService {
    private Uploader qCloudCosUtils = new Uploader();

    public String upload(MultipartFile multipartFile) {
        return qCloudCosUtils.upload(multipartFile);
    }

    public String upload(File file) {
        return qCloudCosUtils.upload(file);
    }
}
