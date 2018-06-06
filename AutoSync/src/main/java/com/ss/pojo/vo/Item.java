package com.ss.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by stella on 2017/9/12.
 */
public class Item {
    private String departure;
    private String destination;
    private ItemCustomer itemCustomer;
    private String itemNumber;
    private String need;
    private String orderTimer;
    private String playDays;
    private String startDay;
    private String userNum;
    private Man man;
    private String url;

    private List<RemarkVo> remark;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RemarkVo> getRemark() {
        return remark;
    }

    public void setRemark(List<RemarkVo> remark) {
        this.remark = remark;
    }

    public String getOrderTimer() {
        return orderTimer;
    }

    public void setOrderTimer(String orderTimer) {
        this.orderTimer = orderTimer;
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ItemCustomer getItemCustomer() {
        return itemCustomer;
    }

    public void setItemCustomer(ItemCustomer itemCustomer) {
        this.itemCustomer = itemCustomer;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getPlayDays() {
        return playDays;
    }

    public void setPlayDays(String playDays) {
        this.playDays = playDays;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}