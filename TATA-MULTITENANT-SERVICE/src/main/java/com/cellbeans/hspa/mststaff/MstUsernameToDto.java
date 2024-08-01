package com.cellbeans.hspa.mststaff;

import com.cellbeans.hspa.mstorg.MstOrg;
import com.cellbeans.hspa.mstunit.MstUnit;

import java.util.List;

public class MstUsernameToDto {
    public List<MstOrg[]> orgList;
    public List<MstUnit> mstUnit;

    public List<MstOrg[]> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<MstOrg[]> orgList) {
        this.orgList = orgList;
    }

    public List<MstUnit> getMstUnit() {
        return mstUnit;
    }

    public void setMstUnit(List<MstUnit> mstUnit) {
        this.mstUnit = mstUnit;
    }
}
