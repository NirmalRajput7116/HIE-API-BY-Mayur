package com.cellbeans.hspa.mstgeneratetoken;

import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
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
@Table(name = "mst_generate_token")
public class MstGenerateToken implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId", unique = true, nullable = true)
    private long tokenId;

    @JsonInclude(NON_NULL)
    @Column(name = "tokenNo")
    private long tokenNo;

    @JsonInclude(NON_NULL)
    @OneToOne
    @JoinColumn(name = "ccId")
    private MstCashCounter ccId;

    @Column(name = "isCalled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCalled = false;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public long getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(long tokenNo) {
        this.tokenNo = tokenNo;
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

    public MstCashCounter getCcId() {
        return ccId;
    }

    public void setCcId(MstCashCounter ccId) {
        this.ccId = ccId;
    }

    public Boolean getIsCalled() {
        return isCalled;
    }

    public void setIsCalled(Boolean iscalled) {
        this.isCalled = iscalled;
    }

    @Override
    public String toString() {
        return "MstGenerateToken{" +
                "tokenId=" + tokenId +
                ", tokenNo=" + tokenNo +
                ", ccId=" + ccId +
                ", isCalled=" + isCalled +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
