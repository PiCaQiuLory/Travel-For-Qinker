package com.ss.pojo;

import java.util.Date;

/**
 * Created by stella on 2017/4/24.
 */
public class SyncCount {
    private int Id;
    private String target;
    private int count;
    private Date tDate;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date gettDate() {
        return tDate;
    }

    public void settDate(Date tDate) {
        this.tDate = tDate;
    }
}
