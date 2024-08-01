package com.cellbeans.hspa.tpathbs;

import java.util.List;

/**
 * @author cellb
 */
public class PatientTest {

    String testCode;
    String testName;
    String organization;
    String associateDate;
    String associateName;
    String refernal;
    String remark;

    List<TestResult> testResult;

    public PatientTest() {
    }

    public PatientTest(String testName, String organization, String associateDate, String associateName, String refernal, String remark) {
        this.organization = organization;
        this.testName = testName;
        this.associateDate = associateDate;
        this.associateName = associateName;
        this.refernal = refernal;
        this.remark = remark;
    }

    public PatientTest(String testCode, String testName, List<TestResult> testResult) {
        this.testCode = testCode;
        this.testName = testName;
        this.testResult = testResult;

    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<TestResult> getTestResult() {
        return testResult;
    }

    public void setTestResult(List<TestResult> testResult) {
        this.testResult = testResult;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAssociateDate() {
        return associateDate;
    }

    public void setAssociateDate(String associateDate) {
        this.associateDate = associateDate;
    }

    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public String getRefernal() {
        return refernal;
    }

    public void setRefernal(String refernal) {
        this.refernal = refernal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
