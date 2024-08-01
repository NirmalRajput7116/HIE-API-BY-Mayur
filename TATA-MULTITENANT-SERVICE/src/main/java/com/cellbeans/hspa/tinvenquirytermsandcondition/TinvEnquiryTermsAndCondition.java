package com.cellbeans.hspa.tinvenquirytermsandcondition;

import com.cellbeans.hspa.invitemtermsandcondition.InvItemTermsAndCondition;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchaseitemenquiry.TinvPurchaseItemEnquiry;
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
@Table(name = "tinv_enquiry_terms_and_condition")
public class TinvEnquiryTermsAndCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etacId", unique = true, nullable = true)
    private long etacId;

    @JsonInclude(NON_NULL)
    @Column(name = "etacName")
    private String etacName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etacPieId")
    private TinvPurchaseItemEnquiry etacPieId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etacTacId")
    private InvItemTermsAndCondition etacTacId;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etacUnitId")
    private MstUnit etacUnitId;

    public MstUnit getEtacUnitId() {
        return etacUnitId;
    }

    @JsonIgnore
    public void setEtacUnitId(MstUnit etacUnitId) {
        this.etacUnitId = etacUnitId;
    }

    public long getEtacId() {
        return etacId;
    }

    public void setEtacId(int etacId) {
        this.etacId = etacId;
    }

    public TinvPurchaseItemEnquiry getEtacPieId() {
        return etacPieId;
    }

    public void setEtacPieId(TinvPurchaseItemEnquiry etacPieId) {
        this.etacPieId = etacPieId;
    }

    public InvItemTermsAndCondition getEtacTacId() {
        return etacTacId;
    }

    public void setEtacTacId(InvItemTermsAndCondition etacTacId) {
        this.etacTacId = etacTacId;
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

    public String getEtacName() {
        return etacName;
    }

    public void setEtacName(String etacName) {
        this.etacName = etacName;
    }

}
