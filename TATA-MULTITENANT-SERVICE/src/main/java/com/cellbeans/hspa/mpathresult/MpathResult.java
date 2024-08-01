package com.cellbeans.hspa.mpathresult;

import com.cellbeans.hspa.mpathparameterrange.MpathParameterRange;
import com.cellbeans.hspa.mpathtestresult.MpathTestResult;
import com.cellbeans.hspa.mpathtesttemplate.MpathTestTemplate;
import com.cellbeans.hspa.rpathtestparameter.RpathTestParameter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mpath_result")
public class MpathResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultId", unique = true, nullable = true)
    private long resultId;

    @JsonInclude(NON_NULL)
    @Column(name = "resultValue")
    private String resultValue;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "resultPrId")
    private MpathParameterRange resultPrId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "resultTestResultId")
    private MpathTestResult resultTestResultId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "resultTestParameterId")
    private RpathTestParameter resultTestParameterId;

    @JsonInclude(NON_NULL)
    @Column(name = "resultParamSequence")
    private String resultParamSequence;

  /*  @ManyToOne
    @JoinColumn(name = "resultMachineId")
    private MpathMachine resultMachineId;*/

    @JsonInclude(NON_NULL)
    @Column(name = "isMachineValue", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isMachineValue = false;

    @Column(name = "createdBy")
    private String createdBy;
    @JsonIgnore
    @Column(name = "createdDate")
    private Date createdDate;
    @JsonIgnore
    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;
    @JsonIgnore
    @Column(name = "lastModifiedByName")
    private String lastModifiedByName;
    @JsonIgnore
    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "resultTestReportPath")
    private String resultTestReportPath;

    @JsonInclude(NON_NULL)
    @Column(name = "resultTestReportName")
    private String resultTestReportName;

    @JsonInclude(NON_NULL)
    @Column(name = "resultTestReportFileName")
    private String resultTestReportFileName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "resultTestTemplateId")
    private MpathTestTemplate resultTestTemplateId;

    @JsonInclude(NON_NULL)
    @Column(name = "isTemplateValue", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isTemplateValue = false;

    public Boolean getIsMachineValue() {
        return isMachineValue;
    }

    public void setIsMachineValue(Boolean isMachineValue) {
        this.isMachineValue = isMachineValue;
    }

    public String getResultTestReportPath() {
        return resultTestReportPath;
    }

    public void setResultTestReportPath(String resultTestReportPath) {
        this.resultTestReportPath = resultTestReportPath;
    }

    public String getResultTestReportName() {
        return resultTestReportName;
    }

    public void setResultTestReportName(String resultTestReportName) {
        this.resultTestReportName = resultTestReportName;
    }

    public String getResultTestReportFileName() {
        return resultTestReportFileName;
    }

    public void setResultTestReportFileName(String resultTestReportFileName) {
        this.resultTestReportFileName = resultTestReportFileName;
    }

    public MpathParameterRange getResultPrId() {
        return resultPrId;
    }

    public void setResultPrId(MpathParameterRange resultPrId) {
        this.resultPrId = resultPrId;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
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
//    public TbillBillService getResultBillServiceId() {
//        return resultBillServiceId;
//    }
//
//    public void setResultBillServiceId(TbillBillService resultBillServiceId) {
//        this.resultBillServiceId = resultBillServiceId;
//    }

    public RpathTestParameter getResultTestParameterId() {
        return resultTestParameterId;
    }

    public void setResultTestParameterId(RpathTestParameter resultTestParameterId) {
        this.resultTestParameterId = resultTestParameterId;
    }

    public MpathTestResult getResultTestResultId() {
        return resultTestResultId;
    }

    public void setResultTestResultId(MpathTestResult resultTestResultId) {
        this.resultTestResultId = resultTestResultId;
    }
//    public MpathProfile getResultProfileServiceId() {
//        return resultProfileServiceId;
//    }
//
//    public void setResultProfileServiceId(MpathProfile resultProfileServiceId) {
//        this.resultProfileServiceId = resultProfileServiceId;
//    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public String getResultParamSequence() {
        return resultParamSequence;
    }

    public void setResultParamSequence(String resultParamSequence) {
        this.resultParamSequence = resultParamSequence;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
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

    public MpathTestTemplate getResultTestTemplateId() {
        return resultTestTemplateId;
    }

    public void setResultTestTemplateId(MpathTestTemplate resultTestTemplateId) {
        this.resultTestTemplateId = resultTestTemplateId;
    }

    public Boolean getIsTemplateValue() {
        return isTemplateValue;
    }

    public void setIsTemplateValue(Boolean isTemplateValue) {
        this.isTemplateValue = isTemplateValue;
    }

    @Override
    public String toString() {
        return "MpathResult [resultId=" + resultId + ", resultValue=" + resultValue + ", isActive=" + isActive
                + ", isDeleted=" + isDeleted + ", resultPrId=" + resultPrId + ", resultTestResultId="
                + resultTestResultId + ", resultTestParameterId=" + resultTestParameterId + ", resultParamSequence="
                + resultParamSequence + ", isMachineValue=" + isMachineValue + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedByName="
                + lastModifiedByName + ", lastModifiedDate=" + lastModifiedDate + ", resultTestReportPath="
                + resultTestReportPath + ", resultTestReportName=" + resultTestReportName
                + ", resultTestReportFileName=" + resultTestReportFileName + "]";
    }

}
