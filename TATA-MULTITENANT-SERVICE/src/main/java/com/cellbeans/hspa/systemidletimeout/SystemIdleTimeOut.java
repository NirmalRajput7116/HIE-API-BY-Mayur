package com.cellbeans.hspa.systemidletimeout;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "system_idle_time_out")
public class SystemIdleTimeOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private Long id;

    @JsonInclude(NON_NULL)
    @Column(name = "radioBA")
    private Boolean radioBA = false;

    @JsonInclude(NON_NULL)
    @Column(name = "idleTO")
    private Integer idleTO = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = id;
    }

    public Boolean getRadioBA() {
        return radioBA;
    }

    public void setRadioBA(Boolean radioBA) {
        this.radioBA = radioBA;
    }

    public Integer getIdleTO() {
        return idleTO;
    }

    public void setIdleTO(Integer idleTO) {
        this.idleTO = idleTO;
    }

}
