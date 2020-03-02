package com.example.robotcontroller;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class TCPClientThread extends Thread {

    private static class TCPHandler extends Handler {

        private final WeakReference<TCPClientThread> tcpClient;

        public TCPHandler(TCPClientThread client) {
            this.tcpClient = new WeakReference(client);
        }

        public void handleMessage(Message msg) {
            TCPClientThread client = this.tcpClient.get();
            String toSend = msg.getData().getString("TCP");
            String res = client.sendMessage(toSend);
            System.out.println(res);
        }
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String host;
    private int port;
    private static TCPClientThread instance;

    public Handler handler;


    TCPClientThread(String host, int port) {
        super();
        this.host = host;
        this.port = port;
        instance = this;
    }

    public static TCPClientThread getInstance() {
        return instance;
    }

    @Override
    public void run() {
        Looper.prepare();
        this.handler = new TCPHandler(this);
        Looper.loop();
    }

    public String sendMessage(String msg) {
        String response;
        try {
            this.socket = new Socket(this.host, this.port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out.println(msg);
            response = this.in.readLine();
            this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            response = null;
        }
        return response;
    }
}
