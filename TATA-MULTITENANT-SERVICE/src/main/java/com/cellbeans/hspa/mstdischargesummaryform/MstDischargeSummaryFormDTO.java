package com.cellbeans.hspa.mstdischargesummaryform;

import com.cellbeans.hspa.mstfield.MstField;

import java.util.List;

public class MstDischargeSummaryFormDTO {
    private MstDischargeSummaryForm mstDischargeSummaryForm;
    private List<MstField> mstFieldList;

    public MstDischargeSummaryFormDTO(MstDischargeSummaryForm mstDischargeSummaryForm, List<MstField> mstFieldList) {
        this.mstDischargeSummaryForm = mstDischargeSummaryForm;
        this.mstFieldList = mstFieldList;
    }

    public MstDischargeSummaryForm getMstDischargeSummaryForm() {
        return mstDischargeSummaryForm;
    }

    public void setMstDischargeSummaryForm(MstDischargeSummaryForm mstDischargeSummaryForm) {
        this.mstDischargeSummaryForm = mstDischargeSummaryForm;
    }

    public List<MstField> getMstFieldList() {
        return mstFieldList;
    }

    public void setMstFieldList(List<MstField> mstFieldList) {
        this.mstFieldList = mstFieldList;
    }
}
