package com.suye.iblog.service.impl;

import com.suye.iblog.moder.File;
import com.suye.iblog.repository.FileRepository;
import com.suye.iblog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * file服务的实现
 */
public class FileServiceImpl  implements FileService{

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File getFile(Long id) {
        return fileRepository.getOne(id);
    }

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }
}
