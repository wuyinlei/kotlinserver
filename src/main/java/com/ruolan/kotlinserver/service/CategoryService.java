package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.Category;

import java.util.List;

public interface CategoryService {

    int addCategory(Category paramCategory);

    List<Category> getCategoryList(Integer paramInteger);

    List<Category> getParentList();
}
