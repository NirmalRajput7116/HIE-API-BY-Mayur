package com.cellbeans.hspa.tatalabanatomyresult;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lis_testresult")
public class TataAnatomyTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "specimenid")
    private String specimenid;

    @Column(name = "classcode")
    private String classcode;

    @Column(name = "itemcode")
    private String itemcode;

    @Column(name = "value")
    private String value;

    @Column(name = "strvalue")
    private String strvalue;

    @Column(name = "unit")
    private String unit;

    @Column(name = "low")
    private String low;

    @Column(name = "high")
    private String high;

    @Column(name = "hint")
    private String Hint;

    @Column(name = "receivetime")
    private String receivetime;

    @Column(name = "inspecttime")
    private String inspecttime;

    @Column(name = "srceditflag")
    private String srceditflag;

    @Column(name = "srcstrvalue")
    private String srcstrvalue;

    @Column(name = "highlowflag")
    private String highlowflag;

    @Column(name = "abnormalflag")
    private String abnormalflag;

    @Column(name = "reagentinvalidflag")
    private String reagentinvalidflag;

    @Column(name = "editflag")
    private String editflag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpecimenid() {
        return specimenid;
    }

    public void setSpecimenid(String specimenid) {
        this.specimenid = specimenid;
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStrvalue() {
        return strvalue;
    }

    public void setStrvalue(String strvalue) {
        this.strvalue = strvalue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getInspecttime() {
        return inspecttime;
    }

    public void setInspecttime(String inspecttime) {
        this.inspecttime = inspecttime;
    }

    public String getSrceditflag() {
        return srceditflag;
    }

    public void setSrceditflag(String srceditflag) {
        this.srceditflag = srceditflag;
    }

    public String getSrcstrvalue() {
        return srcstrvalue;
    }

    public void setSrcstrvalue(String srcstrvalue) {
        this.srcstrvalue = srcstrvalue;
    }

    public String getHighlowflag() {
        return highlowflag;
    }

    public void setHighlowflag(String highlowflag) {
        this.highlowflag = highlowflag;
    }

    public String getAbnormalflag() {
        return abnormalflag;
    }

    public void setAbnormalflag(String abnormalflag) {
        this.abnormalflag = abnormalflag;
    }

    public String getReagentinvalidflag() {
        return reagentinvalidflag;
    }

    public void setReagentinvalidflag(String reagentinvalidflag) {
        this.reagentinvalidflag = reagentinvalidflag;
    }

    public String getEditflag() {
        return editflag;
    }

    public void setEditflag(String editflag) {
        this.editflag = editflag;
    }

    @Override
    public String toString() {
        return "TataAnatomyTestResult{" + "id=" + id + ", specimenid='" + specimenid + '\'' + ", classcode='" + classcode + '\'' + ", itemcode='" + itemcode + '\'' + ", value='" + value + '\'' + ", strvalue='" + strvalue + '\'' + ", unit='" + unit + '\'' + ", low='" + low + '\'' + ", high='" + high + '\'' + ", Hint='" + Hint + '\'' + ", receivetime='" + receivetime + '\'' + ", inspecttime='" + inspecttime + '\'' + ", srceditflag='" + srceditflag + '\'' + ", srcstrvalue='" + srcstrvalue + '\'' + ", highlowflag='" + highlowflag + '\'' + ", abnormalflag='" + abnormalflag + '\'' + ", reagentinvalidflag='" + reagentinvalidflag + '\'' + ", editflag='" + editflag + '\'' + '}';
    }

}
