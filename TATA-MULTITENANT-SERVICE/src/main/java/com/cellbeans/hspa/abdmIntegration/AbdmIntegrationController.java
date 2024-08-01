package com.cellbeans.hspa.abdmIntegration;

import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.securityhelper.AbhaObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

@RestController
@RequestMapping("/abdm/api/")
public class AbdmIntegrationController {

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Value("${fileWritePath}")
    private String fileWritePath;

    @Value("${clientIdLIVE}")
    private String clientIdLIVE;

    @Value("${clientSecretLIVE}")
    private String clientSecretLIVE;


    @Value("${getewayUrlLIVE}")
    private String getewayUrlLIVE;

    @Value("${apiUrlLIVE}")
    private String apiUrlLIVE;


    public String generateToken() {

        ResponseEntity<String> response = null;
        JSONObject myObject = new JSONObject();

        // sandbox setting

        RestTemplate restTemplate = new RestTemplate();
        String url = getewayUrlLIVE + "v0.5/sessions";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("clientId", clientIdLIVE);
        jsonObject.put("clientSecret", clientSecretLIVE);

        String accessToken = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            myObject = new JSONObject(response.getBody());
            accessToken = myObject.getString("accessToken");
            System.out.println("accessToken"+accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    @RequestMapping("/searchByMobile")
    public HashMap<String, String> searchByMobile(@RequestBody AbhaObject object) {

        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String mobile = object.getMobile();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (mobile != null) {
            String url = apiUrlLIVE + "v1/search/searchByMobile";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/searchByHealthId")
    public HashMap<String, String> searchByHealthId(@RequestBody AbhaObject object) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String healthId = object.getHealthId();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (healthId != null) {
            String url = apiUrlLIVE + "v1/search/searchByHealthId";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("healthId", healthId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/registrationViaMobile/generateOtp/{mobile}")
    public HashMap<String, String> registrationViaMobileGenerateOtp(@PathVariable String mobile) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (mobile != null) {
            String url = apiUrlLIVE + "v2/registration/mobile/login/generateOtp";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/registrationViaMobile/resendOtp/{txnId}")
    public HashMap<String, String> registrationViaMobileResendOtp(@PathVariable String txnId) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (txnId != null) {
            String url = apiUrlLIVE + "v2/registration/mobile/login/resendOtp";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txnId", txnId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/registrationViaMobile/verifyOtp/{txnId}/{otp}")
    public HashMap<String, String> registrationViaMobileVerifyOtp(@PathVariable String txnId, @PathVariable String otp) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (txnId != null) {
            String url = apiUrlLIVE + "v2/registration/mobile/login/verifyOtp";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txnId", txnId);
            jsonObject.put("otp", otp);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/registrationViaMobile/userAuthorizedToken")
    public HashMap<String, String> registrationViaMobileUserAuthorizedToken(@RequestBody AbhaObject object) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        String xToken = object.getToken();
        String healthId = object.getHealthId();
        String txnId = object.getTxnId();
        Boolean flag = false;
        if (healthId != null) {
            String url = apiUrlLIVE + "v2/registration/mobile/login/userAuthorizedToken";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("healthId", healthId);
            jsonObject.put("txnId", txnId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
                headers.add("X-Token", "Bearer " + xToken);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }


    @RequestMapping("/sendAadharOtp/{aadharNumber}")
    public HashMap<String, String> sendAadharOtp(@PathVariable String aadharNumber) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String accessToken = this.generateToken();
        if (accessToken != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = null;

            if (accessToken != null) {
                String url = apiUrlLIVE + "v1/registration/aadhaar/generateOtp";
                String token = accessToken;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("aadhaar", aadharNumber);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                if (token != null) {
                    headers.add("Authorization", "Bearer " + token);
                }

                HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

                try {
                    response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                    // System.out.println("Response : " + response);
                    myObject = new JSONObject(response.getBody());
                    resMap.put("txnId", myObject.getString("txnId"));
                    resMap.put("status", "true");
                    resMap.put("response", myObject.toString());
                } catch (HttpStatusCodeException e) {
                    e.printStackTrace();
                    resMap.put("statusCode", e.getStatusCode().toString());
                    resMap.put("getStatusText", e.getStatusText());
                    resMap.put("getMessage", e.getMessage());
                    resMap.put("status", "false");
                }
            }
        }
        return resMap;
    }

    @RequestMapping("/resendAadhaarOtp/{txnId}")
    public HashMap<String, String> resendAadhaarOtp(@PathVariable String txnId) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String accessToken = this.generateToken();
        if (accessToken != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = null;

            if (accessToken != null) {
                String url = apiUrlLIVE + "v1/registration/aadhaar/resendAadhaarOtp";
                String token = accessToken;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("txnId", txnId);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                if (token != null) {
                    headers.add("Authorization", "Bearer " + token);
                }

                HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

                try {
                    response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                    // System.out.println("Response : " + response);
                    myObject = new JSONObject(response.getBody());
                    resMap.put("txnId", myObject.getString("txnId"));
//                    resMap.put("mobileNumber", myObject.getString("mobileNumber"));
                    resMap.put("status", "true");
                    resMap.put("response", myObject.toString());
                } catch (HttpStatusCodeException e) {
                    e.printStackTrace();
                    resMap.put("statusCode", e.getStatusCode().toString());
                    resMap.put("getStatusText", e.getStatusText());
                    resMap.put("getMessage", e.getMessage());
                    resMap.put("status", "false");
                }
            }
        }
        return resMap;
    }

    @RequestMapping("/verifyAadharOtp/{txnId}/{otp}")
    public HashMap<String, String> verifyAadharOtp(@PathVariable String txnId, @PathVariable String otp) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (txnId != null) {
            String url = apiUrlLIVE + "v1/registration/aadhaar/verifyOTP";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("otp", otp);
            jsonObject.put("txnId", txnId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                // System.out.println("MyObject : " + myObject);
                resMap.put("txnId", myObject.getString("txnId"));
                resMap.put("token", token);
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/sendMobileOtp/{txnId}/{mobile}")
    public HashMap<String, String> sendMobileOtp(@PathVariable String txnId, @PathVariable String mobile) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String accessToken = this.generateToken();
        if (accessToken != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = null;

            if (accessToken != null) {
                String url = apiUrlLIVE + "v1/registration/aadhaar/generateMobileOTP";
                String token = accessToken;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mobile", mobile);
                jsonObject.put("txnId", txnId);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                if (token != null) {
                    headers.add("Authorization", "Bearer " + token);
                }

                HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

                try {
                    response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                    // System.out.println("Response : " + response);
                    myObject = new JSONObject(response.getBody());
                    resMap.put("txnId", myObject.getString("txnId"));
                    resMap.put("status", "true");
                    resMap.put("response", myObject.toString());
                } catch (HttpStatusCodeException e) {
                    e.printStackTrace();
                    resMap.put("statusCode", e.getStatusCode().toString());
                    resMap.put("getStatusText", e.getStatusText());
                    resMap.put("getMessage", e.getMessage());
                    resMap.put("status", "false");
                }
            }
        }
        return resMap;
    }


    @RequestMapping("/verifyMobileOtp/{txnId}/{otp}")
    public HashMap<String, String> verifyMobileOtp(@PathVariable String txnId, @PathVariable String otp) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (txnId != null) {
            String url = apiUrlLIVE + "v1/registration/aadhaar/verifyMobileOTP";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("otp", otp);
            jsonObject.put("txnId", txnId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                // System.out.println("the saagar : " + myObject);
                resMap.put("txnId", myObject.getString("txnId"));
                resMap.put("token", token);
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/createHealthIdWithPreVerified")
    public HashMap<String, String> createHealthIdWithPreVerified(@RequestBody AbhaObject object) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;

        String txnId = object.getTxnId();
        String healthId = object.getHealthId();
        if (txnId != null) {
            String url = apiUrlLIVE + "v1/registration/aadhaar/createHealthIdWithPreVerified";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("password", "India@143");
            jsonObject.put("healthId", healthId);
            jsonObject.put("txnId", txnId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                myObject = new JSONObject(response.getBody());
                resMap.put("response", myObject.toString());
                resMap.put("abhaPassword", "India@143");
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }


    @RequestMapping("/authInitByHealthIdNumberAndAadharOtp/{healthIdNumber}")
    public HashMap<String, String> authInitByHealthIdNumberAndAadharOtp(@PathVariable String healthIdNumber) {

        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (healthIdNumber != null) {
            String url = apiUrlLIVE + "v1/auth/init";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("authMethod", "AADHAAR_OTP");
            jsonObject.put("healthid", healthIdNumber);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/authConfirmWithAadhaarOtp/{txnId}/{otp}")
    public HashMap<String, String> authConfirmWithAadhaarOtp(@PathVariable String txnId, @PathVariable String otp) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        ResponseEntity<String> response2 = null;
        Boolean flag = false;
        if (txnId != null) {
            String url = apiUrlLIVE + "v1/auth/confirmWithAadhaarOtp";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("otp", otp);
            jsonObject.put("txnId", txnId);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

    @RequestMapping("/accountProfileDetailsByXtoken")
    public HashMap<String, String> accountProfileDetailsByXtoken(@RequestBody AbhaObject object) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        // System.out.println(object.toString());
        String xToken = object.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (xToken != null) {
            String url = apiUrlLIVE + "v1/account/profile";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
                headers.add("X-Token", "Bearer " + xToken);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }

//    @RequestMapping("/getCard")
//    public Map<String, Object> getCard(@RequestBody AbhaObject object, HttpServletRequest request,
//                                       HttpServletResponse resp) throws IOException {
//         // System.out.println(object.toString());
//        String xToken = object.getToken();
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = null;
//        Boolean flag = false;
//        respObj = new HashMap<String, Object>();
//        if (xToken != null) {
//            String url = apiUrlLIVE + "v1/account/getCard";
//            String token = this.generateToken();
//
//            JSONObject jsonObject = new JSONObject();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            if (token != null) {
//                headers.add("Authorization", "Bearer " + token);
//                headers.add("X-Token", "Bearer " + xToken);
//            }
//
//            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
//
//            try {
//                response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//                 // System.out.println(response.getBody());
//
//                File file = new File(fileWritePath + "AbhaCard.pdf");
//                 // System.out.println("EXTERNAL_FILE_PATH" + fileWritePath + "Prescription-" + visitId + ".pdf");
//                file.createNewFile();
//                FileWriter myWriter = new FileWriter(file);
//                myWriter.write(response.getBody());
//                myWriter.close();
//
//                resp.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//                FileCopyUtils.copy(inputStream, resp.getOutputStream());
//
//
//            } catch (HttpStatusCodeException e) {
//                e.printStackTrace();
//                response = null;
//                respObj.put("statusCode", e.getStatusCode().toString());
//                respObj.put("getStatusText", e.getStatusText());
//                respObj.put("getMessage", e.getMessage());
//                respObj.put("getCause", e.getCause().toString());
//                respObj.put("getRootCause", e.getRootCause().toString());
//                respObj.put("response", response.toString());
//                respObj.put("status", "false");
//            }
//        }
//        return respObj;
//    }

    @RequestMapping("/authPassword")
    public HashMap<String, String> authPassword(@RequestBody AbhaObject object) {
        HashMap<String, String> resMap = new HashMap();
        JSONObject myObject = new JSONObject();

        String healthIdNumber = object.getHealthIdNumber();
        String password = object.getPassword();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Boolean flag = false;
        if (healthIdNumber != null && password != null) {
            String url = apiUrlLIVE + "v1/auth/authPassword";
            String token = this.generateToken();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("healthIdNumber", healthIdNumber);
            jsonObject.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (token != null) {
                headers.add("Authorization", "Bearer " + token);
            }

            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                // System.out.println("Response : " + response);
                myObject = new JSONObject(response.getBody());
                resMap.put("status", "true");
                resMap.put("response", myObject.toString());
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                resMap.put("statusCode", e.getStatusCode().toString());
                resMap.put("getStatusText", e.getStatusText());
                resMap.put("getMessage", e.getMessage());
                resMap.put("status", "false");
            }
        }
        return resMap;
    }




}