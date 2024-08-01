package com.cellbeans.hspa.mstroute;

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
@RequestMapping("/mst_route")
public class MstRouteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstRouteRepository mstRouteRepository;

    @Autowired
    private MstRouteService mstRouteService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRoute mstRoute) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstRoute.getRouteName() != null) {
            mstRoute.setRouteName(mstRoute.getRouteName().trim());
            MstRoute mstRouteObject = mstRouteRepository.findByRouteNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstRoute.getRouteName());
            if (mstRouteObject == null) {
                mstRouteRepository.save(mstRoute);
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
        List<MstRoute> records;
        records = mstRouteRepository.findByRouteNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{routeId}")
    public MstRoute read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("routeId") Long routeId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRoute mstRoute = mstRouteRepository.getById(routeId);
        return mstRoute;
    }

    @RequestMapping("update")
    public MstRoute update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRoute mstRoute) {
        TenantContext.setCurrentTenant(tenantName);
        return mstRouteRepository.save(mstRoute);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstRoute> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "routeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstRouteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstRouteRepository.findByRouteNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{routeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("routeId") Long routeId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRoute mstRoute = mstRouteRepository.getById(routeId);
        if (mstRoute != null) {
            mstRoute.setIsDeleted(true);
            mstRouteRepository.save(mstRoute);
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
        List<Tuple> items = mstRouteService.getMstRouteForDropdown(page, size, globalFilter);
        return items;
    }

}
            
