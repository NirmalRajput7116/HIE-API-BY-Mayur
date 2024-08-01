package com.cellbeans.hspa.mipdblock;

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
@RequestMapping("/mipd_block")
public class MipdBlockController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdBlockRepository mipdBlockRepository;
    @Autowired
    private MipdBlockService mipdBlockService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBlock mipdBlock) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdBlock.getBlockName() != null) {
            mipdBlockRepository.save(mipdBlock);
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
        List<MipdBlock> records;
        records = mipdBlockRepository.findByBlockNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{blockId}")
    public MipdBlock read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("blockId") Long blockId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBlock mipdBlock = mipdBlockRepository.getById(blockId);
        return mipdBlock;
    }

    @RequestMapping("update")
    public MipdBlock update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBlock mipdBlock) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdBlockRepository.save(mipdBlock);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdBlock> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "blockId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdBlockRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdBlockRepository.findByBlockNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{blockId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("blockId") Long blockId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBlock mipdBlock = mipdBlockRepository.getById(blockId);
        if (mipdBlock != null) {
            mipdBlock.setIsDeleted(true);
            mipdBlockRepository.save(mipdBlock);
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
        List<Tuple> items = mipdBlockService.getMipdBlockForDropdown(page, size, globalFilter);
        return items;
    }

}
            
