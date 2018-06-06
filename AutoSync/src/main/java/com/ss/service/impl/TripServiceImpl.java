package com.ss.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ss.mapper.IUserDao;
import com.ss.mapper.TripDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pojo.User;
import com.ss.pojo.trip.Consumer;
import com.ss.pojo.trip.Plane;
import com.ss.pojo.trip.TravelReservation;
import com.ss.pojo.trip.TravelResult;
import com.ss.pojo.trip.Trip;
import com.ss.pojo.trip.TripItem;
import com.ss.pojo.trip.TripItemDescription;
import com.ss.pojo.trip.TripItemVo;
import com.ss.service.TripService;



@Service
public class TripServiceImpl implements TripService {
	
	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<Trip> queryByUser(int userId) {
		return tripDao.queryByUser(userId);
	}

	@Override
	public List<Trip> queryByDepartment(int departmentId) {
		return tripDao.queryByDepartment(departmentId);
	}

	@Override
	public List<Trip> queryAllTrip() {
		return tripDao.queryAllTrip();
	}

	@Override
	public void saveTrip(Trip trip) {
		tripDao.saveTrip(trip);
	}

	@Override
	public void saveTripItems(TripItem tripItems) {
		tripDao.saveTripItems(tripItems);
	}

	@Override
	public void saveTripItemDescription(List<TripItemDescription> tripItemDescriptions) {
		tripDao.saveTripItemDescription(tripItemDescriptions);
	}

	@Override
	public List<TripItemVo> getTripItemById(int id) {
		return tripDao.getTripItemById(id);
	}

	@Override
	public List<TripItemDescription> getTripItemDescById(int id) {
		return tripDao.getTripItemDescById(id);
	}

	@Override
	public void deleteTripItemDesc(int id) {
		tripDao.deleteTripItemDesc(id);
	}

	@Override
	public void deleteTripItemDescs(int id) {
		tripDao.deleteTripItemDescs(id);
	}

	@Override
	public void deleteTripItem(int id) {
		tripDao.deleteTripItem(id);
	}

	@Override
	public Integer[] selectTripItemByTripId(int id) {
		return tripDao.selectTripItemByTripId(id);
	}

	@Override
	public void deleteTripDescByTrip(int[] ids) {
		tripDao.deleteTripDescByTrip(ids);
	}

	@Override
	public void deleteTripItems(int id) {
		tripDao.deleteTripItems(id);
	}

	@Override
	public void deleteTrip(int id) {
		tripDao.deleteTrip(id);
	}

	@Override
	public void updateTrip(Trip trip) {
		tripDao.updateTrip(trip);
	}

	@Override
	public void updateTripItem(TripItemVo tripItemvo) {
		tripDao.updateTripItem(tripItemvo);
	}

	@Override
	public void updateTripItemDesc(TripItemDescription desc) {
		tripDao.updateTripItemDesc(desc);
	}

	@Override
	public List<Trip> getTripForApp(String wechat) {
		return tripDao.getTripForApp(wechat);
	}

	@Override
	public List<Trip> getTripForAppNumber(String phoneNumber) {
		return tripDao.getTripForAppNumber(phoneNumber);
	}

	@Override
	public Trip getTripById(Integer id) {
		return tripDao.getTripById(id);
	}

	@Override
	public void insertPlane(List<Plane> plane) {
		tripDao.insertPlane(plane);
	}

	@Override
	public List<Plane> getPlaneByTripId(int trip_id) {
		return tripDao.getPlaneByTripId(trip_id);
	}

	@Override
	public void updatePlane(Plane plane) {
		tripDao.updatePlane(plane);
	}

	@Override
	public List<Plane> getPlaneTo(int trip_id) {
		return tripDao.getPlaneTo(trip_id);
	}

	@Override
	public List<Plane> getPlaneLand(int trip_id) {
		return tripDao.getPlaneLand(trip_id);
	}

	@Override
	public List<Plane> getPlaneBack(int trip_id) {
		return tripDao.getPlaneBack(trip_id);
	}

	@Override
	public void deletePlane(int trip_id) {
		tripDao.deletePlane(trip_id);
	}

	@Override
	public Trip getTripByItemNumber(String itemNumber) {
		return tripDao.getTripByItemNumber(itemNumber);
	}

	@Override
	public void updateTicketTripId(int lastId, int id) {
			tripDao.updateTicketTripId(lastId, id);
	}

	@Override
	public void updateBusTicketTripId(int lastId, int id) {
		tripDao.updateBusTicketTripId(lastId, id);
	}

	@Override
	public void updateCharteredTripId(int lastId, int id) {
		tripDao.updateCharteredTripId(lastId, id);
	}

	@Override
	public void updateScheduleTripId(int lastId, int id) {
		tripDao.updateScheduleTripId(lastId, id);
	}

	@Override
	public String getUrlByItemNumber(String itemNumber) {
		return tripDao.getUrlByItemNumber(itemNumber);
	}

	@Override
	public TravelResult transUpdateNewTrip(Trip trip) {
		if (trip == null || trip.getTripItems() == null || trip.getTripItems().size() == 0) {
			return TravelResult.buildError(1, "保存失败");
		}
		try {
			if (trip.getPlane() != null && trip.getPlane().size() > 0) {
				for (Plane p : trip.getPlane()) {
					if (p.getTo_date_str() != null && p.getTo_date_str() != "") {
						p.setTo_date(stringToDate(p.getTo_date_str()));
					}
					if (p.getLand_date_str() != null && p.getLand_date_str() != "") {
						p.setLand_date(stringToDate(p.getLand_date_str()));
					}
					if (p.getBack_date_str() != null && p.getBack_date_str() != "") {
						p.setBack_date(stringToDate(p.getBack_date_str()));
					}
				}
			}
		}catch (Exception e) {
			return TravelResult.buildError(1, "日期格式不正确");
		}
		try {
			Integer[] ids = tripDao.selectTripItemByTripId(trip.getId());
			int[] idsi = new int[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idsi[i] = ids[i].intValue();
			}
			tripDao.deleteTripDescByTrip(idsi);
			tripDao.deleteTravelReservation(idsi);
			tripDao.deleteTripItems(trip.getId());
			tripDao.deleteTrip(trip.getId());
			tripDao.deletePlane(trip.getId());
			tripDao.deleteConsumer(trip.getId());
			User user = userDao.selectByPrimaryKey(trip.getUser_id());
			trip.setCreate_date(new Date());
			trip.setDepartment_id(user.getDepartment_id());
			tripDao.saveTrip(trip);
			if(trip.getConsumer()!=null) {
				trip.getConsumer().setTrip_id(trip.getId());
				tripDao.saveConsumer(trip.getConsumer());
			}
			if (trip.getPlane() != null && trip.getPlane().size() > 0) {
				for (Plane p : trip.getPlane()) {
					p.setTrip_id(trip.getId());
				}
				tripDao.insertPlane(trip.getPlane());
			}
			if (trip.getTripItems() != null && trip.getTripItems().size() > 0) {
				saveTripItems(trip);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("保存成功");
	}

	private Date stringToDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date res = format.parse(s);
		return res;
	}
	
	private void saveTripItems(Trip trip){
		for (TripItemVo itemVo : trip.getTripItems()) {
			TripItem item = new TripItem();
			item.setTrip_id(trip.getId());
			item.setDay(itemVo.getDay());
			item.setTitle(itemVo.getTitle());
			tripDao.saveTripItems(item);
			if(itemVo.getTravelReservation()!=null) {
				itemVo.getTravelReservation().setTrip_item_id(item.getId());
				tripDao.saveTravelReservation(itemVo.getTravelReservation());
			}
			for (TripItemDescription des : itemVo.getItemDescriptions()) {
				des.setTrip_item_id(item.getId());
			}
			tripDao.saveTripItemDescription(itemVo.getItemDescriptions());
		}
	}

	@Override
	public TravelResult getTripByIdWeb(String id) {
		if (id == null || id.length() == 0) {
			return TravelResult.buildError(1, "参数错误");
		}
		Trip trip = tripDao.getTripByItemNumber(id);
		if(trip!=null) {
			List<Plane> plane = tripDao.getPlaneByTripId(trip.getId());
			for (Plane p : plane) {
				p.setTo_date_str(dateToString(p.getTo_date()));
				p.setLand_date_str(dateToString(p.getLand_date()));
				p.setBack_date_str(dateToString(p.getBack_date()));
			}
			trip.setPlane(plane);
			trip.setTravelDateStr(dateToString(trip.getTravel_date()));
			trip.setEndDateStr(dateToString(trip.getEnd_date()));
			List<TripItemVo> tripItems = tripDao.getTripItemById(trip.getId());
			Consumer consumer = tripDao.getConsumer(trip.getId());
			if(consumer==null) {
				consumer = new Consumer();
				consumer.setTrip_id(trip.getId());
				consumer.setContent("");
			}
			trip.setConsumer(consumer);
			if (tripItems != null && tripItems.size() > 0) {
				for (TripItemVo item : tripItems) {
					List<TripItemDescription> descs = tripDao.getTripItemDescById(item.getId());
					TravelReservation travelReservation = tripDao.getTravelReservation(item.getId());
					if(travelReservation==null) {
						travelReservation = new TravelReservation();
						travelReservation.setDescription("");
						travelReservation.setTrip_item_id(item.getId());
					}
					item.setTravelReservation(travelReservation);
					item.setItemDescriptions(descs);
				}
			}
			trip.setTripItems(tripItems);
			List<Trip> tripList = new ArrayList<>();
			tripList.add(trip);
			return TravelResult.buildOk(tripList);
		}else {
			return TravelResult.buildError(1,null);
		}
	}
	
	private String dateToString(Date date) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String res = dateFormat.format(date);
			return res;
		} else {
			return "";
		}
	}

	@Override
	public TravelResult updateTripItemNew(Trip trip) {
		
		return null;
	}
}
