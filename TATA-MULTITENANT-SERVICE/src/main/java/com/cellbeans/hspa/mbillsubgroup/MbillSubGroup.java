package com.cellbeans.hspa.mbillsubgroup;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
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

/**
 * @author Mohit Shinde
 * This Class is responsible for
 * <p>
 * Class has
 * {@link ManyToOne} with sgGroupId
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mbill_sub_group")
public class MbillSubGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sgId", unique = true, nullable = true)
    private long sgId;

    @JsonInclude(NON_NULL)
    @Column(name = "sgName")
    private String sgName;

    @JsonInclude(NON_NULL)
    @Column(name = "sgCode")
    private String sgCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sgGroupId")
    private MbillGroup sgGroupId;

    @JsonInclude(NON_NULL)
    @Column(name = "sgLedgerName")
    private String sgLedgerName;

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

    public long getSgId() {
        return sgId;
    }

    public void setSgId(long sgId) {
        this.sgId = sgId;
    }

    public String getSgName() {
        return sgName;
    }

    public void setSgName(String sgName) {
        this.sgName = sgName;
    }

    public String getSgCode() {
        return sgCode;
    }

    public void setSgCode(String sgCode) {
        this.sgCode = sgCode;
    }

    public MbillGroup getSgGroupId() {
        return sgGroupId;
    }

    public void setSgGroupId(MbillGroup sgGroupId) {
        this.sgGroupId = sgGroupId;
    }

    public String getSgLedgerName() {
        return sgLedgerName;
    }

    public void setSgLedgerName(String sgLedgerName) {
        this.sgLedgerName = sgLedgerName;
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
