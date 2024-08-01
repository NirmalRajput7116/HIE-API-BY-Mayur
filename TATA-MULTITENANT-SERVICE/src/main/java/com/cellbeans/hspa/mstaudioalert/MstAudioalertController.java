package com.cellbeans.hspa.mstaudioalert;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst-audioalert")
public class MstAudioalertController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstAudioalertRepository mstAudioalertRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAudioalert mstAudioalert) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAudioalert.getAuAlertname() != null) {
            mstAudioalertRepository.save(mstAudioalert);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

   /* @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstBloodgroup> records;
        records = mstBloodgroupRepository.findByBloodgroupNameContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{auId}")
    public MstAudioalert read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("auId") Long auId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAudioalert mstAudioalert = mstAudioalertRepository.getById(auId);
        return mstAudioalert;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAudioalert mstAudioalert) {
        TenantContext.setCurrentTenant(tenantName);
        /*  return mstBloodgroupRepository.save(mstBloodgroup);*/
        if (mstAudioalert != null) {
            mstAudioalertRepository.save(mstAudioalert);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Faild to Update");
            return respMap;
        }
    }

    @GetMapping
    @CrossOrigin
    @RequestMapping("list")
    public Iterable<MstAudioalert> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "audioalertId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAudioalertRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAudioalertRepository.findAllByAuAlertnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{auId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("auId") Long auId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAudioalert mstAudioalert = mstAudioalertRepository.getById(auId);
        if (mstAudioalert != null) {
            mstAudioalert.setIsDeleted(true);
            mstAudioalert.setActive(false);
            mstAudioalertRepository.save(mstAudioalert);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}