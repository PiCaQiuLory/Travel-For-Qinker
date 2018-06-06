package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Traveler {
	@Element(required=false)
	private long TravelerId;


public long getTravelerId() {
	return TravelerId;
 }

public void setTravelerId(long travelerid) {
TravelerId = travelerid;
}
	@Element(required=false)
	        private String ChineseName;


public String getChineseName() {
	return ChineseName;
 }

public void setChineseName(String chinesename) {
ChineseName = chinesename;
}
	@Element(required=false)
	        private String EnglishName;


public String getEnglishName() {
	return EnglishName;
 }

public void setEnglishName(String englishname) {
EnglishName = englishname;
}
	@Element(required=false)
	        private String Gender;


public String getGender() {
	return Gender;
 }

public void setGender(String gender) {
Gender = gender;
}
	@Element(required=false)
	        private String Nationality;


public String getNationality() {
	return Nationality;
 }

public void setNationality(String nationality) {
Nationality = nationality;
}
	@Element(required=false)
	        private String BirthDate;


public String getBirthDate() {
	return BirthDate;
 }

public void setBirthDate(String birthdate) {
BirthDate = birthdate;
}
	@Element(required=false)
	        private String BirthCity;


public String getBirthCity() {
	return BirthCity;
 }

public void setBirthCity(String birthcity) {
BirthCity = birthcity;
}
	@Element(required=false)
	        private String Mobile;


public String getMobile() {
	return Mobile;
 }

public void setMobile(String mobile) {
Mobile = mobile;
}
	@Element(required=false)
	        private AgeCategory AgeCategory;


public AgeCategory getAgeCategory() {
	return AgeCategory;
 }

public void setAgeCategory(AgeCategory agecategory) {
AgeCategory = agecategory;
}
	@Element(required=false)
	        private IDType IDType;


public IDType getIDType() {
	return IDType;
 }

public void setIDType(IDType idtype) {
IDType = idtype;
}
	@Element(required=false)
	        private String IDNumber;


public String getIDNumber() {
	return IDNumber;
 }

public void setIDNumber(String idnumber) {
IDNumber = idnumber;
}
	@Element(required=false)
	        private String IssuedDate;


public String getIssuedDate() {
	return IssuedDate;
 }

public void setIssuedDate(String issueddate) {
IssuedDate = issueddate;
}
	@Element(required=false)
	        private String ExpiredDate;


public String getExpiredDate() {
	return ExpiredDate;
 }

public void setExpiredDate(String expireddate) {
ExpiredDate = expireddate;
}
	@Element(required=false)
	        private String IssuedPlace;


public String getIssuedPlace() {
	return IssuedPlace;
 }

public void setIssuedPlace(String issuedplace) {
IssuedPlace = issuedplace;
}
	@Element(required=false)
	        private boolean IsRoomShare;


public boolean getIsRoomShare() {
	return IsRoomShare;
 }

public void setIsRoomShare(boolean isroomshare) {
IsRoomShare = isroomshare;
}
}
