package org.oyvang.socketserver;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * Created by GeirMorten on 10.10.2014.
 */
public class Server extends Thread{
    private final static String TAG = "MyServerThread";
    private final static Integer PORT = 12345;
    private static TextView tv;
    private Activity activity;

    public Server(TextView tv,  Activity activity){
        this.tv=tv;
        this.activity=activity;
    }

    public void run() {
        ServerSocket ss = null;
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try{
            serverText("Starting server ...");
            ss = new ServerSocket(PORT);
            while(true) {
                serverText("Waiting for client ...");
                s = ss.accept();
                serverText("Client " + getIpAddress() + " is connected");
                out = new PrintWriter(s.getOutputStream(), false);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String[] param = in.readLine().split(":");
                Log.e(TAG, param[0] + "   " + param[1]);
                serverText("Client asks for: " + param[0] + " + " + param[1]);
                Double res = Double.parseDouble(param[0]) + Double.parseDouble(param[1]);
                serverText("Returning result = " + res);
                out.println(res);
                out.flush();
                out.close();
                in.close();
                s.close();
                serverText("Closed connection with " + getIpAddress() +" \n ");

            }
        } catch (IOException e) {
            Log.e(TAG,"ERROR: "+ e);
            e.printStackTrace();
        }finally{
            try{
                out.close();
                in.close();
                s.close();
                ss.close();
            }catch(Exception e){}
        }
    }

/*
Just a metod to update a TextView in main thread
 */
    public void serverText(final String text){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                tv.append("\n"+text);
                try {
                }catch (Exception e){};
            }
        });
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }

                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
}
