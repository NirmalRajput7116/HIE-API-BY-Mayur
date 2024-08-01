package com.cellbeans.hspa.tnstinputoutput;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "tnst_input_output")
public class TnstInputOutput implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ioStaffId")
    protected MstStaff ioStaffId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ioId", unique = true, nullable = true)
    private long ioId;
    @JsonInclude(NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "ioDate")
    private Date ioDate;

    //    @Temporal(TemporalType.TIME)
//    @Column(name = "ioTime")
//    private Date ioTime;
    @JsonInclude(NON_NULL)
    @Column(name = "ioPersionDate")
    private String ioPersionDate;
    @JsonInclude(NON_NULL)
    @Column(name = "ioTime")
    private String ioTime;
    @JsonInclude(NON_NULL)
    @Column(name = "ioTypeFluid")
    private String ioTypeFluid;
    @JsonInclude(NON_NULL)
    @Column(name = "ioMethod")
    private String ioMethod;
    @JsonInclude(NON_NULL)
    @Column(name = "ioAmount")
    private String ioAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "ioUrine")
    private String ioUrine;
    @JsonInclude(NON_NULL)
    @Column(name = "ioAspiration")
    private String ioAspiration;
    @JsonInclude(NON_NULL)
    @Column(name = "ioDrainStoma")
    private String ioDrainStoma;
    @JsonInclude(NON_NULL)
    @Column(name = "ioStool")
    private String ioStool;
    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "ioNotes")
    private String ioNotes;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ioAdmissionId")
    private TrnAdmission ioAdmissionId;
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

    public long getIoId() {
        return ioId;
    }

    public void setIoId(long ioId) {
        this.ioId = ioId;
    }

    public Date getIoDate() {
        return ioDate;
    }

    public void setIoDate(Date ioDate) {
        this.ioDate = ioDate;
    }
//    public Date getIoTime() {
//        return ioTime;
//    }
//
//    public void setIoTime(Date ioTime) {
//        this.ioTime = ioTime;
//    }

    public String getIoTypeFluid() {
        return ioTypeFluid;
    }

    public void setIoTypeFluid(String ioTypeFluid) {
        this.ioTypeFluid = ioTypeFluid;
    }

    public String getIoMethod() {
        return ioMethod;
    }

    public void setIoMethod(String ioMethod) {
        this.ioMethod = ioMethod;
    }

    public String getIoAmount() {
        return ioAmount;
    }

    public void setIoAmount(String ioAmount) {
        this.ioAmount = ioAmount;
    }

    public String getIoUrine() {
        return ioUrine;
    }

    public void setIoUrine(String ioUrine) {
        this.ioUrine = ioUrine;
    }

    public String getIoAspiration() {
        return ioAspiration;
    }

    public void setIoAspiration(String ioAspiration) {
        this.ioAspiration = ioAspiration;
    }

    public String getIoDrainStoma() {
        return ioDrainStoma;
    }

    public void setIoDrainStoma(String ioDrainStoma) {
        this.ioDrainStoma = ioDrainStoma;
    }

    public String getIoStool() {
        return ioStool;
    }

    public void setIoStool(String ioStool) {
        this.ioStool = ioStool;
    }

    public String getIoNotes() {
        return ioNotes;
    }

    public void setIoNotes(String ioNotes) {
        this.ioNotes = ioNotes;
    }

    public TrnAdmission getIoAdmissionId() {
        return ioAdmissionId;
    }

    public void setIoAdmissionId(TrnAdmission ioAdmissionId) {
        this.ioAdmissionId = ioAdmissionId;
    }

    public MstStaff getIoStaffId() {
        return ioStaffId;
    }

    public void setIoStaffId(MstStaff ioStaffId) {
        this.ioStaffId = ioStaffId;
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

    public String getIoPersionDate() {
        return ioPersionDate;
    }

    public void setIoPersionDate(String ioPersionDate) {
        this.ioPersionDate = ioPersionDate;
    }

    public String getIoTime() {
        return ioTime;
    }

    public void setIoTime(String ioTime) {
        this.ioTime = ioTime;
    }

}
