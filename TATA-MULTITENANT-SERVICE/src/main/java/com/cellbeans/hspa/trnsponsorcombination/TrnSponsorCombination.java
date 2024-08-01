package com.cellbeans.hspa.trnsponsorcombination;

import com.cellbeans.hspa.mbillsponsorpaydetails.MbillSponcerPayDetails;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstcompanytype.MstCompanyType;
import com.cellbeans.hspa.mstpriority.MstPriority;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "trn_sponsor_combination")
public class TrnSponsorCombination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scId", unique = true, nullable = true)
    private long scId;

    @JsonInclude(NON_NULL)
    @Column(name = "scAdvancedAmount")
    private String scAdvancedAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "scPercentage")
    private String scPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "scBalanceAmount")
    private String scBalanceAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "scDateOfIssue")
    private String scDateOfIssue;

    @JsonInclude(NON_NULL)
    @Column(name = "scExpiryDate")
    private String scExpiryDate;

    @JsonInclude(NON_NULL)
    private String scExpiryDatePersian;

    @JsonInclude(NON_NULL)
    private String scDateOfIssuePersian;

    @JsonInclude(NON_NULL)
    @Column(name = "scPolicyNo")
    private String scPolicyNo;

    @JsonInclude(NON_NULL)
    @Column(name = "scUhidNo")
    private String scUhidNo;

    @JsonInclude(NON_NULL)
    @Column(name = "scPolicyCode")
    private String scPolicyCode;

    @JsonInclude(NON_NULL)
    @Column(name = "scDeductibles")
    private Float scDeductibles;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scPriorityId")
    private MstPriority scPriorityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scTariffId")
    private MbillTariff scTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scCtId")
    private MstCompanyType scCtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scCompanyId")
    private MstCompany scCompanyId;

   /* @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scSpdId")
    private MbillSponcerPayDetails scSpdId;*/

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(name = "sponsorpaydetails")
    private List<MbillSponcerPayDetails> scSpdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scUserId")
    private MstUser scUserId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scVisitId")
    private MstVisit scVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "scSponcerCard")
    private String scSponcerCard;

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

    public String getScSponcerCard() {
        return scSponcerCard;
    }

    public void setScSponcerCard(String scSponcerCard) {
        this.scSponcerCard = scSponcerCard;
    }

    public long getScId() {
        return scId;
    }

    public void setScId(int scId) {
        this.scId = scId;
    }

    public String getScAdvancedAmount() {
        return scAdvancedAmount;
    }

    public void setScAdvancedAmount(String scAdvancedAmount) {
        this.scAdvancedAmount = scAdvancedAmount;
    }

    public String getScBalanceAmount() {
        return scBalanceAmount;
    }

    public void setScBalanceAmount(String scBalanceAmount) {
        this.scBalanceAmount = scBalanceAmount;
    }

    public String getScPolicyNo() {
        return scPolicyNo;
    }

    public void setScPolicyNo(String scPolicyNo) {
        this.scPolicyNo = scPolicyNo;
    }

    public String getScPolicyCode() {
        return scPolicyCode;
    }

    public void setScPolicyCode(String scPolicyCode) {
        this.scPolicyCode = scPolicyCode;
    }

    public MstPriority getScPriorityId() {
        return scPriorityId;
    }

    public void setScPriorityId(MstPriority scPriorityId) {
        this.scPriorityId = scPriorityId;
    }

    public MbillTariff getScTariffId() {
        return scTariffId;
    }

    public void setScTariffId(MbillTariff scTariffId) {
        this.scTariffId = scTariffId;
    }

    public MstCompanyType getScCtId() {
        return scCtId;
    }

    public void setScCtId(MstCompanyType scCtId) {
        this.scCtId = scCtId;
    }

    //    public int getScTOR() {
//        return scTOR;
//    }
//
    public void setScDeductibles(Float scDeductibles) {
        this.scDeductibles = scDeductibles;
    }

    public Float getScDeductibles() {
        return scDeductibles;
    }

    public List<MbillSponcerPayDetails> getScSpdId() {
        return scSpdId;
    }

    public void setScSpdId(List<MbillSponcerPayDetails> scSpdId) {
        this.scSpdId = scSpdId;
    }
//    public void setScTOR(int scTOR) {
//        this.scTOR = scTOR;
//    }

    public MstUser getScUserId() {
        return scUserId;
    }

    public void setScUserId(MstUser scUserId) {
        this.scUserId = scUserId;
    }

    public MstVisit getScVisitId() {
        return scVisitId;
    }

    public void setScVisitId(MstVisit scVisitId) {
        this.scVisitId = scVisitId;
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

    public String getScDateOfIssue() {
        return scDateOfIssue;
    }

    public void setScDateOfIssue(String scDateOfIssue) {
        this.scDateOfIssue = scDateOfIssue;
    }

    public String getScExpiryDate() {
        return scExpiryDate;
    }

    public void setScExpiryDate(String scExpiryDate) {
        this.scExpiryDate = scExpiryDate;
    }

    public MstCompany getScCompanyId() {
        return scCompanyId;
    }

    public void setScCompanyId(MstCompany scCompanyId) {
        this.scCompanyId = scCompanyId;
    }

    public String getScPercentage() {
        return scPercentage;
    }

    public void setScPercentage(String scPercentage) {
        this.scPercentage = scPercentage;
    }

    public String getScExpiryDatePersian() {
        return scExpiryDatePersian;
    }

    public void setScExpiryDatePersian(String scExpiryDatePersian) {
        this.scExpiryDatePersian = scExpiryDatePersian;
    }

    public String getScDateOfIssuePersian() {
        return scDateOfIssuePersian;
    }

    public void setScDateOfIssuePersian(String scDateOfIssuePersian) {
        this.scDateOfIssuePersian = scDateOfIssuePersian;
    }

    public String getScUhidNo() {
        return scUhidNo;
    }

    public void setScUhidNo(String scUhidNo) {
        this.scUhidNo = scUhidNo;
    }
}
