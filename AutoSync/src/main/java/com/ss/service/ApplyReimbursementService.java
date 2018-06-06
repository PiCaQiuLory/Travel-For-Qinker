package com.ss.service;

import java.util.List;

import com.ss.pojo.POIDatePojo;
import com.ss.pojo.vo.ApplyReimbursement;

public interface ApplyReimbursementService {

public List<ApplyReimbursement> getAllForUser(int id);
	
	public List<ApplyReimbursement> getAllApplyWithAuditor(ApplyReimbursement reimbursement);
	
	
	public void addOneEvent(ApplyReimbursement reimbursement);
	
	public void applyAndJudy(ApplyReimbursement reimbursement);
		
	public int getNumber(); 
	
	public String getNickName(int id);
	
	List<ApplyReimbursement> getByGroup(ApplyReimbursement reimbursement);
	
	List<ApplyReimbursement> searchByUser(Integer userId);
	
	List<ApplyReimbursement> searchByUserJudy(Integer userId);
	
	void judyList(List<ApplyReimbursement> applyReimbursements);
	
	void judyListForAuditor(List<ApplyReimbursement> applyReimbursements);
	
	List<ApplyReimbursement> selectByGroupWithNoJudy(ApplyReimbursement applyReimbursement);
	
	List<ApplyReimbursement> selectByGroupWithNoJudyUserItem(ApplyReimbursement applyReimbursement);
	
	List<ApplyReimbursement> selectByGroupWithNoJudyForExcel(POIDatePojo datePojo);
	
	List<ApplyReimbursement> selectByGroupWithMonth(POIDatePojo datePojo);
}
