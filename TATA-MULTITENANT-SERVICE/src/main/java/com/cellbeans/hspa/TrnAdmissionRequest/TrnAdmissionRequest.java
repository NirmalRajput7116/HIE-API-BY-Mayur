package com.cellbeans.hspa.TrnAdmissionRequest;

import com.cellbeans.hspa.mipdward.MipdWard;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstuser.MstUser;
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
@Table(name = "trn_admission_request")
public class TrnAdmissionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admissionrequestId", unique = true, nullable = true)
    private long admissionrequestId;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestMrNo")
    private String admissionrequestMrNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionrequestPatientId")
    private MstPatient admissionrequestPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "admissionrequestUserId")
    private MstUser admissionrequestUserId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionrequestDepartmentId")
    private MstDepartment admissionrequestDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionrequestSdId")
    private MstSubDepartment admissionrequestSdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionrequestStaffId")
    private MstStaff admissionrequestStaffId;
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "admissionrequestBtId")
//    private MstBookingType admissionrequestBtId;
//
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "admissionrequestRetId")
//    private MstReferringEntityType admissionrequestRetId;
//
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "admissionrequestReId")
//    private MstReferringEntity admissionrequestReId;
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "admissionrequestPtId")
//    private MstPatientType admissionrequestPtId;
//    @JsonInclude(NON_NULL)
//    @Column(name = "admissionrequestbookby")
//    private String admissionrequestbookby;
//
//    @JsonInclude(NON_NULL)
//    @Column(name = "admissionrequestSlot")
//    private String admissionrequestSlot;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestRefDoctor")
    private String admissionrequestRefDoctor;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestRemark")
    private String admissionrequestRemark;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "admissionrequestDate")
    private Date admissionrequestDate;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "admissionrequestExpAdmDate")
    private Date admissionrequestExpAdmDate;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestIsConfirm", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionrequestIsConfirm = false;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestIsCancelled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionrequestIsCancelled = false;

    // @JsonInclude(NON_NULL)
    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestIsCancelledreasone")
    private String admissionrequestIsCancelledreasone;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestIsVisitMark", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionrequestIsVisitMark = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionrequestIsBlock", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionrequestIsBlock = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bedWardId")
    private MipdWard bedWardId;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionrequestUnitId")
    private MstUnit admissionrequestUnitId;

    @Transient
    private int count;

    public long getAdmissionrequestId() {
        return admissionrequestId;
    }

    public void setAdmissionrequestId(long admissionrequestId) {
        this.admissionrequestId = admissionrequestId;
    }

    public String getAdmissionrequestMrNo() {
        return admissionrequestMrNo;
    }

    public void setAdmissionrequestMrNo(String admissionrequestMrNo) {
        this.admissionrequestMrNo = admissionrequestMrNo;
    }

    public MstPatient getAdmissionrequestPatientId() {
        return admissionrequestPatientId;
    }

    public void setAdmissionrequestPatientId(MstPatient admissionrequestPatientId) {
        this.admissionrequestPatientId = admissionrequestPatientId;
    }

    public MstUser getAdmissionrequestUserId() {
        return admissionrequestUserId;
    }

    public void setAdmissionrequestUserId(MstUser admissionrequestUserId) {
        this.admissionrequestUserId = admissionrequestUserId;
    }
//    public MstPatientType getAdmissionrequestPtId() {
//        return admissionrequestPtId;
//    }
//
//    public void setAdmissionrequestPtId(MstPatientType admissionrequestPtId) {
//        this.admissionrequestPtId = admissionrequestPtId;
//    }

    public MstDepartment getAdmissionrequestDepartmentId() {
        return admissionrequestDepartmentId;
    }

    public void setAdmissionrequestDepartmentId(MstDepartment admissionrequestDepartmentId) {
        this.admissionrequestDepartmentId = admissionrequestDepartmentId;
    }

    public MstSubDepartment getAdmissionrequestSdId() {
        return admissionrequestSdId;
    }

    public void setAdmissionrequestSdId(MstSubDepartment admissionrequestSdId) {
        this.admissionrequestSdId = admissionrequestSdId;
    }

    public MstStaff getAdmissionrequestStaffId() {
        return admissionrequestStaffId;
    }

    public void setAdmissionrequestStaffId(MstStaff admissionrequestStaffId) {
        this.admissionrequestStaffId = admissionrequestStaffId;
    }
//    public MstBookingType getAdmissionrequestBtId() {
//        return admissionrequestBtId;
//    }
//
//    public void setAdmissionrequestBtId(MstBookingType admissionrequestBtId) {
//        this.admissionrequestBtId = admissionrequestBtId;
//    }
//
//    public MstReferringEntityType getAdmissionrequestRetId() {
//        return admissionrequestRetId;
//    }
//
//    public void setAdmissionrequestRetId(MstReferringEntityType admissionrequestRetId) {
//        this.admissionrequestRetId = admissionrequestRetId;
//    }
//
//    public MstReferringEntity getAdmissionrequestReId() {
//        return admissionrequestReId;
//    }
//
//    public void setAdmissionrequestReId(MstReferringEntity admissionrequestReId) {
//        this.admissionrequestReId = admissionrequestReId;
//    }

    public String getAdmissionrequestRemark() {
        return admissionrequestRemark;
    }

    public void setAdmissionrequestRemark(String admissionrequestRemark) {
        this.admissionrequestRemark = admissionrequestRemark;
    }
//    public String getAdmissionrequestbookby() {
//        return admissionrequestbookby;
//    }
//
//    public void setAdmissionrequestbookby(String admissionrequestbookby) {
//        this.admissionrequestbookby = admissionrequestbookby;
//    }

    public Date getAdmissionrequestDate() {
        return admissionrequestDate;
    }

    public void setAdmissionrequestDate(Date admissionrequestDate) {
        this.admissionrequestDate = admissionrequestDate;
    }
//    public String getAdmissionrequestSlot() {
//        return admissionrequestSlot;
//    }
//
//    public void setAdmissionrequestSlot(String admissionrequestSlot) {
//        this.admissionrequestSlot = admissionrequestSlot;
//    }

    public Boolean getAdmissionrequestIsConfirm() {
        return admissionrequestIsConfirm;
    }

    public void setAdmissionrequestIsConfirm(Boolean admissionrequestIsConfirm) {
        this.admissionrequestIsConfirm = admissionrequestIsConfirm;
    }

    public Boolean getAdmissionrequestIsCancelled() {
        return admissionrequestIsCancelled;
    }

    public void setAdmissionrequestIsCancelled(Boolean admissionrequestIsCancelled) {
        this.admissionrequestIsCancelled = admissionrequestIsCancelled;
    }

    public String getAdmissionrequestIsCancelledreasone() {
        return admissionrequestIsCancelledreasone;
    }

    public void setAdmissionrequestIsCancelledreasone(String admissionrequestIsCancelledreasone) {
        this.admissionrequestIsCancelledreasone = admissionrequestIsCancelledreasone;
    }

    public Boolean getAdmissionrequestIsVisitMark() {
        return admissionrequestIsVisitMark;
    }

    public void setAdmissionrequestIsVisitMark(Boolean admissionrequestIsVisitMark) {
        this.admissionrequestIsVisitMark = admissionrequestIsVisitMark;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getAdmissionrequestIsBlock() {
        return admissionrequestIsBlock;
    }

    public void setAdmissionrequestIsBlock(Boolean admissionrequestIsBlock) {
        this.admissionrequestIsBlock = admissionrequestIsBlock;
    }

    public MipdWard getBedWardId() {
        return bedWardId;
    }

    public void setBedWardId(MipdWard bedWardId) {
        this.bedWardId = bedWardId;
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

    public MstUnit getAdmissionrequestUnitId() {
        return admissionrequestUnitId;
    }

    public void setAdmissionrequestUnitId(MstUnit admissionrequestUnitId) {
        this.admissionrequestUnitId = admissionrequestUnitId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAdmissionrequestRefDoctor() {
        return admissionrequestRefDoctor;
    }

    public void setAdmissionrequestRefDoctor(String admissionrequestRefDoctor) {
        this.admissionrequestRefDoctor = admissionrequestRefDoctor;
    }

    public Date getAdmissionrequestExpAdmDate() {
        return admissionrequestExpAdmDate;
    }

    public void setAdmissionrequestExpAdmDate(Date admissionrequestExpAdmDate) {
        this.admissionrequestExpAdmDate = admissionrequestExpAdmDate;
    }

    @Override
    public String toString() {
        return "TrnAdmissionRequest{" +
                "admissionrequestId=" + admissionrequestId +
                ", admissionrequestMrNo='" + admissionrequestMrNo + '\'' +
                ", admissionrequestPatientId=" + admissionrequestPatientId +
                ", admissionrequestUserId=" + admissionrequestUserId +
                ", admissionrequestDepartmentId=" + admissionrequestDepartmentId +
                ", admissionrequestSdId=" + admissionrequestSdId +
                ", admissionrequestStaffId=" + admissionrequestStaffId +
                ", admissionrequestRefDoctor='" + admissionrequestRefDoctor + '\'' +
                ", admissionrequestRemark='" + admissionrequestRemark + '\'' +
                ", admissionrequestDate=" + admissionrequestDate +
                ", admissionrequestExpAdmDate=" + admissionrequestExpAdmDate +
                ", admissionrequestIsConfirm=" + admissionrequestIsConfirm +
                ", admissionrequestIsCancelled=" + admissionrequestIsCancelled +
                ", admissionrequestIsCancelledreasone='" + admissionrequestIsCancelledreasone + '\'' +
                ", admissionrequestIsVisitMark=" + admissionrequestIsVisitMark +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", admissionrequestIsBlock=" + admissionrequestIsBlock +
                ", bedWardId=" + bedWardId +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", admissionrequestUnitId=" + admissionrequestUnitId +
                ", count=" + count +
                '}';
    }
}
