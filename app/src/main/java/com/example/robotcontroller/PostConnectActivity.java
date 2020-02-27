package com.example.robotcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class PostConnectActivity extends AppCompatActivity {

    private TextToSpeechThread tts;

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
                Message msg = Message.obtain(tts.handler);
                msg.obj = input;
                msg.sendToTarget();
            }
        });
    }
}
