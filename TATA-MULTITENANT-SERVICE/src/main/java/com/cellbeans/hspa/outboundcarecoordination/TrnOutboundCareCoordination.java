package com.cellbeans.hspa.outboundcarecoordination;

import com.cellbeans.hspa.memrcallcategory.MemrCallCategory;
import com.cellbeans.hspa.memrcallsubcategory.MemrCallSubCategory;
import com.cellbeans.hspa.memrresolutioncategory.MemrResolutionCategory;
import com.cellbeans.hspa.memrservicetype.MemrServiceType;
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
@Table(name = "trn_outbound_cc")
public class TrnOutboundCareCoordination implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "outboundccId", unique = true, nullable = true)
    private long outboundccId;

    @JsonInclude(NON_NULL)
    @Column(name = "outboundQuery")
    private String outboundQuery;

    @JsonInclude(NON_NULL)
    @Column(name = "outboundResponce")
    private String outboundResponce;

    @JsonInclude(NON_NULL)
    @Column(name = "outbounddescription")
    private String outbounddescription;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundserviceTypeId")
    private MemrServiceType outboundserviceTypeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundcallCategoryId")
    private MemrCallCategory outboundcallCategoryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundcallsubcategory")
    private MemrCallSubCategory outboundcallsubcategory;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundresolutionCategoryId")
    private MemrResolutionCategory outboundresolutionCategoryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundccUnitId")
    private MstUnit outboundccUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundccStaffId")
    private MstStaff outboundccStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundccPatientId")
    private MstPatient outboundccPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundClinicalFromCategoryId")
    private NcliClinicalFormCategory outboundClinicalFromCategoryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundClinicalFormId")
    private NcliClinicalForm outboundClinicalFormId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "outboundVisitId")
    private MstVisit outboundVisitId;


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

    public long getOutboundccId() {
        return outboundccId;
    }

    public void setOutboundccId(long outboundccId) {
        this.outboundccId = outboundccId;
    }

    public String getOutboundQuery() {
        return outboundQuery;
    }

    public void setOutboundQuery(String outboundQuery) {
        this.outboundQuery = outboundQuery;
    }

    public String getOutboundResponce() {
        return outboundResponce;
    }

    public void setOutboundResponce(String outboundResponce) {
        this.outboundResponce = outboundResponce;
    }

    public String getOutbounddescription() {
        return outbounddescription;
    }

    public void setOutbounddescription(String outbounddescription) {
        this.outbounddescription = outbounddescription;
    }

    public MemrServiceType getOutboundserviceTypeId() {
        return outboundserviceTypeId;
    }

    public void setOutboundserviceTypeId(MemrServiceType outboundserviceTypeId) {
        this.outboundserviceTypeId = outboundserviceTypeId;
    }

    public MemrCallCategory getOutboundcallCategoryId() {
        return outboundcallCategoryId;
    }

    public void setOutboundcallCategoryId(MemrCallCategory outboundcallCategoryId) {
        this.outboundcallCategoryId = outboundcallCategoryId;
    }

    public MemrCallSubCategory getOutboundcallsubcategory() {
        return outboundcallsubcategory;
    }

    public void setOutboundcallsubcategory(MemrCallSubCategory outboundcallsubcategory) {
        this.outboundcallsubcategory = outboundcallsubcategory;
    }

    public MemrResolutionCategory getOutboundresolutionCategoryId() {
        return outboundresolutionCategoryId;
    }

    public void setOutboundresolutionCategoryId(MemrResolutionCategory outboundresolutionCategoryId) {
        this.outboundresolutionCategoryId = outboundresolutionCategoryId;
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

    public MstUnit getOutboundccUnitId() {
        return outboundccUnitId;
    }

    public void setOutboundccUnitId(MstUnit outboundccUnitId) {
        this.outboundccUnitId = outboundccUnitId;
    }

    public MstStaff getOutboundccStaffId() {
        return outboundccStaffId;
    }

    public void setOutboundccStaffId(MstStaff outboundccStaffId) {
        this.outboundccStaffId = outboundccStaffId;
    }

    public MstPatient getOutboundccPatientId() {
        return outboundccPatientId;
    }

    public void setOutboundccPatientId(MstPatient outboundccPatientId) {
        this.outboundccPatientId = outboundccPatientId;
    }

    public NcliClinicalFormCategory getOutboundClinicalFromCategoryId() {        return outboundClinicalFromCategoryId;    }

    public void setOutboundClinicalFromCategoryId(NcliClinicalFormCategory outboundClinicalFromCategoryId) {        this.outboundClinicalFromCategoryId = outboundClinicalFromCategoryId;    }

    public NcliClinicalForm getOutboundClinicalFormId() {        return outboundClinicalFormId;    }

    public void setOutboundClinicalFormId(NcliClinicalForm outboundClinicalFormId) {        this.outboundClinicalFormId = outboundClinicalFormId;    }

    public MstVisit getOutboundVisitId() {        return outboundVisitId;    }

    public void setOutboundVisitId(MstVisit outboundVisitId) {        this.outboundVisitId = outboundVisitId;    }
}
