package com.ss.pojo.vo;

/**
 * Created by stella on 2017/9/17.
 */
public enum ItemType {
    flight("机票"), hotel("酒店"), car("用车"), oneday("一日游"), other("其他项目"), tool("旅游用品"), visa("签证"),
    gongying("供应商结算单"), flighthotel("机加酒"), platform("平台收入"), actual("实际收入"), custo("定制师提成"), income("订单收入") ;


    private final String name;
    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
