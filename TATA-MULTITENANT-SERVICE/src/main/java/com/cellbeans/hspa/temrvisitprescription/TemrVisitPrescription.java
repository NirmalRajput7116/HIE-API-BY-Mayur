package com.cellbeans.hspa.temrvisitprescription;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_visit_prescription")
public class TemrVisitPrescription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vpId", unique = true, nullable = true)
    private long vpId;

    @JsonInclude(NON_NULL)
    @Column(name = "vpStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean vpStatus = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vpVisitId")
    private MstVisit vpVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vpTimelineId")
    private TemrTimeline vpTimelineId;

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemPrescriptionList")
    List<TemrItemPrescription> itemPrescriptionList;
*/
    @JsonInclude(NON_NULL)
    @Column(name = "vpPatientMrNumber")
    private String vpPatientMrNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vpPrescribedStaffId")
    private MstStaff vpPrescribedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "closePrescription", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean closePrescription = false;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonInclude(NON_NULL)
    @Column(name = "isOrdertoEPharmacy", columnDefinition = "binary(1) ", nullable = true)
    private Boolean isOrdertoEPharmacy;

    public Boolean getClosePrescription() {
        return closePrescription;
    }

    public void setClosePrescription(Boolean closePrescription) {
        this.closePrescription = closePrescription;
    }

    public long getVpId() {
        return vpId;
    }

    public void setVpId(long vpId) {
        this.vpId = vpId;
    }

    public Boolean getVpStatus() {
        return vpStatus;
    }

    public void setVpStatus(Boolean vpStatus) {
        this.vpStatus = vpStatus;
    }

    public MstVisit getVpVisitId() {
        return vpVisitId;
    }

    public void setVpVisitId(MstVisit vpVisitId) {
        this.vpVisitId = vpVisitId;
    }

    public String getVpPatientMrNumber() {
        return vpPatientMrNumber;
    }

    public void setVpPatientMrNumber(String vpPatientMrNumber) {
        this.vpPatientMrNumber = vpPatientMrNumber;
    }

    public MstStaff getVpPrescribedStaffId() {
        return vpPrescribedStaffId;
    }

    public void setVpPrescribedStaffId(MstStaff vpPrescribedStaffId) {
        this.vpPrescribedStaffId = vpPrescribedStaffId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public TemrTimeline getVpTimelineId() {
        return vpTimelineId;
    }

    public void setVpTimelineId(TemrTimeline vpTimelineId) {
        this.vpTimelineId = vpTimelineId;
    }

    public Boolean getOrdertoEPharmacy() {
        return isOrdertoEPharmacy;
    }

    public void setOrdertoEPharmacy(Boolean ordertoEPharmacy) {
        isOrdertoEPharmacy = ordertoEPharmacy;
    }
}
