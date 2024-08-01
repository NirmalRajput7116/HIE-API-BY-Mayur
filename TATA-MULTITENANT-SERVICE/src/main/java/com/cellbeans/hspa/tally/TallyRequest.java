package com.cellbeans.hspa.tally;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TallyRequest {

    public void SendToTally(String xml_file, String tally_url, String companyName) {
        String Url = tally_url;
        String SOAPAction = "";
        String Voucher = xml_file;
        URL url;
        try {
            url = new URL(Url);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayInputStream bin = new ByteArrayInputStream(Voucher.getBytes());
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            copy(bin, bout);
            byte[] b = bout.toByteArray();
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        synchronized (in) {
            synchronized (out) {
                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
    // public static void main(String[] args) {
    //
    // String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ENVELOPE>"
    // + " <HEADER>"
    // + " <TALLYREQUEST>Import Data</TALLYREQUEST>" + " </HEADER>" + " <BODY>"
    // + " <IMPORTDATA>\n" + " <REQUESTDESC>" + "
    // <REPORTNAME>Vouchers</REPORTNAME>"
    // + " <STATICVARIABLES><SVCURRENTCOMPANY>Test4</SVCURRENTCOMPANY>"
    // + " </STATICVARIABLES>" + " </REQUESTDESC>" + " <REQUESTDATA>"
    // + " <TALLYMESSAGE xmlns:UDF=\"TallyUDF\">\n" + " <VOUCHER
    // REMOTEID=\"20190506\">\n"
    // + " <ISOPTIONAL>No</ISOPTIONAL>\n" + " <ISINVOICE>No</ISINVOICE>\n"
    // + " <VOUCHERTYPENAME>Receipt</VOUCHERTYPENAME>\n" + "
    // <DATE>20190506</DATE>\n"
    // + " <EFFECTIVEDATE>20190506</EFFECTIVEDATE>\n"
    // + " <ISCANCELLED>No</ISCANCELLED>\n" + "
    // <VOUCHERNUMBER>5</VOUCHERNUMBER>\n"
    // + " <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>\n" + "
    // <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Advanced</LEDGERNAME>\n" + " <AMOUNT>20190506" +
    // "</AMOUNT>\n"
    // + " </ALLLEDGERENTRIES.LIST>\n" + " <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Cheque</LEDGERNAME>\n" + " <AMOUNT>20190506" +
    // "</AMOUNT>\n"
    // + " </ALLLEDGERENTRIES.LIST>\n" + " <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Card</LEDGERNAME>\n" + " <AMOUNT>20190506" +
    // "</AMOUNT>\n"
    // + " </ALLLEDGERENTRIES.LIST>\n" + " <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Cash Amount</LEDGERNAME>\n" + " <AMOUNT>20190506"
    // + "</AMOUNT>\n" + " </ALLLEDGERENTRIES.LIST>\n" + "
    // <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Refund</LEDGERNAME>\n" + " <AMOUNT>-" +
    // "20190506</AMOUNT>\n"
    // + " </ALLLEDGERENTRIES.LIST>\n" + " <ALLLEDGERENTRIES.LIST>\n"
    // + " <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>\n"
    // + " <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n"
    // + " <LEDGERFROMITEM>No</LEDGERFROMITEM>\n"
    // + " <LEDGERNAME>Cash</LEDGERNAME>\n" + " <AMOUNT>-20190506" +
    // "</AMOUNT>\n"
    // + " </ALLLEDGERENTRIES.LIST>\n" + " </VOUCHER>\n" + " </TALLYMESSAGE>\n"
    // + " </REQUESTDATA>\n" + " </IMPORTDATA>\n" + " </BODY>\n" +
    // "</ENVELOPE>";
    //
    // TallyRequest tr = new TallyRequest();
    // tr.SendToTally(xml);
    //
    // }

}
