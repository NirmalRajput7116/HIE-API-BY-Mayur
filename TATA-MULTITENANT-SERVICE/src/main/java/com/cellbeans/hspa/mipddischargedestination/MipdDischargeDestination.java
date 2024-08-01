package com.cellbeans.hspa.mipddischargedestination;

import com.cellbeans.hspa.mstunit.MstUnit;
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
@Table(name = "mipd_discharge_destination")
public class MipdDischargeDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ddId", unique = true, nullable = true)
    private long ddId;

    @JsonInclude(NON_NULL)
    @Column(name = "ddName")
    private String ddName;

    @JsonInclude(NON_NULL)
    @Column(name = "ddCode")
    private String ddCode;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "destinationUnitId")
    private MstUnit destinationUnitId;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public MstUnit getDestinationUnitId() {
        return destinationUnitId;
    }

    public void setDestinationUnitId(MstUnit destinationUnitId) {
        this.destinationUnitId = destinationUnitId;
    }

    public long getDdId() {
        return ddId;
    }

    public void setDdId(long ddId) {
        this.ddId = ddId;
    }

    public String getDdName() {
        return ddName;
    }

    public void setDdName(String ddName) {
        this.ddName = ddName;
    }

    public String getDdCode() {
        return ddCode;
    }

    public void setDdCode(String ddCode) {
        this.ddCode = ddCode;
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
