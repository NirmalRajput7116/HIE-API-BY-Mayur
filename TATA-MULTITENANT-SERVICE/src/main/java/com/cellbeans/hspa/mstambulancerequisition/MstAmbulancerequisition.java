package com.cellbeans.hspa.mstambulancerequisition;

import com.cellbeans.hspa.mstambulance.MstAmbulance;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.mststaff.MstStaff;
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
@Table(name = "mst_ambulancerequisition")
public class MstAmbulancerequisition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ambulanceRequisitionId", unique = true, nullable = true)
    private long ambulanceRequisitionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ArpatientId")
    private MstPatient ArpatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "applicantName")
    private String applicantName;

    @JsonInclude(NON_NULL)
    @Column(name = "callType")
    private String callType;

    @JsonInclude(NON_NULL)
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "mobileNumber")
    private String mobileNumber;
    @JsonInclude(NON_NULL)
    @Column(name = "patient_name")
    private String patientname;
    @JsonInclude(NON_NULL)
    @Column(name = "gender")
    private String geneder;
    @JsonInclude(NON_NULL)
    @Column(name = "age")
    private int age;
    @JsonInclude(NON_NULL)
    @Column(name = "pickuplocation")
    private String pickuploc;
    @JsonInclude(NON_NULL)
    @Column(name = "droplocation")
    private String droploc;
    @JsonInclude(NON_NULL)
    @Column(name = "pickupdate")
    private String pickupdate;
    @JsonInclude(NON_NULL)
    @Column(name = "persionpickupDate")
    private String persionpickupDate;

    @JsonInclude(NON_NULL)
    @Column(name = "pickuptime")
    private String pickuptime;
    @JsonInclude(NON_NULL)
    @Column(name = "remark")
    private String remark;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "relation")
    private MstRelation relation;

    @JsonInclude(NON_NULL)
    @Column(name = "bookingNumber")
    private String bookingNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "bookingDate")
    private String bookingDate;
    @JsonInclude(NON_NULL)
    @Column(name = "persionbookingDate")
    private String persionbookingDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ambulanceId")
    private MstAmbulance ambulanceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "driverId")
    private MstStaff driverId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "attendentId")
    private MstStaff attendentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private MstStaff doctorId;

    @JsonInclude(NON_NULL)
    @Column(name = "distance")
    private String distance;

    @JsonInclude(NON_NULL)
    @Column(name = "charges")
    private int charges;
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

    public long getAmbulanceRequisitionId() {
        return ambulanceRequisitionId;
    }

    public void setAmbulanceRequisitionId(int ambulanceRequisitionId) {
        this.ambulanceRequisitionId = ambulanceRequisitionId;
    }

    public MstPatient getArpatientId() {
        return ArpatientId;
    }

    public void setArpatientId(MstPatient arpatientId) {
        ArpatientId = arpatientId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public MstRelation getRelation() {
        return relation;
    }

    public void setRelation(MstRelation relation) {
        this.relation = relation;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public MstAmbulance getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(MstAmbulance ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public MstStaff getDriverId() {
        return driverId;
    }

    public void setDriverId(MstStaff driverId) {
        this.driverId = driverId;
    }

    public MstStaff getAttendentId() {
        return attendentId;
    }

    public void setAttendentId(MstStaff attendentId) {
        this.attendentId = attendentId;
    }

    public MstStaff getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(MstStaff doctorId) {
        this.doctorId = doctorId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
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

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getGeneder() {
        return geneder;
    }

    public void setGeneder(String geneder) {
        this.geneder = geneder;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPickuploc() {
        return pickuploc;
    }

    public void setPickuploc(String pickuploc) {
        this.pickuploc = pickuploc;
    }

    public String getDroploc() {
        return droploc;
    }

    public void setDroploc(String droploc) {
        this.droploc = droploc;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPersionpickupDate() {
        return persionpickupDate;
    }

    public void setPersionpickupDate(String persionpickupDate) {
        this.persionpickupDate = persionpickupDate;
    }

    public String getPersionbookingDate() {
        return persionbookingDate;
    }

    public void setPersionbookingDate(String persionbookingDate) {
        this.persionbookingDate = persionbookingDate;
    }

}
