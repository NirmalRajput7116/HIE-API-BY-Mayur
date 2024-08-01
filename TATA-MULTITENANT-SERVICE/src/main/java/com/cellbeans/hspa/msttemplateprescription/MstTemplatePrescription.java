package com.cellbeans.hspa.msttemplateprescription;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.memrdruginstruction.MemrDrugInstruction;
import com.cellbeans.hspa.mstdosefrequency.MstDoseFrequency;
import com.cellbeans.hspa.mstemrtemplate.MstEmrTemplate;
import com.cellbeans.hspa.mstroute.MstRoute;
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
@Table(name = "mst_template_prescription")
public class MstTemplatePrescription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpId", unique = true, nullable = true)
    private long tpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpEtId")
    private MstEmrTemplate tpEtId;

    @JsonInclude(NON_NULL)
    @Column(name = "tpQuantity")
    private String tpQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "tpInstruction")
    private String tpInstruction;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpInvItemId")
    private InvItem tpInvItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpDiId")
    private MemrDrugInstruction tpDiId;

    @JsonInclude(NON_NULL)
    @Column(name = "tpDdId")
    private String tpDdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpDfId")
    private MstDoseFrequency tpDfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpRouteId")
    private MstRoute tpRouteId;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonInclude(NON_NULL)
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonInclude(NON_NULL)
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getTpId() {
        return tpId;
    }

    public void setTpId(long tpId) {
        this.tpId = tpId;
    }

    public MstEmrTemplate getTpEtId() {
        return tpEtId;
    }

    public void setTpEtId(MstEmrTemplate tpEtId) {
        this.tpEtId = tpEtId;
    }

    public String getTpQuantity() {
        return tpQuantity;
    }

    public void setTpQuantity(String tpQuantity) {
        this.tpQuantity = tpQuantity;
    }

    public String getTpInstruction() {
        return tpInstruction;
    }

    public void setTpInstruction(String tpinstruction) {
        this.tpInstruction = tpinstruction;
    }

    public InvItem getTpInvItemId() {
        return tpInvItemId;
    }

    public void setTpInvItemId(InvItem tpInvItemId) {
        this.tpInvItemId = tpInvItemId;
    }

    public MemrDrugInstruction getTpDiId() {
        return tpDiId;
    }

    public void setTpDiId(MemrDrugInstruction tpDiId) {
        this.tpDiId = tpDiId;
    }

    public String getTpDdId() {
        return tpDdId;
    }

    public void setTpDdId(String tpDdId) {
        this.tpDdId = tpDdId;
    }

    public MstDoseFrequency getTpDfId() {
        return tpDfId;
    }

    public void setTpDfId(MstDoseFrequency tpDfId) {
        this.tpDfId = tpDfId;
    }

    public MstRoute getTpRouteId() {
        return tpRouteId;
    }

    public void setTpRouteId(MstRoute tpRouteId) {
        this.tpRouteId = tpRouteId;
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

}

