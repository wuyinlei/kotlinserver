package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.Category;

import java.util.List;

public interface CategoryDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(Category paramCategory);

    int insertSelective(Category paramCategory);

    Category selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(Category paramCategory);

    int updateByPrimaryKey(Category paramCategory);

    List<Category> parentCategory();

    List<Category> selectCategory(Integer paramInteger);

}
