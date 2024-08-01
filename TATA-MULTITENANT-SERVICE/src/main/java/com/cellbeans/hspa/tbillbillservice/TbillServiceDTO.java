package com.cellbeans.hspa.tbillbillservice;

import com.cellbeans.hspa.mbillservice.MbillService;

public class TbillServiceDTO {

    private MbillService service;
    private double qty;
    private double baserate;
    private double grossrate;
    private double concessionper;
    private double concessionamt;
    private double tariffcprate;

    public TbillServiceDTO(MbillService service, double qty, double baserate, double grossrate, double concessionper, double concessionamt, double tariffcprate) {
        this.service = service;
        this.qty = qty;
        this.baserate = baserate;
        this.grossrate = grossrate;
        this.concessionper = concessionper;
        this.concessionamt = concessionamt;
        this.tariffcprate = tariffcprate;
    }

    public TbillServiceDTO(MbillService service) {
        this.service = service;
    }

    public MbillService getService() {
        return service;
    }

    public void setService(MbillService service) {
        this.service = service;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getBaserate() {
        return baserate;
    }

    public void setBaserate(double baserate) {
        this.baserate = baserate;
    }

    public double getGrossrate() {
        return grossrate;
    }

    public void setGrossrate(double grossrate) {
        this.grossrate = grossrate;
    }

    public double getConcessionper() {
        return concessionper;
    }

    public void setConcessionper(double concessionper) {
        this.concessionper = concessionper;
    }

    public double getConcessionamt() {
        return concessionamt;
    }

    public void setConcessionamt(double concessionamt) {
        this.concessionamt = concessionamt;
    }

    public double getTariffcprate() {
        return tariffcprate;
    }

    public void setTariffcprate(double tariffcprate) {
        this.tariffcprate = tariffcprate;
    }

}
