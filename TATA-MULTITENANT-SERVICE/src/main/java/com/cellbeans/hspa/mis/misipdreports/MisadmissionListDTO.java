package com.cellbeans.hspa.mis.misipdreports;

import com.cellbeans.hspa.mstunit.MstUnit;

public class MisadmissionListDTO {

    public String mrNo;
    public String patientName;
    public String comSponsor;
    public long age;
    public String gender;
    public String admitDate;
    public String admitTime;
    public String ipdNo;
    public String doctorName;
    public String patientAddress;
    public String mobNo;
    public String bedNo;
    public String refDoctorName;
    public String userName;
    public String unitName;
    public String wardName;
    public String regNo;
    public String advAmount;
    public String chargeAmount;
    public long bsServiceId;
    public long bsStaffId;
    public long count;
    public long patientId;
    private String tariffName;
    public MstUnit objHeaderData;
    public String roomName;
    public String bedTransferRemark = "";
    public String bedTransferDate;
    public boolean print;

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getComSponsor() {
        return comSponsor;
    }

    public void setComSponsor(String comSponsor) {
        this.comSponsor = comSponsor;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public String getAdmitTime() {
        return admitTime;
    }

    public void setAdmitTime(String admitTime) {
        this.admitTime = admitTime;
    }

    public String getIpdNo() {
        return ipdNo;
    }

    public void setIpdNo(String ipdNo) {
        this.ipdNo = ipdNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getRefDoctorName() {
        return refDoctorName;
    }

    public void setRefDoctorName(String refDoctorName) {
        this.refDoctorName = refDoctorName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public long getBsServiceId() {
        return bsServiceId;
    }

    public void setBsServiceId(long bsServiceId) {
        this.bsServiceId = bsServiceId;
    }

    public long getBsStaffId() {
        return bsStaffId;
    }

    public void setBsStaffId(long bsStaffId) {
        this.bsStaffId = bsStaffId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getAdvAmount() {
        return advAmount;
    }

    public void setAdvAmount(String advAmount) {
        this.advAmount = advAmount;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBedTransferRemark() {
        return bedTransferRemark;
    }

    public void setBedTransferRemark(String bedTransferRemark) {
        this.bedTransferRemark = bedTransferRemark;
    }

    public String getBedTransferDate() {
        return bedTransferDate;
    }

    public void setBedTransferDate(String bedTransferDate) {
        this.bedTransferDate = bedTransferDate;
    }
}
