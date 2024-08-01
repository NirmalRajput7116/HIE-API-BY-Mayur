package com.cellbeans.hspa.mstlandmark;

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
@RequestMapping("/mst_landmark")
public class MstLandmarkController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstLandmarkRepository mstLandmarkRepository;

    @Autowired
    private MstLandmarkService mstLandmarkService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstLandmark mstLandmark) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstLandmark.getLandmarkName() != null) {
            mstLandmark.setLandmarkName(mstLandmark.getLandmarkName().trim());
            MstLandmark mLandObj = mstLandmarkRepository.findByLandMarkName(mstLandmark.getLandmarkName(), mstLandmark.getLandmarkCityId().getCityId());
            if (mLandObj == null) {
                mstLandmarkRepository.save(mstLandmark);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("search")
    public Iterable<MstLandmark> search(@RequestHeader("X-tenantId") String tenantName,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                        @RequestParam(value = "size", required = false, defaultValue = "500") String size,
                                        @RequestParam(value = "qString", required = false) String qString,
                                        @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                        @RequestParam(value = "col", required = false, defaultValue = "landmarkId") String col,
                                        @RequestParam(value = "colname", required = false) String colname,
                                        @RequestParam(value = "cityId", required = false) Long cityId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("-----col name-----------------------" + colname);
        if (colname == "landmarkName" || colname.equals("landmarkName")) {
            return mstLandmarkRepository.findByLandmarkNameContainsAndLandmarkCityIdCityIdAndIsActiveTrueAndIsDeletedFalse(qString, cityId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstLandmarkRepository.findByLandmarkNameContainsAndLandmarkCityIdCityIdAndIsActiveTrueAndIsDeletedFalse(qString, cityId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstLandmark> records;
        records = mstLandmarkRepository.findByLandmarkNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{landmarkId}")
    public MstLandmark read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("landmarkId") Long landmarkId) {
        TenantContext.setCurrentTenant(tenantName);
        MstLandmark mstLandmark = mstLandmarkRepository.getById(landmarkId);
        return mstLandmark;
    }

    @RequestMapping("landmarkbyID/{landmarkId}")
    public List<MstLandmark> landmarkbyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("landmarkId") Long landmarkId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstLandmark> records;
        records = mstLandmarkRepository.findByLandmarkCityIdCityIdEquals(landmarkId);
        return records;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstLandmark mstLandmark) {
        TenantContext.setCurrentTenant(tenantName);
//        return mstLandmarkRepository.save(mstLandmark);
        if (mstLandmark.getLandmarkName() != null) {
            mstLandmark.setLandmarkName(mstLandmark.getLandmarkName().trim());
            MstLandmark mLandObj = mstLandmarkRepository.findByLandMarkName(mstLandmark.getLandmarkName(), mstLandmark.getLandmarkCityId().getCityId());
            if (mLandObj == null) {
                mstLandmarkRepository.save(mstLandmark);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstLandmark> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "landmarkId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstLandmarkRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByLandmarkNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstLandmarkRepository.findByLandmarkNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByLandmarkNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{landmarkId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("landmarkId") Long landmarkId) {
        TenantContext.setCurrentTenant(tenantName);
        MstLandmark mstLandmark = mstLandmarkRepository.getById(landmarkId);
        if (mstLandmark != null) {
            mstLandmark.setIsDeleted(true);
            mstLandmarkRepository.save(mstLandmark);
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
        List<Tuple> items = mstLandmarkService.getMstLandmarkForDropdown(page, size, globalFilter);
        return items;
    }

}

