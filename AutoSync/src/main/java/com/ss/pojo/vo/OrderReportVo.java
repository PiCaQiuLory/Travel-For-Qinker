package com.ss.pojo.vo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stella on 2017/9/26.
 */
public class OrderReportVo {
    private AtomicInteger newOrder;
    private AtomicInteger endOrder;
    private AtomicInteger abortOrder;
    private AtomicInteger pendingTrackClient;
    private AtomicInteger abortClient;
    private double turnRate;
    private double allIncome;
    private String manName;

    public OrderReportVo() {
        this.newOrder = new AtomicInteger(0);
        this.endOrder = new AtomicInteger(0);
        this.abortOrder = new AtomicInteger(0);
        this.pendingTrackClient = new AtomicInteger(0);
        this.abortClient = new AtomicInteger(0);
        this.turnRate = 0;
        this.allIncome = 0;
    }

    public void setTurnRate(){
        if (this.newOrder.intValue()>0) {
            this.turnRate = (double) this.endOrder.intValue() / this.newOrder.intValue();
        }
    }

    public AtomicInteger getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(AtomicInteger newOrder) {
        this.newOrder = newOrder;
    }

    public AtomicInteger getEndOrder() {
        return endOrder;
    }

    public void setEndOrder(AtomicInteger endOrder) {
        this.endOrder = endOrder;
    }

    public AtomicInteger getAbortOrder() {
        return abortOrder;
    }

    public void setAbortOrder(AtomicInteger abortOrder) {
        this.abortOrder = abortOrder;
    }

    public AtomicInteger getPendingTrackClient() {
        return pendingTrackClient;
    }

    public void setPendingTrackClient(AtomicInteger pendingTrackClient) {
        this.pendingTrackClient = pendingTrackClient;
    }

    public AtomicInteger getAbortClient() {
        return abortClient;
    }

    public void setAbortClient(AtomicInteger abortClient) {
        this.abortClient = abortClient;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    public double getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(double allIncome) {
        this.allIncome = allIncome;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }
}
