package com.cellbeans.hspa.mstgpdepartment;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_gp_department")
public class MstGpDepartmentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstGpDepartmentRepository mstGpDepartmentRepository;
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    private MstGpDepartmentService mstGpDepartmentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpDepartment mstDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("hi vj :" + mstDepartment);
        MstGpDepartment obj = new MstGpDepartment();
        if (mstDepartment.getGpDepartmentName() != null) {
            obj.setGpDepartmentName(mstDepartment.getGpDepartmentName());
            obj.setCreatedDate(new Date());
            mstGpDepartmentRepository.save(obj);
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
        List<MstGpDepartment> records;
        records = mstGpDepartmentRepository.findByGpDepartmentNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{departmentId}")
    public MstGpDepartment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpDepartment mstDepartment = mstGpDepartmentRepository.getById(departmentId);
        return mstDepartment;
    }

    @RequestMapping("update")
    public MstGpDepartment update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpDepartment mstDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        return mstGpDepartmentRepository.save(mstDepartment);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstGpDepartment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "gpDepartmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
        return mstGpDepartmentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

      /*  }
        else {

            return mstGpDepartmentRepository.findByDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentHeadContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }*/
    }

    @PutMapping("delete/{gpDepartmentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("gpDepartmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpDepartment mstDepartment = mstGpDepartmentRepository.getById(departmentId);
        if (mstDepartment != null) {
            mstDepartment.setIsDeleted(true);
            mstGpDepartmentRepository.save(mstDepartment);
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
        List<Tuple> items = mstGpDepartmentService.getMstGpDepartmentForDropdown(page, size, globalFilter);
        return items;
    }
}
            
