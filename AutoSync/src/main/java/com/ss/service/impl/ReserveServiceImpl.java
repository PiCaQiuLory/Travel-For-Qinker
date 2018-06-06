package com.ss.service.impl;


import com.ss.mapper.ReserveDao;
import com.ss.pojo.trip.*;
import com.ss.service.ReserveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Resource
    private ReserveDao reserveDao;

    @Override
    public TravelResult saveReserve(ReserveVo reserveVo) throws RuntimeException{
            save(reserveVo);
            return TravelResult.buildOk("保存成功");
    }

    @Override
    public TravelResult updateReserve(ReserveVo reserveVo,int tripId) throws RuntimeException{
        reserveDao.deleteAllBusTicket(tripId);
        reserveDao.deleteAllChartered(tripId);
        reserveDao.deleteAllSchedule(tripId);
        reserveDao.deleteAllTicket(tripId);
        save(reserveVo);
        return TravelResult.buildOk("更新成功");
    }

    @Override
    public void deleteReserve(int tripId) throws RuntimeException {
        reserveDao.deleteAllBusTicket(tripId);
        reserveDao.deleteAllChartered(tripId);
        reserveDao.deleteAllSchedule(tripId);
        reserveDao.deleteAllTicket(tripId);
    }

    @Override
    public ReserveVo getReserve(int trip_id) {
        ReserveVo reserveVo = new ReserveVo();
        List<TicketEntity> ticketEntities = reserveDao.getTicketByTripId(trip_id);
        List<BusTicketEntity> busTicketEntities = reserveDao.getBusTicketByTripId(trip_id);
        List<Schedule> schedules = reserveDao.getScheduleByTripId(trip_id);
        List<CharteredEntity> charteredEntities = reserveDao.getCharteredByTripId(trip_id);
        reserveVo.setBusTicketEntities(busTicketEntities);
        reserveVo.setCharteredEntities(charteredEntities);
        reserveVo.setScheduleList(schedules);
        reserveVo.setTicketEntities(ticketEntities);
        return reserveVo;
    }

    private void save(ReserveVo reserveVo){
        if (reserveVo.getBusTicketEntities() != null && reserveVo.getBusTicketEntities().size() > 0) {
            for (BusTicketEntity s: reserveVo.getBusTicketEntities()){
                s.setTrip_id(reserveVo.getTripId());
            }
            reserveDao.insertAllBusTicket(reserveVo.getBusTicketEntities());
        }
        if (reserveVo.getCharteredEntities() != null && reserveVo.getCharteredEntities().size() > 0) {
            for (CharteredEntity s: reserveVo.getCharteredEntities()){
                s.setTrip_id(reserveVo.getTripId());
            }
            reserveDao.insertAllChartered(reserveVo.getCharteredEntities());
        }
        if (reserveVo.getTicketEntities() != null && reserveVo.getTicketEntities().size() > 0) {
            for (TicketEntity s: reserveVo.getTicketEntities()){
                s.setTrip_id(reserveVo.getTripId());
            }
            reserveDao.insertAllTicket(reserveVo.getTicketEntities());
        }
        if (reserveVo.getScheduleList() != null && reserveVo.getScheduleList().size() > 0) {
            for (Schedule s: reserveVo.getScheduleList()){
                s.setTrip_id(reserveVo.getTripId());
            }
            reserveDao.insertAllSchedule(reserveVo.getScheduleList());
        }
    }

}
