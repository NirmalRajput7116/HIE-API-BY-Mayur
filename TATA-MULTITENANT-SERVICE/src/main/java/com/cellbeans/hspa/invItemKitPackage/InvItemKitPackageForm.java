package com.cellbeans.hspa.invItemKitPackage;

import java.util.List;

public class InvItemKitPackageForm {
    private long ikpId;
    private String ikpName;
    private String ikpCode;
    private double ikpAmount;
    private List<InvItemKitPackageItemsDto> invItemKitPackageItemsList;

    public List<InvItemKitPackageItemsDto> getInvItemKitPackageItemsList() {
        return invItemKitPackageItemsList;
    }

    public void setInvItemKitPackageItemsList(List<InvItemKitPackageItemsDto> invItemKitPackageItemsList) {
        this.invItemKitPackageItemsList = invItemKitPackageItemsList;
    }

    public long getIkpId() {
        return ikpId;
    }

    public void setIkpId(long ikpId) {
        this.ikpId = ikpId;
    }

    public String getIkpName() {
        return ikpName;
    }

    public void setIkpName(String ikpName) {
        this.ikpName = ikpName;
    }

    public String getIkpCode() {
        return ikpCode;
    }

    public void setIkpCode(String ikpCode) {
        this.ikpCode = ikpCode;
    }

    public double getIkpAmount() {
        return ikpAmount;
    }

    public void setIkpAmount(double ikpAmount) {
        this.ikpAmount = ikpAmount;
    }

}
