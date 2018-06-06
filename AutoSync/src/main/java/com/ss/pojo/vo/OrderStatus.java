package com.ss.pojo.vo;

/**
 * Created by stella on 2017/9/17.
 */
public enum  OrderStatus {
    NEW("未成团"), PENDING("待出团"), FINISH("已成团"), END_ORDER("订单结束"), REFUND("已退团"),
    REVIEWED("已审核"), PROBLEM("问题订单"), CANCEL("取消订单");

    private final String name;
    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
