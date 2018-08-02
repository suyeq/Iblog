package com.suye.iblog.service;

import com.suye.iblog.moder.File;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    /**
     * 获取一个文件
     * @param id
     * @return
     */
    File getFile(Long id);

    /**
     * 保存一个文件
     * @param file
     * @return
     */
    File saveFile(File file);
}
