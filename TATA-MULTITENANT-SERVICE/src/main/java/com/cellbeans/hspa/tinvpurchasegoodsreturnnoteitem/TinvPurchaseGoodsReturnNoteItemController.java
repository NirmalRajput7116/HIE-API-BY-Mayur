package com.cellbeans.hspa.tinvpurchasegoodsreturnnoteitem;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_goods_return_note_item")
public class TinvPurchaseGoodsReturnNoteItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchaseGoodsReturnNoteItemRepository tinvPurchaseGoodsReturnNoteItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPurchaseGoodsReturnNoteItem> tinvPurchaseGoodsReturnNoteItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseGoodsReturnNoteItem.size() != 0) {
            tinvPurchaseGoodsReturnNoteItemRepository.saveAll(tinvPurchaseGoodsReturnNoteItem);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
        
     /*   @RequestMapping("/autocomplete/{key}")
	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TinvPurchaseGoodsReturnNoteItem> records;
		records = tinvPurchaseGoodsReturnNoteItemRepository.findByPgrniItemIdContains(key);
		automap.put("record", records);
		return automap;
	}*/

    @RequestMapping("byid/{pgrniId}")
    public TinvPurchaseGoodsReturnNoteItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrniId") Long pgrniId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReturnNoteItem tinvPurchaseGoodsReturnNoteItem = tinvPurchaseGoodsReturnNoteItemRepository.getById(pgrniId);
        return tinvPurchaseGoodsReturnNoteItem;
    }

    @RequestMapping("update")
    public TinvPurchaseGoodsReturnNoteItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReturnNoteItem tinvPurchaseGoodsReturnNoteItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseGoodsReturnNoteItemRepository.save(tinvPurchaseGoodsReturnNoteItem);
    }
	
	/*@GetMapping
	@RequestMapping("list")
	public Iterable<TinvPurchaseGoodsReturnNoteItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "pgrniId") String col) {

		if (qString == null || qString.equals("")) {
			return tinvPurchaseGoodsReturnNoteItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(new PageRequest(Integer.parseInt(page) - 1,Integer.parseInt(size), Sort.Direction.fromStringOrNull(sort), col));

		} else {

			return tinvPurchaseGoodsReturnNoteItemRepository.findByPgrniNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, new PageRequest(Integer.parseInt(page) - 1,Integer.parseInt(size), Sort.Direction.fromStringOrNull(sort), col));

		}

	}*/

    @PutMapping("delete/{pgrniId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrniId") Long pgrniId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReturnNoteItem tinvPurchaseGoodsReturnNoteItem = tinvPurchaseGoodsReturnNoteItemRepository.getById(pgrniId);
        if (tinvPurchaseGoodsReturnNoteItem != null) {
            tinvPurchaseGoodsReturnNoteItem.setIsActive(false);
            tinvPurchaseGoodsReturnNoteItemRepository.save(tinvPurchaseGoodsReturnNoteItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getItem/{pgrnId}")
    public List<TinvPurchaseGoodsReturnNoteItem> getitemsbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseGoodsReturnNoteItemRepository.findAllByPgrniPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(pgrnId);
    }

}
            
