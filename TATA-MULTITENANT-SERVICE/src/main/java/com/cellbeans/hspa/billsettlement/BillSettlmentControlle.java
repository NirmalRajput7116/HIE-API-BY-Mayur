package com.cellbeans.hspa.billsettlement;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsor;
import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsorRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill_settelment")
public class BillSettlmentControlle {

    @Autowired
    TrnBillBillSponsorRepository trnBillBillSponsorRepository;

    @Autowired
    TbillBillRepository tbillBillRepository;

    @GetMapping("companyBillList")
    public Iterable<TrnBillBillSponsor> companyBillList(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "companyName ", required = false) String companyName,
            @RequestParam(value = "billNo", required = false) String billNo,
            @RequestParam(value = "mrNo", required = false) String mrNo,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "bbsId") String col,
            @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        Page<TrnBillBillSponsor> pages = null;
        if (qString == null || qString.equals("") || qString.equals("opd")) {
            if (companyName != null && !companyName.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(companyName, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (billNo != null && !billNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(billNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (mrNo != null && !mrNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(mrNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdIpdBillFalseAndBbsBillIdEmrbillFalseAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
        } else if (qString.equals("ipd")) {
            if (companyName != null && !companyName.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(companyName, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (billNo != null && !billNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(billNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (mrNo != null && !mrNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndBbsBillIdIpdBillTrueAndIsDeletedFalse(mrNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdIpdBillTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
        } else if (qString.equals("emg")) {
            if (companyName != null && !companyName.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsSCIdScCompanyIdCompanyNameContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(companyName, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (billNo != null && !billNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillNumberContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(billNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (mrNo != null && !mrNo.equals("")) {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsAndBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(mrNo, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else {
                pages = trnBillBillSponsorRepository.findAllByBbsBillIdIpdBillFalseAndBbsBillIdEmrbillTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }
        } else {
        }
        return pages;
    }

    @GetMapping("settleBillList")
    public Iterable<TrnBillBillSponsor> settleBillList(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "bbsId") String col,
            @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        Page<TrnBillBillSponsor> pages = null;
        if (qString == null || qString.equals("") || qString.equals("opd")) {
            pages = trnBillBillSponsorRepository.findAllByBbsBillIdANDBbsBillIdBillIpdBillFalseAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (qString.equals("ipd")) {
            pages = trnBillBillSponsorRepository.findAllByBbsBillIdBillCompanyOutStandingANDBbsBillIdBillIpdBillTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (qString.equals("emg")) {
        } else {
        }
        return pages;
    }

    @GetMapping("companyBillListByCompanyName")
    public Iterable<TrnBillBillSponsor> companyBillListByCompanyName(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "bbsId") String col,
            @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId,
            @RequestParam(value = "company", required = false, defaultValue = "1") String company) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("company : " + company);
        System.out.println("company : " + Long.valueOf(company));
        Page<TrnBillBillSponsor> pages = null;
        if (qString == null || qString.equals("") || qString.equals("opd")) {
            pages = trnBillBillSponsorRepository.findAllByBbsSCIdScCompanyIdCompanyIdAndBbsBillIdIpdBillFalseAndIsDeletedFalse(Long.valueOf(company), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (qString.equals("ipd")) {
            pages = trnBillBillSponsorRepository.findAllByBbsSCIdScCompanyIdCompanyIdAndBbsBillIdIpdBillTrueAndIsDeletedFalse(Long.valueOf(company), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else if (qString.equals("emg")) {
        } else {
        }
        return pages;
    }

    @PostMapping("settleCompanyBill")
    public int updateCompanyBillByBillId(@RequestHeader("X-tenantId") String tenantName, @RequestBody String input) {
        TenantContext.setCurrentTenant(tenantName);
        JSONArray array = new JSONArray(input);
        int result = 0;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            result = tbillBillRepository.updateCompanyBill(object.getLong("billId"), object.getDouble("companyOutStanding"), object.getDouble("discountPer"), object.getDouble("discountAmount"), object.getDouble("crnDiscountPer"), object.getDouble("crnDiscountAmount"), object.getDouble("paidAmount"), object.getString("remark"), object.getBoolean("isSettle"));
        }
        return result;
    }

}
