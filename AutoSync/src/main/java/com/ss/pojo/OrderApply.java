package com.ss.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderApply implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1001;

	private Long id;

    private Date applyTime;

    private BigDecimal total;

    private Boolean status;

    private Date payTime;

    private Boolean payWay;

    private String payNumber;

    private Integer itemId;

    private Integer userId;

    private Integer auditorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Boolean getPayWay() {
        return payWay;
    }

    public void setPayWay(Boolean payWay) {
        this.payWay = payWay;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber == null ? null : payNumber.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }
}