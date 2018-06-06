package com.ss.pojo.trip;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class TicketEntity implements Serializable{
    private int id;
    private String name;
    private int pnumber;
    private String pname;
    private String ptype;
    private Date pdate;
    private BigDecimal pmoney;
    private String success;
    private BigDecimal truemoney;
    private BigDecimal profit;
    private String getway;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPnumber() {
        return pnumber;
    }

    public void setPnumber(int pnumber) {
        this.pnumber = pnumber;
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

    public Date getPdate() {
        return pdate;
    }

    //@JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setPdate(Date pdate) {
        this.pdate = pdate;
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

    public String getGetway() {
        return getway;
    }

    public void setGetway(String getway) {
        this.getway = getway;
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
        TicketEntity that = (TicketEntity) o;
        return id == that.id &&
                pnumber == that.pnumber &&
                Objects.equals(name, that.name) &&
                Objects.equals(pname, that.pname) &&
                Objects.equals(ptype, that.ptype) &&
                Objects.equals(pdate, that.pdate) &&
                Objects.equals(pmoney, that.pmoney) &&
                Objects.equals(success, that.success) &&
                Objects.equals(truemoney, that.truemoney) &&
                Objects.equals(profit, that.profit) &&
                Objects.equals(getway, that.getway);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, pnumber, pname, ptype, pdate, pmoney, success, truemoney, profit, getway);
    }
}
