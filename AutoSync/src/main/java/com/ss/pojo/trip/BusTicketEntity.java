package com.ss.pojo.trip;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class BusTicketEntity implements Serializable{
    private int id;
    private String from;
    private String towhere;
    private String pname;
    private String ptype;
    private String psection;
    private String getplace;
    private Date getdate;
    private BigDecimal pmoney;
    private String success;
    private BigDecimal truemoney;
    private BigDecimal profit;
    private int trip_id;
    private int trip_item_id;

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTowhere() {
        return towhere;
    }

    public void setTowhere(String towhere) {
        this.towhere = towhere;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPsection() {
        return psection;
    }

    public void setPsection(String psection) {
        this.psection = psection;
    }

    public String getGetplace() {
        return getplace;
    }

    public void setGetplace(String getplace) {
        this.getplace = getplace;
    }

    public Date getGetdate() {
        return getdate;
    }

    //@JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setGetdate(Date getdate) {
        this.getdate = getdate;
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
    
    

    public int getTrip_item_id() {
		return trip_item_id;
	}

	public void setTrip_item_id(int trip_item_id) {
		this.trip_item_id = trip_item_id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusTicketEntity that = (BusTicketEntity) o;
        return id == that.id &&
                Objects.equals(from, that.from) &&
                Objects.equals(towhere, that.towhere) &&
                Objects.equals(pname, that.pname) &&
                Objects.equals(ptype, that.ptype) &&
                Objects.equals(psection, that.psection) &&
                Objects.equals(getplace, that.getplace) &&
                Objects.equals(getdate, that.getdate) &&
                Objects.equals(pmoney, that.pmoney) &&
                Objects.equals(success, that.success) &&
                Objects.equals(truemoney, that.truemoney) &&
                Objects.equals(profit, that.profit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, from, towhere, pname, ptype, psection, getplace, getdate, pmoney, success, truemoney, profit);
    }
}
