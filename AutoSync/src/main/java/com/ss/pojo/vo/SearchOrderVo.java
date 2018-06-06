package com.ss.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by stella on 2017/9/16.
 */
public class SearchOrderVo {
    private Integer id;
    private String type;
    private String status;
    private Date from;
    private Date to;
    private String role;
    private Integer userId;
    private String itemNumber;
    private String manFlat;
    private String destination;
    private Date outFrom;
    private Date outTo;
    private int pageFrom;
    private int pageTo;
    private Object filter;
    private String filterStr;
    private List<Object> sort;
    private String sortStr;

    public String getFilterStr() {
        return filterStr;
    }

    public void setFilterStr(String filterStr) {
        this.filterStr = filterStr;
    }

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public Object getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        this.filter = filter;
    }

    public List<Object> getSort() {
        return sort;
    }

    public void setSort(List<Object> sort) {
        this.sort = sort;
    }

    public int getPageTo() {
        return pageTo;
    }

    public void setPageTo(int pageTo) {
        this.pageTo = pageTo;
    }

    public int getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(int pageFrom) {
        this.pageFrom = pageFrom;
    }

    public Date getOutFrom() {
        return outFrom;
    }

    public void setOutFrom(Date outFrom) {
        this.outFrom = outFrom;
    }

    public Date getOutTo() {
        return outTo;
    }

    public void setOutTo(Date outTo) {
        this.outTo = outTo;
    }

    public String getManFlat() {
        return manFlat;
    }

    public void setManFlat(String manFlat) {
        this.manFlat = manFlat;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
