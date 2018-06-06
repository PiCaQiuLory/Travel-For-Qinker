package com.ss.service;

import com.ss.pojo.trip.ReserveVo;
import com.ss.pojo.trip.TravelResult;

public interface ReserveService {

    TravelResult saveReserve(ReserveVo reserveVo) throws RuntimeException;

    TravelResult updateReserve(ReserveVo reserveVo, int tripId) throws RuntimeException;

    void deleteReserve(int tripId) throws RuntimeException;

    ReserveVo getReserve(int trip_id);
}
