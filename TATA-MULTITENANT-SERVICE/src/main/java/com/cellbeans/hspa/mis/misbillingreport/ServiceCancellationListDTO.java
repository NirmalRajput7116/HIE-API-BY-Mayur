/*package com.cellbeans.hspa.mis.misbillingreport;

public class ServiceCancellationListDTO {
}*/
package com.cellbeans.hspa.mis.misbillingreport;

public class ServiceCancellationListDTO {

    public String serviceCancellDateAndTime;
    public String mrNo;
    public String patientName;
    public String ipdNo;
    public String ServiceName;
    public String NetAmount;
    public String remark;
    public String unitName;
    public String userName;
    public long count;

    public String getServiceCancellDateAndTime() {
        return serviceCancellDateAndTime;
    }

    public void setServiceCancellDateAndTime(String serviceCancellDateAndTime) {
        this.serviceCancellDateAndTime = serviceCancellDateAndTime;
    }

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

    public String getIpdNo() {
        return ipdNo;
    }

    public void setIpdNo(String ipdNo) {
        this.ipdNo = ipdNo;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ServiceCancellationListDTO{" +
                "serviceCancellDateAndTime='" + serviceCancellDateAndTime + '\'' +
                ", mrNo='" + mrNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                ", NetAmount=" + NetAmount +
                ", remark='" + remark + '\'' +
                ", unitName='" + unitName + '\'' +
                ", userName='" + userName + '\'' +
                ", count=" + count +
                '}';
    }
}
