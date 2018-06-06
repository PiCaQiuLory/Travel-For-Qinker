package com.ss.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ss.pojo.ModelResult;
import com.ss.pojo.User;
import com.ss.pojo.trip.Plane;
import com.ss.pojo.trip.TravelResult;
import com.ss.pojo.trip.Trip;
import com.ss.pojo.trip.TripItem;
import com.ss.pojo.trip.TripItemDescription;
import com.ss.pojo.trip.TripItemVo;



public interface TripService {

	public List<Trip> queryByUser(int userId);

	public List<Trip> queryByDepartment(int departmentId);

	public List<Trip> queryAllTrip();

	public void saveTrip(Trip trip);

	public void saveTripItems(TripItem tripItems);

	public void saveTripItemDescription(List<TripItemDescription> tripItemDescriptions);

	public List<TripItemVo> getTripItemById(int id);

	public List<TripItemDescription> getTripItemDescById(int id);

	public void deleteTripItemDesc(int id);

	public void deleteTripItemDescs(int id);

	public void deleteTripItem(int id);

	public Integer[] selectTripItemByTripId(int id);

	public void deleteTripDescByTrip(int[] ids);

	public void deleteTripItems(int id);

	public void deleteTrip(int id);

	public void updateTrip(Trip trip);

	public void updateTripItem(TripItemVo tripItemvo);

	public void updateTripItemDesc(TripItemDescription desc);

	public List<Trip> getTripForApp(String wechat);

	public List<Trip> getTripForAppNumber(String phoneNumber);

	public List<Plane> getPlaneByTripId(int trip_id);

	public Trip getTripById(Integer id);

	public void insertPlane(List<Plane> plane);

	public void updatePlane(Plane plane);

	public List<Plane> getPlaneTo(int trip_id);

	public List<Plane> getPlaneLand(int trip_id);

	public List<Plane> getPlaneBack(int trip_id);
	
	public void deletePlane(int trip_id);
	
	public Trip getTripByItemNumber(String itemNumber);
	
	void updateTicketTripId(@Param("lastId") int lastId, @Param("id") int id);
	void updateBusTicketTripId(@Param("lastId") int lastId, @Param("id") int id);
	void updateCharteredTripId(@Param("lastId") int lastId, @Param("id") int id);
	void updateScheduleTripId(@Param("lastId") int lastId, @Param("id") int id);
	String getUrlByItemNumber(String itemNumber);
	
	TravelResult transUpdateNewTrip(Trip trip);
	
	TravelResult getTripByIdWeb(String id);
	
	TravelResult updateTripItemNew(Trip trip);
}
