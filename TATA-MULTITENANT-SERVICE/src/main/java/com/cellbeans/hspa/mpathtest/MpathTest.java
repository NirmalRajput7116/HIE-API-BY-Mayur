package com.cellbeans.hspa.mpathtest;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mpathcontainer.MpathContainer;
import com.cellbeans.hspa.mpathmachine.MpathMachine;
import com.cellbeans.hspa.mpathsample.MpathSample;
import com.cellbeans.hspa.mpathtesttemplate.MpathTestTemplate;
import com.cellbeans.hspa.mstuser.MstUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mpath_test")
public class MpathTest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testId", unique = true, nullable = true)
    private long testId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "mBillServiceId", unique = true)
    private MbillService mbillServiceId;

    @JsonInclude(NON_NULL)
    @Column(name = "testName")
    private String testName;

    @JsonInclude(NON_NULL)
    @Column(name = "testPrintName")
    private String testPrintName;

    @JsonInclude(NON_NULL)
    @Column(name = "shortName")
    private String shortName;

    @JsonInclude(NON_NULL)
    @Column(name = "testCode")
    private String testCode;

    @JsonInclude(NON_NULL)
    @Column(name = "isTemplateTest", columnDefinition = "binary(1) default false")
    private Boolean isTemplateTest = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isIterativeTest", columnDefinition = "binary(1) default false")
    private Boolean isIterativeTest = false;

    @JsonInclude(NON_NULL)
    @Column(name = "testIterationNo")
    private int testIterationNo;

    @Column(name = "isActive", columnDefinition = "binary(1) default true")
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false")
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "testSuggestionNote")
    private String testSuggestionNote;

    @JsonInclude(NON_NULL)
    @Column(name = "testResult")
    private String testResult;

    @JsonInclude(NON_NULL)
    @Column(name = "testDescription")
    private String testDescription;

    @JsonInclude(NON_NULL)
    @Column(name = "testFootNote")
    private String testFootNote;

    @JsonInclude(NON_NULL)
    @Column(name = "testComments")
    private String testComments;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testSampleId")
    private MpathSample testSampleId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testContainerId")
    private MpathContainer testContainerId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testMachineId")
    private MpathMachine testMachineId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testTemplateId")
    private MpathTestTemplate testTemplateId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private MstUser createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updatedBy")
    private MstUser updatedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "createdDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDatetime;

    @JsonInclude(NON_NULL)
    @Column(name = "updatedDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedDatetime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTestSuggestionNote() {
        return testSuggestionNote;
    }

    public void setTestSuggestionNote(String testSuggestionNote) {
        this.testSuggestionNote = testSuggestionNote;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTestFootNote() {
        return testFootNote;
    }

    public void setTestFootNote(String testFootNote) {
        this.testFootNote = testFootNote;
    }

    public String getTestComments() {
        return testComments;
    }

    public void setTestComments(String testComments) {
        this.testComments = testComments;
    }

    public MbillService getMbillServiceId() {
        return mbillServiceId;
    }

    public void setMbillServiceId(MbillService mbillServiceId) {
        this.mbillServiceId = mbillServiceId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestPrintName() {
        return testPrintName;
    }

    public void setTestPrintName(String testPrintName) {
        this.testPrintName = testPrintName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public boolean getIsTemplateTest() {
        return isTemplateTest;
    }

    public void setIsTemplateTest(Boolean isTemplateTest) {
        this.isTemplateTest = isTemplateTest;
    }

    public void setIsTemplateTest(boolean isTemplateTest) {
        this.isTemplateTest = isTemplateTest;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public MstUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MstUser createdBy) {
        this.createdBy = createdBy;
    }

    public MstUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(MstUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    public Boolean getIsIterativeTest() {
        return isIterativeTest;
    }

    public void setIsIterativeTest(Boolean isIterativeTest) {
        this.isIterativeTest = isIterativeTest;
    }

    public int getTestIterationNo() {
        return testIterationNo;
    }

    public void setTestIterationNo(int testIterationNo) {
        this.testIterationNo = testIterationNo;
    }

    public MpathSample getTestSampleId() {
        return testSampleId;
    }

    public void setTestSampleId(MpathSample testSampleId) {
        this.testSampleId = testSampleId;
    }

    public MpathContainer getTestContainerId() {
        return testContainerId;
    }

    public void setTestContainerId(MpathContainer testContainerId) {
        this.testContainerId = testContainerId;
    }

    public MpathMachine getTestMachineId() {
        return testMachineId;
    }

    public void setTestMachineId(MpathMachine testMachineId) {
        this.testMachineId = testMachineId;
    }

    public MpathTestTemplate getTestTemplateId() {
        return testTemplateId;
    }

    public void setTestTemplateId(MpathTestTemplate testTemplateId) {
        this.testTemplateId = testTemplateId;
    }

    @Override
    public String toString() {
        return "MpathTest{" +
                "testId=" + testId +
                ", mbillServiceId=" + mbillServiceId +
                ", testName='" + testName + '\'' +
                ", testPrintName='" + testPrintName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", testCode='" + testCode + '\'' +
                ", isTemplateTest=" + isTemplateTest +
                ", isIterativeTest=" + isIterativeTest +
                ", testIterationNo=" + testIterationNo +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", testSuggestionNote='" + testSuggestionNote + '\'' +
                ", testResult='" + testResult + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", testFootNote='" + testFootNote + '\'' +
                ", testComments='" + testComments + '\'' +
                ", testSampleId=" + testSampleId +
                ", testContainerId=" + testContainerId +
                ", testMachineId=" + testMachineId +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdDatetime=" + createdDatetime +
                ", updatedDatetime=" + updatedDatetime +
                '}';
    }
}
