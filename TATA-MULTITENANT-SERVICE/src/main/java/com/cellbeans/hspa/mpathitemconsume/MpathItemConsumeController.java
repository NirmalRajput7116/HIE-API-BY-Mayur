package com.cellbeans.hspa.mpathitemconsume;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.querydsl.core.Tuple;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_item_consume")
public class MpathItemConsumeController {

    @Autowired
    MpathItemConsumeRepository mpathItemConsumeRepository;
    @Autowired
    CreateJSONObject createJSONObject;
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    private MpathItemConsumeService mpathItemConsumeService;

    @RequestMapping("create")
    public Boolean create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MpathItemConsume> item) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("add itemlist :" + item);
        if (item.get(0).getItemConsumeTestId() != null) {
            List<MpathItemConsume> newBillServiceList = mpathItemConsumeRepository.saveAll(item);
            List<Long> psIdList = new ArrayList<Long>();
            for (MpathItemConsume bs : newBillServiceList) {
                psIdList.add(bs.getItemConsumeId());
            }
            return true;
        }
        return true;

    }

    @RequestMapping("findobiidbytestid/{testId}")
    public List<MpathItemConsume> getListOfItemById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") long testId) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathItemConsumeRepository.findAllByItemConsumeTestIdTestIdAndIsActiveTrueAndIsDeletedFalse(testId);
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mpathItemConsumeService.getMpathItemConsumeForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping("getitemconsumebytestid/{testId}")
    public List<MpathItemConsume> getItemConsumeByTestid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("testId") long testId) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathItemConsumeRepository.findAllByItemConsumeTestIdTestIdAndIsActiveTrueAndIsDeletedFalse(testId);
    }

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathItemConsume itemConsume = mpathItemConsumeRepository.getById(icId);
        String msg = "";
        String success = "0";
        if (itemConsume != null) {
            itemConsume.setDeleted(true);
            mpathItemConsumeRepository.save(itemConsume);
            msg = "Record Deleted Successfully";
            success = "1";
        } else {
            msg = "Record Deletion Failed";
            success = "0";
        }
        respMap.put("msg", msg);
        respMap.put("success", success);
        return respMap;
    }

    @RequestMapping("reagentconsumption")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity reagentConsumption(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject json) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("labitemcount :" + json + "  :..0 :" + json.get("dateFrom"));
        String query = "SELECT it.item_id,it.item_name,uom.uom_name,idt.idt_name,ic.ic_name,count(it.item_id) as item_count,count(bs.ps_test_id) as testcount,sum(mic.item_consume_count) as consume_sum FROM mpath_item_consume mic " + "inner join tpath_bs bs on bs.ps_test_id=mic.item_consume_test_id " + "inner join inv_item it on it.item_id=mic.item_consume_item_id " + "inner join inv_item_category ic on ic.ic_id=it.item_ic_id " + "inner join inv_unit_of_measurment uom on uom.uom_id=mic.measure_unit " + "inner join inv_item_dispensing_type idt on it.item_stocking_uom_id=idt.idt_id " + "where ic.ic_name='Reagent'  and (date(bs.created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "') group by it.item_id ";
        String[] col = {"itemId", "itemName", "consumeUnit", "stockingUnit", "itemCategory", "itemCount", "testCount", "consumeSum"};
        return ResponseEntity.ok(createJSONObject.createJsonObj(col, query));
    }

    @RequestMapping("labitemcountbytestid")
    // @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity testCountByTestId(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject json) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("labitemcount :" + json + "  :..0 :" + json.get("dateFrom"));
        String query = "SELECT t.test_id,t.test_name,count(bs.ps_test_id) as testcount FROM tpath_bs bs inner join mpath_test t on t.test_id=bs.ps_test_id where t.test_id in (select distinct(item_consume_test_id) from mpath_item_consume) and (date(bs.created_date) between '" + json.get("dateFrom") + "' and '" + json.get("dateTo") + "')  group by bs.ps_test_id";
        String[] col = {"testId", "testName", "testCount"};
        return ResponseEntity.ok(createJSONObject.createJsonObj(col, query));
    }

}