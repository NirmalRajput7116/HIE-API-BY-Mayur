package com.cellbeans.hspa.tpathbs;

import com.cellbeans.hspa.mbillipdcharges.IPDChargesService;
import com.cellbeans.hspa.mbillipdcharges.MbillIPDCharge;
import com.cellbeans.hspa.mpathagency.MpathAgency;
import com.cellbeans.hspa.mpathsamplerejection.MpathSampleRejection;
import com.cellbeans.hspa.mpathsamplesuitability.MpathSampleSuitability;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.cellbeans.hspa.tbillbill.TBillBill;
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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tpath_bs")
public class TpathBs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psId", unique = true, nullable = true)
    private long psId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psBillId")
    private TBillBill psBillId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "mbillIPDCharge")
    private MbillIPDCharge mbillIPDCharge;

    @JsonInclude(NON_NULL)
    @Column(name = "isIPD")
    private boolean isIPD;

    @JsonInclude(NON_NULL)
    @Column(name = "workOrderNumber")
    private String workOrderNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psTestId")
    private MpathTest psTestId;

    @JsonInclude(NON_NULL)
    //not used
    @ManyToOne
    @JoinColumn(name = "psSampleSuitabilityId")
    private MpathSampleSuitability psSampleSuitabilityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psSampleRejectionId")
    private MpathSampleRejection psSampleRejectionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psAgencyId")
    private MpathAgency psAgencyId;

    @JsonInclude(NON_NULL)
    @Column(name = "psSampleNumber")
    private String psSampleNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isSampleRejected = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleAccepted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isSampleAccepted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psSampleAcceptedDate")
    private Date psSampleAcceptedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "psSampleRejectedDate")
    private Date psSampleRejectedDate;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleCollected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isSampleCollected = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psSampleCollectionDate")
    private Date psSampleCollectionDate;

    @JsonInclude(NON_NULL)
    @Column(name = "psSampleOutsourceDate")
    private Date psSampleOutsourceDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isResultEntry", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isResultEntry = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psResultEntryDate")
    private Date psResultEntryDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isFinalized = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psFinalizedDate")
    private Date psFinalizedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportUploaded", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isReportUploaded = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psReportUploadDate")
    private Date psReportUploadDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDelivered", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isReportDelivered = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleOutsource", columnDefinition = "binary(1) default false")
    private Boolean isSampleOutsource = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psReportDeliveryDate")
    private Date psReportDeliveryDate;

    @JsonInclude(NON_NULL)
    @Column(name = "psRemark")
    private String psRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "psRetest")
    private String psRetest;

    @JsonInclude(NON_NULL)
    @Column(name = "isPerformed", columnDefinition = "binary(1) default false")
    private Boolean isPerformed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwarded", columnDefinition = "binary(1) default false")
    private Boolean isTestForwarded = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psPerformedByDate")
    private Date psPerformedByDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isLabOrderAcceptance", columnDefinition = "binary(1) default false")
    private Boolean isLabOrderAcceptance = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psLabOrderAcceptanceDate")
    private Date psLabOrderAcceptanceDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isLabOrderCancel", columnDefinition = "binary(1) default false")
    private Boolean isLabOrderCancel = false;

    @JsonInclude(NON_NULL)
    @Column(name = "psLabOrderCancelDate")
    private Date psLabOrderCancelDate;

    @JsonInclude(NON_NULL)
    @Column(name = "psTestCancelReason")
    private String psTestCancelReason;

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

    @JsonInclude(NON_NULL)
    @Column(name = "isPerformedByUnitId")
    private String isPerformedByUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "isPerformedByUnitName")
    private String isPerformedByUnitName;

    @JsonInclude(NON_NULL)
    @Column(name = "isPerformedBy")
    private String isPerformedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportUploadedBy")
    private String isReportUploadedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleRejectedBy")
    private String isSampleRejectedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleAcceptedBy")
    private String isSampleAcceptedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleCollectedBy")
    private String isSampleCollectedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalizedBy")
    private String isFinalizedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDeliveredBy")
    private String isReportDeliveredBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleOutsourceBy")
    private String isSampleOutsourceBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isResultEntryDoneBy")
    private String isResultEntryDoneBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isLabOrderAcceptanceBy")
    private String isLabOrderAcceptanceBy;

    @JsonInclude(NON_NULL)
    @Column(name = "psStaffName")
    private String psStaffName;

    @JsonInclude(NON_NULL)
    @Column(name = "isPerformedByName")
    private String isPerformedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportUploadedByName")
    private String isReportUploadedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleRejectedByName")
    private String isSampleRejectedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleAcceptedByName")
    private String isSampleAcceptedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleCollectedByName")
    private String isSampleCollectedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalizedByName")
    private String isFinalizedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDeliveredByName")
    private String isReportDeliveredByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isSampleOutsourceByName")
    private String isSampleOutsourceByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isResultEntryDoneByName")
    private String isResultEntryDoneByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isLabOrderAcceptanceByName")
    private String isLabOrderAcceptanceByName;

    @Column(name = "createdByName")
    private String createdByName;

    @Column(name = "lastModifiedByName")
    private String lastModifiedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwardedByName")
    private String isTestForwardedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwardedBy")
    private String isTestForwardedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwardDate")
    private Date isTestForwardDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwardToUnitId")
    private String isTestForwardToUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "isTestForwardToUnitName")
    private String isTestForwardToUnitName;

    @JsonInclude(NON_NULL)
    @Column(name = "isEmergency", columnDefinition = "binary(1) default false")
    private Boolean isEmergency = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psCsId")
    private IPDChargesService psCsId;

    @JsonInclude(NON_NULL)
    @Column(name = "psLabNo")
    private String psLabNo;

    @Transient
    private Integer completedFlag;
    @Transient
    private Long totalSize;

    @Transient
    private long count;

    public IPDChargesService getPsCsId() {
        return psCsId;
    }

    public void setPsCsId(IPDChargesService psCsId) {
        this.psCsId = psCsId;
    }

    public MbillIPDCharge getMbillIPDCharge() {
        return mbillIPDCharge;
    }

    public void setMbillIPDCharge(MbillIPDCharge mbillIPDCharge) {
        this.mbillIPDCharge = mbillIPDCharge;
    }

    public boolean getIsIPD() {
        return isIPD;
    }

    public void setIsIPD(boolean isIPD) {
        this.isIPD = isIPD;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Boolean getIsTestForwarded() {
        return isTestForwarded;
    }

    public void setIsTestForwarded(Boolean testForwarded) {
        isTestForwarded = testForwarded;
    }

    public String getIsTestForwardedByName() {
        return isTestForwardedByName;
    }

    public void setIsTestForwardedByName(String isTestForwardedByName) {
        this.isTestForwardedByName = isTestForwardedByName;
    }

    public String getIsTestForwardedBy() {
        return isTestForwardedBy;
    }

    public void setIsTestForwardedBy(String isTestForwardedBy) {
        this.isTestForwardedBy = isTestForwardedBy;
    }

    public Date getIsTestForwardDate() {
        return isTestForwardDate;
    }

    public void setIsTestForwardDate(Date isTestForwardDate) {
        this.isTestForwardDate = isTestForwardDate;
    }

    public String getIsTestForwardToUnitId() {
        return isTestForwardToUnitId;
    }

    public void setIsTestForwardToUnitId(String isTestForwardToUnitId) {
        this.isTestForwardToUnitId = isTestForwardToUnitId;
    }

    public String getIsTestForwardToUnitName() {
        return isTestForwardToUnitName;
    }

    public void setIsTestForwardToUnitName(String isTestForwardToUnitName) {
        this.isTestForwardToUnitName = isTestForwardToUnitName;
    }

    public String getIsPerformedByUnitName() {
        return isPerformedByUnitName;
    }

    public void setIsPerformedByUnitName(String isPerformedByUnitName) {
        this.isPerformedByUnitName = isPerformedByUnitName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public String getPsStaffName() {
        return psStaffName;
    }

    public void setPsStaffName(String psStaffName) {
        this.psStaffName = psStaffName;
    }

    public String getIsPerformedByName() {
        return isPerformedByName;
    }

    public void setIsPerformedByName(String isPerformedByName) {
        this.isPerformedByName = isPerformedByName;
    }

    public String getIsReportUploadedByName() {
        return isReportUploadedByName;
    }

    public void setIsReportUploadedByName(String isReportUploadedByName) {
        this.isReportUploadedByName = isReportUploadedByName;
    }

    public String getIsSampleRejectedByName() {
        return isSampleRejectedByName;
    }

    public void setIsSampleRejectedByName(String isSampleRejectedByName) {
        this.isSampleRejectedByName = isSampleRejectedByName;
    }

    public String getIsSampleAcceptedByName() {
        return isSampleAcceptedByName;
    }

    public void setIsSampleAcceptedByName(String isSampleAcceptedByName) {
        this.isSampleAcceptedByName = isSampleAcceptedByName;
    }

    public String getIsSampleCollectedByName() {
        return isSampleCollectedByName;
    }

    public void setIsSampleCollectedByName(String isSampleCollectedByName) {
        this.isSampleCollectedByName = isSampleCollectedByName;
    }

    public String getIsFinalizedByName() {
        return isFinalizedByName;
    }

    public void setIsFinalizedByName(String isFinalizedByName) {
        this.isFinalizedByName = isFinalizedByName;
    }

    public String getIsReportDeliveredByName() {
        return isReportDeliveredByName;
    }

    public void setIsReportDeliveredByName(String isReportDeliveredByName) {
        this.isReportDeliveredByName = isReportDeliveredByName;
    }

    public String getIsSampleOutsourceByName() {
        return isSampleOutsourceByName;
    }

    public void setIsSampleOutsourceByName(String isSampleOutsourceByName) {
        this.isSampleOutsourceByName = isSampleOutsourceByName;
    }

    public String getIsResultEntryDoneByName() {
        return isResultEntryDoneByName;
    }

    public void setIsResultEntryDoneByName(String isResultEntryDoneByName) {
        this.isResultEntryDoneByName = isResultEntryDoneByName;
    }

    public String getIsLabOrderAcceptanceByName() {
        return isLabOrderAcceptanceByName;
    }

    public void setIsLabOrderAcceptanceByName(String isLabOrderAcceptanceByName) {
        this.isLabOrderAcceptanceByName = isLabOrderAcceptanceByName;
    }

    public Boolean getIsLabOrderCancel() {
        return isLabOrderCancel;
    }

    public void setIsLabOrderCancel(Boolean isLabOrderCancel) {
        this.isLabOrderCancel = isLabOrderCancel;
    }

    public Date getPsLabOrderCancelDate() {
        return psLabOrderCancelDate;
    }

    public void setPsLabOrderCancelDate(Date psLabOrderCancelDate) {
        this.psLabOrderCancelDate = psLabOrderCancelDate;
    }

  /*  public MstStaff getIsLabOrderCancelBy() {
        return isLabOrderCancelBy;
    }

    public void setIsLabOrderCancelBy(MstStaff isLabOrderCancelBy) {
        this.isLabOrderCancelBy = isLabOrderCancelBy;
    }*/

    public Boolean getIsPerformed() {
        return isPerformed;
    }

    public void setIsPerformed(Boolean isPerformed) {
        this.isPerformed = isPerformed;
    }

    public Date getPsPerformedByDate() {
        return psPerformedByDate;
    }

    public void setPsPerformedByDate(Date psPerformedByDate) {
        this.psPerformedByDate = psPerformedByDate;
    }

    public String getIsPerformedBy() {
        return isPerformedBy;
    }

    public void setIsPerformedBy(String isPerformedBy) {
        this.isPerformedBy = isPerformedBy;
    }

    public Boolean getIsLabOrderAcceptance() {
        return isLabOrderAcceptance;
    }

    public void setIsLabOrderAcceptance(Boolean islabOrderAcceptance) {
        isLabOrderAcceptance = islabOrderAcceptance;
    }

    public String getIsLabOrderAcceptanceBy() {
        return isLabOrderAcceptanceBy;
    }

    public void setIsLabOrderAcceptanceBy(String isLabOrderAcceptanceBy) {
        this.isLabOrderAcceptanceBy = isLabOrderAcceptanceBy;
    }

    public String getPsTestCancelReason() {
        return psTestCancelReason;
    }

    public void setPsTestCancelReason(String psTestCancelReason) {
        this.psTestCancelReason = psTestCancelReason;
    }

    public String getIsSampleRejectedBy() {
        return isSampleRejectedBy;
    }

    public void setIsSampleRejectedBy(String isSampleRejectedBy) {
        this.isSampleRejectedBy = isSampleRejectedBy;
    }

    public String getIsSampleAcceptedBy() {
        return isSampleAcceptedBy;
    }

    public void setIsSampleAcceptedBy(String isSampleAcceptedBy) {
        this.isSampleAcceptedBy = isSampleAcceptedBy;
    }

    public String getIsSampleCollectedBy() {
        return isSampleCollectedBy;
    }

    public void setIsSampleCollectedBy(String isSampleCollectedBy) {
        this.isSampleCollectedBy = isSampleCollectedBy;
    }

    public String getIsResultEntryDoneBy() {
        return isResultEntryDoneBy;
    }

    public void setIsResultEntryDoneBy(String isResultEntryDoneBy) {
        this.isResultEntryDoneBy = isResultEntryDoneBy;
    }

    public String getIsFinalizedBy() {
        return isFinalizedBy;
    }

    public void setIsFinalizedBy(String isFinalizedBy) {
        this.isFinalizedBy = isFinalizedBy;
    }

    public String getIsReportDeliveredBy() {
        return isReportDeliveredBy;
    }

    public void setIsReportDeliveredBy(String isReportDeliveredBy) {
        this.isReportDeliveredBy = isReportDeliveredBy;
    }

    public String getIsSampleOutsourceBy() {
        return isSampleOutsourceBy;
    }

    public void setIsSampleOutsourceBy(String isSampleOutsourceBy) {
        this.isSampleOutsourceBy = isSampleOutsourceBy;
    }

    public String getPsRemark() {
        return psRemark;
    }

    public void setPsRemark(String psRemark) {
        this.psRemark = psRemark;
    }

    public Boolean getIsSampleOutsource() {
        return isSampleOutsource;
    }

    public void setIsSampleOutsource(Boolean sampleOutsource) {
        isSampleOutsource = sampleOutsource;
    }

    public long getPsId() {
        return psId;
    }

    public void setPsId(long psId) {
        this.psId = psId;
    }

    public String getPsSampleNumber() {
        return psSampleNumber;
    }

    public void setPsSampleNumber(String psSampleNumber) {
        this.psSampleNumber = psSampleNumber;
    }

    public MpathTest getPsTestId() {
        return psTestId;
    }

    public void setPsTestId(MpathTest psTestId) {
        this.psTestId = psTestId;
    }

    public MpathSampleSuitability getPsSampleSuitabilityId() {
        return psSampleSuitabilityId;
    }

    public void setPsSampleSuitabilityId(MpathSampleSuitability psSampleSuitabilityId) {
        this.psSampleSuitabilityId = psSampleSuitabilityId;
    }

    public MpathSampleRejection getPsSampleRejectionId() {
        return psSampleRejectionId;
    }

    public void setPsSampleRejectionId(MpathSampleRejection psSampleRejectionId) {
        this.psSampleRejectionId = psSampleRejectionId;
    }

    public Boolean getIsSampleRejected() {
        return isSampleRejected;
    }

    public void setIsSampleRejected(Boolean isSampleRejected) {
        this.isSampleRejected = isSampleRejected;
    }

    public Boolean getIsSampleAccepted() {
        return isSampleAccepted;
    }

    public void setIsSampleAccepted(Boolean isSampleAccepted) {
        this.isSampleAccepted = isSampleAccepted;
    }

    public Date getPsSampleAcceptedDate() {
        return psSampleAcceptedDate;
    }

    public void setPsSampleAcceptedDate(Date psSampleAcceptedDate) {
        this.psSampleAcceptedDate = psSampleAcceptedDate;
    }

    public Date getPsSampleRejectedDate() {
        return psSampleRejectedDate;
    }

    public void setPsSampleRejectedDate(Date psSampleRejectedDate) {
        this.psSampleRejectedDate = psSampleRejectedDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getIsSampleCollected() {
        return isSampleCollected;
    }

    public void setIsSampleCollected(Boolean isSampleCollected) {
        this.isSampleCollected = isSampleCollected;
    }

    public Boolean getReportUploaded() {
        return isReportUploaded;
    }

    public void setReportUploaded(Boolean reportUploaded) {
        isReportUploaded = reportUploaded;
    }

    public TBillBill getPsBillId() {
        return psBillId;
    }

    public void setPsBillId(TBillBill psBillId) {
        this.psBillId = psBillId;
    }

  /*  public MbillService getPsServiceId() {
        return psServiceId;
    }

    public void setPsServiceId(MbillService psServiceId) {
        this.psServiceId = psServiceId;
    }
*/

    public String getIsPerformedByUnitId() {
        return isPerformedByUnitId;
    }

    public void setIsPerformedByUnitId(String isPerformedByUnitId) {
        this.isPerformedByUnitId = isPerformedByUnitId;
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

    public Date getPsSampleCollectionDate() {
        return psSampleCollectionDate;
    }

    public void setPsSampleCollectionDate(Date psSampleCollectionDate) {
        this.psSampleCollectionDate = psSampleCollectionDate;
    }

    public boolean getIsResultEntry() {
        return isResultEntry;
    }

    public Date getPsSampleOutsourceDate() {
        return psSampleOutsourceDate;
    }

    public void setPsSampleOutsourceDate(Date psSampleOutsourceDate) {
        this.psSampleOutsourceDate = psSampleOutsourceDate;
    }

    public void setIsResultEntry(boolean isResultEntry) {
        this.isResultEntry = isResultEntry;
    }

    public Date getPsResultEntryDate() {
        return psResultEntryDate;
    }

    public void setPsResultEntryDate(Date psResultEntryDate) {
        this.psResultEntryDate = psResultEntryDate;
    }

    public boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    public Date getPsFinalizedDate() {
        return psFinalizedDate;
    }

    public void setPsFinalizedDate(Date psFinalizedDate) {
        this.psFinalizedDate = psFinalizedDate;
    }

    public boolean getIsReportUploaded() {
        return isReportUploaded;
    }

    public void setIsReportUploaded(boolean isReportUploaded) {
        this.isReportUploaded = isReportUploaded;
    }

    public Date getPsReportUploadDate() {
        return psReportUploadDate;
    }

    public void setPsReportUploadDate(Date psReportUploadDate) {
        this.psReportUploadDate = psReportUploadDate;
    }

    public boolean getIsReportDelivered() {
        return isReportDelivered;
    }

    public void setIsReportDelivered(boolean isReportDelivered) {
        this.isReportDelivered = isReportDelivered;
    }

    public Date getPsReportDeliveryDate() {
        return psReportDeliveryDate;
    }

    public void setPsReportDeliveryDate(Date psReportDeliveryDate) {
        this.psReportDeliveryDate = psReportDeliveryDate;
    }

    public String getPsRetest() {
        return psRetest;
    }

    public void setPsRetest(String psRetest) {
        this.psRetest = psRetest;
    }
//    public TbillBillService getPsBsId() {
//        return psBsId;
//    }
//
//    public void setPsBsId(TbillBillService psBsId) {
//        this.psBsId = psBsId;
//    }

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

    public MpathAgency getPsAgencyId() {
        return psAgencyId;
    }

    public void setPsAgencyId(MpathAgency psAgencyId) {
        this.psAgencyId = psAgencyId;
    }

    public Date getPsLabOrderAcceptanceDate() {
        return psLabOrderAcceptanceDate;
    }

    public void setPsLabOrderAcceptanceDate(Date psLabOrderAcceptanceDate) {
        this.psLabOrderAcceptanceDate = psLabOrderAcceptanceDate;
    }

    public Integer getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(Integer completedFlag) {
        this.completedFlag = completedFlag;
    }

    public String getIsReportUploadedBy() {
        return isReportUploadedBy;
    }

    public void setIsReportUploadedBy(String isReportUploadedBy) {
        this.isReportUploadedBy = isReportUploadedBy;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Boolean getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public String getPsLabNo() {
        return psLabNo;
    }

    public void setPsLabNo(String psLabNo) {
        this.psLabNo = psLabNo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TpathBs{" + "psId=" + psId + ", psBillId=" + psBillId + ", psTestId=" + psTestId + ", mbillIPDCharge=" + mbillIPDCharge + ", isIPD=" + isIPD + ", workOrderNumber=" + workOrderNumber + ", psSampleSuitabilityId=" + psSampleSuitabilityId +
                ", psSampleRejectionId=" + psSampleRejectionId + ", psAgencyId=" + psAgencyId + ", psSampleNumber='" + psSampleNumber + '\'' + ", isSampleRejected=" + isSampleRejected + ", isSampleAccepted=" + isSampleAccepted + ", psSampleAcceptedDate=" + psSampleAcceptedDate + ", psSampleRejectedDate=" + psSampleRejectedDate + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", isSampleCollected=" + isSampleCollected + ", psSampleCollectionDate=" + psSampleCollectionDate + ", isResultEntry=" + isResultEntry + ", psResultEntryDate=" + psResultEntryDate + ", isFinalized=" + isFinalized + ", psFinalizedDate=" + psFinalizedDate + ", isReportUploaded=" + isReportUploaded + ", psReportUploadDate=" + psReportUploadDate + ", isReportDelivered=" + isReportDelivered + ", isSampleOutsource=" + isSampleOutsource + ", psReportDeliveryDate=" + psReportDeliveryDate + ", psRemark='" + psRemark + '\'' + ", psRetest='" + psRetest + '\'' + ", isPerformed=" + isPerformed + ", psPerformedByDate=" + psPerformedByDate + ", isLabOrderAcceptance=" + isLabOrderAcceptance + ", psLabOrderAcceptanceDate=" + psLabOrderAcceptanceDate + ", isLabOrderCancel=" + isLabOrderCancel + ", psLabOrderCancelDate=" + psLabOrderCancelDate + ", psTestCancelReason='" + psTestCancelReason + '\'' + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", isPerformedByUnitId='" + isPerformedByUnitId + '\'' + ", isPerformedByUnitName='" + isPerformedByUnitName + '\'' + ", isPerformedBy='" + isPerformedBy + '\'' + ", isReportUploadedBy='" + isReportUploadedBy + '\'' + ", isSampleRejectedBy='" + isSampleRejectedBy + '\'' + ", isSampleAcceptedBy='" + isSampleAcceptedBy + '\'' + ", isSampleCollectedBy='" + isSampleCollectedBy + '\'' + ", isFinalizedBy='" + isFinalizedBy + '\'' + ", isReportDeliveredBy='" + isReportDeliveredBy + '\'' + ", isSampleOutsourceBy='" + isSampleOutsourceBy + '\'' + ", isResultEntryDoneBy='" + isResultEntryDoneBy + '\'' + ", isLabOrderAcceptanceBy='" + isLabOrderAcceptanceBy + '\'' + ", psStaffName='" + psStaffName + '\'' + ", isPerformedByName='" + isPerformedByName + '\'' + ", isReportUploadedByName='" + isReportUploadedByName + '\'' + ", isSampleRejectedByName='" + isSampleRejectedByName + '\'' + ", isSampleAcceptedByName='" + isSampleAcceptedByName + '\'' + ", isSampleCollectedByName='" + isSampleCollectedByName + '\'' + ", isFinalizedByName='" + isFinalizedByName + '\'' + ", isReportDeliveredByName='" + isReportDeliveredByName + '\'' + ", isSampleOutsourceByName='" + isSampleOutsourceByName + '\'' + ", isResultEntryDoneByName='" + isResultEntryDoneByName + '\'' + ", isLabOrderAcceptanceByName='" + isLabOrderAcceptanceByName + '\'' + ", createdByName='" + createdByName + '\'' + ", lastModifiedByName='" + lastModifiedByName + '\'' + '}';
    }

}

