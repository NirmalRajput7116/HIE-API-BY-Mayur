package com.cellbeans.hspa.tbillserviceradiology;

import java.util.Date;

public class TbillServiceRadiologyDTO {

    private String serviceCode;
    private String serviceName;
    private String performedBy;
    private Boolean isServiceBilled;
    private Boolean isServiceDone;
    private Date serviceDate;
    private String reportPath;
    private int reportId;
    private Boolean isReported;
    private Boolean cancelService;
    private long bsrId;
    private Boolean ipd;
    private String patientName;
    private String patientMrNo;

    public TbillServiceRadiologyDTO() {
    }

    public TbillServiceRadiologyDTO(String serviceCode, String serviceName, String performedBy, Boolean isServiceBilled, Boolean isServiceDone, Date serviceDate, String reportPath, int reportId, Boolean isReported) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.performedBy = performedBy;
        this.isServiceBilled = isServiceBilled;
        this.isServiceDone = isServiceDone;
        this.serviceDate = serviceDate;
        this.reportPath = reportPath;
        this.reportId = reportId;
        this.isReported = isReported;
    }

    public Boolean getReported() {
        return isReported;
    }

    public void setReported(Boolean reported) {
        isReported = reported;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public Boolean getServiceBilled() {
        return isServiceBilled;
    }

    public void setServiceBilled(Boolean serviceBilled) {
        isServiceBilled = serviceBilled;
    }

    public Boolean getServiceDone() {
        return isServiceDone;
    }

    public void setServiceDone(Boolean serviceDone) {
        isServiceDone = serviceDone;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public long getBsrId() {
        return bsrId;
    }

    public void setBsrId(long bsrId) {
        this.bsrId = bsrId;
    }

    public Boolean getIpd() {
        return ipd;
    }

    public void setIpd(Boolean ipd) {
        this.ipd = ipd;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public Boolean getCancelService() {
        return cancelService;
    }

    public void setCancelService(Boolean cancelService) {
        this.cancelService = cancelService;
    }
}
