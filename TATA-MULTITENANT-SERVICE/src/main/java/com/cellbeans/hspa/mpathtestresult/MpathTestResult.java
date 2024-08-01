package com.cellbeans.hspa.mpathtestresult;

import com.cellbeans.hspa.tpathbs.TpathBs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mpath_test_result")
public class MpathTestResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trId", unique = true, nullable = true)
    private long trId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "trPsId")
    private TpathBs trPsId;

    //	@JsonInclude(NON_NULL)
//	@ManyToOne
//	@JoinColumn(name = "trTestId", nullable = true)
    @Column(name = "trTestId")
    private long trTestId;

    @JsonInclude(NON_NULL)
    @Column(name = "trResult")
    private String trResult;

    @JsonInclude(NON_NULL)
    @Column(name = "trSuggestionNote")
    private String trSuggestionNote;

    @JsonInclude(NON_NULL)
    @Column(name = "trFootNote")
    private String trFootNote;

    @JsonInclude(NON_NULL)
    @Column(name = "trComments")
    private String trComments;

    @JsonInclude(NON_NULL)
    @Column(name = "trFinalizedDate")
    private Date trFinalizedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "trPathologistId")
    private String trPathologistId;

    @JsonInclude(NON_NULL)
    @Column(name = "trPathologistName")
    private String trPathologistName;

    @JsonInclude(NON_NULL)
    @Column(name = "trRefferedBy")
    private String trRefferedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "trRefferedByName")
    private String trRefferedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "trReportDeliveryDate")
    private Date trReportDeliveryDate;

    @JsonInclude(NON_NULL)
    @Column(name = "trReportUploadDate")
    private Date trReportUploadDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalized", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isFinalized;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalizedBy")
    private String isFinalizedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalizedByName")
    private String isFinalizedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDelivered")
    private Boolean isReportDelivered;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDeliveredBy")
    private String isReportDeliveredBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportDeliveredByName")
    private String isReportDeliveredByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isReportUploaded")
    private Boolean isReportUploaded;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "createdBy", nullable = true, updatable = false)
    private String createdBy;

    @JsonInclude(NON_NULL)
    @Column(name = "createdDate", nullable = true, updatable = false)
    private Date createdDate;

    @JsonInclude(NON_NULL)
    @Column(name = "createdByName")
    private String createdByName;

    @JsonInclude(NON_NULL)
    @Column(name = "lastModifiedBy", nullable = true)
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "lastModifiedByName")
    private String lastModifiedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "lastModifiedDate", nullable = true)
    private Date lastModifiedDate;

    // code start



    //code end

    public long getTrId() {
        return trId;
    }

    public void setTrId(long trId) {
        this.trId = trId;
    }

    public TpathBs getTrPsId() {
        return trPsId;
    }

    public void setTrPsId(TpathBs trPsId) {
        this.trPsId = trPsId;
    }

    public String getTrResult() {
        return trResult;
    }

    public void setTrResult(String trResult) {
        this.trResult = trResult;
    }

    public String getTrSuggestionNote() {
        return trSuggestionNote;
    }

    public void setTrSuggestionNote(String trSuggestionNote) {
        this.trSuggestionNote = trSuggestionNote;
    }

    public String getTrFootNote() {
        return trFootNote;
    }

    public void setTrFootNote(String trFootNote) {
        this.trFootNote = trFootNote;
    }

    public String getTrComments() {
        return trComments;
    }

    public void setTrComments(String trComments) {
        this.trComments = trComments;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MpathTestResult [trId=" + trId + ", trPsId=" + trPsId + ", trResult=" + trResult + ", trSuggestionNote="
                + trSuggestionNote + ", trFootNote=" + trFootNote + ", trComments=" + trComments + ", isActive="
                + isActive + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", createdByName=" + createdByName + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedByName="
                + lastModifiedByName + ", lastModifiedDate=" + lastModifiedDate + "]";
    }

    public long getTrTestId() {
        return trTestId;
    }

    public void setTrTestId(long trTestId) {
        this.trTestId = trTestId;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public String getIsFinalizedBy() {
        return isFinalizedBy;
    }

    public void setIsFinalizedBy(String isFinalizedBy) {
        this.isFinalizedBy = isFinalizedBy;
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

    public String getIsFinalizedByName() {
        return isFinalizedByName;
    }

    public void setIsFinalizedByName(String isFinalizedByName) {
        this.isFinalizedByName = isFinalizedByName;
    }
}
