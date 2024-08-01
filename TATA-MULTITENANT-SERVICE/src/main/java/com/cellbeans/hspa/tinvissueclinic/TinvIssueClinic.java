package com.cellbeans.hspa.tinvissueclinic;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItem;
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
@Table(name = "tinv_issue_clinic")
public class TinvIssueClinic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    List<TinvIssueClinicItem> tinvIssueClinicItems;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icId", unique = true, nullable = true)
    private long icId;
    @JsonInclude(NON_NULL)
    @Column(name = "icDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date icDate;
    @JsonInclude(NON_NULL)
    @Column(name = "icNo")
    private String icNo;
    @Transient
    private long count;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icFromStoreId")
    private InvStore icFromStoreId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icToStoreId")
    private InvStore icToStoreId;
    @JsonInclude(NON_NULL)
    @Column(name = "icSdId")
    private String icSdId;
    @JsonInclude(NON_NULL)
    @Column(name = "icTotalIssuedItem")
    private int icTotalIssuedItem;
    @JsonInclude(NON_NULL)
    @Column(name = "icTotalAmount")
    private double icTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "icTotalMrp")
    private double icTotalMrp;
    @JsonInclude(NON_NULL)
    @Column(name = "icRemark")
    private String icRemark;
    @JsonInclude(NON_NULL)
    @Column(name = "icIndentNo")
    private String icIndentNo;
    @JsonInclude(NON_NULL)
    @Column(name = "icAgainstIndent")
    private Boolean icAgainstIndent;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icStaffD")
    private MstStaff icStaffD;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isCancel", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCancel = false;
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
    @JoinColumn(name = "issueClinicUnitId")
    private MstUnit issueClinicUnitId;
    @JsonInclude(NON_NULL)
    @Column(name = "persionIcDate")
    private Date persionIcDate;
    @JsonInclude(NON_NULL)
    @Column(name = "issueIndentStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean issueIndentStatus = false;

    public Boolean getIssueIndentStatus() {
        return issueIndentStatus;
    }

    public void setIssueIndentStatus(Boolean issueIndentStatus) {
        this.issueIndentStatus = issueIndentStatus;
    }

    public Date getPersionIcDate() {
        return persionIcDate;
    }

    public void setPersionIcDate(Date persionIcDate) {
        this.persionIcDate = persionIcDate;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    public List<TinvIssueClinicItem> getTinvIssueClinicItems() {
        return tinvIssueClinicItems;
    }

    public void setTinvIssueClinicItems(List<TinvIssueClinicItem> tinvIssueClinicItems) {
        this.tinvIssueClinicItems = tinvIssueClinicItems;
    }

    public MstUnit getIssueClinicUnitId() {
        return issueClinicUnitId;
    }

    public void setIssueClinicUnitId(MstUnit issueClinicUnitId) {
        this.issueClinicUnitId = issueClinicUnitId;
    }

    public long getIcId() {
        return icId;
    }

    public void setIcId(int icId) {
        this.icId = icId;
    }

    public Date getIcDate() {
        return icDate;
    }

    public void setIcDate(Date icDate) {
        this.icDate = icDate;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public InvStore getIcFromStoreId() {
        return icFromStoreId;
    }

    public void setIcFromStoreId(InvStore icFromStoreId) {
        this.icFromStoreId = icFromStoreId;
    }

    public InvStore getIcToStoreId() {
        return icToStoreId;
    }

    public void setIcToStoreId(InvStore icToStoreId) {
        this.icToStoreId = icToStoreId;
    }

    public String getIcSdId() {
        return icSdId;
    }

    public void setIcSdId(String icSdId) {
        this.icSdId = icSdId;
    }

    public int getIcTotalIssuedItem() {
        return icTotalIssuedItem;
    }

    public void setIcTotalIssuedItem(int icTotalIssuedItem) {
        this.icTotalIssuedItem = icTotalIssuedItem;
    }

    public double getIcTotalAmount() {
        return icTotalAmount;
    }

    public void setIcTotalAmount(double icTotalAmount) {
        this.icTotalAmount = icTotalAmount;
    }

    public double getIcTotalMrp() {
        return icTotalMrp;
    }

    public void setIcTotalMrp(double icTotalMrp) {
        this.icTotalMrp = icTotalMrp;
    }

    public String getIcRemark() {
        return icRemark;
    }

    public void setIcRemark(String icRemark) {
        this.icRemark = icRemark;
    }

    public MstStaff getIcStaffD() {
        return icStaffD;
    }

    public void setIcStaffD(MstStaff icStaffD) {
        this.icStaffD = icStaffD;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getIcIndentNo() {
        return icIndentNo;
    }

    public void setIcIndentNo(String icIndentNo) {
        this.icIndentNo = icIndentNo;
    }

}
