package com.ruolan.kotlinserver.domain;

public class GetGoodsListByKeywordRequest {

    private String keyword;

    private int pageNo;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
