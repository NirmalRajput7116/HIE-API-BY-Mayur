package com.cellbeans.hspa.mstprocedure;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillservicecodetype.MbillServiceCodeType;
import com.cellbeans.hspa.mstanaesthesiatype.MstAnaesthesiaType;
import com.cellbeans.hspa.mstconsent.MstConsent;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstintraoperativeinstructions.MstIntraOperativeInstructions;
import com.cellbeans.hspa.mstoperationtheatre.MstOperationTheatre;
import com.cellbeans.hspa.mstoperationtheatretable.MstOperationTheatreTable;
import com.cellbeans.hspa.mstpostoperativeinstructions.MstPostOperativeInstructions;
import com.cellbeans.hspa.mstpreoperativeinstructions.MstPreOperativeInstructions;
import com.cellbeans.hspa.mstprocedurechecklist.MstProcedureChecklist;
import com.cellbeans.hspa.mstproceduretype.MstProcedureType;
import com.cellbeans.hspa.mstspeciality.MstSpeciality;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_procedure")
public class MstProcedure implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedureId", unique = true, nullable = true)
    private long procedureId;

    @JsonInclude(NON_NULL)
    @Column(name = "procedureName")
    private String procedureName;

    @JsonInclude(NON_NULL)
    @Column(name = "procedureDuration")
    private String procedureDuration;

    @JsonInclude(NON_NULL)
    @Column(name = "procedureNote")
    private String procedureNote;

    @JsonInclude(NON_NULL)
    @Column(name = "procedureCodeName")
    private String procedureCodeName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureServiceName")
    private MbillService procedureServiceName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureType")
    private MstProcedureType procedureType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureAnaesthesiaType")
    private MstAnaesthesiaType procedureAnaesthesiaType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureOperationTheatre")
    private MstOperationTheatre procedureOperationTheatre;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureSctId")
    private MbillServiceCodeType procedureSctId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureOperationTable")
    private MstOperationTheatreTable procedureOperationTable;

    @JsonInclude(NON_NULL)
    @Column(name = "procedureIsGenderSensitive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean procedureIsGenderSensitive = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureGender")
    private MstGender procedureGender;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "procedureSpeiclityId")
    private MstSpeciality procedureSpeiclityId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureDoctor")
    private List<MstStaff> procedureDoctor;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureStaff")
    private List<MstStaff> procedureStaff;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureChecklist")
    private List<MstProcedureChecklist> procedureChecklist;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureItem")
    private List<InvItem> procedureItem;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedurePreOperativeInstruction")
    private List<MstPreOperativeInstructions> procedurePreOperativeInstruction;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedurePostOperativeInstruction")
    private List<MstPostOperativeInstructions> procedurePostOperativeInstruction;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureIntraOperativeInstruction")
    private List<MstIntraOperativeInstructions> procedureIntraOperativeInstruction;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureConsent")
    private List<MstConsent> procedureConsent;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "procedureService")
    private List<MbillService> procedureService;

    public long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(long procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureDuration() {
        return procedureDuration;
    }

    public void setProcedureDuration(String procedureDuration) {
        this.procedureDuration = procedureDuration;
    }

    public String getProcedureNote() {
        return procedureNote;
    }

    public void setProcedureNote(String procedureNote) {
        this.procedureNote = procedureNote;
    }

    public MbillService getProcedureServiceName() {
        return procedureServiceName;
    }

    public void setProcedureServiceName(MbillService procedureServiceName) {
        this.procedureServiceName = procedureServiceName;
    }

    public MstProcedureType getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(MstProcedureType procedureType) {
        this.procedureType = procedureType;
    }

    public MstAnaesthesiaType getProcedureAnaesthesiaType() {
        return procedureAnaesthesiaType;
    }

    public void setProcedureAnaesthesiaType(MstAnaesthesiaType procedureAnaesthesiaType) {
        this.procedureAnaesthesiaType = procedureAnaesthesiaType;
    }

    public MstOperationTheatre getProcedureOperationTheatre() {
        return procedureOperationTheatre;
    }

    public void setProcedureOperationTheatre(MstOperationTheatre procedureOperationTheatre) {
        this.procedureOperationTheatre = procedureOperationTheatre;
    }

    public MstOperationTheatreTable getProcedureOperationTable() {
        return procedureOperationTable;
    }

    public void setProcedureOperationTable(MstOperationTheatreTable procedureOperationTable) {
        this.procedureOperationTable = procedureOperationTable;
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public MstSpeciality getProcedureSpeiclityId() {
        return procedureSpeiclityId;
    }

    public void setProcedureSpeiclityId(MstSpeciality procedureSpeiclityId) {
        this.procedureSpeiclityId = procedureSpeiclityId;
    }

    public Boolean getProcedureIsGenderSensitive() {
        return procedureIsGenderSensitive;
    }

    public void setProcedureIsGenderSensitive(Boolean procedureIsGenderSensitive) {
        this.procedureIsGenderSensitive = procedureIsGenderSensitive;
    }

    public MstGender getProcedureGender() {
        return procedureGender;
    }

    public void setProcedureGender(MstGender procedureGender) {
        this.procedureGender = procedureGender;
    }

    public List<MstStaff> getProcedureDoctor() {
        return procedureDoctor;
    }

    public void setProcedureDoctor(List<MstStaff> procedureDoctor) {
        this.procedureDoctor = procedureDoctor;
    }

    public List<MstStaff> getProcedureStaff() {
        return procedureStaff;
    }

    public void setProcedureStaff(List<MstStaff> procedureStaff) {
        this.procedureStaff = procedureStaff;
    }

    public List<MstProcedureChecklist> getProcedureChecklist() {
        return procedureChecklist;
    }

    public void setProcedureChecklist(List<MstProcedureChecklist> procedureChecklist) {
        this.procedureChecklist = procedureChecklist;
    }

    public List<InvItem> getProcedureItem() {
        return procedureItem;
    }

    public void setProcedureItem(List<InvItem> procedureItem) {
        this.procedureItem = procedureItem;
    }

    public List<MstPreOperativeInstructions> getProcedurePreOperativeInstruction() {
        return procedurePreOperativeInstruction;
    }

    public void setProcedurePreOperativeInstruction(List<MstPreOperativeInstructions> procedurePreOperativeInstruction) {
        this.procedurePreOperativeInstruction = procedurePreOperativeInstruction;
    }

    public List<MstPostOperativeInstructions> getProcedurePostOperativeInstruction() {
        return procedurePostOperativeInstruction;
    }

    public void setProcedurePostOperativeInstruction(List<MstPostOperativeInstructions> procedurePostOperativeInstruction) {
        this.procedurePostOperativeInstruction = procedurePostOperativeInstruction;
    }

    public List<MstIntraOperativeInstructions> getProcedureIntraOperativeInstruction() {
        return procedureIntraOperativeInstruction;
    }

    public void setProcedureIntraOperativeInstruction(List<MstIntraOperativeInstructions> procedureIntraOperativeInstruction) {
        this.procedureIntraOperativeInstruction = procedureIntraOperativeInstruction;
    }

    public List<MstConsent> getProcedureConsent() {
        return procedureConsent;
    }

    public void setProcedureConsent(List<MstConsent> procedureConsent) {
        this.procedureConsent = procedureConsent;
    }

    public List<MbillService> getProcedureService() {
        return procedureService;
    }

    public void setProcedureService(List<MbillService> procedureService) {
        this.procedureService = procedureService;
    }

    public String getProcedureCodeName() {
        return procedureCodeName;
    }

    public void setProcedureCodeName(String procedureCodeName) {
        this.procedureCodeName = procedureCodeName;
    }

    public MbillServiceCodeType getProcedureSctId() {
        return procedureSctId;
    }

    public void setProcedureSctId(MbillServiceCodeType procedureSctId) {
        this.procedureSctId = procedureSctId;
    }
}
