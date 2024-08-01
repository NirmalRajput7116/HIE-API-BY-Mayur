package com.cellbeans.hspa.mstgeneratetoken;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_generate_token")
public class MstGenerateTokenController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstGenerateTokenRepository mstGenerateTokenRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGenerateToken mstGenerateToken) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstGenerateToken.getTokenNo() > 0) {
            mstGenerateTokenRepository.save(mstGenerateToken);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{tokenId}")
    public MstGenerateToken read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tokenId") Long tokenId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGenerateToken mstGenerateToken = mstGenerateTokenRepository.getById(tokenId);
        return mstGenerateToken;
    }

    @GetMapping
    @RequestMapping("callnexttoken")
    public MstGenerateToken callToken(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        MstGenerateToken mstGenerateToken = mstGenerateTokenRepository.getNextToken();
        System.out.println("mstGenerateToken" + mstGenerateToken);
        if (mstGenerateToken == null) {
            //   MstGenerateToken mstGenerateToken1= mstGenerateTokenRepository.getLastToken();
            // mstGenerateToken1.setTokenNo(0);
            MstGenerateToken mstGenerateToken1 = new MstGenerateToken();
            mstGenerateToken1.setTokenNo(0);
            return mstGenerateToken1;
        } else {
            return mstGenerateToken;
        }
    }

    @GetMapping
    @RequestMapping("generatetoken")
    public MstGenerateToken getToken(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        MstGenerateToken mstGenerateToken = mstGenerateTokenRepository.generateToken();
        if (mstGenerateToken == null) {
            MstGenerateToken mstGenerateToken1 = new MstGenerateToken();
            mstGenerateToken1.setTokenNo(0);
            return mstGenerateToken1;
        } else {
            return mstGenerateToken;
        }

    }

    @GetMapping
    @RequestMapping("displayTokens")
    public List<MstGenerateToken> displayTokens(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstGenerateTokenRepository.findAll();
    }

    @RequestMapping("update")
    public MstGenerateToken update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGenerateToken mstGenerateToken) {
        TenantContext.setCurrentTenant(tenantName);
        return mstGenerateTokenRepository.save(mstGenerateToken);
    }

    @GetMapping
    @RequestMapping("tokenlist")
    public Iterable<MstGenerateToken> Tokenlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "fromdate", required = false, defaultValue = "") String fromdate,
                                                @RequestParam(value = "todate", required = false, defaultValue = "") String todate,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                @RequestParam(value = "qString", required = false) String qString,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "token_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (qString != null && (qString.equals("") != true)) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = sd.format(new Date());
            return mstGenerateTokenRepository.findAllByDate(fromdate, todate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstGenerateTokenRepository.findAllByDate(fromdate, todate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

}
