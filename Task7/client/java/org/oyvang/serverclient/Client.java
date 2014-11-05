package org.oyvang.serverclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import android.util.Log;


public class Client extends Thread {
    private final static String TAG = "MyClient";
    private final static String IP = "192.168.0.104";
    private final static Integer PORT = 12345;

    private Double num1;
    private Double num2;


    public Client (Double num1, Double num2){
        this.num1 = num1;
        this.num2 = num2;
    }

    public void run() {
        Log.e("MyClient", "KLIENT KJØRER");
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            Log.v(TAG, "Optaining connection...");
            s = new Socket(IP, PORT);
            Log.v(TAG, "Connected to server: " + s.toString());
            out = new PrintWriter(s.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println(num1+":"+num2);
            out.flush();
            Log.e(TAG, "Sendt nummere");
            String res = in.readLine();
            Log.i(TAG,res);
            main.viewResult(num1,num2,res);
            out.println("PING to server from client");
        } catch (Exception e) {
            Log.e(TAG,""+e);
            e.printStackTrace();
        }finally{
            try{
                out.close();
                in.close();
                s.close();
            }catch(IOException e){}
        }
    }
}
