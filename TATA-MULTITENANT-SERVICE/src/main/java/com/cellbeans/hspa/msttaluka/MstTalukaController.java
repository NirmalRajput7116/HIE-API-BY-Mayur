package com.cellbeans.hspa.msttaluka;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_taluka")
public class MstTalukaController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTalukaRepository mstTalukaRepository;
    @Autowired
    private MstTalukaService mstTalukaService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTaluka mstTaluka) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstTaluka.getTalukaName() != null) {
            mstTaluka.setTalukaName(mstTaluka.getTalukaName().trim());
            MstTaluka mTalukaObj = mstTalukaRepository.findByTalukaName(mstTaluka.getTalukaName(), mstTaluka.getTalukaDistrictId().getDistrictId());
            if (mTalukaObj == null) {
                mstTalukaRepository.save(mstTaluka);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstTaluka> records;
        records = mstTalukaRepository.findByTalukaNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{talukaId}")
    public MstTaluka read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("talukaId") Long talukaId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTaluka mstTaluka = mstTalukaRepository.getById(talukaId);
        return mstTaluka;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTaluka mstTaluka) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstTalukaRepository.save(mstTaluka);
        if (mstTaluka.getTalukaName() != null) {
            mstTaluka.setTalukaName(mstTaluka.getTalukaName().trim());
            MstTaluka mTalukaObj = mstTalukaRepository.findByTalukaName(mstTaluka.getTalukaName(), mstTaluka.getTalukaDistrictId().getDistrictId());
            if (mTalukaObj == null) {
                mstTalukaRepository.save(mstTaluka);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTaluka> list(@RequestHeader("X-tenantId") String tenantName,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                    @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                    @RequestParam(value = "qString", required = false) String qString,
                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                    @RequestParam(value = "col", required = false, defaultValue = "talukaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTalukaRepository.findAllByIsActiveTrueAndIsDeleteFalseOrderByTalukaNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstTalukaRepository.findByTalukaNameContainsAndIsActiveTrueAndIsDeleteFalseOrderByTalukaNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{talukaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("talukaId") Long talukaId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTaluka mstTaluka = mstTalukaRepository.getById(talukaId);
        if (mstTaluka != null) {
            mstTaluka.setIsDelete(true);
            mstTaluka.setIsActive(false);
            mstTalukaRepository.save(mstTaluka);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("bydistrctid/{distrctid}")
    public List<MstTaluka> statebyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("distrctid") Long distrctid) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTalukaRepository.findByTalukaDistrictIdDistrictIdEqualsAndIsActiveTrueAndIsDeleteFalse(distrctid);

    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName,
                            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstTalukaService.getMstTalukaForDropdown(page, size, globalFilter);
        return items;
    }

}
            
