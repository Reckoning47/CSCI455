package com.example.robotcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PORT = 9999;
    private TCPClientThread client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.hostInput);
                String host = input.getText().toString();
                client = new TCPClientThread(host, PORT);
                client.start();
                Intent intent = new Intent(v.getContext(), PostConnectActivity.class);
                startActivity(intent);
            }
        });
    }
}
