package com.ds.avare.eabtools;

/**
 * Created by tommy on 6/19/17.
 */

public class EABDelta {
    private long start;

    public EABDelta() {
        start=EABUtils.time_ms();
    }

    public long delta() {
        long end=EABUtils.time_ms();
        return end-start;
    }

}
