package com.example.robotcontroller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class TextToSpeechThread extends Thread implements android.speech.tts.TextToSpeech.OnInitListener {

    private static class TTSHandler extends Handler {

        private final WeakReference<TextToSpeechThread> ttsThread;

        public TTSHandler(TextToSpeechThread ttsThread) {
            this.ttsThread = new WeakReference(ttsThread);
        }

        public void handleMessage(Message msg) {
            TextToSpeechThread tts = this.ttsThread.get();
            String speech = msg.getData().getString("TTS");
            tts.speakOut(speech);
        }

    }

    private android.speech.tts.TextToSpeech tts;
    private Context context;
    private String last;

    public Handler handler;


    TextToSpeechThread(Context con) {
        context = con;
        tts = new android.speech.tts.TextToSpeech(con, this);
        last = "c";
    }

    public void run() {
        Looper.prepare();
        this.handler = new TTSHandler(this);
        Looper.loop();
    }

    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            tts.setPitch(0);
            tts.setSpeechRate((float) 1);

            if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Language or data not working", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void speakOut(String text) {
        if (last != text) {
            last = text;
            tts.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null);

            while (tts.isSpeaking()) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
