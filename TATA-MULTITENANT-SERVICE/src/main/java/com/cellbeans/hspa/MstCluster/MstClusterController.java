package com.cellbeans.hspa.MstCluster;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_cluster")
public class MstClusterController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstClusterRepository mstClusterRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCluster mstCluster) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCluster.getClusterName() != null) {
            mstCluster.setClusterName(mstCluster.getClusterName().trim());
            MstCluster mstClusterObject = mstClusterRepository.findByClusterNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstCluster.getClusterName());
            if (mstClusterObject == null) {
                mstClusterRepository.save(mstCluster);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
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
        List<MstCluster> records;
        records = mstClusterRepository.findByClusterNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{clusterId}")
    public MstCluster read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("clusterId") Long clusterId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCluster mstCluster = mstClusterRepository.getById(clusterId);
        return mstCluster;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCluster mstCluster) {
        TenantContext.setCurrentTenant(tenantName);
        /*  return mstClusterRepository.save(mstCluster);*/
        mstCluster.setClusterName(mstCluster.getClusterName().trim());
        MstCluster mstClusterObject = mstClusterRepository.findByClusterNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstCluster.getClusterName());
        if (mstClusterObject == null) {
            mstClusterRepository.save(mstCluster);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstClusterObject.getClusterName() == mstCluster.getClusterName()) {
            mstClusterRepository.save(mstCluster);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCluster> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "clusterId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstClusterRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstClusterRepository.findByClusterNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyunitid/{unitId}")
    public Iterable<MstCluster> getListRecordByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") String unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstClusterRepository.findAllByIsActiveTrueAndIsDeletedFalse(unitId);
    }

    @PutMapping("delete/{clusterId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("clusterId") Long clusterId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCluster mstCluster = mstClusterRepository.getById(clusterId);
        if (mstCluster != null) {
            mstCluster.setDeleted(true);
            mstClusterRepository.save(mstCluster);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
