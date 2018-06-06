package com.ss.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by stella on 2017/9/24.
 */
public class OrderItem {
    private long id;
    private String category;
    private String type;
    private long providerId;
    private Provider provider;
    private String fapiao;
    private String paystatus;
    private Date useDate;
    private Double cost;
    private String orderNo;
    private long orderId;
    private String itemNumber;
    private String status;
    private String payaccount;
    private List<Attachment> attachmentList;
    private Date fapiaoDate;
    private Date payDate;
    private Date payDateFrom;
    private Date payDateTo;
    private Double fapiaoCost;

    public Double getFapiaoCost() {
        return fapiaoCost;
    }

    public void setFapiaoCost(Double fapiaoCost) {
        this.fapiaoCost = fapiaoCost;
    }

    public Date getPayDateFrom() {
        return payDateFrom;
    }

    public void setPayDateFrom(Date payDateFrom) {
        this.payDateFrom = payDateFrom;
    }

    public Date getPayDateTo() {
        return payDateTo;
    }

    public void setPayDateTo(Date payDateTo) {
        this.payDateTo = payDateTo;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getFapiaoDate() {
        return fapiaoDate;
    }

    public void setFapiaoDate(Date fapiaoDate) {
        this.fapiaoDate = fapiaoDate;
    }

    public String getPayaccount() {
        return payaccount;
    }

    public void setPayaccount(String payaccount) {
        this.payaccount = payaccount;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
