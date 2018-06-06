package com.ss.service.impl;

import java.util.List;

import com.ss.mapper.ApplyReimbursementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.pojo.POIDatePojo;
import com.ss.pojo.vo.ApplyReimbursement;
import com.ss.service.ApplyReimbursementService;

@Service
public class ApplyReimbursementServiceImpl implements ApplyReimbursementService{

	@Autowired
	private ApplyReimbursementMapper mapper;
	
	@Override
	public List<ApplyReimbursement> getAllForUser(int id) {
		return mapper.getAllForUser(id);
	}

	@Override
	public List<ApplyReimbursement> getAllApplyWithAuditor(ApplyReimbursement applyReimbursement) {
		return mapper.getAllApplyWithAuditor(applyReimbursement);
	}


	@Override
	@Transactional
	public void addOneEvent(ApplyReimbursement reimbursement) {
		mapper.addOneEvent(reimbursement);
	}

	@Transactional
	@Override
	public void applyAndJudy(ApplyReimbursement reimbursement) {
		mapper.applyAndJudy(reimbursement);
	}

	@Override
	public int getNumber() {
		return mapper.getNumber();
	}

	@Override
	public String getNickName(int id) {
		
		return mapper.getNickName(id);
	}

	@Override
	public List<ApplyReimbursement> getByGroup(ApplyReimbursement reimbursement) {
		return mapper.getByGroup(reimbursement);
	}

	@Override
	public List<ApplyReimbursement> searchByUser(Integer userId) {
		return mapper.searchByUser(userId);
	}

	@Override
	public void judyList(List<ApplyReimbursement> applyReimbursements) {
		mapper.judyList(applyReimbursements);
	}

	@Override
	public List<ApplyReimbursement> searchByUserJudy(Integer userId) {
		return mapper.searchByUserJudy(userId);
	}

	@Override
	public List<ApplyReimbursement> selectByGroupWithNoJudy(ApplyReimbursement applyReimbursement) {
		return mapper.selectByGroupWithNoJudy(applyReimbursement);
	}

	@Override
	public List<ApplyReimbursement> selectByGroupWithNoJudyUserItem(ApplyReimbursement applyReimbursement) {
		return mapper.selectByGroupWithNoJudyUserItem(applyReimbursement);
	}

	@Override
	public List<ApplyReimbursement> selectByGroupWithNoJudyForExcel(POIDatePojo datePojo) {
		return mapper.selectByGroupWithNoJudyForExcel(datePojo);
	}
	
	@Override
	public List<ApplyReimbursement> selectByGroupWithMonth(POIDatePojo datePojo) {
		return mapper.selectByGroupWithMonth(datePojo);
	}

	@Override
	public void judyListForAuditor(List<ApplyReimbursement> applyReimbursements) {
		mapper.judyListForAuditor(applyReimbursements);
	}
	
}
