package com.cellbeans.hspa.EmgConsentForm;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstconsent.MstConsent;
import com.cellbeans.hspa.mstconsent.MstConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vijay Patil
 */

@RestController
@RequestMapping("/emg_consent_form")
public class EmgConsentFormController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstConsentRepository mstConsentRepository;

    @GetMapping
    @RequestMapping("emgconsentformlist")
    public Iterable<MstConsent> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                     @RequestParam(value = "size", required = false, defaultValue = "500") String size,
                                     @RequestParam(value = "qString", required = false) String qString,
                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                     @RequestParam(value = "col", required = false, defaultValue = "consentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstConsentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//            return mstConsentRepository.findAllByConsentDepartmentIdDepartmentNameAndIsActiveTrueAndIsDeletedFalse("Emergency",PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstConsentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

}
