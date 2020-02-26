package com.example.robotcontroller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText textEditableTalk;
    Button buttonTalk;
    TTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TTS(this);
        tts.start();



        textEditableTalk = (EditText) findViewById(R.id.textEditableTalk);
        buttonTalk = (Button) findViewById(R.id.buttonTalk);
        buttonTalk.setOnClickListener(this);


    }

    public void onClick(View v) {
        Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
        switch(v.getId()) {
            case R.id.buttonTalk:
                String input = textEditableTalk.getText().toString();
                Message sendMsg = tts.handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("TT", input);
                sendMsg.setData(b);
                tts.handler.sendMessage(sendMsg);
                break;
        }
    }

    public final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            String m = message.getData().getString("result");
        }
    };



}


