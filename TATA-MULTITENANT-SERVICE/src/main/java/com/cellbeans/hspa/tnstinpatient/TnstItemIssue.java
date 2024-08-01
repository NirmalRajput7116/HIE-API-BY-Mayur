package com.cellbeans.hspa.tnstinpatient;

import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
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
@Table(name = "tnst_item_issue")
public class TnstItemIssue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iiId", unique = true, nullable = true)
    private long iiId;

    @JsonInclude(NON_NULL)
    @Column(name = "iiItemTaken")
    private int iiItemTaken;

    @JsonInclude(NON_NULL)
    @Column(name = "isReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isReturn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isBilled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isBilled = false;

    @JsonInclude(NON_NULL)
    @Column(name = "consumedQty")
    private int consumedQty;

    @JsonInclude(NON_NULL)
    @Column(name = "returnQty")
    private int returnQty;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "iiItemId")
    private TinvOpeningBalanceItem iiItemId;

    @Transient
    private Long issueId;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public long getIiId() {
        return iiId;
    }

    public void setIiId(long iiId) {
        this.iiId = iiId;
    }

    public int getIiItemTaken() {
        return iiItemTaken;
    }

    public void setIiItemTaken(int iiItemTaken) {
        this.iiItemTaken = iiItemTaken;
    }

    public TinvOpeningBalanceItem getIiItemId() {
        return iiItemId;
    }

    public void setIiItemId(TinvOpeningBalanceItem iiItemId) {
        this.iiItemId = iiItemId;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public int getConsumedQty() {
        return consumedQty;
    }

    public void setConsumedQty(int consumedQty) {
        this.consumedQty = consumedQty;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
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

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Boolean getBilled() {
        return isBilled;
    }

    public void setBilled(Boolean billed) {
        isBilled = billed;
    }

}