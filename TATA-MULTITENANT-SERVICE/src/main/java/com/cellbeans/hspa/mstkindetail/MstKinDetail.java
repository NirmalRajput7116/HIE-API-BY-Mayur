package com.cellbeans.hspa.mstkindetail;

import com.cellbeans.hspa.mstaddress.MstAddress;
import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstoccupation.MstOccupation;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.msttitle.MstTitle;
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
@Table(name = "mst_kin_detail")
public class MstKinDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kdId", unique = true, nullable = true)
    private long kdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "kdTitleId")
    private MstTitle kdTitleId;

    @JsonInclude(NON_NULL)
    @Column(name = "kdName")
    private String kdName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "kdCompanyId")
    private MstCompany kdCompanyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "kdOccupationId")
    private MstOccupation kdOccupationId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "kdAddressId")
    private MstAddress kdAddressId;

    @JsonInclude(NON_NULL)
    @Column(name = "kdRemark")
    private String kdRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "kdRelationId")
    private MstRelation kdRelationId;

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

    public long getKdId() {
        return kdId;
    }

    public void setKdId(long kdId) {
        this.kdId = kdId;
    }

    public MstTitle getKdTitleId() {
        return kdTitleId;
    }

    public void setKdTitleId(MstTitle kdTitleId) {
        this.kdTitleId = kdTitleId;
    }

    public String getKdName() {
        return kdName;
    }

    public void setKdName(String kdName) {
        this.kdName = kdName;
    }

    public MstCompany getKdCompanyId() {
        return kdCompanyId;
    }

    public void setKdCompanyId(MstCompany kdCompanyId) {
        this.kdCompanyId = kdCompanyId;
    }

    public MstOccupation getKdOccupationId() {
        return kdOccupationId;
    }

    public void setKdOccupationId(MstOccupation kdOccupationId) {
        this.kdOccupationId = kdOccupationId;
    }

    public MstAddress getKdAddressId() {
        return kdAddressId;
    }

    public void setKdAddressId(MstAddress kdAddressId) {
        this.kdAddressId = kdAddressId;
    }

    public String getKdRemark() {
        return kdRemark;
    }

    public void setKdRemark(String kdRemark) {
        this.kdRemark = kdRemark;
    }

    public MstRelation getKdRelationId() {
        return kdRelationId;
    }

    public void setKdRelationId(MstRelation kdRelationId) {
        this.kdRelationId = kdRelationId;
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
