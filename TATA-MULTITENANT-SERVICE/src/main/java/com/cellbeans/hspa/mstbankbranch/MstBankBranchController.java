package com.cellbeans.hspa.mstbankbranch;

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
@RequestMapping("/mst_bank_branch")
public class MstBankBranchController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstBankBranchRepository mstBankBranchRepository;
    @Autowired
    private MstBankBranchService mstBankBranchService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBankBranch mstBankBranch) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstBankBranch.getBbName() != null) {
            mstBankBranchRepository.save(mstBankBranch);
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
        List<MstBankBranch> records;
        records = mstBankBranchRepository.findByBbNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bbId}")
    public MstBankBranch read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBankBranch mstBankBranch = mstBankBranchRepository.getById(bbId);
        return mstBankBranch;
    }

    @RequestMapping("update")
    public MstBankBranch update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBankBranch mstBankBranch) {
        TenantContext.setCurrentTenant(tenantName);
        return mstBankBranchRepository.save(mstBankBranch);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstBankBranch> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bbId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstBankBranchRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstBankBranchRepository.findByBbNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bbId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBankBranch mstBankBranch = mstBankBranchRepository.getById(bbId);
        if (mstBankBranch != null) {
            mstBankBranch.setIsDeleted(true);
            mstBankBranchRepository.save(mstBankBranch);
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
        List<Tuple> items = mstBankBranchService.getMstBankBranchForDropdown(page, size, globalFilter);
        return items;
    }

}
            
