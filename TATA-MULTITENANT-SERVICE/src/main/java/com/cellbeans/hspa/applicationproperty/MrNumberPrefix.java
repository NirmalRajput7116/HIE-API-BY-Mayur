package com.cellbeans.hspa.applicationproperty;

import java.util.Calendar;

public class MrNumberPrefix {

    public String GenerateMRNO(String prefix, boolean isDate, String unitcode, String getUnitcode) {
        if (isDate) {
            String generatedMRNO = "";
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);
            String yearInString = String.valueOf(year);
            String monthInString = String.valueOf(month + 1);
            if (Boolean.parseBoolean(getUnitcode.trim())) {
                generatedMRNO = prefix + "/" + yearInString + "/"; //code Changes by rohan for disable karad EMR No
            } else {
                generatedMRNO = prefix + "/" + yearInString + "/" + unitcode + "/"; //code Changes by rohan for disable karad EMR No
            }
            // String generatedMRNO = prefix + "/" + yearInString + "/";
            return generatedMRNO;
        } else {
            String generatedMRNO = prefix;
            return generatedMRNO;
        }
        }

//    public String CBGenerateMRNO(String prefix, String formattedUnitId, boolean isDate, String unitcode, String getUnitcode) {
    public String CBGenerateMRNO(String prefix, boolean isDate, String unitcode, String getUnitcode) {

        if (isDate) {
            String generatedMRNO = "";
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);
            String yearInString = String.valueOf(year);
            String monthInString = String.valueOf(month + 1);


            if (Boolean.parseBoolean(getUnitcode.trim())) {
                generatedMRNO = prefix + "/" + yearInString + "/"; //code Changes by rohan for disable karad EMR No
//                generatedMRNO = prefix + "/" + formattedUnitId + "/" + yearInString + "/"; //code Changes by rohan for disable karad EMR No
            } else {
                generatedMRNO = prefix + "/" + yearInString + "/" + unitcode + "/"; //code Changes by rohan for disable karad EMR No
//                generatedMRNO = prefix + "/" + formattedUnitId + "/" + yearInString + "/" + unitcode + "/"; //code Changes by rohan for disable karad EMR No
            }
            // String generatedMRNO = prefix + "/" + yearInString + "/";
            return generatedMRNO;
        } else {
            String generatedMRNO = prefix;
            return generatedMRNO;
        }

    }

}
