package com.ss.mapper;



import java.util.List;

import com.ss.pojo.trip.BusTicketEntity;
import com.ss.pojo.trip.CharteredEntity;
import com.ss.pojo.trip.Schedule;
import com.ss.pojo.trip.TicketEntity;

public interface ReserveDao {

    void insertAllTicket(List<TicketEntity> tickets);
    void insertAllBusTicket(List<BusTicketEntity> busTicketEntities);
    void insertAllChartered(List<CharteredEntity> charteredEntities);
    void insertAllSchedule(List<Schedule> schedules);

    void deleteAllTicket(int tripId);
    void deleteAllBusTicket(int tripId);
    void deleteAllChartered(int tripId);
    void deleteAllSchedule(int tripId);


    List<TicketEntity> getTicketByTripId(int tripId);
    List<BusTicketEntity> getBusTicketByTripId(int tripId);
    List<CharteredEntity> getCharteredByTripId(int tripId);
    List<Schedule> getScheduleByTripId(int tripId);
}
