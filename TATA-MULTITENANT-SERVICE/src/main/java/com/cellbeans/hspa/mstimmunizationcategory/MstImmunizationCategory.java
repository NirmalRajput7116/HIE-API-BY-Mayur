
package com.cellbeans.hspa.mstimmunizationcategory;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_immunization_category")
public class MstImmunizationCategory implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "icId", unique = true, nullable = false)
                            private long icId;
                        
                           @Column(name = "icName")
                           private String icName;
                        
                            @Column(name = "icAgeFromInDays")
                            private int icAgeFromInDays;
                        
                            @Column(name = "icAgeTillInDays")
                            private int icAgeTillInDays;
                        
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;
    
    @JsonIgnore
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;
                        
                            public long getIcId() {
                                    return this.icId;
                            }

                            public void setIcId(int icId) {
                                    this.icId = icId;
                            }
                        
                            public String getIcName() {
                                    return this.icName;
                            }

                            public void setIcName(String icName) {
                                    this.icName = icName;
                            }
                        
                           public int getIcAgeFromInDays() {
                                    return this.icAgeFromInDays;
                            }

                            public void setIcAgeFromInDays(int icAgeFromInDays) {
                                    this.icAgeFromInDays = icAgeFromInDays;
                            }
                        
                           public int getIcAgeTillInDays() {
                                    return this.icAgeTillInDays;
                            }

                            public void setIcAgeTillInDays(int icAgeTillInDays) {
                                    this.icAgeTillInDays = icAgeTillInDays;
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
                        
}
