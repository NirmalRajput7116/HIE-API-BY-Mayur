package com.cellbeans.hspa.tinvpurchaseitemenquiry;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "enquiryTemplate")
public class EnquiryTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etId", unique = true, nullable = true)
    private long etId;

    @JsonInclude(NON_NULL)
    @Column(name = "templateName")
    private String templateName;

    @JsonInclude(NON_NULL)
    //@Lob
    @Column(name = "templateData")
    private String templateData;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "templateUnitId")
    private MstUnit templateUnitId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    public long getEtId() {
        return etId;
    }

    public void setEtId(long etId) {
        this.etId = etId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
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

    public MstUnit getTemplateUnitId() {
        return templateUnitId;
    }

    public void setTemplateUnitId(MstUnit templateUnitId) {
        this.templateUnitId = templateUnitId;
    }

}
