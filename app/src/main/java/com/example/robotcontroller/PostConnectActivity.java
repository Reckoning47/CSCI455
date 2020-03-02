package com.example.robotcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostConnectActivity extends AppCompatActivity {

    private TextToSpeechThread tts;
    private TCPClientThread tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_connect);

        tts = new TextToSpeechThread(this);
        tts.start();

        tcp = TCPClientThread.getInstance();

        final Button speakButton = findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("speak button clicked");
                EditText input = findViewById(R.id.speakInput);
                String speech = input.getText().toString();
                Message msg = tts.handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("TTS", speech);
                msg.setData(b);
                tts.handler.sendMessage(msg);
            }
        });

        final Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Stop Button Clicked");
                sendStop();
            }
        });

        final Button resetButton = findViewById(R.id.resetButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Stop Button Clicked");
                sendStop();
            }
        });
        final Button forwardButton = findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Forward Button Clicked");
                sendForward();
            }
        });

        final Button reverseButton = findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Reverse Button Clicked");
                sendReverse();
            }
        });
    }
    public void sendStop() {
        tcpSend("stop");
    }
    public void sendReset() {
        tcpSend("reverse");
    }
    public void sendForward() {
        tcpSend("forward");
    }
    public void sendReverse() {
        tcpSend("reverse");
    }
    public void sendTurnLeft() {
        tcpSend("turnleft");
    }
    public void sendTurnRight() {
        tcpSend("turnright");
    }
    public void sendWaistLeft() {
        tcpSend("waistleft");
    }
    public void sendWaistRight() {
        tcpSend("waistright");
    }
    public void sendWaistCenter() {
        tcpSend("waistcenter");
    }
    public void sendHeadLeft() {
        tcpSend("headleft");
    }
    public void sendHeadRight() {
        tcpSend("headright");
    }
    public void tcpSend(String command) {
        Message msg = tcp.handler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("TCP", command);
        msg.setData(b);
       tcp.handler.sendMessage(msg);
    }
}
