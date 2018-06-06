package com.ss.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.pojo.ModelResult;
import com.ss.pojo.User;
import com.ss.pojo.trip.*;
import com.ss.service.IUserService;
import com.ss.service.ReserveService;
import com.ss.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: TripController
 * @Description: 行程（出团书）控制层
 * @author: lory
 * @date: 2018年3月12日 下午1:46:33
 * 
 * @Copyright: 2018 http://www.zmfne.com/ Inc. All rights reserved.
 *             注意：本内容仅限于坤刻网络科技技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
public class TripController {

	@Autowired
	private TripService tripService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ReserveService reserveService;

	/**
	 * 
	 * @Title: queryTrip @Description: 根据当前用户登录信息查询用户拥有的出团书 @param: @param
	 *         request @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/query/{pageNum}")
	@ResponseBody
	public TravelResult queryTrip(HttpServletRequest request, @PathVariable Integer pageNum) {
		User currUser = (User) request.getSession().getAttribute("user");
		if (currUser == null) {
			return TravelResult.buildError(1, "请先登录");
		}
		if (pageNum == null) {
			pageNum = 0;
		}
		PageHelper.startPage(pageNum, 10);
		List<Trip> tripList = new ArrayList<>();
		switch (currUser.getRole_id()) {
		case 1:
			tripList = tripService.queryByUser(currUser.getId());
			break;
		case 2:
			tripList = tripService.queryByDepartment(currUser.getDepartment_id());
		case 3:
			tripList = tripService.queryAllTrip();
		default:
			break;
		}
		PageInfo<Trip> info = new PageInfo<>(tripList);
		return TravelResult.buildOk(info);
	}

	/**
	 * @throws UnsupportedEncodingException
	 * 
	 * @Title: save @Description: 保存行程单方法 ，事务控制 @param: @param
	 *         tripVo @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/trip/save")
	@ResponseBody
	@Transactional
	public TravelResult save(@RequestBody Trip trip, HttpServletRequest request) throws RuntimeException {
		User currUser = (User) request.getSession().getAttribute("user");
		if (currUser == null || trip == null || trip.getTripItems() == null || trip.getTripItems().size() == 0) {
			return TravelResult.buildError(1, "保存失败,请先登录");
		}
		try {
			uploadTrip(trip, currUser);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("保存成功");
	}

	/**
	 * 石墨行程上传
	 * @param trip
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws RuntimeException
	 */
	@ResponseBody
	@RequestMapping("/upload/trip")
	@Transactional
	public TravelResult uploadFromShiMo(@RequestBody Trip trip, @RequestParam int userId, HttpServletRequest request)
			throws Exception,RuntimeException {
		User user = userService.getUserById(userId);
		if (user == null || trip == null) {
			return TravelResult.buildError(1, "参数错误");
		}
		try {
			if (trip.getItemNumber() != null && trip.getItemNumber() != "") {
				Trip tripExist = tripService.getTripByItemNumber(trip.getItemNumber());
				if (tripExist != null) {
					trip.setId(tripExist.getId());
					trip.setUser_id(userId);
					updateTripMethod(trip, request);
				}else {
					uploadTrip(trip, user);
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("上传成功");

	}



	/**
	 * 
	 * @Title: getTripById @Description: 根据trip的id拿取明细信息 @param: @param
	 *         id @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/getTripById")
	@ResponseBody
	public TravelResult getTripById(@RequestParam String id) {
		if (id == null || id.length() == 0) {
			return TravelResult.buildError(1, "参数错误");
		}
		Trip trip = tripService.getTripByItemNumber(id);
		if(trip!=null) {
			List<Plane> plane = tripService.getPlaneByTripId(trip.getId());
			for (Plane p : plane) {
				p.setTo_date_str(dateToString(p.getTo_date()));
				p.setLand_date_str(dateToString(p.getLand_date()));
				p.setBack_date_str(dateToString(p.getBack_date()));
			}
			trip.setPlane(plane);
			trip.setTravelDateStr(dateToString(trip.getTravel_date()));
			trip.setEndDateStr(dateToString(trip.getEnd_date()));
			List<TripItemVo> tripItems = tripService.getTripItemById(trip.getId());
			if (tripItems != null && tripItems.size() > 0) {
				for (TripItemVo item : tripItems) {
					List<TripItemDescription> descs = tripService.getTripItemDescById(item.getId());
					item.setItemDescriptions(descs);
				}
			}
			trip.setTripItems(tripItems);
			List<Trip> tripList = new ArrayList<>();
			tripList.add(trip);
			return TravelResult.buildOk(tripList);
		}else {
			return TravelResult.buildError(1,"无内容");
		}

	}
	@RequestMapping("/getTripByIdWeb")
	@ResponseBody
	public TravelResult getTripByIdWeb(@RequestParam String id) {
		return tripService.getTripByIdWeb(id);
	}

	/**
	 *
	 * @Title: getTripById @Description: 根据trip的id拿取明细信息 @param: @param
	 *         id @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/getTrip")
	@ResponseBody
	public TravelResult getTrip(@RequestParam Integer id) {
		if (id == null || id == 0) {
			return TravelResult.buildError(1, "参数错误");
		}
		Trip trip = tripService.getTripById(id);
		if(trip!=null) {
			List<Plane> plane = tripService.getPlaneByTripId(id);
			for (Plane p : plane) {
				p.setTo_date_str(dateToString(p.getTo_date()));
				p.setLand_date_str(dateToString(p.getLand_date()));
				p.setBack_date_str(dateToString(p.getBack_date()));
			}
			trip.setPlane(plane);
			trip.setTravelDateStr(dateToString(trip.getTravel_date()));
			trip.setEndDateStr(dateToString(trip.getEnd_date()));
			List<TripItemVo> tripItems = tripService.getTripItemById(id);
			if (tripItems != null && tripItems.size() > 0) {
				for (TripItemVo item : tripItems) {
					List<TripItemDescription> descs = tripService.getTripItemDescById(item.getId());
					item.setItemDescriptions(descs);
				}
			}
			trip.setTripItems(tripItems);
			List<Trip> tripList = new ArrayList<>();
			tripList.add(trip);
			return TravelResult.buildOk(tripList);
		}else {
			return TravelResult.buildError(1,"无内容");
		}

	}


	/**
	 * 
	 * @throws ParseException
	 * @Title: updateTrip @Description: 对应行程单修改保存功能 @param: @param
	 *         trip @param: @param request @param: @return @return:
	 *         TravelResult @throws
	 */
	@RequestMapping("/trip/update")
	@ResponseBody
	@Transactional
	public TravelResult updateTrip(@RequestBody Trip trip, HttpServletRequest request)
			throws  Exception {
		//TravelResult res = updateTripMethod(trip, request);
		
		return tripService.transUpdateNewTrip(trip);
	}



	/**
	 * 仅仅修改最外层的行程方法
	 * 
	 * @param trip
	 * @param request
	 * @return
	 */
	@RequestMapping("/trip/update/on")
	@ResponseBody
	@Transactional
	public TravelResult updateTripOn(@RequestBody Trip trip, HttpServletRequest request) throws RuntimeException {
		if (trip == null || trip.getId() == 0) {
			return TravelResult.buildError(1, "参数错误");
		}
		try {
			tripService.updateTrip(trip);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("修改成功");
	}

	/**
	 * 
	 * @Title: deleteTripDesc @Description: 行程单当中删除一天当中某个条目的方法 @param: @param
	 *         id @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/dele/desc")
	@ResponseBody
	public TravelResult deleteTripDesc(@RequestParam Integer id) {
		try {
			tripService.deleteTripItemDesc(id);
			return TravelResult.buildOk("删除成功");
		} catch (Exception e) {
			return TravelResult.buildError(1, "删除失败");
		}
	}

	/**
	 * 
	 * @Title: deleteTripItem @Description: 行程单当中删除某一天行程的方法 @param: @param
	 *         id @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/dele/item")
	@ResponseBody
	@Transactional
	public TravelResult deleteTripItem(@RequestParam Integer id) throws RuntimeException {
		try {
			tripService.deleteTripItemDescs(id);
			tripService.deleteTripItem(id);
			return TravelResult.buildOk("删除成功");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: deleteTrip @Description: 行程单最外层删除整个行程的方法 @param: @param
	 *         id @param: @return @return: TravelResult @throws
	 */
	@RequestMapping("/dele/trip")
	@ResponseBody
	@Transactional
	public TravelResult deleteTrip(@RequestParam int id) throws RuntimeException {
		try {
			Integer[] ids = tripService.selectTripItemByTripId(id);
			int[] idsi = new int[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idsi[i] = ids[i].intValue();
			}
			if (idsi.length > 0) {
				tripService.deleteTripDescByTrip(idsi);
			}
			tripService.deleteTripItems(id);
			tripService.deleteTrip(id);
			tripService.deletePlane(id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("删除成功");
	}


	/*@RequestMapping("/app/trip/phone")
	@ResponseBody
	public TravelResult getTripForAppNumber(String encrypted, String session_key, String iv) {

		encrypted = encrypted.trim().replace(" ", "+");
		session_key = session_key.trim().replace(" ", "+");
		iv = iv.trim().replace(" ", "+");
		String phoneNumber = AES.wxDecrypt(encrypted, session_key, iv);
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		PhoneNumber num = gson.fromJson(phoneNumber, PhoneNumber.class);
		List<Trip> trips = tripService.getTripForAppNumber(num.getPhoneNumber());
		if (trips != null && trips.size() > 0) {
			for (Trip trip : trips) {
				trip.setTravelDateStr(this.dateToString(trip.getTravel_date()));
				trip.setEndDateStr(this.dateToString(trip.getEnd_date()));
				List<TripItemVo> tripItems = tripService.getTripItemById(trip.getId());
				if (tripItems != null && tripItems.size() > 0) {
					for (TripItemVo item : tripItems) {
						List<TripItemDescription> descs = tripService.getTripItemDescById(item.getId());
						item.setItemDescriptions(descs);
					}
				}
				trip.setTripItems(tripItems);
			}
			return TravelResult.buildOk(trips);
		}
		return TravelResult.buildOk(trips);
	}*/

	/*@RequestMapping("/reservo/save")
	@ResponseBody
	@Transactional
	public TravelResult saveReserveVo(@RequestBody  ReserveVo reserveVo,@RequestParam Integer tripId) throws RuntimeException{
		if (reserveVo == null){
			return TravelResult.buildError(1,"参数错误");
		}
		reserveVo.setTripId(tripId);
		TravelResult result = reserveService.saveReserve(reserveVo);
		return result;
	}*/

	@RequestMapping("/reservo/update")
	@ResponseBody
	@Transactional
	public TravelResult updateReserveVo(@RequestBody  ReserveVo reserveVo,@RequestParam Integer tripId) throws RuntimeException{
		if (reserveVo == null){
			return TravelResult.buildError(1,"参数错误");
		}
		try {
			reserveVo.setTripId(tripId);
			TravelResult result = reserveService.updateReserve(reserveVo,tripId);
			return result;
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据tripId获取用户行程预定资源明细
	 * @param tripId
	 * @return
	 */
	@RequestMapping("/reverse/getByTripId")
	@ResponseBody
	public TravelResult getReserveById(@RequestParam Integer tripId){
		if(tripId !=null){
			ReserveVo reserveVo = reserveService.getReserve(tripId);
			return TravelResult.buildOk(reserveVo);
		}else {
			return null;
		}
	}

	/*@RequestMapping("/reservo/delete")
	@ResponseBody
	@Transactional
	public TravelResult updateReserveVo(@RequestParam Integer tripId) throws RuntimeException{
		if (tripId == null || tripId==0){
			return TravelResult.buildError(1,"参数错误");
		}
		try {
			reserveService.deleteReserve(tripId);
			return TravelResult.buildOk("删除成功");
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}*/

	/**
	 * 设置行程预定的TripId公共方法
	 * @param reserveVo
	 * @param tripId
	 */
	/*private void setTripId(ReserveVo reserveVo,int tripId){
		for (Schedule s: reserveVo.getScheduleList()){
			s.setTrip_id(tripId);
		}
		for (TicketEntity s: reserveVo.getTicketEntities()){
			s.setTrip_id(tripId);
		}
		for (BusTicketEntity s: reserveVo.getBusTicketEntities()){
			s.setTrip_id(tripId);
		}
		for (CharteredEntity s: reserveVo.getCharteredEntities()){
			s.setTrip_id(tripId);
		}
	}*/

	/**
	 * 保存行程公共方法
	 *
	 * @param trip
	 * @param currUser
	 * @throws RuntimeException
	 */
	private void uploadTrip(Trip trip, User currUser) throws RuntimeException {
		if (trip.getPlane() != null && trip.getPlane().size() > 0) {
			for (Plane p : trip.getPlane()) {
				if (p.getTo_start_end_date() != null && p.getTo_start_end_date().size() > 0) {
					p.setTo_start_end(dateToString(p.getTo_start_end_date().get(0)) + "-"
							+ dateToString(p.getTo_start_end_date().get(1)));
				}
				if (p.getBack_start_end_date() != null && p.getBack_start_end_date().size() > 0) {
					p.setBack_start_end(dateToString(p.getBack_start_end_date().get(0)) + "-"
							+ dateToString(p.getBack_start_end_date().get(1)));
				}
				if (p.getLand_start_end_date() != null && p.getLand_start_end_date().size() > 0) {
					p.setLand_start_end(dateToString(p.getLand_start_end_date().get(0)) + "-"
							+ dateToString(p.getLand_start_end_date().get(1)));
				}
			}
		}
		trip.setUser_id(currUser.getId());
		trip.setCreate_date(new Date());
		trip.setDepartment_id(currUser.getDepartment_id());
		tripService.saveTrip(trip);
		if (trip.getPlane() != null && trip.getPlane().size() > 0) {
			for (Plane plane : trip.getPlane()) {
				plane.setTrip_id(trip.getId());
			}
			tripService.insertPlane(trip.getPlane());
		}
		saveTripItems(trip);
	}

	/**
	 * 保存行程明细公共方法
	 * @param trip
	 */
	private void saveTripItems(Trip trip){
		for (TripItemVo itemVo : trip.getTripItems()) {
			TripItem item = new TripItem();
			item.setTrip_id(trip.getId());
			item.setDay(itemVo.getDay());
			item.setTitle(itemVo.getTitle());
			tripService.saveTripItems(item);
			for (TripItemDescription des : itemVo.getItemDescriptions()) {
				des.setTrip_item_id(item.getId());
			}
			tripService.saveTripItemDescription(itemVo.getItemDescriptions());
		}
	}


	/**
	 * 行程更新公共方法
	 *
	 * @param trip
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private TravelResult updateTripMethod(Trip trip, HttpServletRequest request) throws Exception {
		if (trip == null || trip.getTripItems() == null || trip.getTripItems().size() == 0) {
			return TravelResult.buildError(1, "保存失败");
		}
		int lastTripId = trip.getId();
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
			Integer[] ids = tripService.selectTripItemByTripId(trip.getId());
			int[] idsi = new int[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idsi[i] = ids[i].intValue();
			}
			tripService.deleteTripDescByTrip(idsi);
			tripService.deleteTripItems(trip.getId());
			tripService.deleteTrip(trip.getId());
			tripService.deletePlane(trip.getId());
			User user = userService.getUserById(trip.getUser_id());
			trip.setCreate_date(new Date());
			trip.setDepartment_id(user.getDepartment_id());
			tripService.saveTrip(trip);
			tripService.updateTicketTripId(trip.getId(), lastTripId);
			tripService.updateBusTicketTripId(trip.getId(), lastTripId);
			tripService.updateCharteredTripId(trip.getId(), lastTripId);
			tripService.updateScheduleTripId(trip.getId(), lastTripId);
			if (trip.getPlane() != null && trip.getPlane().size() > 0) {
				for (Plane p : trip.getPlane()) {
					p.setTrip_id(trip.getId());
				}
				tripService.insertPlane(trip.getPlane());
			}
			if (trip.getTripItems() != null && trip.getTripItems().size() > 0) {
				saveTripItems(trip);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return TravelResult.buildOk("保存成功");
	}

	/**
	 * 日期转换
	 * @param date
	 * @return
	 */
	private String dateToString(Date date) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String res = dateFormat.format(date);
			return res;
		} else {
			return "";
		}
	}

	/**
	 * 日期转换
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	private Date stringToDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date res = format.parse(s);
		return res;
	}
	
	@RequestMapping("/getUrlByItemNumber")
	@ResponseBody
	public ModelResult getUrlByItemNumber(@RequestParam(required=true)String itemNumber) {
		String url = tripService.getUrlByItemNumber(itemNumber);
		if(url != null && url != "") {
			return ModelResult.buildOk(url);
		}
		return ModelResult.buildError(1, "未有链接地址");
	}
}
