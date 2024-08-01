package com.cellbeans.hspa.tnststillbirthcertificate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_still_birth_certificate")
public class TnstStillBirthCertificateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstStillBirthCertificateRepository tnstStillBirthCertificateRepository;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link TnstStillBirthCertificate#getSbcChildName()} size as not null
     *
     * @param tnstStillBirthCertificate : data from input form
     * @return HashMap : new entry in database with {@link TnstStillBirthCertificate}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link TnstStillBirthCertificate} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */
    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstStillBirthCertificate tnstStillBirthCertificate) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstStillBirthCertificate.getSbcChildName() != null) {
            tnstStillBirthCertificateRepository.save(tnstStillBirthCertificate);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    /*    @RequestMapping("/autocomplete/{key}")
    public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TnstStillBirthCertificate> records;
		records = tnstStillBirthCertificateRepository.findByContains(key);
		automap.put("record", records);
		return automap;
	}*/

    /**
     * This Method is use get {@link TnstStillBirthCertificate} Object from database
     *
     * @param sbcId: id send by request
     * @return : Object Response of {@link TnstStillBirthCertificate} if found or will return null
     */
    @RequestMapping("byid/{sbcId}")
    public TnstStillBirthCertificate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sbcId") Long sbcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstStillBirthCertificate tnstStillBirthCertificate = tnstStillBirthCertificateRepository.getById(sbcId);
        return tnstStillBirthCertificate;
    }

    /**
     * This method is use to update {@link TnstStillBirthCertificate} object and save in database
     *
     * @param tnstStillBirthCertificate : object of {@link TnstStillBirthCertificate}
     * @return : newly created object of  {@link TnstStillBirthCertificate}
     */
    @RequestMapping("update")
    public TnstStillBirthCertificate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstStillBirthCertificate tnstStillBirthCertificate) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstStillBirthCertificateRepository.save(tnstStillBirthCertificate);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link TnstStillBirthCertificate}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link TnstStillBirthCertificate}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link TnstStillBirthCertificate}
     * @return {@link Pageable} : of {@link TnstStillBirthCertificate} on the provided parameters
     */
    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstStillBirthCertificate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                    @RequestParam(value = "qString", required = false) Long qString,
                                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                    @RequestParam(value = "col", required = false, defaultValue = "sbcId") String col,
                                                    @RequestParam(value = "search", required = false) String search) {
        TenantContext.setCurrentTenant(tenantName);
        if (search == null || search.equals("")) {
            return tnstStillBirthCertificateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstStillBirthCertificateRepository.findAllBySbcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrSbcChildNameContainsAndSbcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(search, search, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link TnstStillBirthCertificate#setDeleted(Boolean)} to true, Sudo delete in database
     *
     * @param sbcId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */
    @PutMapping("delete/{sbcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sbcId") Long sbcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstStillBirthCertificate tnstStillBirthCertificate = tnstStillBirthCertificateRepository.getById(sbcId);
        if (tnstStillBirthCertificate != null) {
            tnstStillBirthCertificate.setDeleted(true);
            tnstStillBirthCertificateRepository.save(tnstStillBirthCertificate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}

