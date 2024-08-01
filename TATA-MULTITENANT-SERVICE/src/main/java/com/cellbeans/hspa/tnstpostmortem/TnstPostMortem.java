package com.cellbeans.hspa.tnstpostmortem;

import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "tnst_post_mortem")
public class TnstPostMortem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pcId", unique = true, nullable = true)
    private long pcId;

    @JsonInclude(NON_NULL)
    @Column(name = "pcNumber")
    private int pcNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "pcPatientName")
    private String pcPatientName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pcVisitId")
    private MstVisit pcVisitId;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "pcDate")
    private Date pcDate;

    @JsonInclude(NON_NULL)
    @Column(name = "pcTime")
    private String pcTime;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pcDepartmentId")
    private MstDepartment pcDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pcDeclaredByStaffId")
    private MstStaff pcDeclaredByStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "pcSampletaken")
    private String pcSampletaken;

    @JsonInclude(NON_NULL)
    @Column(name = "pcBodyHandover")
    private String pcBodyHandover;

    @JsonInclude(NON_NULL)
    @Column(name = "pcInternalExaminations")
    private String pcInternalExaminations;

    @JsonInclude(NON_NULL)
    @Column(name = "pcExternalExaminations")
    private String pcExternalExaminations;

    @JsonInclude(NON_NULL)
    @Column(name = "pcClinicalExaminations")
    private String pcClinicalExaminations;

    @JsonInclude(NON_NULL)
    @Column(name = "pcForensicInvestigation")
    private String pcForensicInvestigation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pcUnitId")
    private MstUnit pcUnitId;

    @JsonIgnore
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
    @ManyToOne
    @JoinColumn(name = "pcAuthorizedStaffId")
    private MstStaff pcAuthorizedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "pcIsAuthorized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean pcIsAuthorized = false;

    public long getPcId() {
        return pcId;
    }

    public void setPcId(long pcId) {
        this.pcId = pcId;
    }

    public int getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(int pcNumber) {
        this.pcNumber = pcNumber;
    }

    public MstVisit getPcVisitId() {
        return pcVisitId;
    }

    public void setPcVisitId(MstVisit pcVisitId) {
        this.pcVisitId = pcVisitId;
    }

    public Date getPcDate() {
        return pcDate;
    }

    public void setPcDate(Date pcDate) {
        this.pcDate = pcDate;
    }

    public String getPcTime() {
        return pcTime;
    }

    public void setPcTime(String pcTime) {
        this.pcTime = pcTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public MstDepartment getPcDepartmentId() {
        return pcDepartmentId;
    }

    public void setPcDepartmentId(MstDepartment pcDepartmentId) {
        this.pcDepartmentId = pcDepartmentId;
    }

    public String getPcSampletaken() {
        return pcSampletaken;
    }

    public void setPcSampletaken(String pcSampletaken) {
        this.pcSampletaken = pcSampletaken;
    }

    public String getPcBodyHandover() {
        return pcBodyHandover;
    }

    public void setPcBodyHandover(String pcBodyHandover) {
        this.pcBodyHandover = pcBodyHandover;
    }

    public String getPcInternalExaminations() {
        return pcInternalExaminations;
    }

    public void setPcInternalExaminations(String pcInternalExaminations) {
        this.pcInternalExaminations = pcInternalExaminations;
    }

    public String getPcExternalExaminations() {
        return pcExternalExaminations;
    }

    public void setPcExternalExaminations(String pcExternalExaminations) {
        this.pcExternalExaminations = pcExternalExaminations;
    }

    public String getPcClinicalExaminations() {
        return pcClinicalExaminations;
    }

    public void setPcClinicalExaminations(String pcClinicalExaminations) {
        this.pcClinicalExaminations = pcClinicalExaminations;
    }

    public String getPcForensicInvestigation() {
        return pcForensicInvestigation;
    }

    public void setPcForensicInvestigation(String pcForensicInvestigation) {
        this.pcForensicInvestigation = pcForensicInvestigation;
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

    public MstUnit getPcUnitId() {
        return pcUnitId;
    }

    public void setPcUnitId(MstUnit pcUnitId) {
        this.pcUnitId = pcUnitId;
    }

    public String getPcPatientName() {
        return pcPatientName;
    }

    public void setPcPatientName(String pcPatientName) {
        this.pcPatientName = pcPatientName;
    }

    public MstStaff getPcDeclaredByStaffId() {
        return pcDeclaredByStaffId;
    }

    public void setPcDeclaredByStaffId(MstStaff pcDeclaredByStaffId) {
        this.pcDeclaredByStaffId = pcDeclaredByStaffId;
    }

    public MstStaff getPcAuthorizedStaffId() {
        return pcAuthorizedStaffId;
    }

    public void setPcAuthorizedStaffId(MstStaff pcAuthorizedStaffId) {
        this.pcAuthorizedStaffId = pcAuthorizedStaffId;
    }

    public Boolean getPcIsAuthorized() {
        return pcIsAuthorized;
    }

    public void setPcIsAuthorized(Boolean pcIsAuthorized) {
        this.pcIsAuthorized = pcIsAuthorized;
    }
}
