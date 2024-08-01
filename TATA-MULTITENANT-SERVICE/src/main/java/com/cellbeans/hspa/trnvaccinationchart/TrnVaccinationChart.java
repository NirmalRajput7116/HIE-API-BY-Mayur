package com.cellbeans.hspa.trnvaccinationchart;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvaccautochart.MstVaccAutoChart;
import com.cellbeans.hspa.mstvaccinationchart.MstVaccinationChart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "trn_vaccination_chart")
public class TrnVaccinationChart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vcId")
    private Long vcId;

    @JsonInclude(NON_NULL)
    @Column(name = "dueDate")
    private String dueDate;

    @JsonInclude(NON_NULL)
    @Column(name = "adminDate")
    private String adminDate;

    @JsonInclude(NON_NULL)
    @Column(name = "comments")
    private String comments;

    @JsonInclude(NON_NULL)
    @Column(name = "givenDate")
    private String givenDate;

    @JsonIgnore
    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @JsonIgnore
    @Column(name = "isActive")
    private Boolean isActive;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "chartId")
    @ManyToOne
    private MstVaccinationChart chartId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "vacId")
    @ManyToOne
    private MstVaccAutoChart vacId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "givenBy")
    @ManyToOne
    private MstStaff givenBy;

    @JsonIgnore
    @Column(name = "createdBy")
    private String createdBy;

    @JsonIgnore
    @Column(name = "createdDate")
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getVcId() {
        return vcId;
    }

    public void setVcId(Long vcId) {
        this.vcId = vcId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAdminDate() {
        return adminDate;
    }

    public void setAdminDate(String adminDate) {
        this.adminDate = adminDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(String givenDate) {
        this.givenDate = givenDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public MstVaccinationChart getChartId() {
        return chartId;
    }

    public void setChartId(MstVaccinationChart chartId) {
        this.chartId = chartId;
    }

    public MstVaccAutoChart getVacId() {
        return vacId;
    }

    public void setVacId(MstVaccAutoChart vacId) {
        this.vacId = vacId;
    }

    public MstStaff getGivenBy() {
        return givenBy;
    }

    public void setGivenby(MstStaff givenby) {
        this.givenBy = givenBy;
    }

    @Override
    public String toString() {
        return "TrnVaccinationChart{" + "vcId=" + vcId + ", dueDate='" + dueDate + '\'' + ", adminDate='" + adminDate + '\'' + ", comments='" + comments + '\'' + ", givenDate='" + givenDate + '\'' + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", chartId=" + chartId + ", vacId=" + vacId + ", givenBy=" + givenBy + '}';
    }

}
