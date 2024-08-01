package com.cellbeans.hspa.tnstinpatient;

import com.cellbeans.hspa.invindentflag.InvIndentFlag;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "tnst_in_patient")
public class TnstInPatient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipId", unique = true, nullable = true)
    private long ipId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipAdmissionId")
    private TrnAdmission ipAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipIfId")
    private InvIndentFlag ipIfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipStaffId")
    private MstStaff ipStaffId;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ipTnstIssueItem")
    private List<TnstItemIssue> ipTnstIssueItem;

    @JsonInclude(NON_NULL)
    // 0 = new , 1=requesting , 2= issued , 3= recevied,4=returned,5= returned received
    @Column(name = "ipStatus", columnDefinition = "int default 0")
    private int ipStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "ipConfirm", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ipConfirm = false;

    @JsonInclude(NON_NULL)
    @Column(name = "ipReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ipReturn = false;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getIpId() {
        return ipId;
    }

    public void setIpId(long ipId) {
        this.ipId = ipId;
    }

    public TrnAdmission getIpAdmissionId() {
        return ipAdmissionId;
    }

    public void setIpAdmissionId(TrnAdmission ipAdmissionId) {
        this.ipAdmissionId = ipAdmissionId;
    }

    public InvIndentFlag getIpIfId() {
        return ipIfId;
    }

    public void setIpIfId(InvIndentFlag ipIfId) {
        this.ipIfId = ipIfId;
    }

    public MstStaff getIpStaffId() {
        return ipStaffId;
    }

    public void setIpStaffId(MstStaff ipStaffId) {
        this.ipStaffId = ipStaffId;
    }

    public List<TnstItemIssue> getIpTnstIssueItem() {
        return ipTnstIssueItem;
    }

    public void setIpTnstIssueItem(List<TnstItemIssue> ipTnstIssueItem) {
        this.ipTnstIssueItem = ipTnstIssueItem;
    }

    public Boolean getIpConfirm() {
        return ipConfirm;
    }

    public void setIpConfirm(Boolean ipConfirm) {
        this.ipConfirm = ipConfirm;
    }

    public Boolean getIpReturn() {
        return ipReturn;
    }

    public void setIpReturn(Boolean ipReturn) {
        this.ipReturn = ipReturn;
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

    public int getIpStatus() {
        return ipStatus;
    }

    public void setIpStatus(int ipStatus) {
        this.ipStatus = ipStatus;
    }

}
