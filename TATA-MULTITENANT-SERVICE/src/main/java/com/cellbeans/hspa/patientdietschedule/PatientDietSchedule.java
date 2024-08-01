package com.cellbeans.hspa.patientdietschedule;

import com.cellbeans.hspa.mstdietchart.MstDietChart;
import com.cellbeans.hspa.mstdietitem.MstDietItem;
import com.cellbeans.hspa.mstdietschedule.MstDietSchedule;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author admin
 */
@Entity
@Table(name = "patient_diet_schedule")
public class PatientDietSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(name = "createdate")
    private String createdate;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @Column(name = "updatedtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedtime;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "diet_item_id")
    @ManyToOne
    private MstDietItem dietItemId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "scheduledby")
    @ManyToOne
    private MstStaff scheduledby;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "vist_id")
    @ManyToOne
    private MstVisit vistId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "diet_schedule")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private MstDietSchedule dietSchedule;

    @JsonInclude(NON_NULL)
    @Column(name = "item_quantity")
    private String itemQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "item_calorie")
    private String itemCalorie;

    @JsonInclude(NON_NULL)
    @Column(name = "item_price")
    private String itemPrice;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "diet_chart_id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private MstDietChart dietChartId;

    @JsonInclude(NON_NULL)
    @Column(name = "comment")
    private String comment;

    public PatientDietSchedule() {
    }

    public PatientDietSchedule(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemCalorie() {
        return itemCalorie;
    }

    public void setItemCalorie(String itemCalorie) {
        this.itemCalorie = itemCalorie;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public MstDietChart getDietChartId() {
        return dietChartId;
    }

    public void setDietChartId(MstDietChart dietChartId) {
        this.dietChartId = dietChartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public MstDietItem getDietItemId() {
        return dietItemId;
    }

    public void setDietItemId(MstDietItem dietItemId) {
        this.dietItemId = dietItemId;
    }

    public MstStaff getScheduledby() {
        return scheduledby;
    }

    public void setScheduledby(MstStaff scheduledby) {
        this.scheduledby = scheduledby;
    }

    public MstVisit getVistId() {
        return vistId;
    }

    public void setVistId(MstVisit vistId) {
        this.vistId = vistId;
    }

    public MstDietSchedule getDietSchedule() {
        return dietSchedule;
    }

    public void setDietSchedule(MstDietSchedule dietSchedule) {
        this.dietSchedule = dietSchedule;
    }

}
