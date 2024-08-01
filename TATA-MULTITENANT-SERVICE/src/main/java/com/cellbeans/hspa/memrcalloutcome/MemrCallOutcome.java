package com.cellbeans.hspa.memrcalloutcome;

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
@Table(name = "memr_calloutcome")
public class MemrCallOutcome {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "callOutcomeId", unique = true, nullable = true)
    private long callOutcomeId;

    @JsonInclude(NON_NULL)
    @Column(name = "callOutcomeName")
    private String callOutcomeName;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default true", nullable = true)
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
    @Column(nullable = true, updatable = true)
    private Date lastModifiedDate;

    public long getCallOutcomeId() {
        return callOutcomeId;
    }

    public void setCallOutcomeId(long callOutcomeId) {
        this.callOutcomeId = callOutcomeId;
    }

    public String getCallOutcomeName() {
        return callOutcomeName;
    }

    public void setCallOutcomeName(String callOutcomeName) {
        this.callOutcomeName = callOutcomeName;
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

    public MemrCallOutcome() {
    }

    @Override
    public String toString() {
        return "MemrCallOutcome{" +
                "callOutcomeId=" + callOutcomeId +
                ", callOutcomeName='" + callOutcomeName + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

