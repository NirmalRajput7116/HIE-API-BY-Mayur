
package com.cellbeans.hspa.healthcarddetails;

import com.cellbeans.hspa.mstpatient.MstPatient;
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
@Table(name = "health_card_details")
public class HealthCardDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hcId", unique = true, nullable = true)
    private Long hcId;

    @Column(name = "hcCardNumber")
    private String hcCardNumber;

    @Column(name = "hcStartDate")
    private String hcStartDate;
    @JsonInclude(NON_NULL)
    @Column(name = "hcEndDate")
    private String hcEndDate;
    @JsonInclude(NON_NULL)
    @Column(name = "createdDate")
    private Date createdDate;
    @JsonInclude(NON_NULL)
    @Column(name = "isActive")
    private Boolean isActive;
    @JsonInclude(NON_NULL)
    @Column(name = "isDeleted")
    private Boolean isDeleted;
    @JsonInclude(NON_NULL)
    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "hcPatientId", referencedColumnName = "patientId")
    private MstPatient hcPatientId;

    public Long getHcId() {
        return hcId;
    }

    public void setHcId(Long hcId) {
        this.hcId = hcId;
    }

    public String getHcCardNumber() {
        return hcCardNumber;
    }

    public void setHcCardNumber(String hcCardNumber) {
        this.hcCardNumber = hcCardNumber;
    }

    public String getHcStartDate() {
        return hcStartDate;
    }

    public void setHcStartDate(String hcStartDate) {
        this.hcStartDate = hcStartDate;
    }

    public String getHcEndDate() {
        return hcEndDate;
    }

    public void setHcEndDate(String hcEndDate) {
        this.hcEndDate = hcEndDate;
    }

    public MstPatient getHcPatientId() {
        return hcPatientId;
    }

    public void setHcPatientId(MstPatient hcPatientId) {
        this.hcPatientId = hcPatientId;
    }
}