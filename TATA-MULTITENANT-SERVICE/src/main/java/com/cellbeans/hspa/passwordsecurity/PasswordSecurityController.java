package com.cellbeans.hspa.passwordsecurity;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import com.cellbeans.hspa.mstvisitbroughtby.MstVisitBroughtBy;
//import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/password_security")
public class PasswordSecurityController {

    @Autowired
    PasswordSecurityRepository passwordSecurityRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody PasswordSecurity passwordSecurity) {
        TenantContext.setCurrentTenant(tenantName);
        HashMap<String, String> respMap = new HashMap<>();
        try {
            if (passwordSecurity.getForceReset()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    String oldDate = sdf.format(new Date());
                    c.setTime(sdf.parse(oldDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DAY_OF_MONTH, passwordSecurity.getDuration());
                String newDate = sdf.format(c.getTime());
                passwordSecurity.setPwdEndDate(sdf.parse(newDate));
                passwordSecurity.setResetDone(false);
            }
            passwordSecurityRepository.save(passwordSecurity);
            respMap.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("status", "fail");
        }
        return respMap;
    }

    @RequestMapping("get/{id}")
    public PasswordSecurity getPasswordSecurity(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return passwordSecurityRepository.getById(id);
    }

    @PostMapping("setPassword/{userId}")
    public HashMap<String, String> setPassword(@RequestHeader("X-tenantId") String tenantName, @PathVariable("userId") Long userId) {
        TenantContext.setCurrentTenant(tenantName);
        HashMap<String, String> response = new HashMap<>();
        MstUser mstUser = mstUserRepository.getById(userId);
        mstUser.setPassword("PassWorD@123!");
        mstUserRepository.save(mstUser);
        response.put("status", "success");
        return response;
    }
}
