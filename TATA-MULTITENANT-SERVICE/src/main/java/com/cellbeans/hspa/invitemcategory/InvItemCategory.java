package com.cellbeans.hspa.invitemcategory;

import com.cellbeans.hspa.invitemgroup.InvItemGroup;
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

/**
 * @author Inya Gaikwad
 * This Class is responsible for item Category.
 * <p>
 * Class has
 * {@link ManyToOne} with {@link InvItemGroup},{@link MbillService}
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inv_item_category")
public class InvItemCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icId", unique = true, nullable = true)
    private long icId;

    @JsonInclude(NON_NULL)
    @Column(name = "icName")
    private String icName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icMbillServiceId")
    private MbillService icMbillServiceId;

    @JsonInclude(NON_NULL)
    @Column(name = "icLedgerName")
    private String icLedgerName;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icGroupId")
    private InvItemGroup icGroupId;

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

    public long getIcId() {
        return icId;
    }

    public void setIcId(long icId) {
        this.icId = icId;
    }

    public String getIcName() {
        return icName;
    }

    public void setIcName(String icName) {
        this.icName = icName;
    }

    public MbillService getIcMbillServiceId() {
        return icMbillServiceId;
    }

    public void setIcMbillServiceId(MbillService icMbillServiceId) {
        this.icMbillServiceId = icMbillServiceId;
    }

    public String getIcLedgerName() {
        return icLedgerName;
    }

    public void setIcLedgerName(String icLedgerName) {
        this.icLedgerName = icLedgerName;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public InvItemGroup getIcGroupId() {
        return icGroupId;
    }

    public void setIcGroupId(InvItemGroup icGroupId) {
        this.icGroupId = icGroupId;
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
