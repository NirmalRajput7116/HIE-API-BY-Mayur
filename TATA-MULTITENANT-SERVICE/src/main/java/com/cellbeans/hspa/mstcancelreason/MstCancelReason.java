package com.cellbeans.hspa.mstcancelreason;

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
@Table(name = "mst_cancelreason")
public class MstCancelReason {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancelrasonId", unique = true, nullable = true)
    private long cancelrasonId;

    @JsonInclude(NON_NULL)
    @Column(name = "cancelrasonName")
    private String cancelrasonName;

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

    public long getCancelrasonId() {
        return cancelrasonId;
    }

    public void setCancelrasonId(long cancelrasonId) {
        this.cancelrasonId = cancelrasonId;
    }

    public String getCancelrasonName() {
        return cancelrasonName;
    }

    public void setCancelrasonName(String cancelrasonName) {
        this.cancelrasonName = cancelrasonName;
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

    public MstCancelReason() {
    }

    @Override
    public String toString() {
        return "MstCancelReason{" +
                "cancelrasonId=" + cancelrasonId +
                ", cancelrasonName='" + cancelrasonName + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
