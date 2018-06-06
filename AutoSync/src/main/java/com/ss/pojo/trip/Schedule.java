package com.ss.pojo.trip;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Schedule implements Serializable {

    private int id;
    private String name;
    private String scheduledproject;
    private Date scheduledate;
    private BigDecimal pmoney;
    private String success;
    private BigDecimal truemoney;
    private BigDecimal profit;
    private int trip_id;
    private int trip_item_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduledproject() {
        return scheduledproject;
    }

    public void setScheduledproject(String scheduledproject) {
        this.scheduledproject = scheduledproject;
    }
    //@JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public Date getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(Date scheduledate) {
        this.scheduledate = scheduledate;
    }

    public BigDecimal getPmoney() {
        return pmoney;
    }

    public void setPmoney(BigDecimal pmoney) {
        this.pmoney = pmoney;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public BigDecimal getTruemoney() {
        return truemoney;
    }

    public void setTruemoney(BigDecimal truemoney) {
        this.truemoney = truemoney;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

	public int getTrip_item_id() {
		return trip_item_id;
	}

	public void setTrip_item_id(int trip_item_id) {
		this.trip_item_id = trip_item_id;
	}
    
    
}
