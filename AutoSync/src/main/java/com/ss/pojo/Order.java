package com.ss.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stella on 2017/9/12.
 */
public class Order implements Serializable{
    private Integer id;
    private String departure;
    private String destination;
    private String customerName;
    private String customerWechat;
    private String customerNumber;
    private String itemNumber;
    private String need;
    private String playDays;
    private Date startDate;
    private String userNum;
    private String manArea;
    private String manName;
    private Integer manNameId;
    private String manFlat;
    private String manAccount;
    private String sonAccount;
    private Date createDate;
    private Date outDate;
    private String status;
    private String comment;
    private String experience;
    private Double income;
    private Date orderTimer;
    private Double revenue;
    private Double cost;
    private String clientStatus;
    private Double incomeNew;
    private String url;
    private Double actualIncome;//not stored
    private Date backDate; //out date + playDays

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public Double getActualIncome() {
        return actualIncome;
    }

    public void setActualIncome(Double actualIncome) {
        this.actualIncome = actualIncome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getIncomeNew() {
        return incomeNew;
    }

    public void setIncomeNew(Double incomeNew) {
        this.incomeNew = incomeNew;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Date getOrderTimer() {
        return orderTimer;
    }

    public void setOrderTimer(Date orderTimer) {
        this.orderTimer = orderTimer;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManAccount() {
        return manAccount;
    }

    public void setManAccount(String manAccount) {
        this.manAccount = manAccount;
    }

    public String getSonAccount() {
        return sonAccount;
    }

    public void setSonAccount(String sonAccount) {
        this.sonAccount = sonAccount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManNameId() {
        return manNameId;
    }

    public void setManNameId(Integer manNameId) {
        this.manNameId = manNameId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerWechat() {
        return customerWechat;
    }

    public void setCustomerWechat(String customerWechat) {
        this.customerWechat = customerWechat;
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

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getManArea() {
        return manArea;
    }

    public void setManArea(String manArea) {
        this.manArea = manArea;
    }

    public String getManFlat() {
        return manFlat;
    }

    public void setManFlat(String manFlat) {
        this.manFlat = manFlat;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
