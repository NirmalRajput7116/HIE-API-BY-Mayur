package com.cellbeans.hspa.tpathbs;

public class TestResult {

    String paramterName;
    String paramterValue;
    String comment;

    public TestResult(String paramterName, String paramterValue, String comment) {
        super();
        this.paramterName = paramterName;
        this.paramterValue = paramterValue;
        this.comment = comment;
    }

    public String getParamterName() {
        return paramterName;
    }

    public void setParamterName(String paramterName) {
        this.paramterName = paramterName;
    }

    public String getParamterValue() {
        return paramterValue;
    }

    public void setParamterValue(String paramterValue) {
        this.paramterValue = paramterValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
