package com.cellbeans.hspa.inboundcarecoordination;

import com.cellbeans.hspa.memrcalloutcome.MemrCallOutcome;
import com.cellbeans.hspa.memrcareactivity.MemrCareActivity;
import com.cellbeans.hspa.memrchannelsource.MemrChannelSource;
import com.cellbeans.hspa.memrissuetype.MemrIssueType;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.ncliclinicalform.NcliClinicalForm;
import com.cellbeans.hspa.ncliclinicalformcategory.NcliClinicalFormCategory;
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
@Table(name = "trn_inbound_cc")
public class TrnInboundCareCoordination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inboundccId", unique = true, nullable = true)
    private long inboundccId;

    @JsonInclude(NON_NULL)
    @Column(name = "inboundQuery")
    private String inboundQuery;

    @JsonInclude(NON_NULL)
    @Column(name = "inboundResponce")
    private String inboundResponce;

    @JsonInclude(NON_NULL)
    @Column(name = "inbounddescription")
    private String inbounddescription;

    @JsonInclude(NON_NULL)
    @Column(name = "inboundclinicalQuation")
    private String inboundclinicalQuation;

    @JsonInclude(NON_NULL)
    @Column(name = "inboundclinicalAns")
    private String inboundclinicalAns;


    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundchannelSourceId")
    private MemrChannelSource inboundchannelSourceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundissueTypeId")
    private MemrIssueType inboundissueTypeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundcallOutcomeId")
    private MemrCallOutcome inboundcallOutcomeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundcareActivityId")
    private MemrCareActivity inboundcareActivityId;


    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundccUnitId")
    private MstUnit inboundccUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundccStaffId")
    private MstStaff inboundccStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundccPatientId")
    private MstPatient inboundccPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundClinicalFromCategoryId")
    private NcliClinicalFormCategory inboundClinicalFromCategoryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundClinicalFormId")
    private NcliClinicalForm inboundClinicalFormId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "inboundVisitId")
    private MstVisit inboundVisitId;

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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getInboundccId() {
        return inboundccId;
    }

    public void setInboundccId(long inboundccId) {
        this.inboundccId = inboundccId;
    }

    public String getInboundQuery() {
        return inboundQuery;
    }

    public void setInboundQuery(String inboundQuery) {
        this.inboundQuery = inboundQuery;
    }

    public String getInboundResponce() {
        return inboundResponce;
    }

    public void setInboundResponce(String inboundResponce) {
        this.inboundResponce = inboundResponce;
    }

    public String getInbounddescription() {
        return inbounddescription;
    }

    public void setInbounddescription(String inbounddescription) {
        this.inbounddescription = inbounddescription;
    }

    public MemrChannelSource getInboundchannelSourceId() {
        return inboundchannelSourceId;
    }

    public void setInboundchannelSourceId(MemrChannelSource inboundchannelSourceId) {
        this.inboundchannelSourceId = inboundchannelSourceId;
    }

    public MemrIssueType getInboundissueTypeId() {
        return inboundissueTypeId;
    }

    public void setInboundissueTypeId(MemrIssueType inboundissueTypeId) {
        this.inboundissueTypeId = inboundissueTypeId;
    }

    public MemrCallOutcome getInboundcallOutcomeId() {
        return inboundcallOutcomeId;
    }

    public void setInboundcallOutcomeId(MemrCallOutcome inboundcallOutcomeId) {
        this.inboundcallOutcomeId = inboundcallOutcomeId;
    }

    public MemrCareActivity getInboundcareActivityId() {
        return inboundcareActivityId;
    }

    public void setInboundcareActivityId(MemrCareActivity inboundcareActivityId) {
        this.inboundcareActivityId = inboundcareActivityId;
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

    public String getInboundclinicalQuation() {
        return inboundclinicalQuation;
    }

    public void setInboundclinicalQuation(String inboundclinicalQuation) {
        this.inboundclinicalQuation = inboundclinicalQuation;
    }

    public String getInboundclinicalAns() {
        return inboundclinicalAns;
    }

    public void setInboundclinicalAns(String inboundclinicalAns) {
        this.inboundclinicalAns = inboundclinicalAns;
    }

    public MstUnit getInboundccUnitId() {
        return inboundccUnitId;
    }

    public void setInboundccUnitId(MstUnit inboundccUnitId) {
        this.inboundccUnitId = inboundccUnitId;
    }

    public MstStaff getInboundccStaffId() {
        return inboundccStaffId;
    }

    public void setInboundccStaffId(MstStaff inboundccStaffId) {
        this.inboundccStaffId = inboundccStaffId;
    }

    public MstPatient getInboundccPatientId() {
        return inboundccPatientId;
    }

    public void setInboundccPatientId(MstPatient inboundccPatientId) {
        this.inboundccPatientId = inboundccPatientId;
    }

    public NcliClinicalFormCategory getInboundClinicalFromCategoryId() {        return inboundClinicalFromCategoryId;    }

    public void setInboundClinicalFromCategoryId(NcliClinicalFormCategory inboundClinicalFromCategoryId) {        this.inboundClinicalFromCategoryId = inboundClinicalFromCategoryId;    }

    public NcliClinicalForm getInboundClinicalFormId() {        return inboundClinicalFormId;    }

    public void setInboundClinicalFormId(NcliClinicalForm inboundClinicalFormId) {        this.inboundClinicalFormId = inboundClinicalFormId;    }

    public MstVisit getInboundVisitId() {        return inboundVisitId;    }

    public void setInboundVisitId(MstVisit inboundVisitId) {        this.inboundVisitId = inboundVisitId;    }
}
