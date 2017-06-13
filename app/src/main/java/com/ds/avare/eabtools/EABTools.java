package com.ds.avare.eabtools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 6/12/17.
 */

public class EABTools {

    private List<GPSPosition> flightlog = new ArrayList<>();
    private TCPSocket socket;
    private long track_id = EABUtils.time_t();
    private long lastConnect = 0;

    private static class Singleton {
        private static final EABTools singleton=new EABTools();
        private Singleton(){}
        public static synchronized EABTools singleton() {
            return singleton;
        }
    }

    private EABTools() {}

    public static EABTools singleton() {
        return Singleton.singleton();
    }

    public static void stop() {
        if(singleton().socket!=null) {
            singleton().socket.disconnect();
            singleton().socket=null;
        }
    }

    public static void location(
            double latitude,
            double longitude,
            double altitude,
            double speed,
            double bearing,
            double declination,
            long   time
    ) {
        singleton().location(new GPSPosition(latitude,
                                             longitude,
                                             altitude,
                                             speed,
                                             bearing,
                                             declination,
                                             time));
    }

    public void location(GPSPosition pos) {
        pos.setId(track_id);
        synchronized(flightlog) {
            flightlog.add(pos);
        }
        EABLog.print("Position "+pos.get());
        send();
    }

    private void connect() {
        long now=EABUtils.time_t();
        if(now>lastConnect+15) {
            lastConnect=now;
            (new Thread(new Runnable() {
                public void run() {
                    TCPSocket socket = new TCPSocket("eabtools.com", 6280);
                    if (!socket.connect()) {
                        // no connection...
                        socket = null;
                    }
                    singleton().socket=socket;
                }
            })).start();
        }
    }

    public void send() {
        if(flightlog.size()>=1) {
            if(socket!=null) {
                (new Thread(new Runnable() {
                    public void run() {
                        while (flightlog.size() > 0) {
                            GPSPosition pos = null;
                            synchronized (flightlog) {
                                pos = flightlog.get(0);
                            }
                            if (pos != null) {
                                int n;
                                String string = "N6280H," + pos.get() + "\n";
                                if ((n = socket.write(string)) == string.length()) {
                                    synchronized (flightlog) {
                                        flightlog.remove(pos);
                                    }
                                } else {
                                    EABLog.print("Invalid write " + n + "/" + string.length());
                                    socket.disconnect();
                                    socket = null;
                                    break;
                                }
                            }
                        }
                    }
                })).start();
            }
            else {
                connect();
            }
        }
    }

}
