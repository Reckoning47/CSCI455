package com.example.robotcontroller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
                String host = findViewById(R.id.hostInput).toString();
                client = new TCPClientThread(host, PORT);
                client.start();
                setContentView(R.layout.activity_post_connect);
            }
        });
    }
}
