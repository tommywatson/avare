package com.ds.avare.eabtools;

/**
 * Created by tommy on 6/12/17.
 */

public class GPSPosition {
    private static long pos_id_count = 0;
    private long id;
    private long pos_id = 0;
    private double speed;
    private double longitude;
    private double latitude;
    private double altitude;
    private double bearing;
    private double declination;
    private long time;

    public GPSPosition(
            double latitude,
            double longitude,
            double altitude,
            double speed,
            double bearing,
            double declination,
            long time
    ) {
        pos_id=++pos_id_count;
        this.speed=speed;
        this.longitude=longitude;
        this.latitude=latitude;
        this.altitude=altitude;
        this.bearing=bearing;
        this.declination=declination;
        this.time=time;
    }

    public void setId(long id) {
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public String get() {
        return id+","+pos_id+","+time+","+latitude+","+longitude+","+altitude+","+bearing+","+speed
                +","+declination;
    }

}

