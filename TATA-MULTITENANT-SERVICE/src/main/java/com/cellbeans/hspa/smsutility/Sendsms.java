package com.cellbeans.hspa.smsutility;

import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class Sendsms {


    static Propertyconfig propertyconfig;

    public String sendmessage(String mobileno, String text) {
        final String url = "http://sms.acropetal.in/API/WebSMS/Http/v1.0a/index.php?username=acrptldemo&password=abc123&sender=DEMOAC&to=" + mobileno + "&message=" + text;
//        final String url =" http://sms.acropetal.in/API/WebSMS/Http/v1.0a/index.php?username=uphcnmc &password=uphcnmc13082018&sender=UPHCNM&to="+mobileno+"&message="+text+"&reqid=1&format={json|text}&route_id=278";
        // final String url = "http://sms.acropetal.in/API/WebSMS/Http/v1.0a/index.php?username=uphcnmc&password=uphcnmc13082018&sender=UPHCNM&to="+mobileno+"&message="+text;
        RestTemplate restTemplate = new RestTemplate();
        String result = "";
        try {
            result = restTemplate.getForObject(URLEncoder.encode(url, StandardCharsets.UTF_8.toString()), String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
//        try {
//            //Gson gson = new Gson();
//            // Construct data
//            String apiKey = "apikey=" + URLEncoder.encode("yourapiKey", "UTF-8");
//            String message = "&message=" + URLEncoder.encode("This is your message", "UTF-8");
//            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
//            String numbers = "&numbers=" + URLEncoder.encode("918123456789", "UTF-8");
//
//            // Send data
//
//
//            String data = "http://sms.acropetal.in/API/WebSMS/Http/v1.0a/index.php?username=acrptldemo&password=abc123&sender=DEMOAC&to="+mno+"&message="+text+"&reqid=1&format={json|text}&route_id=278";
//            URL url = new URL(data);
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//
//            // Get the response
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            String sResult="";
//            while ((line = rd.readLine()) != null) {
//                // Process line...
//                sResult=sResult+line+" ";
//            }
//            rd.close();
//
//            //sResult = gson.fromJson(sResult);
//            return sResult.toString();
//        } catch (Exception e) {
//            System.out.println("Error SMS "+e);
//            return "Error "+e;
//        }
        // for Post
//        try {
//
//            // Construct data
//            String apiKey = "apikey=" + "vieYzCc7LWQ-XLJCCNjncjqxSXcKJFOy8FImpyoazy";
//            String message = "&message=" + text;
//            String sender = "&sender=" + "TXTLCL";
//            String numbers = "&numbers=" + mno;
//
//            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//            String data = apiKey + numbers + message + sender;
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//            conn.getOutputStream().write(data.getBytes("UTF-8"));
//            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            final StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//            rd.close();
//
//            return stringBuffer.toString();
//        } catch (Exception e) {
//            System.out.println("Error SMS "+e);
//            return "Error "+e;
//        }
    }

    //Repo for SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) Start
    public String sendMessage(String mobileno, String text) {
        final String url = "http://sms.acropetal.in/API/WebSMS/Http/v1.0a/index.php?username=Cellbeans&password=abc123&sender=CHITPL&to="
                + mobileno + "&message=" + text + "&reqid=1&format={json|text}&route_id=278&msgtype=unicode";
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();

    }
    //Repo for SMS Code For IILDS in (Confirm Admission By RohanAnd Chetan 25.07.2019) End

    public String getToken() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://smartmessaging.etisalat.ae:5676/login/user";
            String requestJson = "{\"username\": \"coordinator\", \"password\": \"Ehealth@1122\"}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            String answer = restTemplate.postForObject(url, entity, String.class);
            JSONObject object = new JSONObject(answer);
            return object.getString("token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendSmsWithAuthentication(String mobileNo, String sms) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String token = getToken();
            String url = "https://smartmessaging.etisalat.ae:5676/campaigns/submissions/sms/nb";
            JSONObject input = new JSONObject();
            input.put("desc", "SMS From MULK");
            input.put("campaignName", "Non Bulk");
            input.put("msgCategory", "4.6");
            input.put("contentType", "3.2");
            input.put("senderAddr", "MULKEHEALTH");
            input.put("dndCategory", "Campaign");
            input.put("priority", "1");
            input.put("clientTxnId", "Campaign");
            input.put("recipient", mobileNo);
            input.put("msg", sms);
            System.out.println("paramter : " + input.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<String>(input.toString(), headers);
            String answer = restTemplate.postForObject(url, entity, String.class);
            JSONObject object = new JSONObject(answer);
            System.out.println("data : " + object.toString());
//            return object.getString("token");
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        }
    }

    public static String sendmessage1(String mobileno, String text) {
        String url = propertyconfig.getSmsURL().replace("cellbeansmobile", mobileno);
        url = url.replace("cellbeanstext", text.replace(" ", " "));
        System.out.println("SmsUrl " + url);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
        return result;
    }
}
