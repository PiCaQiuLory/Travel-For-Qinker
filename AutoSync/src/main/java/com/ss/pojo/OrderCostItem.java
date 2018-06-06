package com.ss.pojo;

import java.util.Date;

/**
 * Created by stella on 2017/10/28.
 */
public class OrderCostItem {
    private long id;
    private String type;
    private String category;
    private String note;
    private Date useDate;
    private Double cost;
    private long orderId;
    private String itemNumber;
    private Date useDateFrom;
    private Date useDateTo;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getUseDateTo() {
        return useDateTo;
    }

    public void setUseDateTo(Date useDateTo) {
        this.useDateTo = useDateTo;
    }

    public Date getUseDateFrom() {
        return useDateFrom;
    }

    public void setUseDateFrom(Date useDateFrom) {
        this.useDateFrom = useDateFrom;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
