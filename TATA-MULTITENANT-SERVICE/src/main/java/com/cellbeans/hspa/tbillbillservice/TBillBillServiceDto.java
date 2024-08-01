package com.cellbeans.hspa.tbillbillservice;

public class TBillBillServiceDto {

    private long bsId;
    private Boolean serviceIsTokenDisplay;

    public long getBsId() {
        return bsId;
    }

    public void setBsId(long bsId) {
        this.bsId = bsId;
    }

    public Boolean getServiceIsTokenDisplay() {
        return serviceIsTokenDisplay;
    }

    public void setServiceIsTokenDisplay(Boolean serviceIsTokenDisplay) {
        this.serviceIsTokenDisplay = serviceIsTokenDisplay;
    }

}
