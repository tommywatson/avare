package com.ds.avare.eabtools;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tommy on 6/12/17.
 */

public class EABLog {

    private static Context context = null;
    private static File log;
    private static FileOutputStream stream;
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss.SSS");
    private static TimeZone tz = TimeZone.getTimeZone("UTC");

    public static void setContext(Context c) {
        context=c;
        if(context!=null) {
            restart();
        }
    }

    public static void restart() {
        if(stream!=null) {
            try {
                stream.close();
            }
            catch(Exception e) {
                print("[r] e: "+e);
            }
        }
        try {
            File path = context.getFilesDir();
            path = context.getExternalFilesDir(null);
            log = new File(path,"log."+(new Date()).getTime()%100);
            stream = new FileOutputStream(log);
            print("Log started: "+log.getAbsolutePath());
        }
        catch(Exception e) {
            print("[D] e: "+e);
            stream=null;
            log=null;
        }
    }

    /**
     * get the current date string
     * @param date to use
     * @return string representation
     */
    public static String timeString(Date date) {
        String out=null;

        if(date!=null) {
            out=sdfTime.format(date);
        }
        return out;
    }
    public static String timeString() {
        return timeString(Calendar.getInstance(tz).getTime());
    }

    public static void print(String string) {
        System.out.println("**EABTOOLS**: "+string);
        if(stream!=null) {
            try {
                string=timeString()+": "+string+"\n";
                stream.write(string.getBytes(),0,string.length());
                stream.flush();
            }
            catch(Exception e) {
                stream=null;
                log=null;
                print("[p] e: "+e);
            }
        }
    }

    public static void dump(String s) {
        String line=new String();
        try {
            StackTraceElement els[]=Thread.currentThread().getStackTrace();
            for(StackTraceElement e:els) {
                line+=s+":TRACE: "+e.getClassName()+":"+e.getMethodName()
                        +"("+e.getFileName()+":"+e.getLineNumber()+")\n";
            }

        }
        catch(Exception e) {
            line+="e: "+e.toString();
        }

        try {
            File path = context.getFilesDir();
            path = context.getExternalFilesDir(null);
            File file = new File(path,"dump."+(new Date()).getTime()%100);
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(line.getBytes(),0,line.length());
            stream.close();
        }
        catch(Exception e) {
            print("[D] e: "+e);
        }

        try {
            stream.close();
            stream = null;
            log = null;
        }
        catch(Exception e) {
            System.out.println("[e] e: "+e);
        }
    }

}
