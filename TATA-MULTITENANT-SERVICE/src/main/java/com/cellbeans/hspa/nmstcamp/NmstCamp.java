
package com.cellbeans.hspa.nmstcamp ;
import java.io.Serializable;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
 import com.cellbeans.hspa.nmstprogramcategory.NmstProgramCategory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nmst_camp")
public class NmstCamp implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "campId", unique = true, nullable = true)
                            private long campId;
                        
                        @ManyToOne
                         @JoinColumn(name = "campPcId")
                         private NmstProgramCategory campPcId;
                        
                           @Column(name = "campName")
                           private String campName;
                        
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


    //    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date campFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date campToDate;


    @Column(name = "campDescription")
    private String campDescription;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "campStaffId")
    private List<MstStaff> campStaffId;


    public long getCampId() {
                                    return this.campId;
                            }

                            public void setCampId(int campId) {
                                    this.campId = campId;
                            }
                        
                          public NmstProgramCategory getCampPcId() {
                                    return this.campPcId;
                            }

                            public void setCampPcId(NmstProgramCategory campPcId) {
                                    this.campPcId = campPcId;
                            }
                        
                            public String getCampName() {
                                    return this.campName;
                            }

                            public void setCampName(String campName) {
                                    this.campName = campName;
                            }
                        
    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }    
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCampFromDate() {        return campFromDate;    }

    public void setCampFromDate(Date campFromDate) {        this.campFromDate = campFromDate;    }

    public Date getCampToDate() {        return campToDate;    }

    public void setCampToDate(Date campToDate) {        this.campToDate = campToDate;    }

    public String getCampDescription() {        return campDescription;    }

    public void setCampDescription(String campDescription) {        this.campDescription = campDescription;    }

    public List<MstStaff> getCampStaffId() {        return campStaffId;    }

    public void setCampStaffId(List<MstStaff> campStaffId) {        this.campStaffId = campStaffId;    }
}
