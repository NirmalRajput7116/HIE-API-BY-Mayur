package com.cellbeans.hspa.EmgVitalDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EmgVitalDetails")
public class EmgVitalDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evdId", unique = true, nullable = true)
    private long evdId;

    @JsonInclude(NON_NULL)
    @Column(name = "evdHeight")
    private String evdHeight;

    @JsonInclude(NON_NULL)
    @Column(name = "evdWeight")
    private String evdWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "evdBmi")
    private String evdBmi;

    @JsonInclude(NON_NULL)
    @Column(name = "evdLmp")
    private String evdLmp;

    @JsonInclude(NON_NULL)
    @Column(name = "evdBodyTemp")
    private String evdBodyTemp;

    @JsonInclude(NON_NULL)
    @Column(name = "evdPulse")
    private String evdPulse;

    @JsonInclude(NON_NULL)
    @Column(name = "evdRespirationRate")
    private String evdRespirationRate;

    @JsonInclude(NON_NULL)
    @Column(name = "evdSpo2")
    private String evdSpo2;

    @JsonInclude(NON_NULL)
    @Column(name = "evdSysBp")
    private String evdSysBp;

    @JsonInclude(NON_NULL)
    @Column(name = "evdDiaBp")
    private String evdDiaBp;

    @JsonInclude(NON_NULL)
    @Column(name = "evdMentalStatus")
    private String evdMentalStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "evdRbs")
    private String evdRbs;

    @JsonInclude(NON_NULL)
    @Column(name = "evdRNnumber")
    private String evdRNnumber;

    @JsonInclude(NON_NULL)
    @Column(name = "evdO2Saturation")
    private String evdO2Saturation;

    @JsonInclude(NON_NULL)
    @Column(name = "evdRespiratoryRate")
    private String evdRespiratoryRate;

    @JsonInclude(NON_NULL)
    @Column(name = "evdPainReassessmentScore")
    private String evdPainReassessmentScore;

    @JsonInclude(NON_NULL)
    @Column(name = "evdPainTool")
    private String evdPainTool;

    public long getEvdId() {
        return evdId;
    }

    public void setEvdId(long evdId) {
        this.evdId = evdId;
    }

    public String getEvdHeight() {
        return evdHeight;
    }

    public void setEvdHeight(String evdHeight) {
        this.evdHeight = evdHeight;
    }

    public String getEvdWeight() {
        return evdWeight;
    }

    public void setEvdWeight(String evdWeight) {
        this.evdWeight = evdWeight;
    }

    public String getEvdBmi() {
        return evdBmi;
    }

    public void setEvdBmi(String evdBmi) {
        this.evdBmi = evdBmi;
    }

    public String getEvdLmp() {
        return evdLmp;
    }

    public void setEvdLmp(String evdLmp) {
        this.evdLmp = evdLmp;
    }

    public String getEvdBodyTemp() {
        return evdBodyTemp;
    }

    public void setEvdBodyTemp(String evdBodyTemp) {
        this.evdBodyTemp = evdBodyTemp;
    }

    public String getEvdPulse() {
        return evdPulse;
    }

    public void setEvdPulse(String evdPulse) {
        this.evdPulse = evdPulse;
    }

    public String getEvdRespirationRate() {
        return evdRespirationRate;
    }

    public void setEvdRespirationRate(String evdRespirationRate) {
        this.evdRespirationRate = evdRespirationRate;
    }

    public String getEvdSpo2() {
        return evdSpo2;
    }

    public void setEvdSpo2(String evdSpo2) {
        this.evdSpo2 = evdSpo2;
    }

    public String getEvdSysBp() {
        return evdSysBp;
    }

    public void setEvdSysBp(String evdSysBp) {
        this.evdSysBp = evdSysBp;
    }

    public String getEvdDiaBp() {
        return evdDiaBp;
    }

    public void setEvdDiaBp(String evdDiaBp) {
        this.evdDiaBp = evdDiaBp;
    }

    public String getEvdMentalStatus() {
        return evdMentalStatus;
    }

    public void setEvdMentalStatus(String evdMentalStatus) {
        this.evdMentalStatus = evdMentalStatus;
    }

    public String getEvdRbs() {
        return evdRbs;
    }

    public void setEvdRbs(String evdRbs) {
        this.evdRbs = evdRbs;
    }

    public String getEvdRNnumber() {
        return evdRNnumber;
    }

    public void setEvdRNnumber(String evdRNnumber) {
        this.evdRNnumber = evdRNnumber;
    }

    public String getEvdO2Saturation() {
        return evdO2Saturation;
    }

    public void setEvdO2Saturation(String evdO2Saturation) {
        this.evdO2Saturation = evdO2Saturation;
    }

    public String getEvdRespiratoryRate() {
        return evdRespiratoryRate;
    }

    public void setEvdRespiratoryRate(String evdRespiratoryRate) {
        this.evdRespiratoryRate = evdRespiratoryRate;
    }

    public String getEvdPainReassessmentScore() {
        return evdPainReassessmentScore;
    }

    public void setEvdPainReassessmentScore(String evdPainReassessmentScore) {
        this.evdPainReassessmentScore = evdPainReassessmentScore;
    }

    public String getEvdPainTool() {
        return evdPainTool;
    }

    public void setEvdPainTool(String evdPainTool) {
        this.evdPainTool = evdPainTool;
    }

    @Override
    public String toString() {
        return "EmgVitalDetails{" +
                "evdId=" + evdId +
                ", evdHeight='" + evdHeight + '\'' +
                ", evdWeight='" + evdWeight + '\'' +
                ", evdBmi='" + evdBmi + '\'' +
                ", evdLmp='" + evdLmp + '\'' +
                ", evdBodyTemp='" + evdBodyTemp + '\'' +
                ", evdPulse='" + evdPulse + '\'' +
                ", evdRespirationRate='" + evdRespirationRate + '\'' +
                ", evdSpo2='" + evdSpo2 + '\'' +
                ", evdSysBp='" + evdSysBp + '\'' +
                ", evdDiaBp='" + evdDiaBp + '\'' +
                ", evdMentalStatus='" + evdMentalStatus + '\'' +
                ", evdRbs='" + evdRbs + '\'' +
                ", evdRNnumber='" + evdRNnumber + '\'' +
                ", evdO2Saturation='" + evdO2Saturation + '\'' +
                ", evdRespiratoryRate='" + evdRespiratoryRate + '\'' +
                ", evdPainReassessmentScore='" + evdPainReassessmentScore + '\'' +
                ", evdPainTool='" + evdPainTool + '\'' +
                '}';
    }
}
