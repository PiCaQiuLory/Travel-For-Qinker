package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.List;

public class ReserveVo implements Serializable {

    private int tripId;

    private List<BusTicketEntity> busTicketEntities;

    private List<TicketEntity> ticketEntities;

    private List<CharteredEntity> charteredEntities;

    private List<Schedule> scheduleList;

    public List<BusTicketEntity> getBusTicketEntities() {
        return busTicketEntities;
    }

    public void setBusTicketEntities(List<BusTicketEntity> busTicketEntities) {
        this.busTicketEntities = busTicketEntities;
    }

    public List<TicketEntity> getTicketEntities() {
        return ticketEntities;
    }

    public void setTicketEntities(List<TicketEntity> ticketEntities) {
        this.ticketEntities = ticketEntities;
    }

    public List<CharteredEntity> getCharteredEntities() {
        return charteredEntities;
    }

    public void setCharteredEntities(List<CharteredEntity> charteredEntities) {
        this.charteredEntities = charteredEntities;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
