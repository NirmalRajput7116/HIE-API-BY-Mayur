package com.cellbeans.hspa.mis.CountReprotMIS;

import java.util.List;

public class VisitTrendPatientCountDto {

    public String unitName;
    private List<VisitTrendDetailsDto> vtcount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<VisitTrendDetailsDto> getVtcount() {
        return vtcount;
    }

    public void setVtcount(List<VisitTrendDetailsDto> vtcount) {
        this.vtcount = vtcount;
    }

}
