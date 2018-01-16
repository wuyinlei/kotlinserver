package com.ruolan.kotlinserver.model;

import java.util.List;

public class CategoryFirstData {

    List<Category> parent;

    List<Category> children;


    public void setParent(List<Category> parent) {
        this.parent = parent;
    }

    public List<Category> getParent() {
        return parent;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public List<Category> getChildren() {
        return children;
    }
}
