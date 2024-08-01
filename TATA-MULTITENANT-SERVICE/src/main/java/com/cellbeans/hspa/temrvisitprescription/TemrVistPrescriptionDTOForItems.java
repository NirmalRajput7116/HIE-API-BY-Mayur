package com.cellbeans.hspa.temrvisitprescription;

import com.cellbeans.hspa.invitem.InvItem;

public class TemrVistPrescriptionDTOForItems {

    private long ipId;

    private String ipQuantity;

    private InvItem ipInvItemId;

    private long vpId;

    private String ipDdId;

    public TemrVistPrescriptionDTOForItems(long ipId, String ipQuantity, InvItem ipInvItemId, long vpId, String ipDdId) {
        this.ipId = ipId;
        this.ipQuantity = ipQuantity;
        this.ipInvItemId = ipInvItemId;
        this.vpId = vpId;
        this.ipDdId = ipDdId;
    }

    public long getIpId() {
        return ipId;
    }

    public void setIpId(long ipId) {
        this.ipId = ipId;
    }

    public String getIpQuantity() {
        return ipQuantity;
    }

    public void setIpQuantity(String ipQuantity) {
        this.ipQuantity = ipQuantity;
    }

    public InvItem getIpInvItemId() {
        return ipInvItemId;
    }

    public void setIpInvItemId(InvItem ipInvItemId) {
        this.ipInvItemId = ipInvItemId;
    }

    public long getVpId() {
        return vpId;
    }

    public void setVpId(long vpId) {
        this.vpId = vpId;
    }

    public String getIpDdId() {
        return ipDdId;
    }

    public void setIpDdId(String ipDdId) {
        this.ipDdId = ipDdId;
    }

}
