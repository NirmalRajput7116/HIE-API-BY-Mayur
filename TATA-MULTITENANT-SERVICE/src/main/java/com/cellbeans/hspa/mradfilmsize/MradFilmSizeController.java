package com.cellbeans.hspa.mradfilmsize;

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
@RequestMapping("/mrad_film_size")
public class MradFilmSizeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MradFilmSizeRepository mradFilmSizeRepository;

    @Autowired
    private MradFilmSizeService mradFilmSizeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradFilmSize mradFilmSize) {
        TenantContext.setCurrentTenant(tenantName);
        if (mradFilmSize.getFsName() != null) {
            mradFilmSizeRepository.save(mradFilmSize);
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
        List<MradFilmSize> records;
        records = mradFilmSizeRepository.findByFsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{fsId}")
    public MradFilmSize read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fsId") Long fsId) {
        TenantContext.setCurrentTenant(tenantName);
        MradFilmSize mradFilmSize = mradFilmSizeRepository.getById(fsId);
        return mradFilmSize;
    }

    @RequestMapping("update")
    public MradFilmSize update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradFilmSize mradFilmSize) {
        TenantContext.setCurrentTenant(tenantName);
        return mradFilmSizeRepository.save(mradFilmSize);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MradFilmSize> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "fsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mradFilmSizeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mradFilmSizeRepository.findByFsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{fsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fsId") Long fsId) {
        TenantContext.setCurrentTenant(tenantName);
        MradFilmSize mradFilmSize = mradFilmSizeRepository.getById(fsId);
        if (mradFilmSize != null) {
            mradFilmSize.setIsDeleted(true);
            mradFilmSizeRepository.save(mradFilmSize);
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
        List<Tuple> items = mradFilmSizeService.getMradFilmSizeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
