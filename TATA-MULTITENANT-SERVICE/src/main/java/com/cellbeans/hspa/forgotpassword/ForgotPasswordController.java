package com.cellbeans.hspa.forgotpassword;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/change_password")
public class ForgotPasswordController {

    @Autowired
    MstUserRepository mstUserRepository;

    @PostMapping("change_password")
    public HashMap<String, String> change_password(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConfirmationBean confirmationBean) {
        TenantContext.setCurrentTenant(tenantName);
        HashMap<String, String> response = new HashMap<>();
        if (!confirmationBean.getConf_pass().equals(confirmationBean.getNew_pass())) {
            response.put("status", "fail");
            response.put("message", "confirm and new password is not same");

        } else {
            MstUser mstUser = mstUserRepository.findByUserNameEqualsAndPasswordEqualsAndIsActiveTrueAndIsDeletedFalse(
                    confirmationBean.getUsername(), confirmationBean.getOld_pass());
            if (mstUser == null) {
                response.put("status", "fail");
                response.put("message", "Old password is not match ...!");
            } else {
                int result = mstUserRepository.updatePassword(confirmationBean.getNew_pass(),
                        confirmationBean.getUsername());
                if (result == 0) {
                    response.put("status", "fail");
                    response.put("message", "Password updation fail ...!");
                } else {
                    response.put("status", "true");
                    response.put("message", "Passwod updated successfully ...!");
                }

            }

        }
        return response;

    }

}
