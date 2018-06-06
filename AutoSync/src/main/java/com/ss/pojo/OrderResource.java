package com.ss.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderResource implements Serializable{
	
	private int id;
	
	private String name;
	
	private Date apply_time;
	
	private String apply_time_str;
	
	private int apply_user_id;
	
	private int auditor_id;
	
	private Float total;
	
	private Float balance;
	
	private String status;
	
	private int pay_way;
	
	private String pay_number;
	
	private Date pay_time;
	
	private String pay_time_str;
	
	private String bill_status;
	
	private Date bill_time;
	
	private String bill_time_str;
	
	private Float bill_cost;
	
	private String nickname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public String getApply_time_str() {
		return apply_time_str;
	}

	public void setApply_time_str(String apply_time_str) {
		this.apply_time_str = apply_time_str;
	}

	public int getApply_user_id() {
		return apply_user_id;
	}

	public void setApply_user_id(int apply_user_id) {
		this.apply_user_id = apply_user_id;
	}

	public int getAuditor_id() {
		return auditor_id;
	}

	public void setAuditor_id(int auditor_id) {
		this.auditor_id = auditor_id;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPay_way() {
		return pay_way;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public String getPay_number() {
		return pay_number;
	}

	public void setPay_number(String pay_number) {
		this.pay_number = pay_number;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public String getBill_status() {
		return bill_status;
	}

	public void setBill_status(String bill_status) {
		this.bill_status = bill_status;
	}

	public Date getBill_time() {
		return bill_time;
	}

	public void setBill_time(Date bill_time) {
		this.bill_time = bill_time;
	}

	public Float getBill_cost() {
		return bill_cost;
	}

	public void setBill_cost(Float bill_cost) {
		this.bill_cost = bill_cost;
	}

	public String getPay_time_str() {
		return pay_time_str;
	}

	public void setPay_time_str(String pay_time_str) {
		this.pay_time_str = pay_time_str;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBill_time_str() {
		return bill_time_str;
	}

	public void setBill_time_str(String bill_time_str) {
		this.bill_time_str = bill_time_str;
	}
	
	

}
