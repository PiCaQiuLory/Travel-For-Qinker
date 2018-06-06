package com.ss.pojo.trip;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class CharteredEntity implements Serializable{
    private int id;
    private String type;//接车/送车类型
    private String pname;//客人姓名
    private String planenumber;//航班号
    private String ptype;//人员构成
    private Date date;//行程日期，接机日期
    private Date pdate;//出发时间，送机时间
    private String bagnumber;//行李数
    private String tohotel;//目的地酒店
    private String card;//举牌接机
    private BigDecimal pmoney;//客人报价
    private BigDecimal truemoney;//真实金额
    private BigDecimal profit;//利润
    private String carplace;//上车地点
    private String caretype;//车型
    private String stopplace;//途中停留城市（如市内包车可不填
    private String endplace;//行程结束城市/目的地
    private Date enddate;//结束时间
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPlanenumber() {
        return planenumber;
    }

    public void setPlanenumber(String planenumber) {
        this.planenumber = planenumber;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public Date getDate() {
        return date;
    }
   // @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPdate() {
        return pdate;
    }
   // @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public String getBagnumber() {
        return bagnumber;
    }

    public void setBagnumber(String bagnumber) {
        this.bagnumber = bagnumber;
    }

    public String getTohotel() {
        return tohotel;
    }

    public void setTohotel(String tohotel) {
        this.tohotel = tohotel;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public BigDecimal getPmoney() {
        return pmoney;
    }

    public void setPmoney(BigDecimal pmoney) {
        this.pmoney = pmoney;
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

    public String getCarplace() {
        return carplace;
    }

    public void setCarplace(String carplace) {
        this.carplace = carplace;
    }

    public String getCaretype() {
        return caretype;
    }

    public void setCaretype(String caretype) {
        this.caretype = caretype;
    }

    public String getStopplace() {
        return stopplace;
    }

    public void setStopplace(String stopplace) {
        this.stopplace = stopplace;
    }

    public String getEndplace() {
        return endplace;
    }

    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }
    
    

    public int getTrip_item_id() {
		return trip_item_id;
	}

	public void setTrip_item_id(int trip_item_id) {
		this.trip_item_id = trip_item_id;
	}

	public Date getEnddate() {
        return enddate;
    }
   // @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharteredEntity that = (CharteredEntity) o;
        return id == that.id &&
                Objects.equals(type, that.type) &&
                Objects.equals(pname, that.pname) &&
                Objects.equals(planenumber, that.planenumber) &&
                Objects.equals(ptype, that.ptype) &&
                Objects.equals(date, that.date) &&
                Objects.equals(pdate, that.pdate) &&
                Objects.equals(bagnumber, that.bagnumber) &&
                Objects.equals(tohotel, that.tohotel) &&
                Objects.equals(card, that.card) &&
                Objects.equals(pmoney, that.pmoney) &&
                Objects.equals(truemoney, that.truemoney) &&
                Objects.equals(profit, that.profit) &&
                Objects.equals(carplace, that.carplace) &&
                Objects.equals(caretype, that.caretype) &&
                Objects.equals(stopplace, that.stopplace) &&
                Objects.equals(endplace, that.endplace) &&
                Objects.equals(enddate, that.enddate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, pname, planenumber, ptype, date, pdate, bagnumber, tohotel, card, pmoney, truemoney, profit, carplace, caretype, stopplace, endplace, enddate);
    }
}
