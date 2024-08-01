package com.cellbeans.hspa.mis.misbillingreport.receipt_collection;

public class RecieptCollectionsearchDTO {
    public boolean print;
    public long unitId;
    public long userId;

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
