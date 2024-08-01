package com.cellbeans.hspa.dailyoccupancy;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mipdward.MipdWard;
import com.cellbeans.hspa.mipdward.MipdWardRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitController;
import com.cellbeans.hspa.tbillbill.TbillBillController;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleController;
import com.cellbeans.hspa.tipdadvanceamount.TIpdAdvanceAmountController;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombinationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/daily_occupancy")
public class DailyoccupancyController {

    Map<String, Object> respMap = new HashMap<String, Object>();

    @Autowired
    MipdWardRepository mipdWardRepository;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    TbillBillController tbillBillController;

    @Autowired
    TIpdAdvanceAmountController tIpdAdvanceAmountController;

    @Autowired
    TrnSponsorCombinationController trnSponsorCombinationController;

    @Autowired
    TinvPharmacySaleController tinvPharmacySaleController;

    @Autowired
    MstVisitController mstVisitController;

    @RequestMapping("getdailyoccupancy")
    public List<Dailyoccupancy> listOfbedrecord(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        List<Dailyoccupancy> responce = new ArrayList<>();
        List<MipdWard> listward = mipdWardRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        for (MipdWard singleward : listward) {
            List<TrnAdmission> admissionlist = trnAdmissionRepository.findByAdmissionStatusIsFalseAndAdmissionIsCancelFalseAndAdmissionPatientBedIdBedWardIdWardIdEqualsAndIsActiveTrueAndIsDeletedFalse(singleward.getWardId());
            List<Object> patientlistwordwise = new ArrayList<>();
            for (TrnAdmission singleadmission : admissionlist) {
                Dailyoccupancy daillydate = new Dailyoccupancy();
                daillydate.setAdmissionDetails(singleadmission);
                Map<String, Double> pharma = tinvPharmacySaleController.pharmacyCallist(tenantName, singleadmission.getAdmissionId());
                Map<String, Double> bill = tbillBillController.billCallist(tenantName, singleadmission.getAdmissionId());
                double outstanding = pharma.get("psTotalAmount") + bill.get("Expenses");
                bill.clear();
                bill.put("Expenses", outstanding);
                daillydate.setOutstandingAmount(bill);
                daillydate.setBalanceAmount(tIpdAdvanceAmountController.advancelistbypatient(tenantName, singleadmission));
                daillydate.setCompany(trnSponsorCombinationController.Listbyuserid(tenantName, singleadmission.getAdmissionPatientId().getPatientUserId().getUserId()));
                daillydate.setNoOfDay(this.getday(tenantName, singleadmission.getAdmissionDate()));
                daillydate.setTraffname(this.gettariffname(tenantName, singleadmission.getAdmissionPatientId().getPatientId()));
                responce.add(daillydate);
            }
        }
        return responce;
    }

    public float getday(@RequestHeader("X-tenantId") String tenantName, String admissiondate) {
        TenantContext.setCurrentTenant(tenantName);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        float daysBetween = 0;
        try {
            System.out.println("admission date: " + admissiondate);
            System.out.println("current date: " + currentdate);
            Date date1 = myFormat.parse(admissiondate);
            Date date2 = myFormat.parse(currentdate);
            long diff = date2.getTime() - date1.getTime();
            daysBetween = (diff / (1000 * 60 * 60 * 24));
            System.out.println("Days: " + daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysBetween;
    }

    public String gettariffname(@RequestHeader("X-tenantId") String tenantName, Long patientid) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisit mstVisit = new MstVisit();
        mstVisit = mstVisitController.lastvisitbypatientid(patientid);
        return mstVisit.getVisitTariffId().getTariffName();
    }

}
