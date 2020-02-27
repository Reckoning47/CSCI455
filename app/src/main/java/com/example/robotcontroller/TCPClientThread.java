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
            Message res = new Message();
            res.obj = client.sendMessage(toSend);
            client.handler.sendMessage(res);
        }
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String host;
    private int port;

    public Handler handler;


    TCPClientThread(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        createConnection();
        Looper.prepare();
        this.handler = new TCPHandler(this);
        Looper.loop();
    }

    public void createConnection() {
        try {
            this.socket = new Socket(this.host, this.port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        this.out.println(msg);
        String response;
        try {
            response = this.in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            response = null;
        }
        return response;
    }
}
