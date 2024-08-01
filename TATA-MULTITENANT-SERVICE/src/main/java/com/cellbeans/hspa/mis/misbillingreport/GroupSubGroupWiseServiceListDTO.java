package com.cellbeans.hspa.mis.misbillingreport;

public class GroupSubGroupWiseServiceListDTO {

    public String tariffName;
    public String serviceName;
    public String groupName;
    public String subGroupName;
    public String serviceRate;
    public String serviceCount;
    public String Revenue;
    public long count;   // total count

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(String serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getRevenue() {
        return Revenue;
    }

    public void setRevenue(String revenue) {
        Revenue = revenue;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GroupSubGroupWiseServiceListDTO{" +
                "tariffName='" + tariffName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", subGroupName='" + subGroupName + '\'' +
                ", serviceRate='" + serviceRate + '\'' +
                ", serviceCount='" + serviceCount + '\'' +
                ", Revenue='" + Revenue + '\'' +
                ", count=" + count +
                '}';
    }

}
