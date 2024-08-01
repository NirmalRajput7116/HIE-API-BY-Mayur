package com.cellbeans.hspa.msttaluka;

import com.cellbeans.hspa.mstdistrict.MstDistrict;
import com.cellbeans.hspa.mststate.MstState;
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
@Table(name = "mst_taluka")
public class MstTaluka implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talukaId", unique = true, nullable = true)
    private long talukaId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "talukaStateId")
    private MstState talukaStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "talukaDistrictId")
    private MstDistrict talukaDistrictId;

    @JsonInclude(NON_NULL)
    @Column(name = "talukaName")
    private String talukaName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDelete", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDelete = false;

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

    public long getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(long talukaId) {
        this.talukaId = talukaId;
    }

    public MstDistrict getTalukaDistrictId() {
        return talukaDistrictId;
    }

    public void setTalukaDistrictId(MstDistrict talukaDistrictId) {
        this.talukaDistrictId = talukaDistrictId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
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

    public MstState getTalukaStateId() {
        return talukaStateId;
    }

    public void setTalukaStateId(MstState talukaStateId) {
        this.talukaStateId = talukaStateId;
    }
}
