package com.cellbeans.hspa.trnotservicedetails;

import com.cellbeans.hspa.mbillservice.MbillService;
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
@Table(name = "trn_ot_service_details")
public class TrnOtServiceDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "osrdId", unique = true, nullable = true)
    private long osrdId;

    @JsonInclude(NON_NULL)
    @Column(name = "osrdOpsId")
    private long osrdOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "osrdOpdId")
    private long osrdOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "osrdProcedureId")
    private long osrdProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "osrdServiceId")
    private MbillService osrdServiceId;

    @JsonInclude(NON_NULL)
    @Column(name = "osrdIsPerformed", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean osrdIsPerformed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "osrdRemark")
    private String osrdRemark;

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

    public long getOsrdId() {
        return osrdId;
    }

    public void setOsrdId(long osrdId) {
        this.osrdId = osrdId;
    }

    public long getOsrdOpsId() {
        return osrdOpsId;
    }

    public void setOsrdOpsId(long osrdOpsId) {
        this.osrdOpsId = osrdOpsId;
    }

    public long getOsrdOpdId() {
        return osrdOpdId;
    }

    public void setOsrdOpdId(long osrdOpdId) {
        this.osrdOpdId = osrdOpdId;
    }

    public long getOsrdProcedureId() {
        return osrdProcedureId;
    }

    public void setOsrdProcedureId(long osrdProcedureId) {
        this.osrdProcedureId = osrdProcedureId;
    }

    public MbillService getOsrdServiceId() {
        return osrdServiceId;
    }

    public void setOsrdServiceId(MbillService osrdServiceId) {
        this.osrdServiceId = osrdServiceId;
    }

    public Boolean getOsrdIsPerformed() {
        return osrdIsPerformed;
    }

    public void setOsrdIsPerformed(Boolean osrdIsPerformed) {
        this.osrdIsPerformed = osrdIsPerformed;
    }

    public String getOsrdRemark() {
        return osrdRemark;
    }

    public void setOsrdRemark(String osrdRemark) {
        this.osrdRemark = osrdRemark;
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
