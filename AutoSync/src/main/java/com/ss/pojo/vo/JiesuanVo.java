package com.ss.pojo.vo;

import com.ss.pojo.OrderCostItem;
import com.ss.pojo.OrderItem;

import java.util.List;

/**
 * Created by stella on 2017/9/24.
 */
public class JiesuanVo {
    private Integer orderId;
    private List<OrderItem> items;
    private List<OrderCostItem> costItems;

    public List<OrderCostItem> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<OrderCostItem> costItems) {
        this.costItems = costItems;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
