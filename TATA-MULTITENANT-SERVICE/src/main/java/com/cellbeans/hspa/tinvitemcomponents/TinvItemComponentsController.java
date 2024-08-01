package com.cellbeans.hspa.tinvitemcomponents;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_component")
public class TinvItemComponentsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemComponentsRepository tinvItemComponentsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemComponents> tinvItemComponents) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemComponents.size() != 0) {
            tinvItemComponentsRepository.saveAll(tinvItemComponents);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

}
