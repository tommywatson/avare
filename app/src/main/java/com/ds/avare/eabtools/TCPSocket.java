package com.ds.avare.eabtools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by tommy on 6/12/17.
 */

public class TCPSocket {

    private Socket socket;
    private String hostname;
    private int port;

    public TCPSocket(String hostname,int port) {
        this.hostname=hostname;
        this.port=port;
    }

    public synchronized boolean connect() {
        boolean connected=false;

        if(socket==null) {
            try {
                socket=new Socket(hostname,port);
                socket.setTcpNoDelay(true);
                connected=true;
            }
            catch(Exception e) {
                EABLog.print("e: "+e+":"+e.getMessage()+" "+hostname+":"+port);
                socket=null;
            }
        }
        return connected;
    }

    public synchronized void disconnect() {
        if(socket!=null) {
            try {
                socket.close();
            }
            catch(Exception e) {
                EABLog.print("e: "+e.getMessage());
            }
            socket=null;
        }
    }

    public synchronized int write(byte[] data,int length) {
        int n=-1;

        if(socket!=null) {
            try {
                OutputStream os=socket.getOutputStream();
                os.write(data,0,length);
                n=length;
            }
            catch(Exception e) {
                EABLog.print("e: "+e+":"+e.getMessage());
                disconnect();
            }
        }
        else {
            EABLog.print("No socket");
        }
        return n;
    }

    int write(String string) {
        return string!=null?write(string.getBytes(),string.length()):-1;
    }

    public boolean connected() {
        return socket!=null;
    }

}
