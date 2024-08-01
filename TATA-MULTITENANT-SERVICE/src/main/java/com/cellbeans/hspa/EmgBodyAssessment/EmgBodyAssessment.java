package com.cellbeans.hspa.EmgBodyAssessment;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EmgBodyAssessment")
public class EmgBodyAssessment {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebaId", unique = true, nullable = true)
    private long ebaId;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaHead")
    private String ebaHead;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaEyeLeft")
    private String ebaEyeLeft;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaEyeRight")
    private String ebaEyeRight;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaEarsLeft")
    private String ebaEarsLeft;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaEarsRight")
    private String ebaEarsRight;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaNose")
    private String ebaNose;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaSkin")
    private String ebaSkin;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaMouth")
    private String ebaMouth;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaNeck")
    private String ebaNeck;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaChest")
    private String ebaChest;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaAbdomen")
    private String ebaAbdomen;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaPelvis")
    private String ebaPelvis;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaExtremities")
    private String ebaExtremities;

    @JsonInclude(NON_NULL)
    @Column(name = "ebaPosterior")
    private String ebaPosterior;

    public long getEbaId() {
        return ebaId;
    }

    public void setEbaId(long ebaId) {
        this.ebaId = ebaId;
    }

    public String getEbaHead() {
        return ebaHead;
    }

    public void setEbaHead(String ebaHead) {
        this.ebaHead = ebaHead;
    }

    public String getEbaEyeLeft() {
        return ebaEyeLeft;
    }

    public void setEbaEyeLeft(String ebaEyeLeft) {
        this.ebaEyeLeft = ebaEyeLeft;
    }

    public String getEbaEyeRight() {
        return ebaEyeRight;
    }

    public void setEbaEyeRight(String ebaEyeRight) {
        this.ebaEyeRight = ebaEyeRight;
    }

    public String getEbaEarsLeft() {
        return ebaEarsLeft;
    }

    public void setEbaEarsLeft(String ebaEarsLeft) {
        this.ebaEarsLeft = ebaEarsLeft;
    }

    public String getEbaEarsRight() {
        return ebaEarsRight;
    }

    public void setEbaEarsRight(String ebaEarsRight) {
        this.ebaEarsRight = ebaEarsRight;
    }

    public String getEbaNose() {
        return ebaNose;
    }

    public void setEbaNose(String ebaNose) {
        this.ebaNose = ebaNose;
    }

    public String getEbaSkin() {
        return ebaSkin;
    }

    public void setEbaSkin(String ebaSkin) {
        this.ebaSkin = ebaSkin;
    }

    public String getEbaMouth() {
        return ebaMouth;
    }

    public void setEbaMouth(String ebaMouth) {
        this.ebaMouth = ebaMouth;
    }

    public String getEbaNeck() {
        return ebaNeck;
    }

    public void setEbaNeck(String ebaNeck) {
        this.ebaNeck = ebaNeck;
    }

    public String getEbaChest() {
        return ebaChest;
    }

    public void setEbaChest(String ebaChest) {
        this.ebaChest = ebaChest;
    }

    public String getEbaAbdomen() {
        return ebaAbdomen;
    }

    public void setEbaAbdomen(String ebaAbdomen) {
        this.ebaAbdomen = ebaAbdomen;
    }

    public String getEbaPelvis() {
        return ebaPelvis;
    }

    public void setEbaPelvis(String ebaPelvis) {
        this.ebaPelvis = ebaPelvis;
    }

    public String getEbaExtremities() {
        return ebaExtremities;
    }

    public void setEbaExtremities(String ebaExtremities) {
        this.ebaExtremities = ebaExtremities;
    }

    public String getEbaPosterior() {
        return ebaPosterior;
    }

    public void setEbaPosterior(String ebaPosterior) {
        this.ebaPosterior = ebaPosterior;
    }

    @Override
    public String toString() {
        return "EmgBodyAssessment{" + "ebaId=" + ebaId + ", ebaHead='" + ebaHead + '\'' + ", ebaEyeLeft='" + ebaEyeLeft + '\'' + ", ebaEyeRight='" + ebaEyeRight + '\'' + ", ebaEarsLeft='" + ebaEarsLeft + '\'' + ", ebaEarsRight='" + ebaEarsRight + '\'' + ", ebaNose='" + ebaNose + '\'' + ", ebaSkin='" + ebaSkin + '\'' + ", ebaMouth='" + ebaMouth + '\'' + ", ebaNeck='" + ebaNeck + '\'' + ", ebaChest='" + ebaChest + '\'' + ", ebaAbdomen='" + ebaAbdomen + '\'' + ", ebaPelvis='" + ebaPelvis + '\'' + ", ebaExtremities='" + ebaExtremities + '\'' + ", ebaPosterior='" + ebaPosterior + '\'' + '}';
    }

}
