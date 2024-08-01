package com.cellbeans.hspa.applicationproperty;

import java.util.Calendar;

public class ErNumberPrefix {

    public String GenerateERNO(String prefix, boolean isDate) {
        if (isDate) {
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);
            String yearInString = String.valueOf(year);
            String monthInString = String.valueOf(month + 1);
            String generatedMRNO = prefix + "/" + monthInString + "/" + yearInString + "/";
            return generatedMRNO;
        } else {
            String generatedMRNO = prefix;
            return generatedMRNO;
        }

    }

}
