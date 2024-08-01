package com.cellbeans.hspa.msttemplateprescription;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_template_prescription")
public class MstTemplatePrescriptionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTemplatePrescriptionRepository mstTemplatePrescriptionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstTemplatePrescription> mstTemplatePrescription) {
        TenantContext.setCurrentTenant(tenantName);
        mstTemplatePrescriptionRepository.saveAll(mstTemplatePrescription);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byid/{tpId}")
    public MstTemplatePrescription read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplatePrescription mstTemplatePrescription = mstTemplatePrescriptionRepository.getById(tpId);
        return mstTemplatePrescription;
    }

    @RequestMapping("update")
    public MstTemplatePrescription update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTemplatePrescription mstTemplatePrescription) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTemplatePrescriptionRepository.save(mstTemplatePrescription);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTemplatePrescription> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTemplatePrescriptionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstTemplatePrescriptionRepository.findAllByTpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplatePrescription mstTemplatePrescription = mstTemplatePrescriptionRepository.getById(tpId);
        if (mstTemplatePrescription != null) {
            mstTemplatePrescription.setDeleted(true);
            mstTemplatePrescriptionRepository.save(mstTemplatePrescription);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("delete_by_etid/{id}/{tpId}")
    public Map<String, String> delete_by_etid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id, @PathVariable("tpId") Long tpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (tpId == 0) {
            List<MstTemplatePrescription> mstTemplatePrescription = mstTemplatePrescriptionRepository.findAllByTpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(id);
            for (MstTemplatePrescription prescription : mstTemplatePrescription) {
                prescription.setDeleted(true);
            }
            mstTemplatePrescriptionRepository.saveAll(mstTemplatePrescription);
        } else {
            MstTemplatePrescription mstTemplatePrescription = mstTemplatePrescriptionRepository.getById(tpId);
            mstTemplatePrescription.setDeleted(true);
            mstTemplatePrescriptionRepository.save(mstTemplatePrescription);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}
            
