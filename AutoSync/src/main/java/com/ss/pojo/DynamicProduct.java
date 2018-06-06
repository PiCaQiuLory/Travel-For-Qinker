package com.ss.pojo;

import java.util.Date;

/**
 * Created by stella on 2017/8/12.
 */
public class DynamicProduct {
    private int id;
    private int sourceId;
    private int no;
    private int breakfast;
    private String room;
    private String hotel;
    private String updatedBy;
    private Date updatedDt;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
