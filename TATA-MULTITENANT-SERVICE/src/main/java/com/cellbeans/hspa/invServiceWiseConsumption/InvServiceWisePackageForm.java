package com.cellbeans.hspa.invServiceWiseConsumption;

import java.util.List;

public class InvServiceWisePackageForm {
    private long iswpId;
    private String serviceName;
    private String serviceCode;
    private long serviceId;
    private long serviceStoreId;
    private long unitId;
    private String serviceStoreName;
    private String serviceUnitName;
    private List<InvServiceWisePackageItemsDto> invServiceWisePackageItemsList;

    public long getServiceStoreId() {
        return serviceStoreId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public void setServiceStoreId(long serviceStoreId) {
        this.serviceStoreId = serviceStoreId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceStoreName() {
        return serviceStoreName;
    }

    public void setServiceStoreName(String serviceStoreName) {
        this.serviceStoreName = serviceStoreName;
    }

    public String getServiceUnitName() {
        return serviceUnitName;
    }

    public void setServiceUnitName(String serviceUnitName) {
        this.serviceUnitName = serviceUnitName;
    }

    public long getIswpId() {
        return iswpId;
    }

    public void setIswpId(long iswpId) {
        this.iswpId = iswpId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public List<InvServiceWisePackageItemsDto> getInvServiceWisePackageItemsList() {
        return invServiceWisePackageItemsList;
    }

    public void setInvServiceWisePackageItemsList(List<InvServiceWisePackageItemsDto> invServiceWisePackageItemsList) {
        this.invServiceWisePackageItemsList = invServiceWisePackageItemsList;
    }
}
