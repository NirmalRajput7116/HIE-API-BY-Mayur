package com.cellbeans.hspa.tnstinpatient;

import com.cellbeans.hspa.TenantContext;

import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Romil Badhe
 */
@RestController
@RequestMapping("/tnst_in_patient")
public class TnstInPatientController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstInPatientRepository tnstInPatientRepository;

    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;

    @Autowired
    TnstItemIssueRepository tnstItemIssueRepository;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link TnstInPatient#ipAdmissionId} size as non zero
     *
     * @param tnstInPatient : data from input form
     * @return HashMap : new entry in database with {@link TnstInPatient}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link TnstInPatient} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */
    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstInPatient tnstInPatient) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstInPatient.getIpAdmissionId() != null) {
            tnstInPatientRepository.save(tnstInPatient);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    /**
     * This Method is use get {@link TnstInPatient} Object from database
     *
     * @param issueId: id send by request
     * @return : Object Response of {@link TnstInPatient} if found or will return null
     */
    @RequestMapping("byid/{issueId}")
    public TnstInPatient read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("issueId") Long issueId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(issueId);
        return tnstInPatient;
    }

    @RequestMapping("byItemId/{issueId}")
    public List<TinvOpeningBalanceItem> readItem(@RequestHeader("X-tenantId") String tenantName, @PathVariable("issueId") Long issueId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> itemList = new ArrayList<TinvOpeningBalanceItem>();
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(issueId);
        for (int i = 0; i < tnstInPatient.getIpTnstIssueItem().size(); i++) {
            tnstInPatient.getIpTnstIssueItem().get(i).getIiItemId().setTakenQty(tnstInPatient.getIpTnstIssueItem().get(i).getIiItemTaken());
            itemList.add(tnstInPatient.getIpTnstIssueItem().get(i).getIiItemId());
        }
        return itemList;
    }

    /**
     * This method is use to update {@link TnstInPatient} object and save in database
     *
     * @param tnstInPatient : object of {@link TnstInPatient}
     * @return : newly created object of  {@link TnstInPatient}
     */
    @RequestMapping("update")
    public TnstInPatient update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstInPatient tnstInPatient) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstInPatientRepository.save(tnstInPatient);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link TnstInPatient}
     *
     * @param page        : input from request or default is 1
     * @param size        : input from request or default is 100
     * @param admissionId : input from request as {@link TnstInPatient#ipAdmissionId} AdmissionId
     * @param sort        : sorting order to {@link PageRequest}
     * @param col         : column to sort or default is {@link TnstInPatient}
     * @return {@link Pageable} : of {@link TnstInPatient} on the provided parameters
     */
    @GetMapping
    @RequestMapping("listByAdmissionId")
    public Iterable<TnstInPatient> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long admissionId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ipId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (admissionId == null || admissionId.equals("")) {
            return tnstInPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstInPatientRepository.findByIpAdmissionIdAdmissionIdAndIsDeletedFalse(admissionId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link TnstInPatient}
     * Get Only Confirmed Items Issued by {@link com.cellbeans.hspa.trnadmission.TrnAdmission#admissionId}
     *
     * @param page        : input from request or default is 1
     * @param size        : input from request or default is 100
     * @param admissionId : input from request as {@link TnstInPatient}
     * @param sort        : sorting order to {@link PageRequest}
     * @param col         : column to sort or default is {@link TnstInPatient}
     * @return {@link Pageable} : of {@link TnstInPatient} on the provided parameters
     */

    @GetMapping
    @RequestMapping("onlyConfirmedListById")
    public Iterable<TnstInPatient> listOnlyConfirmedItems(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long admissionId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ipId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (admissionId == null || admissionId.equals("")) {
            return tnstInPatientRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstInPatientRepository.findByIpAdmissionIdAdmissionIdAndIpStatusEqualsOrIpStatusEqualsOrIpStatusEqualsAndIsDeletedFalse(admissionId, 3, 4, 5, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * @param admissionId unique admission of patient
     * @return return list of items which are consumed for patient by nurse; help in IPD billing
     * @Author Mohit Shinde
     */
    @GetMapping("onlyconfirmeditemlistbyId/{admissionId}")
    public List<TnstItemIssue> getRespMap(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) throws NullPointerException {
        TenantContext.setCurrentTenant(tenantName);
        if (admissionId != null || admissionId != 0) {
            return tnstInPatientRepository.findIssueItemListByAdmissionId(admissionId);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * This Method is used to cancel Item Issued in NST
     *
     * @param issueId : {@link TnstInPatient#setDeleted(Boolean)} false
     * @return HashMap: respMap.put("msg" , "Operation Successful");
     * respMap.put("success" , "1"); on Success or
     * <p>
     * respMap.put("msg" , "Operation Failed");
     * respMap.put("success" , "0"); on fail
     */
    @PutMapping("issueCancel/{issueId}")
    public Map<String, String> issueCancel(@RequestHeader("X-tenantId") String tenantName, @PathVariable("issueId") Long issueId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(issueId);
        if (tnstInPatient != null) {
            tnstInPatient.setActive(false);
            tnstInPatient.setDeleted(false);
            tnstInPatientRepository.save(tnstInPatient);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("issueConfirm/{issueId}")
    public Map<String, String> issueApproved(@RequestHeader("X-tenantId") String tenantName, @PathVariable("issueId") Long issueId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(issueId);
        if (tnstInPatient != null) {
            tnstInPatient.setIpConfirm(true);
            tnstInPatientRepository.save(tnstInPatient);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("returnItemToStore")
    public Map<String, String> itemReturnToStore(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TnstItemIssue> tnstItemIssue) {
        TenantContext.setCurrentTenant(tenantName);
        Boolean flag = false;
        if (tnstItemIssue != null) {
            List<TnstItemIssue> tnstItemIssueNew = tnstItemIssueRepository.saveAll(tnstItemIssue);
            // flag = tinvOpeningBalanceItemRepository.updateStoreAndQuanatityOnReturn(tnstItemIssueNew , tinvOpeningBalanceItemRepository);
        }
        Long sendIssueId = tnstItemIssue.get(0).getIssueId();
        TnstInPatient patient = tnstInPatientRepository.getById(sendIssueId);
        patient.setIpReturn(true);
        patient.setIpStatus(4);
        tnstInPatientRepository.save(patient);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    @PutMapping("delete/{issueId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("issueId") Long issueId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(issueId);
        if (tnstInPatient != null) {
            tnstInPatient.setDeleted(true);
            tnstInPatientRepository.save(tnstInPatient);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("searchByNameAndStoreId")
    public List<TinvOpeningBalanceItem> listItemsForDrugAllergy(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData, @RequestParam(value = "storeId", required = false, defaultValue = " ") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findByObiItemNameContainsAndObiStoreIdStoreIdAndIsActiveTrueAndIsDeletedFalse(searchData, storeId);
    }

    //update_status by ipId
    @RequestMapping("update_status/{ipId}/{ipStatus}")
    public Map<String, String> update_status(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ipId") Long ipId, @PathVariable("ipStatus") int ipStatus) {
        TenantContext.setCurrentTenant(tenantName);
        TnstInPatient tnstInPatient = tnstInPatientRepository.getById(ipId);
        if (tnstInPatient != null) {
            tnstInPatient.setIpStatus(ipStatus);
            tnstInPatientRepository.save(tnstInPatient);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //get requersted item in nursing by seetanshu
    @RequestMapping("getNursingItemByPatientId")
    public List<TnstInPatient> getNursingItemByPatientId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId", required = false) long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select n from TnstInPatient n where n.ipAdmissionId.admissionPatientId.patientId=" + patientId + " and n.ipStatus=1 and n.isActive=1 and n.isDeleted=0 order by n.ipId desc";
        return entityManager.createQuery(query, TnstInPatient.class).getResultList();
    }

    //get requersted item in nursing inden requsets by seetanshu
    @RequestMapping("getAllNursingIndentRequsets")
    public Iterable<TnstInPatient> getAllNursingIndentRequsets(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ip_id") String col) {
//        String query = "select n from TnstInPatient n where n.ipStatus=1 and n.isActive=1 and n.isDeleted=0 order by n.ipId desc";
//        return entityManager.createQuery(query, TnstInPatient.class).getResultList();
        TenantContext.setCurrentTenant(tenantName);
        return tnstInPatientRepository.findAllNursingIndentRequsets(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));           // pagination by neha
    }

    @RequestMapping("searchNursingList")
    public List<TnstInPatient> getAllNursingIndentListRequsets(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstlnPatientDto tnstlnPatientDto) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("searchFromDate : " + tnstlnPatientDto.getSearchFromDate());
        System.out.println("searchToDate : " + tnstlnPatientDto.getSearchToDate());
        System.out.println("Mr NO : " + tnstlnPatientDto.getSearchMrNo());
        // System.out.println("Name : " + tnstlnPatientDto.getSearchPatientName());
        try {
            Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse("1998-12-31");
            Date toDate = new Date();
            try {
                if (!tnstlnPatientDto.getSearchFromDate().equals("null")) {
                    fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(tnstlnPatientDto.getSearchFromDate());
                    toDate = new SimpleDateFormat("yyyy-MM-dd").parse(tnstlnPatientDto.getSearchToDate());
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            if (tnstlnPatientDto.getSearchMrNo() == null)
                tnstlnPatientDto.setSearchMrNo("");
            if (tnstlnPatientDto.getPatientFirstName() == null)
                tnstlnPatientDto.setPatientFirstName("");
            if (tnstlnPatientDto.getPatientLastName() == null)
                tnstlnPatientDto.setPatientLastName("");
            if (tnstlnPatientDto.getSearchFromDate() == null) {
                String sDate1 = "1998-12-31";
                tnstlnPatientDto.setSearchFromDate(sDate1);
            }
            if (tnstlnPatientDto.getSearchToDate() == null) {
                String sDate1 = "1998-12-31";
                tnstlnPatientDto.setSearchToDate(sDate1);
            }
            String query = "select n from TnstInPatient n where n.ipStatus=1 and n.isActive=1 and n.isDeleted=0 and date(n.createdDate) between :searchFromDate and :searchToDate ";
            if (tnstlnPatientDto.getSearchFromDate().equals("") && tnstlnPatientDto.getSearchFromDate().equals("null")) {
            }
            if (tnstlnPatientDto.getPatientFirstName().isEmpty() || !tnstlnPatientDto.getPatientFirstName().equals("") || !tnstlnPatientDto.getPatientFirstName().equals("null") || tnstlnPatientDto.getPatientFirstName() != null) {
                query += " and n.ipAdmissionId.admissionPatientId.patientUserId.userFirstname like :patientFirstNmae ";
            }
            if (tnstlnPatientDto.getPatientLastName().isEmpty() || !tnstlnPatientDto.getPatientLastName().equals("") || !tnstlnPatientDto.getPatientLastName().equals("null") || tnstlnPatientDto.getPatientLastName() != null) {
                query += " and n.ipAdmissionId.admissionPatientId.patientUserId.userLastname like :patientLastName ";
            }
            if (!tnstlnPatientDto.getSearchMrNo().equals("") || !tnstlnPatientDto.getSearchMrNo().equals("null")) {
                query += " and n.ipAdmissionId.admissionPatientId.patientMrNo like :mrNo ";
            }
            query += " order by n.ipId desc ";
            System.out.println("Query: " + query);
            return entityManager.createQuery(query, TnstInPatient.class).setParameter("patientFirstNmae", "%" + tnstlnPatientDto.getPatientFirstName() + "%").
                    setParameter("patientLastName", "%" + tnstlnPatientDto.getPatientLastName() + "%").
                    setParameter("mrNo", "%" + tnstlnPatientDto.getSearchMrNo() + "%").
                    setParameter("searchFromDate", fromDate).
                    setParameter("searchToDate", toDate).
                    getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//
//
//    @RequestMapping ("returnItemToStore")
//    public Map<String, String> itemReturnToStore(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TnstItemIssue> tnstItemIssue) {
//        Boolean flag = false;
//        if (tnstItemIssue != null) {
//            Long sendIssueId = tnstItemIssue.get(0).getIssueId();
//            TnstInPatient patient = tnstInPatientRepository.getById(sendIssueId);
//            patient.setIpStatus(4);
//            tnstInPatientRepository.save(patient);
//
//            respMap.put("msg" , "Operation Successful");
//            respMap.put("success" , "1");
//        } else {
//            respMap.put("msg" , "Operation Successful");
//            respMap.put("success" , "1");
//        }
//        return respMap;
//    }
//

    @RequestMapping("bysiid/{id}")
    public List<TnstInPatient> getNursingItemPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select n from TnstInPatient n where n.ipAdmissionId.admissionPatientId.patientId=" + id + " and n.ipStatus=1 and n.isActive=1 and n.isDeleted=0 order by n.ipId desc";
        return entityManager.createQuery(query, TnstInPatient.class).getResultList();
    }

    @GetMapping
    @RequestMapping("getReturnByUnitId")
    public List<TnstInPatient> getReturnByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstInPatientRepository.findAllByIpAdmissionIdAdmissionUnitIdUnitIdAndIpStatusAndIsActiveTrueAndIsDeletedFalse(unitId, 4);
    }

}
            
