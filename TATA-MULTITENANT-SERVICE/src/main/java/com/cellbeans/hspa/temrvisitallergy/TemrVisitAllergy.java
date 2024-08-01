package com.cellbeans.hspa.temrvisitallergy;

import com.cellbeans.hspa.invcompoundname.InvCompoundName;
import com.cellbeans.hspa.memrallergysensitivity.MemrAllergySensitivity;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "temr_visit_allergy")
public class TemrVisitAllergy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaId", unique = true, nullable = true)
    private long vaId;

    /* @ManyToOneyyyy
     @JoinColumn(name = "vaDrugId")
     private MstDrug vaDrugId;
 */
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "vaInvItemId")
//    private InvItem vaInvItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaCompoundId")
    private InvCompoundName vaCompoundId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaPatientId")
    private MstPatient vaPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaTimelineId")
    private TemrTimeline vaTimelineId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaAsId")
    private MemrAllergySensitivity vaAsId;

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

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

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

    public long getVaId() {
        return vaId;
    }

    public void setVaId(long vaId) {
        this.vaId = vaId;
    }

    public MstPatient getVaPatientId() {
        return vaPatientId;
    }

    public void setVaPatientId(MstPatient vaPatientId) {
        this.vaPatientId = vaPatientId;
    }

    public MemrAllergySensitivity getVaAsId() {
        return vaAsId;
    }

    public void setVaAsId(MemrAllergySensitivity vaAsId) {
        this.vaAsId = vaAsId;
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

    public TemrTimeline getVaTimelineId() {
        return vaTimelineId;
    }

    public void setVaTimelineId(TemrTimeline vaTimelineId) {
        this.vaTimelineId = vaTimelineId;
    }

    public InvCompoundName getVaCompoundId() {
        return vaCompoundId;
    }

    public void setVaCompoundId(InvCompoundName vaCompoundId) {
        this.vaCompoundId = vaCompoundId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
