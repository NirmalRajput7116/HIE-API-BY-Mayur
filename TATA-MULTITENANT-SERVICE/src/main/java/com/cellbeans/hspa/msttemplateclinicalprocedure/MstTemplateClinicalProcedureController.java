package com.cellbeans.hspa.msttemplateclinicalprocedure;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_template_clinical_procedure")
public class MstTemplateClinicalProcedureController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTemplateClinicalProcedureRepository mstTemplateClinicalProcedureRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstTemplateClinicalProcedure> mstTemplateClinicalProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        mstTemplateClinicalProcedureRepository.saveAll(mstTemplateClinicalProcedure);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byid/{tcpId}")
    public MstTemplateClinicalProcedure read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcpId") Long tcpId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplateClinicalProcedure mstTemplateClinicalProcedure = mstTemplateClinicalProcedureRepository.getById(tcpId);
        return mstTemplateClinicalProcedure;
    }

    @RequestMapping("update")
    public MstTemplateClinicalProcedure update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTemplateClinicalProcedure mstTemplateClinicalProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTemplateClinicalProcedureRepository.save(mstTemplateClinicalProcedure);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTemplateClinicalProcedure> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tcpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTemplateClinicalProcedureRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstTemplateClinicalProcedureRepository.findAllByTcpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tcpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tcpId") Long tcpId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplateClinicalProcedure mstTemplateClinicalProcedure = mstTemplateClinicalProcedureRepository.getById(tcpId);
        if (mstTemplateClinicalProcedure != null) {
            mstTemplateClinicalProcedure.setDeleted(true);
            mstTemplateClinicalProcedureRepository.save(mstTemplateClinicalProcedure);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("delete_by_etid/{id}/{tcpId}")
    public Map<String, String> delete_by_etid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id, @PathVariable("tcpId") Long tcpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (tcpId == 0) {
            System.out.println();
            List<MstTemplateClinicalProcedure> mstTemplateClinicalProcedure = mstTemplateClinicalProcedureRepository.findAllByTcpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(id);
            for (MstTemplateClinicalProcedure procedure : mstTemplateClinicalProcedure) {
                procedure.setDeleted(true);
            }
            mstTemplateClinicalProcedureRepository.saveAll(mstTemplateClinicalProcedure);
        } else {
            MstTemplateClinicalProcedure mstTemplateClinicalProcedure = mstTemplateClinicalProcedureRepository.getById(tcpId);
            mstTemplateClinicalProcedure.setDeleted(true);
            mstTemplateClinicalProcedureRepository.save(mstTemplateClinicalProcedure);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}
            
