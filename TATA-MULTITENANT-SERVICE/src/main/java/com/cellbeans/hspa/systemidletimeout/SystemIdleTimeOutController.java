package com.cellbeans.hspa.systemidletimeout;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
//import com.cellbeans.hspa.mstvisitbroughtby.MstVisitBroughtBy;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.cellbeans.hspa.mstvisitbroughtby.MstVisitBroughtBy;

@RestController
@RequestMapping("/system_idle_time_out")
public class SystemIdleTimeOutController {

    @Autowired
    SystemIdleTimeOutRepository systemIdleTimeOutRepository;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody SystemIdleTimeOut systemIdleTimeOut) {
        TenantContext.setCurrentTenant(tenantName);
        HashMap<String, Object> respMap = new HashMap<>();
        try {
            SystemIdleTimeOut systemIdleTimeOut1 = systemIdleTimeOutRepository.getById(1L);
            if (systemIdleTimeOut1 == null) {
                SystemIdleTimeOut systemIdleTimeOutNew = new SystemIdleTimeOut();
                systemIdleTimeOutNew.setIdleTO(systemIdleTimeOut.getIdleTO());
                systemIdleTimeOutNew.setRadioBA(systemIdleTimeOut.getRadioBA());
                systemIdleTimeOutRepository.save(systemIdleTimeOutNew);
                respMap.put("status", "success");
                respMap.put("obj", systemIdleTimeOutNew);
            } else {
                systemIdleTimeOut1.setIdleTO(systemIdleTimeOut.getIdleTO());
                systemIdleTimeOut1.setRadioBA(systemIdleTimeOut.getRadioBA());
                systemIdleTimeOutRepository.save(systemIdleTimeOut1);
                respMap.put("status", "success");
                respMap.put("obj", systemIdleTimeOut1);
            }
        } catch (Exception e) {
            respMap.put("status", "fail");
        }
        return respMap;
    }

    @RequestMapping("get/{id}")
    public SystemIdleTimeOut getSystemIdleTimeOut(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        System.out.print(tenantName);
        TenantContext.setCurrentTenant(tenantName);
//        SystemIdleTimeOut systemIdleTimeOut =
        return systemIdleTimeOutRepository.getById(id);
//        return systemIdleTimeOut;
    }
//    @PostMapping("setPassword/{userId}")
//    public HashMap<String , String> setPassword(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userId") Long userId) {
//
//        HashMap<String , String> response = new HashMap<>();
//        MstUser mstUser = mstUserRepository.findByUserId(userId);
//        mstUser.setPassword("PassWorD@123!");
//        mstUserRepository.save(mstUser);
//        response.put("status" , "success");
//
//        return response;
//    }
}
