package com.cellbeans.hspa.mstprocedure;

public class MstProcedureDto {

    public Long procedureId;
    public String procedureName;

    public MstProcedureDto(Long procedureId, String procedureName) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }
}
