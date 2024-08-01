package com.cellbeans.hspa.trndoctorscheduledetail;

import com.cellbeans.hspa.mstday.MstDay;

import java.util.List;

public class DoctorListDto {
    private String name;
    private String sname;
    private String education;
    private String city_name;
    private String unit_name;
    private String lang;
    private Integer rating;
    private Integer followup_count;
    private String exp;
    private String staffnmcno;
    private String language_name;
    private Long staff_id;
    private Double doctorRate;
    private Long unitId;
    private String currencyName;
    private Integer isUserFollow;
    private String userImage;
    private String isDoctorOnline;
    private List<MstDay> dayList;
    private String parentUnitId;
    private String staffMinDuration;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getFollowup_count() {
        return followup_count;
    }

    public void setFollowup_count(Integer followup_count) {
        this.followup_count = followup_count;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public Long getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Long staff_id) {
        this.staff_id = staff_id;
    }

    public Double getDoctorRate() {
        return doctorRate;
    }

    public void setDoctorRate(Double doctorRate) {
        this.doctorRate = doctorRate;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getIsUserFollow() {
        return isUserFollow;
    }

    public void setIsUserFollow(Integer isUserFollow) {
        this.isUserFollow = isUserFollow;
    }

    public String getStaffnmcno() {
        return staffnmcno;
    }

    public void setStaffnmcno(String staffnmcno) {
        this.staffnmcno = staffnmcno;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public List<MstDay> getDayList() {
        return dayList;
    }

    public void setDayList(List<MstDay> dayList) {
        this.dayList = dayList;
    }

    public String getIsDoctorOnline() {
        return isDoctorOnline;
    }

    public void setIsDoctorOnline(String isDoctorOnline) {
        this.isDoctorOnline = isDoctorOnline;
    }

    public String getParentUnitId() {
        return parentUnitId;
    }

    public void setParentUnitId(String parentUnitId) {
        this.parentUnitId = parentUnitId;
    }

    public String getStaffMinDuration() {
        return staffMinDuration;
    }

    public void setStaffMinDuration(String staffMinDuration) {
        this.staffMinDuration = staffMinDuration;
    }
}

