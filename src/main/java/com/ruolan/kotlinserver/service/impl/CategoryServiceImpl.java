package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.CategoryDao;
import com.ruolan.kotlinserver.model.Category;
import com.ruolan.kotlinserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public int addCategory(Category paramCategory) {
        return categoryDao.insert(paramCategory);
    }

    @Override
    public List<Category> getCategoryList(Integer paramInteger) {
        return categoryDao.selectCategory(paramInteger);
    }

    @Override
    public List<Category> getParentList() {
        return categoryDao.parentCategory();
    }

}
