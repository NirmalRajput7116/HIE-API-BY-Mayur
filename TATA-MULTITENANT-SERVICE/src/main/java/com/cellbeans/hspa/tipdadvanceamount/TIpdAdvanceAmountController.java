package com.cellbeans.hspa.tipdadvanceamount;

import com.cellbeans.hspa.TenantContext;

import com.cellbeans.hspa.advancedhistory.AdvanceRefundHistory;
import com.cellbeans.hspa.advancedhistory.AdvancedHistory;
import com.cellbeans.hspa.advancedhistory.AdvancedHistoryRepository;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillreciept.TbillRecieptRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipd_advanced_amount")
public class TIpdAdvanceAmountController {

    Map<String, String> respMap = new HashMap();

    @Autowired
    TIpdAdvanceAmountRepository tIpdAdvanceAmountRepository;

    @Autowired
    AdvanceRefundHistoryRepository advanceRefundHistoryRepository;

    @Autowired
    AdvancedHistoryRepository advancedHistoryRepository;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    TbillRecieptRepository tbillRecieptRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TIpdAdvanceAmount tipdAdvancedAmount) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap2 = new HashMap();
        if (tipdAdvancedAmount.getAdvancedAmount() > 0) {
            tipdAdvancedAmount.setAaRecieptNumber(tIpdAdvanceAmountRepository.makeRecieptNumber());
            TIpdAdvanceAmount newtIpdAdvanceAmount = tIpdAdvanceAmountRepository.save(tipdAdvancedAmount);
            respMap2.put("success", "1");
            respMap2.put("msg", "Added Successfully");
            String recieptNumbers = tipdAdvancedAmount.getAaRecieptNumber();
            respMap2.put("recieptNumbers", recieptNumbers);
            return respMap2;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Amount is ZERO: no need to save");
            return respMap2;
        }
    }

    @RequestMapping("update")
    public TIpdAdvanceAmount update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TIpdAdvanceAmount tipdAdvancedAmount) {
        TenantContext.setCurrentTenant(tenantName);
        AdvanceRefundHistory advanceRefundHistory = new AdvanceRefundHistory();
        advanceRefundHistory.setPaymentMode(tipdAdvancedAmount.getAaPmId().getPmName());
        advanceRefundHistory.setRefundAmount(tipdAdvancedAmount.getRefundAmount());
        advanceRefundHistory.setRefundDate(new Date());
        advanceRefundHistory.setAdvanceId(tipdAdvancedAmount);
        advanceRefundHistoryRepository.save(advanceRefundHistory);
        return tIpdAdvanceAmountRepository.save(tipdAdvancedAmount);
    }

    @RequestMapping("refund_advance/{refund_amount}")
    public TIpdAdvanceAmount refundAdvance(@RequestHeader("X-tenantId") String tenantName, @RequestBody TIpdAdvanceAmount tipdAdvancedAmount, @PathVariable("refund_amount") Double refund_amount) {
        TenantContext.setCurrentTenant(tenantName);
        AdvanceRefundHistory advanceRefundHistory = new AdvanceRefundHistory();
        advanceRefundHistory.setPaymentMode(tipdAdvancedAmount.getAaPmId().getPmName());
        advanceRefundHistory.setRefundAmount(refund_amount);
        advanceRefundHistory.setRefundDate(new Date());
        advanceRefundHistory.setAdvanceId(tipdAdvancedAmount);
        advanceRefundHistoryRepository.save(advanceRefundHistory);
        return tIpdAdvanceAmountRepository.save(tipdAdvancedAmount);
    }

    @RequestMapping("updateadvancedhistory/{billId}")
    public TIpdAdvanceAmount updateWithHistory(@RequestHeader("X-tenantId") String tenantName, @RequestBody TIpdAdvanceAmount tipdAdvancedAmount,
                                               @PathVariable("billId") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        AdvancedHistory advancedHistory = new AdvancedHistory();
        advancedHistory.setConsumedDate(new Date());
        advancedHistory.setAdvancedAmountId(tipdAdvancedAmount);
        TBillBill tbBill = new TBillBill();
        tbBill.setBillId(billId.intValue());
        advancedHistory.setBillId(tbBill);
        advancedHistory.setConsumedAmount(tipdAdvancedAmount.getCurrentAmount());
        advancedHistory.setBalancedAmount(tipdAdvancedAmount.getAdvancedBalance());
        advancedHistoryRepository.save(advancedHistory);
        return tIpdAdvanceAmountRepository.save(tipdAdvancedAmount);
    }

    // Author : Mohit
    @GetMapping("searchpatient")
    public List<MstPatient> searchPatientWithAdvance(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "qString", required = false, defaultValue = "") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstPatient> mstPatients = mstPatientRepository
                .findAllByPatientMrNoContainsOrPatientUserIdUserFirstnameContainsOrPatientUserIdUserLastnameContainsOrPatientUserIdUserMobileContainsAndIsActiveTrueAndIsDeletedFalse(
                        qString, qString, qString, qString);
        if (mstPatients.size() > 0) {
            for (MstPatient mstPatient : mstPatients) {
                TIpdAdvanceAmount tIpdAdvanceAmount = tIpdAdvanceAmountRepository
                        .findByAaPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(mstPatient.getPatientId());
                if (tIpdAdvanceAmount != null) {
                    mstPatient.setAdvanceAmount(tIpdAdvanceAmount.getAdvancedAmount());
                    mstPatient.setIpdadvancedId(tIpdAdvanceAmount.getIpdadvancedId());
                    mstPatient.setIpdAdvanceRemarks(tIpdAdvanceAmount.getAdvanceRemark());
                }
            }
        }
        return mstPatients;
    }

    @GetMapping("searchByAdvancePaymentId/{advancedId}")
    public List<AdvancedHistory> searchByAdvancePaymentId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("advancedId") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return advancedHistoryRepository.findByAdvancedAmountIdIpdadvancedId(id);
    }

    @GetMapping("list")
    public Iterable<TIpdAdvanceAmount> list(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "ipdadvancedId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tIpdAdvanceAmountRepository.findByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            // long id=Long.parseLong(qString);
            return tIpdAdvanceAmountRepository
                    .findAllByAdvanceAgainstIdAaNameAndIsActiveTrueAndIsDeletedFalseOrAaCompanyIdCompanyNameAndIsActiveTrueAndIsDeletedFalseOrAaRecieptNumberContainsAndIsActiveTrueAndIsDeletedFalse(
                            qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping("listbypatient")
    public Iterable<TIpdAdvanceAmount> listbypatient(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "ipdadvancedId") String col,
            @RequestParam(value = "patientId", required = false, defaultValue = "0") long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return tIpdAdvanceAmountRepository.findAllByAaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(
                patientId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("listbyadmission")
    public List<TIpdAdvanceAmount> getbyadmission(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        String adddate = trnAdmission.getAdmissionDate().substring(0, 10);
        if (trnAdmission.getAdmissionDischargeDate() == null) {
            return tIpdAdvanceAmountRepository
                    .findAllByAaPatientIdPatientIdAdmissionidEqualsAndIsActiveTrueAndIsDeletedFalse(
                            trnAdmission.getAdmissionPatientId().getPatientId(), adddate);
        } else {
            return tIpdAdvanceAmountRepository
                    .findAllByAaPatientIdPatientIdAdmissionidanddischargeEqualsAndIsActiveTrueAndIsDeletedFalse(
                            trnAdmission.getAdmissionPatientId().getPatientId(), adddate,
                            trnAdmission.getAdmissionDischargeDate());
        }
    }

    @GetMapping("listbyopdpatient")
    public Iterable<TIpdAdvanceAmount> listbyopdpatient(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "ipdadvancedId") String col,
            @RequestParam(value = "MrNo", required = false, defaultValue = "0") String MrNo) {
        TenantContext.setCurrentTenant(tenantName);
        return tIpdAdvanceAmountRepository.findAllByAaPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(MrNo,
                PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @PutMapping("delete/{advanceId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("advanceId") Long advanceId) {
        TenantContext.setCurrentTenant(tenantName);
        TIpdAdvanceAmount tIpdAdvanceAmount = tIpdAdvanceAmountRepository.getById(advanceId);
        if (tIpdAdvanceAmount != null) {
            tIpdAdvanceAmount.setDeleted(true);
            tIpdAdvanceAmountRepository.save(tIpdAdvanceAmount);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping("getbypatient/{patientid}")
    public MstPatient getPatientForAdvanceBalance(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientid") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.getById(patientId);
        if (mstPatient.getPatientId() > 0) {
            TIpdAdvanceAmount tIpdAdvanceAmount = tIpdAdvanceAmountRepository
                    .findByAaPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(patientId);
            if (tIpdAdvanceAmount != null) {
                mstPatient.setAdvanceAmount(tIpdAdvanceAmount.getAdvancedAmount());
                mstPatient.setIpdadvancedId(tIpdAdvanceAmount.getIpdadvancedId());
                mstPatient.setIpdAdvanceRemarks(tIpdAdvanceAmount.getAdvanceRemark());
            }
        }
        return mstPatient;
    }

    @GetMapping("byid/{id}")
    public TIpdAdvanceAmount getAdvancedAmount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long Id) {
        TenantContext.setCurrentTenant(tenantName);
        TenantContext.setCurrentTenant(tenantName);
        TIpdAdvanceAmount tipdAdvanceAmount = tIpdAdvanceAmountRepository
                .findByIpdadvancedIdAndIsActiveTrueAndIsDeletedFalse(Id);
        return tipdAdvanceAmount;
    }

    public Map<String, Double> advancelistbypatient(@RequestHeader("X-tenantId") String tenantName, TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Double> respCalMap = new HashMap<String, Double>();
        String query = "select sum(i.advanced_amount),sum(i.advanced_balance),sum(i.advanced_consumed) from trn_ipd_advanced i where i.aa_patient_id = "
                + trnAdmission.getAdmissionPatientId().getPatientId() + " and created_date > '"
                + trnAdmission.getAdmissionDate() + "'";
        try {
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
            if (list.size() > 0) {
                for (Object[] obj : list) {
                    respCalMap.put("paidAmount", Double.valueOf(obj[0].toString()));
                    respCalMap.put("balanceAmount", Double.valueOf(obj[1].toString()));
                    respCalMap.put("consumedAmount", Double.valueOf(obj[2].toString()));
                }
            } else {
                respCalMap.put("paidAmount", Double.valueOf(0));
                respCalMap.put("balanceAmount", Double.valueOf(0));
                respCalMap.put("consumedAmount", Double.valueOf(0));

            }
        } catch (Exception e) {
            respCalMap.put("paidAmount", Double.valueOf(0));
            respCalMap.put("balanceAmount", Double.valueOf(0));
            respCalMap.put("consumedAmount", Double.valueOf(0));

        }
        return respCalMap;
    }

    @GetMapping("getbydate")
    public Iterable<TIpdAdvanceAmount> getPatientByDateForAdvanceBalance(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size,
            @RequestParam(value = "fromdate", required = false) String fromdate,
            @RequestParam(value = "todate", required = false) String todate,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "ipdadvancedId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (fromdate == null || fromdate.equals("") || todate == null || todate.equals("")) {
            return tIpdAdvanceAmountRepository.findByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            Date fdate = null, tdate = null;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fdate = df.parse(fromdate);
                tdate = df.parse(todate);
                long ltime = tdate.getTime() + 24 * 60 * 60 * 1000;
                tdate = new Date(ltime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tIpdAdvanceAmountRepository.findByCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(fdate, tdate,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }
}