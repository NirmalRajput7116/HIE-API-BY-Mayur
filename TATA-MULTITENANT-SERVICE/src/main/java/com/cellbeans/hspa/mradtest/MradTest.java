package com.cellbeans.hspa.mradtest;

import com.cellbeans.hspa.mbillitem.MbillItem;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mradmodality.MradModality;
import com.cellbeans.hspa.mradtemplate.MradTemplate;
import com.cellbeans.hspa.mradtestcategory.MradTestCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mrad_test")
public class MradTest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testId", unique = true, nullable = true)
    private long testId;

    @JsonInclude(NON_NULL)
    @Column(name = "testName")
    private String testName;

    @JsonInclude(NON_NULL)
    @Column(name = "testTurnaroundTime")
    private String testTurnaroundTime;

    @JsonInclude(NON_NULL)
    @Column(name = "testTemplateResultEntryOnlyToOrdiringDoctor")
    private String testTemplateResultEntryOnlyToOrdiringDoctor;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testTcId")
    private MradTestCategory testTcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testTsId")
    private MbillService testTsId;

    @JsonInclude(NON_NULL)
    @Column(name = "testTemplatePrientTestName")
    private String testTemplatePrientTestName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "testTmId")
    private MradModality testTmId;

    @JsonInclude(NON_NULL)
    @OneToMany
    @JoinColumn(name = "testTdId")
    private List<MradTemplate> testTdId;

    @JsonInclude(NON_NULL)
    @OneToMany
    @JoinColumn(name = "testItemId")
    private List<MbillItem> testItemId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

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

    public String getTestTurnaroundTime() {
        return testTurnaroundTime;
    }

    public void setTestTurnaroundTime(String testTurnaroundTime) {
        this.testTurnaroundTime = testTurnaroundTime;
    }

    public String getTestTemplateResultEntryOnlyToOrdiringDoctor() {
        return testTemplateResultEntryOnlyToOrdiringDoctor;
    }

    public void setTestTemplateResultEntryOnlyToOrdiringDoctor(String testTemplateResultEntryOnlyToOrdiringDoctor) {
        this.testTemplateResultEntryOnlyToOrdiringDoctor = testTemplateResultEntryOnlyToOrdiringDoctor;
    }

    public MradTestCategory getTestTcId() {
        return testTcId;
    }

    public void setTestTcId(MradTestCategory testTcId) {
        this.testTcId = testTcId;
    }

    public MbillService getTestTsId() {
        return testTsId;
    }

    public void setTestTsId(MbillService testTsId) {
        this.testTsId = testTsId;
    }

    public String getTestTemplatePrientTestName() {
        return testTemplatePrientTestName;
    }

    public void setTestTemplatePrientTestName(String testTemplatePrientTestName) {
        this.testTemplatePrientTestName = testTemplatePrientTestName;
    }

    public MradModality getTestTmId() {
        return testTmId;
    }

    public void setTestTmId(MradModality testTmId) {
        this.testTmId = testTmId;
    }

    public List<MradTemplate> getTestTdId() {
        return testTdId;
    }

    public void setTestTdId(List<MradTemplate> testTdId) {
        this.testTdId = testTdId;
    }

    public List<MbillItem> getTestItemId() {
        return testItemId;
    }

    public void setTestItemId(List<MbillItem> testItemId) {
        this.testItemId = testItemId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}            
