package com.cellbeans.hspa.mpathitemconsume;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invunitofmeasurment.InvUnitOfMeasurment;
import com.cellbeans.hspa.mpathtest.MpathTest;
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
@Table(name = "mpath_item_consume")
public class MpathItemConsume implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemConsumeId", unique = true, nullable = true)
    private long itemConsumeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "measureUnit")
    private InvUnitOfMeasurment measureUnit;

    @JsonInclude(NON_NULL)
    @Column(name = "itemConsumeCount")
    private String itemConsumeCount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemConsumeItemId")
    private InvItem itemConsumeItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "itemConsumeTestId")
    private MpathTest itemConsumeTestId;

    @JsonInclude(NON_NULL)
    @Column(name = "isConsumable", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isConsumable = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isDrop", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDrop = false;
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

    public InvUnitOfMeasurment getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(InvUnitOfMeasurment measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Boolean getIsDrop() {
        return isDrop;
    }

    public void setIsDrop(Boolean isDrop) {
        this.isDrop = isDrop;
    }

    public Boolean getIsConsumable() {
        return isConsumable;
    }

    public void setIsConsumable(Boolean isConsumable) {
        this.isConsumable = isConsumable;
    }

    public long getItemConsumeId() {
        return itemConsumeId;
    }

    public void setItemConsumeId(long itemConsumeId) {
        this.itemConsumeId = itemConsumeId;
    }

    public String getItemConsumeCount() {
        return itemConsumeCount;
    }

    public void setItemConsumeCount(String itemConsumeCount) {
        this.itemConsumeCount = itemConsumeCount;
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

    public MpathTest getItemConsumeTestId() {
        return itemConsumeTestId;
    }

    public void setItemConsumeTestId(MpathTest itemConsumeTestId) {
        this.itemConsumeTestId = itemConsumeTestId;
    }

    public InvItem getItemConsumeItemId() {
        return itemConsumeItemId;
    }

    public void setItemConsumeItemId(InvItem itemConsumeItemId) {
        this.itemConsumeItemId = itemConsumeItemId;
    }

    @Override
    public String toString() {
        return "MpathItemConsume{" + "itemConsumeId=" + itemConsumeId + ", measureUnit=" + measureUnit + ", itemConsumeCount='" + itemConsumeCount + '\'' + ", itemConsumeItemId=" + itemConsumeItemId + ", itemConsumeTestId=" + itemConsumeTestId + ", isConsumable=" + isConsumable + ", isDrop=" + isDrop + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
