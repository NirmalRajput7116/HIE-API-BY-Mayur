package com.cellbeans.hspa.mstreferringentity;

import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mstspeciality.MstSpeciality;
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
@Table(name = "mst_referring_entity")
public class MstReferringEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reId", unique = true, nullable = true)
    private long reId;

    @JsonInclude(NON_NULL)
    @Column(name = "reName")
    private String reName;

    @JsonInclude(NON_NULL)
    @Column(name = "reCode")
    private String reCode;

    @JsonInclude(NON_NULL)
    @Column(name = "reAreaCovered")
    private String reAreaCovered;

    @JsonInclude(NON_NULL)
    @Column(name = "reDob")
    private String reDob;

    @JsonInclude(NON_NULL)
    @Column(name = "reFieldOfProficiency")
    private String reFieldOfProficiency;

    @JsonInclude(NON_NULL)
    @Column(name = "reAddress")
    private String reAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "reCell")
    private String reCell;

    @JsonInclude(NON_NULL)
    @Column(name = "reAccountNo")
    private String reAccountNo;

    @JsonInclude(NON_NULL)
    @Column(name = "rePhone")
    private String rePhone;

    @JsonInclude(NON_NULL)
    @Column(name = "reDegree")
    private String reDegree;

    @JsonInclude(NON_NULL)
    @Column(name = "reEmail")
    private String reEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "reRegistrationNo")
    private String reRegistrationNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reRetId")
    private MstReferringEntityType reRetId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reBankId")
    private MstBank reBankId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "reSpecialityId")
    private MstSpeciality reSpecialityId;

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

    public long getReId() {
        return reId;
    }

    public void setReId(long reId) {
        this.reId = reId;
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getReAreaCovered() {
        return reAreaCovered;
    }

    public void setReAreaCovered(String reAreaCovered) {
        this.reAreaCovered = reAreaCovered;
    }

    public String getReDob() {
        return reDob;
    }

    public void setReDob(String reDob) {
        this.reDob = reDob;
    }

    public String getReFieldOfProficiency() {
        return reFieldOfProficiency;
    }

    public void setReFieldOfProficiency(String reFieldOfProficiency) {
        this.reFieldOfProficiency = reFieldOfProficiency;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getReCell() {
        return reCell;
    }

    public void setReCell(String reCell) {
        this.reCell = reCell;
    }

    public String getReAccountNo() {
        return reAccountNo;
    }

    public void setReAccountNo(String reAccountNo) {
        this.reAccountNo = reAccountNo;
    }

    public String getRePhone() {
        return rePhone;
    }

    public void setRePhone(String rePhone) {
        this.rePhone = rePhone;
    }

    public String getReDegree() {
        return reDegree;
    }

    public void setReDegree(String reDegree) {
        this.reDegree = reDegree;
    }

    public String getReEmail() {
        return reEmail;
    }

    public void setReEmail(String reEmail) {
        this.reEmail = reEmail;
    }

    public String getReRegistrationNo() {
        return reRegistrationNo;
    }

    public void setReRegistrationNo(String reRegistrationNo) {
        this.reRegistrationNo = reRegistrationNo;
    }

    public MstReferringEntityType getReRetId() {
        return reRetId;
    }

    public void setReRetId(MstReferringEntityType reRetId) {
        this.reRetId = reRetId;
    }

    public MstBank getReBankId() {
        return reBankId;
    }

    public void setReBankId(MstBank reBankId) {
        this.reBankId = reBankId;
    }

    public MstSpeciality getReSpecialityId() {
        return reSpecialityId;
    }

    public void setReSpecialityId(MstSpeciality reSpecialityId) {
        this.reSpecialityId = reSpecialityId;
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
