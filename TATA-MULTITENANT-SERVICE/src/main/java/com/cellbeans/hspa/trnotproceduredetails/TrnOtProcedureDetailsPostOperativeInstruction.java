package com.cellbeans.hspa.trnotproceduredetails;

import com.cellbeans.hspa.mstpostoperativeinstructions.MstPostOperativeInstructions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_ot_procedure_details_post_operative_instruction")
public class TrnOtProcedureDetailsPostOperativeInstruction {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opdpooiId", unique = true, nullable = true)
    private long opdpooiId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opdpooiPooiId")
    private MstPostOperativeInstructions opdpooiPooiId;

    @JsonInclude(NON_NULL)
    @Column(name = "opdpooiResult")
    private Boolean opdpooiResult;

    @JsonInclude(NON_NULL)
    @Column(name = "opdpooiRemark")
    private String opdpooiRemark;

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

    public MstPostOperativeInstructions getOpdpooiPooiId() {
        return opdpooiPooiId;
    }

    public void setOpdpooiPooiId(MstPostOperativeInstructions opdpooiPooiId) {
        this.opdpooiPooiId = opdpooiPooiId;
    }

    public Boolean getOpdpooiResult() {
        return opdpooiResult;
    }

    public void setOpdpooiResult(Boolean opdpooiResult) {
        this.opdpooiResult = opdpooiResult;
    }

    public String getOpdpooiRemark() {
        return opdpooiRemark;
    }

    public void setOpdpooiRemark(String opdpooiRemark) {
        this.opdpooiRemark = opdpooiRemark;
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
