package com.ruolan.kotlinserver.model;


import java.util.List;

public class HomeRealInfo {


    private List<String> banners;

    private List<String> discounts;

    private List<String> topics;

    private List<String> message;


    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<String> discounts) {
        this.discounts = discounts;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }
}
