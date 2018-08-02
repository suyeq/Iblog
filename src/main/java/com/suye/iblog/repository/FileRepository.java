package com.suye.iblog.repository;

import com.suye.iblog.moder.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件仓库
 */
@Repository
public interface FileRepository extends JpaRepository<File,Long>{
}
