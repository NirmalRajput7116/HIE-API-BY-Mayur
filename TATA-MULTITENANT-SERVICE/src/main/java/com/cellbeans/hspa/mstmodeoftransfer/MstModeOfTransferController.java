package com.cellbeans.hspa.mstmodeoftransfer;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mode_of_transfer")
public class MstModeOfTransferController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstModeOfTransferRepository mstModeOfTransferRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstModeOfTransfer mstModeOfTransfer) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstModeOfTransfer.getMotName() != null) {
            mstModeOfTransferRepository.save(mstModeOfTransfer);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{motId}")
    public MstModeOfTransfer read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("motId") Long motId) {
        TenantContext.setCurrentTenant(tenantName);
        MstModeOfTransfer mstModeOfTransfer = mstModeOfTransferRepository.getById(motId);
        return mstModeOfTransfer;
    }

    @RequestMapping("update")
    public MstModeOfTransfer update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstModeOfTransfer mstModeOfTransfer) {
        TenantContext.setCurrentTenant(tenantName);
        return mstModeOfTransferRepository.save(mstModeOfTransfer);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstModeOfTransfer> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "motId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstModeOfTransferRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstModeOfTransferRepository.findByMotNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{motId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("motId") Long motId) {
        TenantContext.setCurrentTenant(tenantName);
        MstModeOfTransfer mstModeOfTransfer = mstModeOfTransferRepository.getById(motId);
        if (mstModeOfTransfer != null) {
            mstModeOfTransfer.setIsDeleted(true);
            mstModeOfTransferRepository.save(mstModeOfTransfer);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

   /* @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        List<Tuple> items = mipdDischargeTypeService.getMipdDischargeTypeForDropdown(page, size, globalFilter);
        return items;
    }*/

}
