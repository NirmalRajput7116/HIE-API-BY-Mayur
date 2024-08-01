package com.cellbeans.hspa.mstCitizenIdProof;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mst_citizen_id_proof")
public class MstCitizenIdProofController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstCitizenIdProofRepository mstCitizenIdProofRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCitizenIdProof mstCitizenIdProof) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCitizenIdProof.getCipName() != null) {
            mstCitizenIdProofRepository.save(mstCitizenIdProof);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{cipId}")
    public MstCitizenIdProof read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cipId") Long cipId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCitizenIdProof mstCitizenIdProof = mstCitizenIdProofRepository.getById(cipId);
        return mstCitizenIdProof;
    }

    @RequestMapping("update")
    public MstCitizenIdProof update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCitizenIdProof mstCitizenIdProof) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCitizenIdProofRepository.save(mstCitizenIdProof);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCitizenIdProof> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cipId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCitizenIdProofRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCitizenIdProofRepository.findByCipNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{cipId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cipId") Long cipId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCitizenIdProof mstCitizenIdProof = mstCitizenIdProofRepository.getById(cipId);
        if (mstCitizenIdProof != null) {
            mstCitizenIdProof.setIsDeleted(true);
            mstCitizenIdProofRepository.save(mstCitizenIdProof);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
