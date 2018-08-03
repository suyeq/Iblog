package com.suye.iblog.service.impl;

import com.suye.iblog.moder.Catalog;
import com.suye.iblog.moder.User;
import com.suye.iblog.repository.CatalogRepository;
import com.suye.iblog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CatalogRepository catalogRepository;

    /**
     * 保存个人的博客分类
     * @param catalog
     * @return
     */
    @Override
    public Catalog saveCatalog(Catalog catalog) {
        // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if(list !=null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    /**
     * 删除某个分类
     * @param id
     */
    @Override
    public void removeCatalog(Long id) {
        catalogRepository.delete(id);
    }

    /**
     * 获取某个分类
     * @param id
     * @return
     */
    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);
    }

    /**
     * 获取某人分类列表
     * @param user
     * @return
     */
    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }
}
