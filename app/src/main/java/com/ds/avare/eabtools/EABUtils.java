package com.ds.avare.eabtools;

import java.util.Date;

/**
 * Created by tommy on 6/12/17.
 */

public class EABUtils {
    // sleep for a few ms
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException e) {
            System.out.println("Interrupted: " + e);
        }
    }

    // unix like time_t
    public static long time_t() {
        return((new Date()).getTime()/1000);
    }

    // current ms
    public static long time_ms() {
        return((new Date()).getTime());
    }

}
