package com.example.robotcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class PostConnectActivity extends AppCompatActivity {

    private TextToSpeechThread tts;
    private TCPClientThread tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_connect);

        tts = new TextToSpeechThread(this);
        tts.start();

        final Button speakButton = findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = findViewById((R.id.speakInput)).toString();
                Message msg = tts.handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("TTS", input);
                msg.setData(b);
                tts.handler.sendMessage(msg);
            }
        });

        final Button forwardButton = findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForward();
            }
        });

        final Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStop();
            }
        });

        final Button reverseButton = findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReverse();
            }
        });
    }

    public void sendForward() {
        tcpSend("forward");
    }

    public void sendStop() {
        tcpSend("stop");
    }

    public void sendReverse() {
        tcpSend("reverse");
    }

    public void tcpSend(String command) {
        Message msg = tcp.handler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("TCP", command);
        msg.setData(b);
        tcp.handler.sendMessage(msg);
    }
}
