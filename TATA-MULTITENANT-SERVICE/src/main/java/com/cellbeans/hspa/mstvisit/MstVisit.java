package com.cellbeans.hspa.mstvisit;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.memrchiefcomplaint.MemrChiefComplaint;
import com.cellbeans.hspa.mstcabin.MstCabin;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstencountertype.MstEncounterType;
import com.cellbeans.hspa.mstkindetail.MstKinDetail;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatientsource.MstPatientSource;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
import com.cellbeans.hspa.mstpolicestation.MstPoliceStation;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisittype.MstVisitType;
import com.cellbeans.hspa.nmstcamp.NmstCamp;
import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.trnappointment.TrnAppointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_visit")
public class MstVisit implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final CascadeType[] ALL = null;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitId", unique = true, nullable = true)
    private Long visitId;




    @Column(name = "veappointmentId")
    private Long eappointmentId;


    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "visitPatientId", referencedColumnName = "patientId")
    private MstPatient visitPatientId;

    @Transient
    private String uploadfile;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitEtId")
    private MstEncounterType visitEtId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitCcId")
    private MstCashCounter visitCcId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitTariffId")
    private MbillTariff visitTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientType")
    private MstPatientType patientType;

    @JsonInclude(NON_NULL)
    @Column(name = "chiefComplaint")
    private String chiefComplaint;

    @JsonInclude(NON_NULL)
    @Column(name = "visitReferByDoctor")
    private String visitReferByDoctor;


    @JsonInclude(NON_NULL)
    @Column(name = "visitStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitStatus = false;

    @JsonInclude(NON_NULL)
    @Column(name = "sponsorCombination")
    private String sponsorCombination;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "visitDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date visitDate;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsDepartment", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsDepartment = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "visitUnitId")
    private MstUnit visitUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitDepartmentId")
    private MstDepartment visitDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitSubDepartmentId")
    private MstSubDepartment visitSubDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitStaffId")
    private MstStaff visitStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitCabinId")
    private MstCabin visitCabinId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitVtId")
    private MstVisitType visitVtId;

    @JsonInclude(NON_NULL)
    @Column(name = "reasonVisit")
    private String reasonVisit;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitPsId")
    private MstPatientSource visitPsId;

    @JsonInclude(NON_NULL)
    @Column(name = "visitTokenNo")
    private String visitTokenNo;

    @JsonInclude(NON_NULL)
    @Column(name = "referByText")
    private String referByText;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referBy")
    private MstReferringEntity referBy;

    @Transient
    private TrnAppointment trnAppointment;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitChiefComplaint")
    private MemrChiefComplaint visitChiefComplaint;
    @Transient
    Page<TemrTimeline> listTemrTimeline;
    // @Transient
    //@OneToMany(mappedBy = "scVisitId", cascade = CascadeType.ALL)
    //@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinColumn(name = "scVisitId")
    //private List<TrnSponsorCombination> sponcerId;

    @JsonInclude(NON_NULL)
    @Column(name = "visitMrNo")
    private String visitMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "visitMlc")
    private String visitMlc;

    @JsonInclude(NON_NULL)
    @Column(name = "isJoinCall" )
    private Boolean isJoinCall;

    @JsonInclude(NON_NULL)
    @Column(name = "visitRemark")
    private String visitRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsMlc", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsMlc = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsCalled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsCalled = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsInConsultation", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsInConsultation = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsBroughtDead", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsBroughtDead = false;

    @JsonInclude(NON_NULL)
    @OneToOne
    @JoinColumn(name = "visitKdId")
    private MstKinDetail visitKdId;

    @JsonInclude(NON_NULL)
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonInclude(NON_NULL)
    @Column(name = "visitNotificationDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitNotificationDeleted = false;
    @JsonInclude(NON_NULL)
    @Column(name = "visitIsEmr", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsEmr = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitIsEmrFinalized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean visitIsEmrFinalized = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitType", columnDefinition = "integer  default 0", nullable = true)
    private Integer visitType = 0;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "userId")
    //private MstUser userId;
    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "visitDoctorAdvice")
    private String visitDoctorAdvice;

    @JsonInclude(NON_NULL)
    @Column(name = "visitRegistrationSource", columnDefinition = "decimal default 0", nullable = true)
    private int visitRegistrationSource;

    // for EMR Doctor's Advice
    // @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @JsonFormat(pattern="dd-MM-yyyy")
//    @Column (name = "visitFollowDate")
//    private Date visitFollowDate;
    @JsonInclude(NON_NULL)
    @Column(name = "visitFollowDate")
    private Date visitFollowDate;

    @Transient
    private int queue;

    @Transient
    private int addressed;

    @JsonInclude(NON_NULL)
    @Column(name = "isVirtual", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isVirtual = false;

    @JsonInclude(NON_NULL)
    @Column(name = "visitWts")
    private String visitWts;

    @JsonInclude(NON_NULL)
    @Column(name = "visitWte")
    private String visitWte;

    @JsonInclude(NON_NULL)
    @Column(name = "visitWaitingTime")
    private Long visitWaitingTime;

    @JsonInclude(NON_NULL)
    @Column(name = "visitWaitingDuration")
    private Long visitWaitingDuration;
    // Visit Book to Emr Finalize Time calculated after finalized emr

    @JsonInclude(NON_NULL)
    @Column(name = "visitConsultationDuration")
    private Long visitConsultationDuration;
    // only Video Call Consult Time calculated at the time of video call end

    @JsonInclude(NON_NULL)
    @Column(name = "isReadyToJoin", columnDefinition = "int default 0", nullable = true)
    private int isReadyToJoin = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "visitCancelReason")
    private String visitCancelReason;

    @JsonInclude(NON_NULL)
    @Column(name = "opdVisitType", columnDefinition = "integer  default 0", nullable = true)
    private Integer opdVisitType = 0;
    // 0 for Diagnostic , 1 for  Walk In,  2 for Future App
    @JsonInclude(NON_NULL)
    @Column(name = "isVisitUrgent", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isVisitUrgent = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isVisitUrgentComments")
    private String isVisitUrgentComments;

    @JsonInclude(NON_NULL)
    // dont put JsonIgnore
    @Column
    private Long createdbyId;


    @ManyToOne
    @JoinColumn(name = "visitCampId")
    private NmstCamp visitCampId;

    @ManyToOne
    @JoinColumn(name = "visitCampVisitId")
    private NmstCampVisit visitCampVisitId;

    public Date getVisitFollowDate() {
//
//
//        Date myDate = new Date();
//
//           // System.out.println(new SimpleDateFormat("yyyy-MM-dd").pares(visitFollowDate));
//        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd").pares(visitFollowDate);
//        SimpleDateFormat sm1=nechiefComplaintw SimpleDateFormat("dd-MM-yyyy").format(sm);
//        // System.out.println("---------------------------------Mst visit-----------------------------------------------");
//        // System.out.println(sm1);
        // visitFollowDate =
        return visitFollowDate;
    }

    public void setVisitFollowDate(Date visitFollowDate) {
        this.visitFollowDate = visitFollowDate;
    }

    public MstVisit() {
    }
//    public MstVisit(MstPatient visitPatientId, String uploadfile, MstEncounterType visitEtId, MstCashCounter visitCcId, MbillTariff visitTariffId, MstPatientType patientType, String chiefComplaint, String sponsorCombination, Date visitDate, Boolean visitIsDepartment, MstUnit visitUnitId, MstSubDepartment visitSubDepartmentId, MstStaff visitStaffId, MstCabin visitCabinId, MstVisitType visitVtId, MstPatientSource visitPsId, String visitTokenNo, MstReferringEntity referBy, String visitMrNo, String visitRemark, Boolean visitIsMlc, Boolean visitIsBroughtDead) {
//        this.visitPatientId = visitPatientId;
//        this.uploadfile = uploadfile;
//        this.visitEtId = visitEtId;
//        this.visitCcId = visitCcId;
//        this.visitTariffId = visitTariffId;
//        this.patientType = patientType;
//        this.chiefComplaint = chiefComplaint;
//        this.sponsorCombination = sponsorCombination;
//        this.visitDate = visitDate;
//        this.visitIsDepartment chiefComplaint= visitIsDepartment;
//        this.visitUnitId = visitUnitId;
//        this.visitSubDepartmentId = visitSubDepartmentId;
//        this.visitStaffId = visitStaffId;
//        this.visitCabinId = visitCabinId;
//        this.visitVtId = visitVtId;
//        this.visitPsId = visitPsId;
//        this.visitTokenNo = visitTokenNo;
//        this.referBy = referBy;
//        this.visitMrNo = visitMrNo;
//        this.visitRemark = visitRemark;
//        this.visitIsMlc = visitIsMlc;
//        this.visitIsBroughtDead = visitIsBroughtDead;
//    }

    //    @JsonIgnore
//    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    // For Birth Certificate Generation
    @JsonInclude(NON_NULL)
    @Column(name = "visitNewBornStatus")
    private Boolean visitNewBornStatus = false;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitPoliceStationId")
    private MstPoliceStation visitPoliceStationId;

    public MstPoliceStation getVisitPoliceStationId() {
        return visitPoliceStationId;
    }

    public void setVisitPoliceStationId(MstPoliceStation visitPoliceStationId) {
        this.visitPoliceStationId = visitPoliceStationId;
    }
    //	public List<TrnSponsorCombination> getSponcerId() {
//		return sponcerId;
//	}
//
//	public void setSponcerId(List<TrnSponsorCombination> sponcerId) {
//		this.sponcerId = sponcerId;
//	}
    public Page<TemrTimeline> getListTemrTimeline() {
        return listTemrTimeline;
    }

    public void setListTemrTimeline(Page<TemrTimeline> listTemrTimeline) {
        this.listTemrTimeline = listTemrTimeline;
    }

    public int getVisitRegistrationSource() {
        return visitRegistrationSource;
    }

    public void setVisitRegistrationSource(int visitRegistrationSource) {
        this.visitRegistrationSource = visitRegistrationSource;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public MstPatient getVisitPatientId() {
        return visitPatientId;
    }

    public void setVisitPatientId(MstPatient visitPatientId) {
        this.visitPatientId = visitPatientId;
    }

    public MstEncounterType getVisitEtId() {
        return visitEtId;
    }

    public void setVisitEtId(MstEncounterType visitEtId) {
        this.visitEtId = visitEtId;
    }

    public MstCashCounter getVisitCcId() {
        return visitCcId;
    }

    public void setVisitCcId(MstCashCounter visitCcId) {
        this.visitCcId = visitCcId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public boolean getVisitIsDepartment() {
        return visitIsDepartment;
    }

    public void setVisitIsDepartment(boolean visitIsDepartment) {
        this.visitIsDepartment = visitIsDepartment;
    }

    public MstUnit getVisitUnitId() {
        return visitUnitId;
    }

    public void setVisitUnitId(MstUnit visitUnitId) {
        this.visitUnitId = visitUnitId;
    }

    public MstDepartment getVisitDepartmentId() {
        return visitDepartmentId;
    }

    public void setVisitDepartmentId(MstDepartment visitDepartmentId) {
        this.visitDepartmentId = visitDepartmentId;
    }

    public String getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(String uploadfile) {
        this.uploadfile = uploadfile;
    }

    public MstSubDepartment getVisitSubDepartmentId() {
        return visitSubDepartmentId;
    }

    public void setVisitSubDepartmentId(MstSubDepartment visitSubDepartmentId) {
        this.visitSubDepartmentId = visitSubDepartmentId;
    }

    public MstStaff getVisitStaffId() {
        return visitStaffId;
    }

    public void setVisitStaffId(MstStaff visitStaffId) {
        this.visitStaffId = visitStaffId;
    }

    public MstCabin getVisitCabinId() {
        return visitCabinId;
    }

    public void setVisitCabinId(MstCabin visitCabinId) {
        this.visitCabinId = visitCabinId;
    }

    public MstVisitType getVisitVtId() {
        return visitVtId;
    }

    public void setVisitVtId(MstVisitType visitVtId) {
        this.visitVtId = visitVtId;
    }

    public MstPatientSource getVisitPsId() {
        return visitPsId;
    }

    public void setVisitPsId(MstPatientSource visitPsId) {
        this.visitPsId = visitPsId;
    }

    public String getVisitTokenNo() {
        return visitTokenNo;
    }

    public void setVisitTokenNo(String visitTokenNo) {
        this.visitTokenNo = visitTokenNo;
    }

    public MstReferringEntity getReferBy() {
        return referBy;
    }

    public void setReferBy(MstReferringEntity referBy) {
        this.referBy = referBy;
    }

    public String getVisitMrNo() {
        return visitMrNo;
    }

    public void setVisitMrNo(String visitMrNo) {
        this.visitMrNo = visitMrNo;
    }

    public String getVisitRemark() {
        return visitRemark;
    }

    public void setVisitRemark(String visitRemark) {
        this.visitRemark = visitRemark;
    }

    public boolean getVisitIsMlc() {
        return visitIsMlc;
    }

    public void setVisitIsMlc(boolean visitIsMlc) {
        this.visitIsMlc = visitIsMlc;
    }

    public boolean getVisitIsBroughtDead() {
        return visitIsBroughtDead;
    }

    public void setVisitIsBroughtDead(boolean visitIsBroughtDead) {
        this.visitIsBroughtDead = visitIsBroughtDead;
    }

    public MstKinDetail getVisitKdId() {
        return visitKdId;
    }

    public void setVisitKdId(MstKinDetail visitKdId) {
        this.visitKdId = visitKdId;
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
//    public MstPatientType getPatientType() {
//        return patientType;
//    }
//
//    public void setPatientType(MstPatientType patientType) {
//        this.patientType = patientType;
//    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getSponsorCombination() {
        return sponsorCombination;
    }

    public void setSponsorCombination(String sponsorCombination) {
        this.sponsorCombination = sponsorCombination;
    }

    public MbillTariff getVisitTariffId() {
        return visitTariffId;
    }

    public void setVisitTariffId(MbillTariff visitTariffId) {
        this.visitTariffId = visitTariffId;
    }
    //	public MstUser getUserId() {
//		return userId;
//	}
//	public void setUserId(MstUser userId) {
    //	this.userId = userId;
    //}

    public String getVisitDoctorAdvice() {
        return visitDoctorAdvice;
    }

    public void setVisitDoctorAdvice(String visitDoctorAdvice) {
        this.visitDoctorAdvice = visitDoctorAdvice;
    }

    public String getVisitMlc() {
        return visitMlc;
    }

    public void setVisitMlc(String visitMlc) {
        this.visitMlc = visitMlc;
    }

    public Boolean getVisitNewBornStatus() {
        return visitNewBornStatus;
    }

    public void setVisitNewBornStatus(Boolean visitNewBornStatus) {
        this.visitNewBornStatus = visitNewBornStatus;
    }

    public String getReasonVisit() {
        return reasonVisit;
    }

    public void setReasonVisit(String reasonVisit) {
        this.reasonVisit = reasonVisit;
    }

    public MemrChiefComplaint getVisitChiefComplaint() {
        return visitChiefComplaint;
    }

    public void setVisitChiefComplaint(MemrChiefComplaint visitChiefComplaint) {
        this.visitChiefComplaint = visitChiefComplaint;
    }

    public void setVisitIsBroughtDead(Boolean visitIsBroughtDead) {
        this.visitIsBroughtDead = visitIsBroughtDead;
    }

    public String getReferByText() {
        return referByText;
    }

    public void setReferByText(String referByText) {
        this.referByText = referByText;
    }

    public Boolean getVisitIsEmr() {
        return visitIsEmr;
    }

    public void setVisitIsEmr(Boolean visitIsEmr) {
        this.visitIsEmr = visitIsEmr;
    }

    public Boolean getVisitIsEmrFinalized() {
        return visitIsEmrFinalized;
    }

    public void setVisitIsEmrFinalized(Boolean visitIsEmrFinalized) {
        this.visitIsEmrFinalized = visitIsEmrFinalized;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getAddressed() {
        return addressed;
    }

    public void setAddressed(int addressed) {
        this.addressed = addressed;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public String getVisitWts() {
        return visitWts;
    }

    public void setVisitWts(String visitWts) {
        this.visitWts = visitWts;
    }

    public String getVisitWte() {
        return visitWte;
    }

    public void setVisitWte(String visitWte) {
        this.visitWte = visitWte;
    }

    public Long getVisitWaitingTime() {
        return visitWaitingTime;
    }

    public void setVisitWaitingTime(Long visitWaitingTime) {
        this.visitWaitingTime = visitWaitingTime;
    }

    public int getIsReadyToJoin() {
        return isReadyToJoin;
    }

    public void setIsReadyToJoin(int isReadyToJoin) {
        this.isReadyToJoin = isReadyToJoin;
    }

    public String getVisitCancelReason() {
        return visitCancelReason;
    }

    public void setVisitCancelReason(String visitCancelReason) {
        this.visitCancelReason = visitCancelReason;
    }

    public Boolean getVisitIsCalled() {
        return visitIsCalled;
    }

    public void setVisitIsCalled(Boolean visitIsCalled) {
        this.visitIsCalled = visitIsCalled;
    }

    public Boolean getVisitNotificationDeleted() {
        return visitNotificationDeleted;
    }

    public void setVisitNotificationDeleted(Boolean visitNotificationDeleted) {
        this.visitNotificationDeleted = visitNotificationDeleted;
    }

    public Integer getOpdVisitType() {
        return opdVisitType;
    }

    public void setOpdVisitType(Integer opdVisitType) {
        this.opdVisitType = opdVisitType;
    }

    public Boolean getIsVisitUrgent() {
        return isVisitUrgent;
    }

    public void setIsVisitUrgent(Boolean visitUrgent) {
        isVisitUrgent = visitUrgent;
    }

    public String getIsVisitUrgentComments() {
        return isVisitUrgentComments;
    }

    public void setIsVisitUrgentComments(String isVisitUrgentComments) {
        this.isVisitUrgentComments = isVisitUrgentComments;
    }

    public Long getVisitWaitingDuration() {
        return visitWaitingDuration;
    }

    public void setVisitWaitingDuration(Long visitWaitingDuration) {
        this.visitWaitingDuration = visitWaitingDuration;
    }

    public Long getVisitConsultationDuration() {
        return visitConsultationDuration;
    }

    public void setVisitConsultationDuration(Long visitConsultationDuration) {
        this.visitConsultationDuration = visitConsultationDuration;
    }

    public Long getCreatedbyId() {
        return createdbyId;
    }

    public void setCreatedbyId(Long createdbyId) {
        this.createdbyId = createdbyId;
    }

    public Boolean getVisitIsInConsultation() {
        return visitIsInConsultation;
    }

    public void setVisitIsInConsultation(Boolean visitIsInConsultation) {
        this.visitIsInConsultation = visitIsInConsultation;
    }

    public MstPatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(MstPatientType patientType) {
        this.patientType = patientType;
    }

    public String getVisitReferByDoctor() {
        return visitReferByDoctor;
    }

    public void setVisitReferByDoctor(String visitReferByDoctor) {
        this.visitReferByDoctor = visitReferByDoctor;
    }

    public Boolean getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Boolean visitStatus) {
        this.visitStatus = visitStatus;
    }

    public void setVisitIsDepartment(Boolean visitIsDepartment) {
        this.visitIsDepartment = visitIsDepartment;
    }

    public void setVisitIsMlc(Boolean visitIsMlc) {
        this.visitIsMlc = visitIsMlc;
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

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getVisitUrgent() {
        return isVisitUrgent;
    }

    public void setVisitUrgent(Boolean visitUrgent) {
        isVisitUrgent = visitUrgent;
    }

    public Boolean getIsJoinCall() {
        return isJoinCall;
    }

    public void setIsJoinCall(Boolean joinCall) {
        isJoinCall = joinCall;
    }

    public TrnAppointment getTrnAppointment() {
        return trnAppointment;
    }

    public void setTrnAppointment(TrnAppointment trnAppointment) {
        this.trnAppointment = trnAppointment;
    }

    public NmstCamp getVisitCampId() {        return visitCampId;    }

    public void setVisitCampId(NmstCamp visitCampId) {        this.visitCampId = visitCampId;    }

    public NmstCampVisit getVisitCampVisitId() {        return visitCampVisitId;    }


    public void setVisitCampVisitId(NmstCampVisit visitCampVisitId) {        this.visitCampVisitId = visitCampVisitId;    }

    public Long getEappointmentId() {
        return eappointmentId;
    }

    public void setEappointmentId(Long eappointmentId) {
        this.eappointmentId = eappointmentId;
    }

}