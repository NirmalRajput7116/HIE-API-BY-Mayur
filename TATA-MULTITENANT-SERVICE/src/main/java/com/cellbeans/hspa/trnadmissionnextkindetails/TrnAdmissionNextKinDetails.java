package com.cellbeans.hspa.trnadmissionnextkindetails;

import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstoccupation.MstOccupation;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.msttitle.MstTitle;
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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_admission_next_kin_details")
public class TrnAdmissionNextKinDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ankdId", unique = true, nullable = true)
    private long ankdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ankdTitleId")
    private MstTitle ankdTitleId;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdFirstname")
    private String ankdFirstname;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdLastname")
    private String ankdLastname;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ankdCompnayId")
    private MstCompany ankdCompnayId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ankdOccupationId")
    private MstOccupation ankdOccupationId;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdAddress")
    private String ankdAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdRemark")
    private String ankdRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ankdRelationId")
    private MstRelation ankdRelationId;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdMobileNo")
    private String ankdMobileNo;

    @JsonInclude(NON_NULL)
    @Column(name = "ankdPhoneNo")
    private String ankdPhoneNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ankdAdmissionId")
    private TrnAdmission ankdAdmissionId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getAnkdId() {
        return ankdId;
    }

    public void setAnkdId(int ankdId) {
        this.ankdId = ankdId;
    }

    public MstTitle getAnkdTitleId() {
        return ankdTitleId;
    }

    public void setAnkdTitleId(MstTitle ankdTitleId) {
        this.ankdTitleId = ankdTitleId;
    }

    public String getAnkdFirstname() {
        return ankdFirstname;
    }

    public void setAnkdFirstname(String ankdFirstname) {
        this.ankdFirstname = ankdFirstname;
    }

    public String getAnkdLastname() {
        return ankdLastname;
    }

    public void setAnkdLastname(String ankdLastname) {
        this.ankdLastname = ankdLastname;
    }

    public MstCompany getAnkdCompnayId() {
        return ankdCompnayId;
    }

    public void setAnkdCompnayId(MstCompany ankdCompnayId) {
        this.ankdCompnayId = ankdCompnayId;
    }

    public MstOccupation getAnkdOccupationId() {
        return ankdOccupationId;
    }

    public void setAnkdOccupationId(MstOccupation ankdOccupationId) {
        this.ankdOccupationId = ankdOccupationId;
    }

    public String getAnkdAddress() {
        return ankdAddress;
    }

    public void setAnkdAddress(String ankdAddress) {
        this.ankdAddress = ankdAddress;
    }

    public String getAnkdRemark() {
        return ankdRemark;
    }

    public void setAnkdRemark(String ankdRemark) {
        this.ankdRemark = ankdRemark;
    }

    public MstRelation getAnkdRelationId() {
        return ankdRelationId;
    }

    public void setAnkdRelationId(MstRelation ankdRelationId) {
        this.ankdRelationId = ankdRelationId;
    }

    public String getAnkdMobileNo() {
        return ankdMobileNo;
    }

    public void setAnkdMobileNo(String ankdMobileNo) {
        this.ankdMobileNo = ankdMobileNo;
    }

    public String getAnkdPhoneNo() {
        return ankdPhoneNo;
    }

    public void setAnkdPhoneNo(String ankdPhoneNo) {
        this.ankdPhoneNo = ankdPhoneNo;
    }

    public TrnAdmission getAnkdAdmissionId() {
        return ankdAdmissionId;
    }

    public void setAnkdAdmissionId(TrnAdmission ankdAdmissionId) {
        this.ankdAdmissionId = ankdAdmissionId;
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
