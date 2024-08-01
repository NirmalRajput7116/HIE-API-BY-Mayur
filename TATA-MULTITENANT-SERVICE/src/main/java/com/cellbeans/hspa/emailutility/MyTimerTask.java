/*
package com.cellbeans.hspa.emailutility;

//import com.cellbeans.hspa.DatabaseAutoBackup.MysqlAutobk;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MyTimerTask extends TimerTask {
    private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;

    //private final static int ONE_DAY = 1;
    private final static int TWO_AM = 13;
    private final static int ZERO_MINUTES = 4;

    private static Date getTomorrowMorning2AM() {
        Date date2am = new java.util.Date();
        date2am.setHours(TWO_AM);
        date2am.setMinutes(ZERO_MINUTES);
        return date2am;
    }

    //call this method from your servlet init method
    public static void startTask() {
        MyTimerTask task = new MyTimerTask();
        Timer timer = new Timer();
        timer.schedule(task, getTomorrowMorning2AM(), 1000 * 60);// for your case u need to give 1000*60*60*24  //1000*60 1 min
    }

    public static void main(String args[]) {

        // String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        //    System.out.println(date);

//
        //    startTask();
    }

    @Override
    public void run() {
     */
/*   MysqlAutobk dbOb = new MysqlAutobk();
             try {
                 System.out.println("Database Backup Activity Started...");
                 dbOb.getDatabaseBackup("hspafinal","root","");
            } catch (Exception e) {
                System.err.println("error in databse backup :" + e);
            }*//*

    }

}
*/
