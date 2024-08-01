package com.cellbeans.hspa.memrcallcategory;

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
@Table(name = "memr_callcategory")
public class MemrCallCategory {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "callCategoryId", unique = true, nullable = true)
    private long callCategoryId;

    @JsonInclude(NON_NULL)
    @Column(name = "callCategoryName")
    private String callCategoryName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = true, updatable = true)
    private Date lastModifiedDate;

    public long getCallCategoryId() {
        return callCategoryId;
    }

    public void setCallCategoryId(long callCategoryId) {
        this.callCategoryId = callCategoryId;
    }

    public String getCallCategoryName() {
        return callCategoryName;
    }

    public void setCallCategoryName(String callCategoryName) {
        this.callCategoryName = callCategoryName;
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

    public MemrCallCategory() {
    }

    @Override
    public String toString() {
        return "MemrCallCategory{" +
                "callCategoryId=" + callCategoryId +
                ", callCategoryName='" + callCategoryName + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

