package com.cellbeans.hspa.temrallergy;

import com.cellbeans.hspa.memrallergy.MemrAllergy;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/*import com.cellbeans.hspa.memrallergytype.MemrAllergyType;*/

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_allergy")
public class TemrAllergy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergyId", unique = true, nullable = true)
    private long allergyId;

    @JsonInclude(NON_NULL)
    @OneToMany
    @JoinColumn(name = "allergyAllergyId")
    private List<MemrAllergy> allergyAllergyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "allergyVisitId")
    private MstVisit allergyVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "allergyAdmissionId")
    private TrnAdmission allergyAdmissionId;

 /*   @JsonInclude(NON_NULL)
    @OneToMany
    @JoinColumn(name = "allergyAtId")
    private List<MemrAllergyType> allergyAtId;*/

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
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
    private Date lastModifiedDate;

    public long getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(long allergyId) {
        this.allergyId = allergyId;
    }

    public List<MemrAllergy> getAllergyAllergyId() {
        return allergyAllergyId;
    }

    public void setAllergyAllergyId(List<MemrAllergy> allergyAllergyId) {
        this.allergyAllergyId = allergyAllergyId;
    }

    public MstVisit getAllergyVisitId() {
        return allergyVisitId;
    }

    public void setAllergyVisitId(MstVisit allergyVisitId) {
        this.allergyVisitId = allergyVisitId;
    }

    public TrnAdmission getAllergyAdmissionId() {
        return allergyAdmissionId;
    }

    public void setAllergyAdmissionId(TrnAdmission allergyAdmissionId) {
        this.allergyAdmissionId = allergyAdmissionId;
    }

/*    public List<MemrAllergyType> getAllergyAtId() {
        return allergyAtId;
    }

    public void setAllergyAtId(List<MemrAllergyType> allergyAtId) {
        this.allergyAtId = allergyAtId;
    }*/

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
