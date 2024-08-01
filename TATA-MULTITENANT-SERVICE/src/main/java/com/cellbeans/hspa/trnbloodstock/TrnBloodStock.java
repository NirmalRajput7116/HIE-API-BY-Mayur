package com.cellbeans.hspa.trnbloodstock;

import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
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
@Table(name = "trn_blood_stock")
public class TrnBloodStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsId", unique = true, nullable = true)
    private long bsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bsBloodgroupId")
    private MstBloodgroup bsBloodgroupId;

    @JsonInclude(NON_NULL)
    @Column(name = "bsNoOf")
    private int bsNoOf;

    @JsonInclude(NON_NULL)
    @Column(name = "bsUnit")
    private String bsUnit;
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

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    public long getBsId() {
        return bsId;
    }

    public void setBsId(int bsId) {
        this.bsId = bsId;
    }

    public MstBloodgroup getBsBloodgroupId() {
        return bsBloodgroupId;
    }

    public void setBsBloodgroupId(MstBloodgroup bsBloodgroupId) {
        this.bsBloodgroupId = bsBloodgroupId;
    }

    public String getBsUnit() {
        return bsUnit;
    }

    public void setBsUnit(String bsUnit) {
        this.bsUnit = bsUnit;
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

    public int getBsNoOf() {
        return bsNoOf;
    }

    public void setBsNoOf(int bsNoOf) {
        this.bsNoOf = bsNoOf;
    }

}
