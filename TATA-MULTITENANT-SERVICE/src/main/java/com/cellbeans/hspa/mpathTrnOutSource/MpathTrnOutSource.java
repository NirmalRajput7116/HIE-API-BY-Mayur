package com.cellbeans.hspa.mpathTrnOutSource;

import com.cellbeans.hspa.tbillAgencyRate.TbillAgencyRate;
import com.cellbeans.hspa.tpathbs.TpathBs;
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
@Table(name = "mpath_trn_outsource")
public class MpathTrnOutSource {

    private static final long serialVersionUID = 1L;
    @Transient
    long count;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tosId", unique = true, nullable = true)
    private long tosId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tosAgencyRate")
    private TbillAgencyRate tosAgencyRate;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tosPsId")
    private TpathBs tosPsId;

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
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTosId() {
        return tosId;
    }

    public void setTosId(long tosId) {
        this.tosId = tosId;
    }

    public TbillAgencyRate getTosAgencyRate() {
        return tosAgencyRate;
    }

    public void setTosAgencyRate(TbillAgencyRate tosAgencyRate) {
        this.tosAgencyRate = tosAgencyRate;
    }

    public TpathBs getTosPsId() {
        return tosPsId;
    }

    public void setTosPsId(TpathBs tosPsId) {
        this.tosPsId = tosPsId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MpathTrnOutSource{" + "tosId=" + tosId + ", tosAgencyRate=" + tosAgencyRate + ", tosPsId=" + tosPsId + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
