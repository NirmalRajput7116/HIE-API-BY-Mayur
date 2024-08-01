package com.cellbeans.hspa.tbillbill;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitController;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsor;
import com.cellbeans.hspa.tbillbillSponsor.TrnBillBillSponsorRepository;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.tbillbillservice.TbillBillServiceRepository;
import com.cellbeans.hspa.tbillreciept.TbillReciept;
import com.cellbeans.hspa.tbillreciept.TbillRecieptController;
import com.cellbeans.hspa.tbillreciept.TbillRecieptRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import com.cellbeans.hspa.tinvpharmacybillrecepit.PharmacyBillRecepit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleBillDTO;
import com.cellbeans.hspa.tinvpharmacysaleitem.TinvPharmacySaleItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tbill_bill")
public class TbillBillController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TbillBillRepository tbillBillRepository;
    @Autowired
    MstVisitController mstVisitController;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    TrnBillBillSponsorRepository trnBillBillSponsorRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @Autowired
    BillSearchDao objBillSearchDao;

    @Autowired
    TbillRecieptRepository tbillRecieptRepository;

    @Autowired
    TbillBillServiceRepository tbillBillServiceRepository;

    @Autowired
    TinvPharmacySaleItemRepository tinvPharmacySaleItemRepository;
    @Autowired
    TbillRecieptController tbillRecieptController;

    @Autowired
    Emailsend emailsend;

    @Autowired
    Sendsms sendsms;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @RequestMapping("create")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        if (tBillBill.getBillAdmissionId() == null && tBillBill.getBillVisitId() == null) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Details are not available");
            return respMap;
        } else {
            tBillBill.setBillDate(new Date());
            tBillBill.setBillNumber(tbillBillRepository.makeBillNumber(tBillBill.getIpdBill()));
            if (tBillBill.getIpdBill()) {
                tBillBill.setBillIpdNumber(tbillBillRepository.makeOPDNumber(true));
                tBillBill.setBillNumber(tBillBill.getBillIpdNumber());
            } else {
                tBillBill.setBillOpdNumber(tbillBillRepository.makeOPDNumber(false));
                tBillBill.setBillNumber(tBillBill.getBillOpdNumber());
            }
            if (tBillBill.getBillVisitId() != null) {
                tBillBill.setBillVisitId(mstVisitRepository.getOne(tBillBill.getBillVisitId().getVisitId()));
            }
            if (tBillBill.getBillVisitId() != null && tBillBill.getBillVisitId().getVisitDepartmentId() != null) {
                tBillBill.setBillWorkOrderNumber(tbillBillRepository
                        .makeWorkOrderNumber(tBillBill.getBillVisitId().getVisitDepartmentId().getDepartmentName()));
            } else if (tBillBill.getBillAdmissionId() != null
                    && tBillBill.getBillAdmissionId().getAdmissionDepartmentId() != null) {
                tBillBill.setBillWorkOrderNumber(tbillBillRepository.makeWorkOrderNumber(
                        tBillBill.getBillAdmissionId().getAdmissionDepartmentId().getDepartmentName()));
            }
            tBillBill.setFreezed(false);
            TBillBill newTBillBill1 = tbillBillRepository.save(tBillBill);
            if (newTBillBill1.getBillVisitId() != null) {
                if (Propertyconfig.getSmsApi()) {
                    MstVisit obj = mstVisitRepository.getById(newTBillBill1.getBillVisitId().getVisitId());
                    String Mobileno = obj.getVisitPatientId().getPatientUserId().getUserMobile();
                    String message = "Bill No." + newTBillBill1.getBillNumber() + " Generated. Bill Amt: "
                            + newTBillBill1.getBillAmountPaid();
                    if (Mobileno != null) {
                        if (Mobileno.length() > 9) {
                            if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                            } else {
                                sendsms.sendmessage(Mobileno, message);
                                sendsms.sendmessage(Mobileno,
                                        "Visit Marked successfully. ID: " + newTBillBill1.getBillVisitId().getVisitId());
                            }
                        }
                    }

                }
            }
            if (newTBillBill1.getBillId() > 0) {
                int i = 0;
                respMap.put("bsBillId", String.valueOf(newTBillBill1.getBillId()));
                respMap.put("billNumber", tBillBill.getBillNumber());
                respMap.put("remark", tBillBill.getBillNarration());
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not saved");
                return respMap;
            }
        }
    }

    //Bill Modification For MBPT @Neha 09-03-2020 START
    @RequestMapping("modifyBill")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, String> modifyBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        if (tBillBill.getBillAdmissionId() == null && tBillBill.getBillVisitId() == null) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Details are not available");
            return respMap;
        } else {
            TBillBill newTBillBill1 = tbillBillRepository.save(tBillBill);
            if (newTBillBill1.getBillId() > 0) {
                int i = 0;
                respMap.put("bsBillId", String.valueOf(newTBillBill1.getBillId()));
                respMap.put("billNumber", tBillBill.getBillNumber());
                respMap.put("remark", tBillBill.getBillNarration());
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not saved");
                return respMap;
            }
        }
    }
    //Bill Modification For MBPT @Neha 09-03-2020 END

    @RequestMapping("reconciliation")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, String> reconciliation(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        if (tBillBill.getBillAdmissionId() == null) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Details are not available");
            return respMap;
        } else {
            TBillBill newTBillBill1 = tbillBillRepository.save(tBillBill);
            if (newTBillBill1.getBillId() > 0) {
                respMap.put("bsBillId", String.valueOf(newTBillBill1.getBillId()));
                respMap.put("billNumber", tBillBill.getBillNumber());
                respMap.put("remark", tBillBill.getBillNarration());
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not saved");
                return respMap;
            }
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TBillBill> records;
        records = tbillBillRepository.findByBillNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{billId}")
    public TBillBill read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository.findByBillIdAndIsActiveTrueAndIsDeletedFalse(billId);
    }

    @RequestMapping("update")
    public TBillBill update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository.save(tBillBill);
    }

    @GetMapping("listtoday")
    public Iterable<TBillBill> listToday(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository.findByBillDate(new Date());
    }

    @GetMapping
    @RequestMapping("listbymrnumber")
    public Iterable<TBillBill> listByMrNumberBillDate(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "mrNumber", required = false, defaultValue = "1") String mrNumber,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository.findByBillVisitIdVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalse(
                mrNumber, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping("list")
    public Iterable<TBillBill> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                    @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                    @RequestParam(value = "qString", required = false) String qString,
                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                    @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        Page<TBillBill> pages = null;
        if (qString == null || qString.equals("")) {
            pages = tbillBillRepository.findAllByIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            pages = tbillBillRepository
                    .findByBillNumberContainsOrBillVisitIdVisitPatientIdPatientMrNoContainsAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(
                            qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        return pages;
    }

    @GetMapping("listByUnitId")
    public Iterable<TBillBillDto> listByUnitId(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "billId") String col,
            @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        Page<TBillBillDto> pages = null;
        if (qString == null || qString.equals("") || qString.equals("opd")) {
            pages = tbillBillRepository
                    .findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId,
//                    .findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAndFreezed(unitId,
                            PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            System.out.println(pages);
            for (TBillBillDto tBillBill : pages.getContent()) {
                tBillBill.setBillCompanyPayable(
                        trnBillBillSponsorRepository.getTotalCompanyPayable(tBillBill.getBillId()));
                tBillBill.setBillCompanyPaid(trnBillBillSponsorRepository.getTotalCompanyPaid(tBillBill.getBillId()));
                tBillBill.setBillCompanyOutstanding(
                        trnBillBillSponsorRepository.getTotalCompanyOutstanding(tBillBill.getBillId()));
                List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
                if (tbillbilspon.size() > 0) {
                    tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
                } else {
                    tBillBill.setCompanyBillApproval(false);
                }
//                tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
            }
        } else if (qString.equals("ipd")) {
            pages = tbillBillRepository
//                    .findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId,
                    .findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAAndFreezedIPD(unitId,
                            PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            for (TBillBillDto tBillBill : pages.getContent()) {
                tBillBill.setBillCompanyPayable(
                        trnBillBillSponsorRepository.getTotalCompanyPayable(tBillBill.getBillId()));
                tBillBill.setBillCompanyPaid(trnBillBillSponsorRepository.getTotalCompanyPaid(tBillBill.getBillId()));
                tBillBill.setBillCompanyOutstanding(
                        trnBillBillSponsorRepository.getTotalCompanyOutstanding(tBillBill.getBillId()));
//                tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
                List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
                if (tbillbilspon.size() > 0) {
                    tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
                } else {
                    tBillBill.setCompanyBillApproval(false);
                }
            }
        } else if (qString.equals("emg")) {
            pages = tbillBillRepository
                    .findAllByTbillUnitIdUnitIdEMRAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId,
//                    .findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAndFreezed(unitId,
                            PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            for (TBillBillDto tBillBill : pages.getContent()) {
                tBillBill.setBillCompanyPayable(
                        trnBillBillSponsorRepository.getTotalCompanyPayable(tBillBill.getBillId()));
                tBillBill.setBillCompanyPaid(trnBillBillSponsorRepository.getTotalCompanyPaid(tBillBill.getBillId()));
                tBillBill.setBillCompanyOutstanding(
                        trnBillBillSponsorRepository.getTotalCompanyOutstanding(tBillBill.getBillId()));
//                tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
                List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
                if (tbillbilspon.size() > 0) {
                    tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
                } else {
                    tBillBill.setCompanyBillApproval(false);
                }
            }
        } else {
            pages = tbillBillRepository
                    .findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId,
                            //                     .findAllByTbillUnitIdUnitIdAndBillDateAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalseAndFreezed(unitId,
                            PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            for (TBillBillDto tBillBill : pages.getContent()) {
                tBillBill.setBillCompanyPayable(
                        trnBillBillSponsorRepository.getTotalCompanyPayable(tBillBill.getBillId()));
                tBillBill.setBillCompanyPaid(trnBillBillSponsorRepository.getTotalCompanyPaid(tBillBill.getBillId()));
                tBillBill.setBillCompanyOutstanding(
                        trnBillBillSponsorRepository.getTotalCompanyOutstanding(tBillBill.getBillId()));
//                tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
                List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
                if (tbillbilspon.size() > 0) {
                    tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
                } else {
                    tBillBill.setCompanyBillApproval(false);
                }
            }
        }
        return pages;
    }

    @GetMapping("billListDoctorShare")
    public Iterable<TBillBillDto> billListDoctorShare(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "billId") String col,
            @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        Page<TBillBillDto> pages = null;
        pages = tbillBillRepository
                .findAllByTbillUnitIdUnitIdIPDAndIsActiveTrueAndIsDeletedFalseAndIsCancelledFalse(unitId,
                        PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        for (TBillBillDto tBillBill : pages.getContent()) {
            tBillBill.setBillCompanyPayable(
                    trnBillBillSponsorRepository.getTotalCompanyPayable(tBillBill.getBillId()));
            tBillBill.setBillCompanyPaid(trnBillBillSponsorRepository.getTotalCompanyPaid(tBillBill.getBillId()));
            tBillBill.setBillCompanyOutstanding(
                    trnBillBillSponsorRepository.getTotalCompanyOutstanding(tBillBill.getBillId()));
//            tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
            List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
            if (tbillbilspon.size() > 0) {
                tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
            } else {
                tBillBill.setCompanyBillApproval(false);
            }
        }
        return pages;
    }

    @PutMapping("delete/{billId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        TBillBill tBillBill = tbillBillRepository.getById(billId);
        if (tBillBill != null) {
            tBillBill.setIsDeleted(true);
            tBillBill.setIsActive(false);
            tbillBillRepository.save(tBillBill);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping("/lastrecord")
    public List<TBillBill> getLastRecord(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qstring", defaultValue = "") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        if ((qString == null) || (qString.equalsIgnoreCase(""))) {
            return tbillBillRepository.findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();
        } else {
            return tbillBillRepository
                    .findByBillNumberContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndFinalBillFalseAndIpdBillTrue(
                            qString, qString, qString, qString);
        }
    }

    @GetMapping
    @RequestMapping("radiologyorderlist")
    public Iterable<TBillBill> radiologyorderlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "dept") String dept,
                                                  @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                  @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository
                .findAllByBillVisitIdVisitSubDepartmentIdSdDepartmentIdDepartmentNameEqualsAndIsActiveTrueAndIsDeletedFalse(
                        dept, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @PutMapping
    @RequestMapping("cancelbill/{billId}")
    public Map<String, String> cancellBill(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long billId) {
        TenantContext.setCurrentTenant(tenantName);
        int i = tbillBillRepository.updateBillForCancellation(billId, true);
        if (i > 0) {
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("updatebilloutstanding")
    public Map<String, String> updateBillOutstaindingMethod(@RequestHeader("X-tenantId") String tenantName, @RequestBody Map<String, String> map) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            if (!map.isEmpty()) {
                tbillBillRepository.updateBillOutstaindingMethod(Long.valueOf(map.get("billId")),
                        Double.valueOf(map.get("billOutstanding")), Double.valueOf(map.get("billAmountPaid")),
                        Double.valueOf(map.get("billAmountTobePaid")), map.get("billNarration"),
                        Boolean.valueOf(map.get("finalBill")));
                respMap.put("success", "1");
                respMap.put("msg", "Successful");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not Updated Due to insuffient Data");
            }
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Exception in API");
        } finally {
            return respMap;
        }
    }

    @PutMapping("updateipdbilloutstanding")
    public Map<String, String> updateipdBillOutstaindingMethod(@RequestHeader("X-tenantId") String tenantName, @RequestBody Map<String, String> map) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            if (!map.isEmpty()) {
                if (Double.valueOf(map.get("billDiscountPercentage")) > 0) {
                    tbillBillRepository.updateBillOutstaindingMethod(Long.valueOf(map.get("billId")),
                            Double.valueOf(map.get("billOutstanding")), Double.valueOf(map.get("billAmountPaid")),
                            Double.valueOf(map.get("billAmountTobePaid")), map.get("billNarration"),
                            Boolean.valueOf(map.get("finalBill")), Double.valueOf(map.get("billNetPayable")),
                            Double.valueOf(map.get("billSubTotal")), Double.valueOf(map.get("billDiscountPercentage")),
                            Double.valueOf(map.get("billDiscountAmount")),
                            Double.valueOf(map.get("commonDiscountStaffId")));
                    respMap.put("success", "1");
                    respMap.put("msg", "Successful");
                } else {
                    tbillBillRepository.updateBillOutstainding(Long.valueOf(map.get("billId")),
                            Double.valueOf(map.get("billOutstanding")), Double.valueOf(map.get("billAmountPaid")),
                            Double.valueOf(map.get("billAmountTobePaid")), Double.valueOf(map.get("billNetPayable")),
                            Double.valueOf(map.get("billSubTotal")), map.get("billNarration"),
                            Boolean.valueOf(map.get("finalBill")));
                    respMap.put("success", "1");
                    respMap.put("msg", "Successful");
                }

            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not Updated Due to insuffient Data");
            }
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Exception in API");
        } finally {
            return respMap;
        }
    }

    @PutMapping("updateipdbill")
    public Map<String, String> updateIpdBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        if (tBillBill.getBillId() > 0) {
            int i = tbillBillRepository.updateIpdBill(tBillBill.getBillId(), tBillBill.getBillNetPayable(),
                    tBillBill.getBillSubTotal(), tBillBill.getBillAmountPaid(), tBillBill.getBillOutstanding(),
                    tBillBill.getBillDiscountAmount(), tBillBill.getBillAmountTobePaid(), tBillBill.getBillUserId());
            if (i > 0) {
                respMap.put("success", "1");
                respMap.put("msg", "Successful");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Charges not added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Bill not get Loadded");
            return respMap;
        }
    }

    @GetMapping("listipdbillsearch")
    List<TBillBill> listIPDBillSearch(@RequestHeader("X-tenantId") String tenantName,
                                      @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        if (qString == null) {
            tBillBills = tbillBillRepository.findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();
        } else {
            tBillBills = tbillBillRepository
                    .findByBillNumberContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIpdBillTrueAndFinalBillFalseOrBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndFinalBillFalseAndIpdBillTrue(
                            qString, qString, qString, qString);
        }
        return tBillBills;
    }

    @GetMapping("listoflatestipdbill")
    public TBillBill listoflatestipdbill(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "id", defaultValue = "", required = false) long id) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(id);
        if (tBillBills.size() > 0)
            return tBillBills.get(0);
        else {
            TBillBill tBillBill = new TBillBill();
            tBillBill.setFinalBill(false);
            return tBillBill;
        }
    }

    @GetMapping("ipdbillbypatient")
    TBillBill iPDBillbyPatient(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientid", defaultValue = "", required = false) long qString) {
        TenantContext.setCurrentTenant(tenantName);
        TBillBill tBillBills;
        try {
            if (qString == 0) {
                tBillBills = tbillBillRepository.findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue()
                        .get(0);
            } else {
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
                                qString)
                        .get(0);
            }
            return tBillBills;
        } catch (Exception e) {
            tBillBills = new TBillBill();
            tBillBills.setBillId(0);
            return tBillBills;
        }
    }

    @GetMapping("ipdbilllistbypatient")
    List<TBillBill> iPDBilllistbyPatient(@RequestHeader("X-tenantId") String tenantName,
                                         @RequestParam(value = "patientid", defaultValue = "", required = false) long qString,
                                         @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        try {
            if (qString == 0) {
                tBillBills = tbillBillRepository.findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();
            } else {
//				tBillBills = tbillBillRepository
//						.findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
//								qString, unitid);
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDescNew(
                                qString, unitid);
            }
            return tBillBills;
        } catch (Exception e) {
            tBillBills = new ArrayList<>();
            return tBillBills;
        }
    }

    @GetMapping("cancelledipdbilllistbypatient")
    List<TBillBill> cancelledipdbilllistbypatient(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "patientid", defaultValue = "", required = false) long qString,
            @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        try {
            if (qString == 0) {
                tBillBills = tbillBillRepository.findByFinalBillFalseAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();
            } else {
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionPatientIdPatientIdEqualsAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
                                qString, unitid);
            }
            return tBillBills;
        } catch (Exception e) {
            tBillBills = new ArrayList<>();
            return tBillBills;
        }
    }

    @GetMapping("ipdbilllistbyadmission")
    List<TBillBill> iPDBilllistbyadmission(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "admissionid", defaultValue = "", required = false) long qString,
            @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.print("qString : " + qString);
        List<TBillBill> tBillBills;
        try {
            if (qString == 0) {
                tBillBills = tbillBillRepository.findByFinalBillFalseAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrue();
            } else {
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueOrderByBillIdDesc(
                                qString, unitid);
            }
            return tBillBills;
        } catch (Exception e) {
            // e.printStackTrace();
            tBillBills = new ArrayList<>();
            return tBillBills;
        }
    }

    @GetMapping("opdbillbypatient")
    List<TBillBill> oPDBillbyPatient(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "patientid", defaultValue = "", required = false) String qString,
            @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("qString : " + qString);
        System.out.println("unitid : " + unitid);
        System.out.println("qString : " + Long.parseLong(qString));
        List<TBillBill> tBillBills;
        try {
            if (qString == "") {
                tBillBills = tbillBillRepository.findByIsActiveTrueAndIsDeletedFalseAndIpdBillFalse();
            } else {
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionPatientIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillFalseOrderByBillIdDesc(
                                Long.parseLong(qString), unitid);
            }
            return tBillBills;
        } catch (Exception e) {
            e.printStackTrace();
            tBillBills = new ArrayList<TBillBill>();
            return tBillBills;
        }
    }

    @GetMapping("cancelledOpdBillBypatientRecord")
    List<TBillBill> cancelledOpdBillBypatientRecord(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "patientid", defaultValue = "", required = false) String qString,
            @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("qString : " + qString);
        System.out.println("unitid : " + unitid);
        System.out.println("qString : " + Long.parseLong(qString));
        List<TBillBill> tBillBills;
        try {
            if (qString == "") {
                tBillBills = tbillBillRepository.findByIsActiveTrueAndIsCancelledTrueAndIsDeletedFalseAndIpdBillFalse();
            } else {
                tBillBills = tbillBillRepository
                        .findByBillAdmissionIdAdmissionPatientIdAndIsCancelledTrueAndIsActiveTrueAndIsDeletedFalseAndIpdBillFalseOrderByBillIdDesc(
                                Long.parseLong(qString), unitid);
            }
            return tBillBills;
        } catch (Exception e) {
            e.printStackTrace();
            tBillBills = new ArrayList<TBillBill>();
            return tBillBills;
        }
    }

    @GetMapping("listipdbillsearchbymr")
    List<TBillBill> listIPDBillSearchMr(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientMrNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
                        qString);
        return tBillBills;
    }

    @GetMapping("listipdbillsearchbyname")
    List<TBillBill> listIPDBillSearchName(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserFirstnameContainsAndIpdBillTrueAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserLastnameContainsAndIpdBillTrueAndFinalBillFalseAndIsActiveTrueAndIsDeletedFalse(
                        qString, qString);
        return tBillBills;
    }

    @GetMapping("listipdbillsearchbymobile")
    List<TBillBill> listIPDBillSearchMobile(@RequestHeader("X-tenantId") String tenantName,
                                            @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserMobileContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
                        qString);
        return tBillBills;
    }

    @GetMapping("listipdbillsearchbyphone")
    List<TBillBill> listIPDBillSearchPhone(@RequestHeader("X-tenantId") String tenantName,
                                           @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserResidencePhoneContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
                        qString);
        return tBillBills;
    }

    @GetMapping("listipdbillsearchbyid")
    List<TBillBill> listIPDBillSearchId(@RequestHeader("X-tenantId") String tenantName,
                                        @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserPassportNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserDrivingNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalseOrBillAdmissionIdAdmissionPatientIdPatientUserIdUserPanNoContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
                        qString, qString, qString);
        return tBillBills;
    }

    @GetMapping("listipdbillsearchbymail")
    List<TBillBill> listIPDBillSearchmail(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "qstring", defaultValue = "", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> tBillBills;
        tBillBills = tbillBillRepository
                .findByBillAdmissionIdAdmissionPatientIdPatientUserIdUserEmailContainsAndFinalBillFalseAndIpdBillTrueAndIsActiveTrueAndIsDeletedFalse(
                        qString);
        return tBillBills;
    }

    // Priyanka : Author
    @GetMapping
    @RequestMapping("listbydate")
    public Iterable<TBillBill> listbydate(@RequestHeader("X-tenantId") String tenantName,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                          @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                          @RequestParam(value = "date", required = false) String date,
                                          @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                          @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (date == null || date.equals("")) {
            return tbillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillBillRepository.findAllByDateAAndIsActiveTrueAndIsDeletedFalse(date,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));

        }

    }

    /*
     * @RequestMapping(value="/billSearchByUnitId",method =
     * RequestMethod.POST,consumes = {"application/json" }) public @ResponseBody
     * List<TBillBill> billfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillSearch objBillSearch) throws
     * Exception{ // System.out.println("Query parameter:" + objBillSearch);
     * List<TBillBill>
     * objbilllist=objBillSearchDao.findBillDetails(objBillSearch);
     *//*
     * for (TBillBill tBillBill : objbilllist) {
     * if(tBillBill.getBillTariffId().getIsDeleted()) {
     * tBillBill.getBillTariffId().setTariffName("-"); } }
     *//*
     * return objbilllist;
     *
     * }
     */

    @RequestMapping(value = "/billSearchByUnitId", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody
    List<TBillBillDto> billfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillSearch objBillSearch) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("Query parameter:" + objBillSearch);
        List<TBillBillDto> objbilllist = objBillSearchDao.findBillDetails(objBillSearch);
        /*
         * for (TBillBill tBillBill : objbilllist) {
         * if(tBillBill.getBillTariffId().getIsDeleted()) {
         * tBillBill.getBillTariffId().setTariffName("-"); } }
         */
        for (TBillBillDto tBillBill : objbilllist) {
            List<TrnBillBillSponsor> tbillbilspon = trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId());
            if (tbillbilspon.size() > 0) {
                tBillBill.setCompanyBillApproval(tbillbilspon.get(0).getBbsSCId().getScCompanyId().getCompanyBillApproval());
            } else {
                tBillBill.setCompanyBillApproval(false);
            }
//            tBillBill.setCompanyBillApproval(trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()) != null ? trnBillBillSponsorRepository.findByBbsBillIdBillId(tBillBill.getBillId()).getBbsSCId().getScCompanyId().getCompanyBillApproval() : false);
        }
        return objbilllist;

    }

    // Author: NST
    @GetMapping
    @RequestMapping("listbyAdmissionId")
    public Iterable<TBillBill> listbyAdmissionId(@RequestHeader("X-tenantId") String tenantName,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                 @RequestParam(value = "qString", required = false) Long qString,
                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                 @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillBillRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillBillRepository.findByBillAdmissionIdAdmissionId(qString,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));

        }

    }

    // saagar Pawar code
    @GetMapping("check_if_bill_is_finalized/{admissionId}")
    public Map<String, String> check_if_bill_is_finalized(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> finalBill = tbillBillRepository
                .findAllByBillAdmissionIdAdmissionIdEqualsAndFinalBillTrue(admissionId);
        System.out.print(finalBill);
        if (finalBill.size() > 0) {
            respMap.put("success", "1");
            respMap.put("msg", "Patient Final Bill is Clear. Click below to Finalize Discharge.");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Final Bill is Due. Please ask Patient to clear Outstanding Dues.");
            return respMap;
        }
    }

    @GetMapping("finaltointermediate/{admissionId}")
    public boolean changefinalebilltointermediate(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> finalBill = tbillBillRepository
                .findAllByBillAdmissionIdAdmissionIdEqualsAndFinalBillTrue(admissionId);
        if (finalBill.size() > 0) {
            finalBill.get(0).setFinalBill(false);
            TBillBill obj = tbillBillRepository.save(finalBill.get(0));
            if (obj != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @GetMapping("finalbillchange/{admissionId}")
    public void changefinalbilldate(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> finalBill = tbillBillRepository
                .findAllByBillAdmissionIdAdmissionIdEqualsAndFinalBillTrue(admissionId);
        if (finalBill.size() > 0) {
            Date date = null;
            Date date1 = null;
            try {
                // date = new SimpleDateFormat("yyyy-MM-dd
                // HH:mm:ss").parse(finalBill.get(0).getBillAdmissionId().getAdmissionDischargeDate());
                date1 = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(finalBill.get(0).getBillAdmissionId().getAdmissionDischargeDate());
                finalBill.get(0).setBillDate(date1);
                finalBill.get(0)
                        .setBilldischargeDate(finalBill.get(0).getBillAdmissionId().getAdmissionDischargeDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tbillBillRepository.save(finalBill.get(0));
        }
    }

    @RequestMapping("createemergencybill")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, String> createEmergencyBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody TBillBill tBillBill) {
        TenantContext.setCurrentTenant(tenantName);
        if (tBillBill.getBillAdmissionId() == null && tBillBill.getBillVisitId() == null) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Details are not available");
            return respMap;
        } else {
            tBillBill.setIpdBill(false);
            tBillBill.setBillDate(new Date());
            tBillBill.setBillNumber(tbillBillRepository.makeEmrgencyNumber(2));
            tBillBill.setBillEmrNumber(tbillBillRepository.makeEmrgencyNumber(2));
            tBillBill.setBillVisitId(mstVisitRepository.getOne(tBillBill.getBillVisitId().getVisitId()));
            /*
             * if (tBillBill.getBillVisitId() != null &&
             * tBillBill.getBillVisitId().getVisitDepartmentId() != null) {
             * tBillBill.setBillWorkOrderNumber(tbillBillRepository.
             * makeWorkOrderNumber(tBillBill.getBillVisitId().
             * getVisitDepartmentId().getDepartmentName())); } else if
             * (tBillBill.getBillAdmissionId() != null &&
             * tBillBill.getBillAdmissionId().getAdmissionDepartmentId() !=
             * null) { tBillBill.setBillWorkOrderNumber(tbillBillRepository.
             * makeWorkOrderNumber(tBillBill.getBillAdmissionId().
             * getAdmissionDepartmentId().getDepartmentName())); }
             */
            TBillBill newTBillBill1 = tbillBillRepository.save(tBillBill);
            if (Propertyconfig.getSmsApi()) {
                MstVisit obj = mstVisitRepository.getById(newTBillBill1.getBillVisitId().getVisitId());
                String Mobileno = obj.getVisitPatientId().getPatientUserId().getUserMobile();
                String message = "Bill No." + newTBillBill1.getBillNumber() + " Generated. Bill Amt: "
                        + newTBillBill1.getBillAmountPaid();
                if (Mobileno.length() > 9) {
                    sendsms.sendmessage(Mobileno, message);
                    sendsms.sendmessage(Mobileno,
                            "Visit Marked successfully. ID: " + newTBillBill1.getBillVisitId().getVisitId());
                }

            }
            if (newTBillBill1.getBillId() > 0) {
                int i = 0;
                respMap.put("bsBillId", String.valueOf(newTBillBill1.getBillId()));
                respMap.put("billNumber", tBillBill.getBillNumber());
                respMap.put("remark", tBillBill.getBillNarration());
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill not saved");
                return respMap;
            }
        }
    }

    // check emergency patient bill final or not
    @RequestMapping("finalbillchack/{patientId}")
    public Map<String, String> finalbillchack(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            List<TBillBill> tBillBillList = tbillBillRepository
                    .findByBillVisitIdVisitPatientIdPatientIdAndBillVisitIdVisitRegistrationSourceAndFinalBillTrueAndIsActiveTrueAndIsDeletedFalseOrderByBillIdDesc(
                            patientId, 2);
            System.out.println("--------tBillBillList.size--------------" + tBillBillList.size());
            if (tBillBillList.size() > 0) {
                respMap.put("success", "1");
                respMap.put("msg", "Patient Final Bill is Clear.");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Patient Final Bill is Due. Please ask Patient to clear Outstanding Dues.");
                return respMap;
            }

        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Final Bill is Due. Please ask Patient to clear Outstanding Dues.");
            return respMap;
        }
    }

    // get emrgency bill list by visit id
    @GetMapping
    @RequestMapping("getEmergencyBillListByVisitId")
    public Iterable<TBillBill> getEmergencyBillListByVisitId(@RequestHeader("X-tenantId") String tenantName,
                                                             @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                                             @RequestParam(value = "qString", required = false) Long qString,
                                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                             @RequestParam(value = "col", required = false, defaultValue = "billId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillBillRepository.findByBillVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(qString,
                PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
    }

    public Map<String, Double> billCallist(@RequestHeader("X-tenantId") String tenantName, long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Double> respCalMap = new HashMap<String, Double>();
        double sum = 0;
        String query = "select sum(COALESCE(c.csGrossRate,0)) from IPDChargesService c where c.csChargeId.chargeAdmissionId.admissionId ="
                + admissionId + " and c.csBilled = false and c.csCancel = false";
        String query1 = "select sum(COALESCE(b.billNetPayable,0)) from TBillBill b where b.billAdmissionId.admissionId ="
                + admissionId + "";
        try {
            double list = (double) entityManager.createQuery(query1).getSingleResult();
            sum = list;
        } catch (Exception e) {
            sum = 0;
        }
        try {
            double list1 = (double) entityManager.createQuery(query).getSingleResult();
            sum = sum + list1;
        } catch (Exception e) {
            sum = sum + 0;
        }
        respCalMap.put("Expenses", sum);
        return respCalMap;
    }

    @RequestMapping("outstandingcheck/{admissionId}")
    public boolean checkoutstanding(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        double sum = 0;
        String query1 = "select sum(COALESCE(b.billOutstanding,0)) from TBillBill b where b.billAdmissionId.admissionId ="
                + admissionId + "";
        try {
            sum = (double) entityManager.createQuery(query1).getSingleResult();
            if (sum > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @RequestMapping("outstandingcheckEmg/{patientId}")
    public boolean checkoutstandingEmg(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        double sum = 0;
        String query1 = "select sum(COALESCE(b.billOutstanding,0)) from TBillBill b where b.billVisitId.visitPatientId.patientId ="
                + patientId + "";
        try {
            sum = (double) entityManager.createQuery(query1).getSingleResult();
            if (sum > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @RequestMapping("cancelbillandchangereceipt/{billId}")
    public void updatebillsandreceipt(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") long billId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            TBillBill obj = tbillBillRepository.getOne(billId);
            obj.setIsCancelled(true);
            tbillBillRepository.save(obj);
            List<TbillBillService> objlist = tbillBillServiceRepository
                    .findByBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(billId);
            for (TbillBillService service : objlist) {
                service.setBsCancel(true);
                tbillBillServiceRepository.save(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("changereceipt/{oldbillId}/{newbillId}")
    public void updatereceipt(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oldbillId") long oldbillId, @PathVariable("newbillId") long newbillId) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            List<TbillReciept> reclist = tbillRecieptRepository
                    .findByBrBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(oldbillId);
            if (reclist.size() > 0) {
                for (TbillReciept tbillReciept : reclist) {
                    tbillReciept.setBrBillId(tbillBillRepository.getOne(newbillId));
                    tbillRecieptRepository.save(tbillReciept);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("cancelledBillList")
    List<TBillBill> cancelledBillList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitid", required = false) long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("unitid : " + unitid);
        return tbillBillRepository.findByIsActiveTrueAndIsCancelledTrueAndIsDeletedFalse();
    }

    @RequestMapping("getcancelledBillList")
    public ResponseEntity getAllCancelledBillList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitid", required = false) long unitid,
                                                  @RequestBody TbillBillSearchDTO tbillBillSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query1 = " SELECT tb.bill_number AS BillNo,ifnull(tb.bill_date,'') AS BillDate,mp.patient_mr_no AS MRNO, " +
                " CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) AS PatientName, " +
                " tb.bill_sub_total AS Total, tb.bill_discount_amount AS Disc, tb.bill_net_payable AS Net, " +
                " tb.refund_amount AS RefundAmount, " +
                " ifnull(tb.cancelled_by,'') AS CancelledBy, " +
                " ifnull(tb.bill_cancel_reason,'') AS CancelledReason " +
                " FROM tbill_bill tb " +
                " LEFT JOIN trn_admission ta ON tb.bill_admission_id = ta.admission_id " +
                " INNER JOIN mst_patient mp ON ta.admission_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " WHERE tb.is_cancelled = 1 AND tb.is_active = 1 AND tb.is_deleted = 0 ";
        String query2 = " UNION ALL SELECT tb.bill_number AS BillNo,tb.bill_date AS BillDate,mp.patient_mr_no AS MRNO, " +
                " CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) AS PatientName, " +
                " tb.bill_sub_total AS Total, tb.bill_discount_amount AS Disc, tb.bill_net_payable AS Net, " +
                " tb.refund_amount AS RefundAmount, " +
                " tb.cancelled_by AS CancelledBy, " +
                " ifnull(tb.bill_cancel_reason,'') AS CancelledReason " +
                " FROM tbill_bill tb " +
                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id " +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id " +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id " +
                " WHERE tb.is_cancelled = 1 AND tb.is_active = 1 AND tb.is_deleted = 0 ";
        if (tbillBillSearchDTO.getFromdate() == null || tbillBillSearchDTO.getFromdate().equals("")) {
            tbillBillSearchDTO.setFromdate("1990-06-07");
        }
        if (tbillBillSearchDTO.getTodate() == null || tbillBillSearchDTO.getTodate().equals("")) {
            tbillBillSearchDTO.setTodate(strDate);
        }
        if (tbillBillSearchDTO.getFromdate() != null || tbillBillSearchDTO.getTodate() != null) {
            query1 += " and (date(tb.bill_date) between '" + tbillBillSearchDTO.getFromdate() + "' and '" + tbillBillSearchDTO.getTodate() + "')  ";
            query2 += " and (date(tb.bill_date) between '" + tbillBillSearchDTO.getFromdate() + "' and '" + tbillBillSearchDTO.getTodate() + "')  ";
        }
        if (tbillBillSearchDTO.getmRNO() != null && !tbillBillSearchDTO.getmRNO().equals("")) {
            query1 += " and mp.patient_mr_no LIKE '%" + tbillBillSearchDTO.getmRNO() + "%'";
            query2 += " and mp.patient_mr_no LIKE '%" + tbillBillSearchDTO.getmRNO() + "%'";

        }
        if (tbillBillSearchDTO.getPatientName() != null && !tbillBillSearchDTO.getPatientName().equals("")) {
            query1 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + tbillBillSearchDTO.getPatientName() + "%' ";
            query2 += " and CONCAT(mt.title_name,' ',mu.user_firstname,' ',mu.user_lastname) LIKE '%" + tbillBillSearchDTO.getPatientName() + "%' ";

        }
        if (tbillBillSearchDTO.getBillNo() != null && !tbillBillSearchDTO.getBillNo().equals("")) {
            query1 += " and tb.bill_number LIKE '%" + tbillBillSearchDTO.getBillNo() + "%' ";
            query2 += " and tb.bill_number LIKE '%" + tbillBillSearchDTO.getBillNo() + "%' ";

        }
        String MainQuery = query1 + query2;
        System.out.println("MainQuery:" + MainQuery);
        String CountQuery = " select count(*) from ( " + MainQuery + " ) as combine ";
        String columnName = "BillNo,BillDate,MRNO,PatientName,Total,Disc,Net,RefundAmount,CancelledBy,CancelledReason";
        MainQuery += " limit " + ((tbillBillSearchDTO.getOffset() - 1) * tbillBillSearchDTO.getLimit()) + "," + tbillBillSearchDTO.getLimit();
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        return ResponseEntity.ok(jsonArray.toString());
    }

    @RequestMapping("freezbillby/{billId}")
    public Map<String, String> freezbillbyBillid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("billId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        TBillBill tBillBill = tbillBillRepository.getById(ppoId);
        if (tBillBill != null) {
            if (tBillBill.getFreezed())
                tBillBill.setFreezed(false);
            else
                tBillBill.setFreezed(true);
            tbillBillRepository.save(tBillBill);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("patientCreditBillList/{patientId}/{unitId}")
    public Map<String, Object> patientCreditBillList(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "patientId") long patientId, @PathVariable(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> creditBillMap = new HashMap<String, Object>();
        String opdQuery = "SELECT *, 1 as type FROM tbill_bill tb    " +
                " left JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id   " +
                " left JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=0 AND tb.ipd_bill=0 AND mp.patient_id = " + patientId + " AND mv.visit_unit_id = " + unitId;
        String opdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,  " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance, " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,   " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 2 as type FROM tinv_pharmacy_sale i, " +
                " mst_visit v,mst_patient p,mst_gender g " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id    " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id    " +
                " and i.ps_out_standing_amount_for_patient>0  " +
                " and i.pharmacy_type=0   " +
                " AND p.patient_id= " + patientId + " and i.pharmacy_unit_id=" + unitId;
        String ipdQuery = " SELECT *, 2 as type FROM tbill_bill tb    " +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                " left JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=0 AND tb.ipd_bill=1 AND mp.patient_id= " + patientId + " AND ta.admission_unit_id =" + unitId;
        String ipdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,   " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance,  " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,  " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 4 as type FROM tinv_pharmacy_sale i,  " +
                " mst_visit v,mst_patient p,mst_gender g   " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id   " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id  " +
                " and i.ps_out_standing_amount_for_patient>0   " +
                " and i.pharmacy_type=1  " +
                " and p.patient_id=" + patientId + " and i.pharmacy_unit_id=" + unitId;
        List<TBillBill> opdList = entityManager.createNativeQuery(opdQuery, TBillBill.class).getResultList();
        List<Object[]> opdPhamList = entityManager.createNativeQuery(opdPhamQuery).getResultList();
        List<TinvPharmacySaleBillDTO> tinvPharmacySaleBillDTOOpdList = new ArrayList<TinvPharmacySaleBillDTO>();
        for (Object[] obj : opdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            tinvPharmacySaleBillDTOOpdList.add(tinvPharmacySaleBillDTO);
        }
        List<TBillBill> ipdList = entityManager.createNativeQuery(ipdQuery, TBillBill.class).getResultList();
        List<Object[]> ipdPhamList = entityManager.createNativeQuery(ipdPhamQuery).getResultList();
        List<TinvPharmacySaleBillDTO> tinvPharmacySaleBillDTOIpdList = new ArrayList<TinvPharmacySaleBillDTO>();
        ;
        for (Object[] obj : ipdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            tinvPharmacySaleBillDTOIpdList.add(tinvPharmacySaleBillDTO);
        }
        creditBillMap.put("opdList", opdList);
        creditBillMap.put("opdPhamList", tinvPharmacySaleBillDTOOpdList);
        creditBillMap.put("ipdList", ipdList);
        creditBillMap.put("ipdPhamList", tinvPharmacySaleBillDTOIpdList);
        return creditBillMap;
    }

    @GetMapping
    @RequestMapping("getOPDBillList/{billId}/{unitId}")
    public Map<String, Object> getOPDBillList(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "billId") long billId, @PathVariable(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        TBillBill tbillbill = tbillBillRepository.getById(billId);
        Map<String, Object> creditBillMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        String opdQuery = "SELECT *, 1 as type FROM tbill_bill tb    " +
                " left JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=1 AND tb.ipd_bill=0 AND mv.visit_id  = " + tbillbill.getBillVisitId().getVisitId() + " AND mv.visit_unit_id = " + unitId;
        String opdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,  " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance, " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,   " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 2 as type FROM tinv_pharmacy_sale i, " +
                " mst_visit v,mst_patient p,mst_gender g " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id    " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id    " +
                " and i.ps_out_standing_amount_for_patient = 0  " +
                " and i.pharmacy_type=0   " +
                " AND v.visit_id  = " + tbillbill.getBillVisitId().getVisitId() + " and i.pharmacy_unit_id=" + unitId;
       /* String ipdQuery = " SELECT *, 2 as type FROM tbill_bill tb    " +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                " left JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=1 AND tb.ipd_bill=1 AND mp.patient_id= " + patientId + " AND ta.admission_unit_id =" + unitId ;*/
        String ipdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,   " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance,  " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,  " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 4 as type FROM tinv_pharmacy_sale i,  " +
                " mst_visit v,mst_patient p,mst_gender g   " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id   " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id  " +
                " and i.ps_out_standing_amount_for_patient = 0   " +
                " and i.pharmacy_type=1  " +
                " and v.visit_id =" + tbillbill.getBillVisitId().getVisitId() + " and i.pharmacy_unit_id=" + unitId;
        ArrayList<Object> arr = new ArrayList<Object>();
        ArrayList<Object> arr1 = new ArrayList<Object>();
        List<TBillBill> opdList = entityManager.createNativeQuery(opdQuery, TBillBill.class).getResultList();
        List<Object[]> opdPhamList = entityManager.createNativeQuery(opdPhamQuery).getResultList();
        List<Object[]> ipdPhamList = entityManager.createNativeQuery(ipdPhamQuery).getResultList();
        for (TBillBill obj : opdList) {
            Map<String, Object> map = null;
            map = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });
            map.put("tbillBillServiceList", tbillBillServiceRepository
                    .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(obj.getBillId()));
            arr.add(map);
        }
        for (Object[] obj : opdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            Map<String, Object> map = null;
            map = mapper.convertValue(tinvPharmacySaleBillDTO, new TypeReference<Map<String, Object>>() {
            });
            map.put("drugList", tinvPharmacySaleItemRepository.findByPsiPsIdPsId(Long.parseLong(obj[0].toString())));
            arr1.add(map);
        }
        for (Object[] obj : ipdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            Map<String, Object> map = null;
            map = mapper.convertValue(tinvPharmacySaleBillDTO, new TypeReference<Map<String, Object>>() {
            });
            map.put("drugList", tinvPharmacySaleItemRepository.findByPsiPsIdPsId(Long.parseLong(obj[0].toString())));
            arr1.add(map);
        }
        getConsolidateReceiptListByvisitId(tenantName, tbillbill.getBillVisitId().getVisitId());
        creditBillMap.put("List", arr);
        creditBillMap.put("phamList", arr1);
        creditBillMap.put("receiptList", getConsolidateReceiptListByvisitId(tenantName, tbillbill.getBillVisitId().getVisitId()));
        creditBillMap.put("phamReceiptList", getPhamConsolidateReceiptListByvisitId(tenantName, tbillbill.getBillVisitId().getVisitId()));
//        creditBillMap.put("opdPhamList", tinvPharmacySaleBillDTOOpdList);
//        creditBillMap.put("ipdList", ipdList);
//        creditBillMap.put("ipdPhamList", tinvPharmacySaleBillDTOIpdList);
        return creditBillMap;
    }

    @GetMapping
    @RequestMapping("getIpdBillList/{billId}/{unitId}")
    public Map<String, Object> getIpdBillList(@RequestHeader("X-tenantId") String tenantName, @PathVariable(value = "billId") long billId, @PathVariable(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        TBillBill tbillbill = tbillBillRepository.getById(billId);
        Map<String, Object> creditBillMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        MstVisit lastVisit = mstVisitController.lastvisitbypatientid(tbillbill.getBillAdmissionId().getAdmissionPatientId().getPatientId());
        String opdQuery = "SELECT *, 1 as type FROM tbill_bill tb    " +
                " left JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=1 AND tb.ipd_bill=0 AND mv.visit_id  = " + lastVisit.getVisitId() + " AND mv.visit_unit_id = " + unitId;
        String opdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,  " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance, " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,   " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 2 as type FROM tinv_pharmacy_sale i, " +
                " mst_visit v,mst_patient p,mst_gender g " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id    " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id    " +
                " and i.ps_out_standing_amount_for_patient = 0  " +
                " and i.pharmacy_type=0   " +
                " AND v.visit_id  = " + lastVisit.getVisitId() + " and i.pharmacy_unit_id=" + unitId;
        String ipdQuery = " SELECT *, 2 as type FROM tbill_bill tb    " +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                " left JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                " WHERE tb.is_active=1 AND tb.is_deleted=0    " +
                " AND tb.is_cancelled=0 AND bill_nill=1 AND tb.ipd_bill=1 AND ta.admission_id= " + tbillbill.getBillAdmissionId().getAdmissionId() + " AND ta.admission_unit_id =" + unitId;
        String ipdPhamQuery = " SELECT i.ps_id,i.ps_total_amount AS TotalAmt,i.ps_concsssion_percentage AS ConcPer,  " +
                " i.ps_concsssion_amount AS ConcAmt,ifnull(i.ps_concession_reason,'') AS ConcReason,   " +
                " i.ps_net_amount AS NetAmt,i.ps_out_standing_amount_for_patient AS Balance,  " +
                " i.ps_tax_amount AS Tax,date(i.created_date) as created_date,i.pharmacy_unit_id AS Unit,  " +
                " ifnull(p.patient_id,0) as patient_id,  " +
                " ifnull(v.visit_tariff_id,0) as visit_tariff_id, 4 as type FROM tinv_pharmacy_sale i,  " +
                " mst_visit v,mst_patient p,mst_gender g   " +
                " where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id   " +
                " and i.ps_visit_id=v.visit_id AND v.visit_patient_id = p.patient_id  " +
                " and i.ps_out_standing_amount_for_patient = 0   " +
                " and i.pharmacy_type=1  " +
                " and v.visit_id =" + lastVisit.getVisitId() + " and i.pharmacy_unit_id=" + unitId;
        ArrayList<Object> arr = new ArrayList<Object>();
        ArrayList<Object> arr1 = new ArrayList<Object>();
        List<TBillBill> opdList = entityManager.createNativeQuery(opdQuery, TBillBill.class).getResultList();
        List<TBillBill> ipdList = entityManager.createNativeQuery(ipdQuery, TBillBill.class).getResultList();
        List<Object[]> opdPhamList = entityManager.createNativeQuery(opdPhamQuery).getResultList();
        List<Object[]> ipdPhamList = entityManager.createNativeQuery(ipdPhamQuery).getResultList();
        for (TBillBill obj : opdList) {
            Map<String, Object> map = null;
            map = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });
            map.put("tbillBillServiceList", tbillBillServiceRepository
                    .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(obj.getBillId()));
            arr.add(map);
        }
        for (TBillBill obj : ipdList) {
            Map<String, Object> map = null;
            map = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });
            map.put("tbillBillServiceList", tbillBillServiceRepository
                    .findByBsBillIdBillIdEqualsAndBsCancelFalseAndIsActiveTrueAndIsDeletedFalse(obj.getBillId()));
            arr.add(map);
        }
        for (Object[] obj : opdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            Map<String, Object> map = null;
            map = mapper.convertValue(tinvPharmacySaleBillDTO, new TypeReference<Map<String, Object>>() {
            });
            map.put("drugList", tinvPharmacySaleItemRepository.findByPsiPsIdPsId(Long.parseLong(obj[0].toString())));
            arr1.add(map);
        }
        for (Object[] obj : ipdPhamList) {
            TinvPharmacySaleBillDTO tinvPharmacySaleBillDTO = new TinvPharmacySaleBillDTO(Long.parseLong(obj[0].toString()), Double.parseDouble(obj[1].toString()), Double.parseDouble(obj[2].toString()), Double.parseDouble(obj[3].toString()), obj[4].toString(), obj[5].toString(), Double.parseDouble(obj[6].toString()), Double.parseDouble(obj[7].toString()), obj[8].toString(), Long.parseLong(obj[9].toString()), Long.parseLong(obj[10].toString()), Long.parseLong(obj[11].toString()), Integer.parseInt(obj[12].toString()));
            Map<String, Object> map = null;
            map = mapper.convertValue(tinvPharmacySaleBillDTO, new TypeReference<Map<String, Object>>() {
            });
            map.put("drugList", tinvPharmacySaleItemRepository.findByPsiPsIdPsId(Long.parseLong(obj[0].toString())));
            arr1.add(map);
        }
        creditBillMap.put("List", arr);
        creditBillMap.put("phamList", arr1);
        creditBillMap.put("receiptList", getIPDConsolidateReceiptListByvisitId(tenantName, tbillbill.getBillAdmissionId().getAdmissionId()));
        creditBillMap.put("phamReceiptList", getPhamConsolidateReceiptListByvisitId(tenantName, lastVisit.getVisitId()));
//        creditBillMap.put("opdPhamList", tinvPharmacySaleBillDTOOpdList);
//        creditBillMap.put("ipdList", ipdList);
//        creditBillMap.put("ipdPhamList", tinvPharmacySaleBillDTOIpdList);
        return creditBillMap;
    }

    public ArrayList<Object> getConsolidateReceiptListByvisitId(@RequestHeader("X-tenantId") String tenantName, Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        String receipt = "SELECT br.* FROM tbill_reciept br " +
                " LEFT join tbill_bill tb ON  tb.bill_id = br.br_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id " +
                " left JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id " +
                " WHERE br.is_active=1 AND br.is_deleted=0 " +
                " AND tb.bill_nill = 1 and mv.visit_id = " + visitId;
        String receipts = " SELECT br.* FROM tbill_reciept br WHERE br.br_id IN ( " +
                " SELECT brs.tbill_reciept_br_id FROM tbill_reciept_br_bill_ids brs " +
                " LEFT join tbill_bill tb ON  tb.bill_id = brs.br_bill_ids_bill_id " +
                " left JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id " +
                " WHERE tb.bill_nill = 1 and  mv.visit_id = " + visitId +
                " )";
        ArrayList<Object> arr = new ArrayList<Object>();
        List<TbillReciept> receiptlist = entityManager.createNativeQuery(receipt, TbillReciept.class).getResultList();
        List<TbillReciept> receiptsList = entityManager.createNativeQuery(receipts, TbillReciept.class).getResultList();
        for (TbillReciept obj : receiptlist) {
            arr.add(obj);
        }
        for (TbillReciept obj : receiptsList) {
            arr.add(obj);
        }
        return arr;
    }

    public ArrayList<Object> getIPDConsolidateReceiptListByvisitId(@RequestHeader("X-tenantId") String tenantName, Long admmisionId) {
        TenantContext.setCurrentTenant(tenantName);
        String receipt = "SELECT br.* FROM tbill_reciept br " +
                " LEFT join tbill_bill tb ON  tb.bill_id = br.br_bill_id " +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id " +
                " WHERE br.is_active=1 AND br.is_deleted=0 " +
                " AND tb.bill_nill= 1 and ta.admission_id = " + admmisionId;
        String receipts = " SELECT br.* FROM tbill_reciept br WHERE br.br_id IN ( " +
                " SELECT brs.tbill_reciept_br_id FROM tbill_reciept_br_bill_ids brs " +
                " LEFT join tbill_bill tb ON  tb.bill_id = brs.br_bill_ids_bill_id " +
                " left JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id " +
                " WHERE tb.bill_nill= 1 and ta.admission_id = " + admmisionId +
                " )";
        ArrayList<Object> arr = new ArrayList<Object>();
        List<TbillReciept> receiptlist = entityManager.createNativeQuery(receipt, TbillReciept.class).getResultList();
        List<TbillReciept> receiptsList = entityManager.createNativeQuery(receipts, TbillReciept.class).getResultList();
        for (TbillReciept obj : receiptlist) {
            arr.add(obj);
        }
        for (TbillReciept obj : receiptsList) {
            arr.add(obj);
        }
        return arr;
    }

    public ArrayList<Object> getPhamConsolidateReceiptListByvisitId(@RequestHeader("X-tenantId") String tenantName, Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        String receipt = "SELECT pbr.* FROM tinv_pharmacy_bill_recepit pbr, " +
                "tinv_pharmacy_sale i, " +
                "mst_visit v " +
                "WHERE i.ps_id = pbr.pbr_ps_id and i.ps_visit_id=v.visit_id  " +
                "AND v.visit_id = " + visitId;
        String receipts = " SELECT pbr.* FROM tinv_pharmacy_bill_recepit pbr WHERE pbr.pbr_id IN ( " +
                " SELECT pbrs.pharmacy_bill_recepit_pbr_id  FROM  tinv_pharmacy_bill_recepit_pbr_ps_ids  pbrs, " +
                " tinv_pharmacy_sale i, " +
                " mst_visit v " +
                " WHERE i.ps_id = pbrs.pbr_ps_ids_ps_id and i.ps_visit_id=v.visit_id  " +
                " AND v.visit_id = " + visitId +
                " ) ";
        ArrayList<Object> arr = new ArrayList<Object>();
        List<PharmacyBillRecepit> receiptlist = entityManager.createNativeQuery(receipt, PharmacyBillRecepit.class).getResultList();
        List<PharmacyBillRecepit> receiptsList = entityManager.createNativeQuery(receipts, PharmacyBillRecepit.class).getResultList();
        for (PharmacyBillRecepit obj : receiptlist) {
            arr.add(obj);
        }
        for (PharmacyBillRecepit obj : receiptsList) {
            arr.add(obj);
        }
        return arr;
    }

}