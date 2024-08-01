package com.cellbeans.hspa.temritemprescription;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.memrdruginstruction.MemrDrugInstruction;
import com.cellbeans.hspa.mstdosefrequency.MstDoseFrequency;
import com.cellbeans.hspa.mstroute.MstRoute;
import com.cellbeans.hspa.temrvisitallergy.TemrVisitAllergy;
import com.cellbeans.hspa.temrvisitprescription.TemrVisitPrescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_item_prescription")
public class TemrItemPrescription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipId", unique = true, nullable = true)
    private long ipId;

    @JsonInclude(NON_NULL)
    @Column(name = "ipQuantity")
    private String ipQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "itemName")
    private String itemName;

    @JsonInclude(NON_NULL)
    @Column(name = "itemFlag", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemFlag;

    @JsonInclude(NON_NULL)
    @Column(name = "ipinstruction")
    private String ipinstruction;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipInvItemId")
    private InvItem ipInvItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipDiId")
    private MemrDrugInstruction ipDiId;

    @JsonInclude(NON_NULL)
    @Column(name = "ipDdId")
    private String ipDdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipVpId")
    private TemrVisitPrescription ipVpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipDfId")
    private MstDoseFrequency ipDfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipRouteId")
    private MstRoute ipRouteId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(nullable = true, updatable = true)
    private String createdBy;

    @Column(nullable = true, updatable = true)
    private long createdByUserId;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "ipIsAllergic", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ipIsAllergic = false;

    @JsonIgnore
    @Column(name = "ipIsPaid", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ipIsPaid = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipVaId")
    private TemrVisitAllergy ipVaId;

    public long getIpId() {
        return ipId;
    }

    public void setIpId(long ipId) {
        this.ipId = ipId;
    }

    public String getIpQuantity() {
        return ipQuantity;
    }

    public void setIpQuantity(String ipQuantity) {
        this.ipQuantity = ipQuantity;
    }

    public InvItem getIpInvItemId() {
        return ipInvItemId;
    }

    public void setIpInvItemId(InvItem ipInvItemId) {
        this.ipInvItemId = ipInvItemId;
    }

    public MemrDrugInstruction getIpDiId() {
        return ipDiId;
    }

    public void setIpDiId(MemrDrugInstruction ipDiId) {
        this.ipDiId = ipDiId;
    }

    public String getIpDdId() {
        return ipDdId;
    }

    public void setIpDdId(String ipDdId) {
        this.ipDdId = ipDdId;
    }

    public TemrVisitPrescription getIpVpId() {
        return ipVpId;
    }

    public void setIpVpId(TemrVisitPrescription ipVpId) {
        this.ipVpId = ipVpId;
    }

    public MstDoseFrequency getIpDfId() {
        return ipDfId;
    }

    public void setIpDfId(MstDoseFrequency ipDfId) {
        this.ipDfId = ipDfId;
    }

    public MstRoute getIpRouteId() {
        return ipRouteId;
    }

    public void setIpRouteId(MstRoute ipRouteId) {
        this.ipRouteId = ipRouteId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getIpIsPaid() {
        return ipIsPaid;
    }

    public void setIpIsPaid(Boolean ipIsPaid) {
        this.ipIsPaid = ipIsPaid;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getIpinstruction() {
        return ipinstruction;
    }

    public void setIpinstruction(String ipinstruction) {
        this.ipinstruction = ipinstruction;
    }

    public Boolean getIpIsAllergic() {
        return ipIsAllergic;
    }

    public void setIpIsAllergic(Boolean ipIsAllergic) {
        this.ipIsAllergic = ipIsAllergic;
    }

    public TemrVisitAllergy getIpVaId() {
        return ipVaId;
    }

    public void setIpVaId(TemrVisitAllergy ipVaId) {
        this.ipVaId = ipVaId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Boolean itemFlag) {
        this.itemFlag = itemFlag;
    }
}
