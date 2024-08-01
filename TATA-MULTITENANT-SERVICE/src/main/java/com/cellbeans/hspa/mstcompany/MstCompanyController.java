package com.cellbeans.hspa.mstcompany;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.trncompanytariff.TrnCompanyTariff;
import com.cellbeans.hspa.trncompanytariff.TrnCompanyTariffRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_company")
public class MstCompanyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCompanyRepository mstCompanyRepository;

    @Autowired
    private MstCompanyService mstCompanyService;

    @Autowired
    private TrnCompanyTariffRepository trnCompanyTariffRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCompany mstCompany) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCompany.getCompanyName() != null) {
            mstCompany = mstCompanyRepository.save(mstCompany);
            respMap.put("companyId", Long.toString(mstCompany.getCompanyId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstCompany> records;
        records = mstCompanyRepository.findByCompanyNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{companyId}")
    public MstCompany read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstCompany> records;
        MstCompany mstCompany = mstCompanyRepository.getById(companyId);
        return mstCompany;
    }

    @RequestMapping("ctbyid/{companyId}")
    public Map<String, Object> ctbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstCompany> records;
        records = mstCompanyRepository.findByCompanyCtIdCtIdEquals(companyId);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("ctbyidandtariifId/{companyId}/{tariffId}")
    public Map<String, Object> ctbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId, @PathVariable("tariffId") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstCompany> records = new ArrayList<>();
        List<TrnCompanyTariff> trnCompanyTariff = trnCompanyTariffRepository.findByCtTariffIdTariffIdAndCtCompanyIdCompanyCtIdCtId(tariffId, companyId);
        for (int i = 0; i < trnCompanyTariff.size(); i++) {
            records.add(trnCompanyTariff.get(i).getCtCompanyId());
        }
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("companybytariifId/{tariffId}")
    public Map<String, Object> ctbyidandtariffId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tariffId") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstCompany> records = new ArrayList<>();
        List<TrnCompanyTariff> trnCompanyTariff = trnCompanyTariffRepository.findByCtTariffIdTariffId(tariffId);
        for (int i = 0; i < trnCompanyTariff.size(); i++) {
            records.add(trnCompanyTariff.get(i).getCtCompanyId());
        }
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("update")
    public MstCompany update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCompany mstCompany) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCompanyRepository.save(mstCompany);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCompany> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASCE") String sort, @RequestParam(value = "col", required = false, defaultValue = "companyId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCompanyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCompanyRepository.findByCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{companyId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("companyId") Long companyId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCompany mstCompany = mstCompanyRepository.getById(companyId);
        if (mstCompany != null) {
            mstCompany.setIsDeleted(true);
            mstCompanyRepository.save(mstCompany);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstCompanyService.getMstCompanyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
