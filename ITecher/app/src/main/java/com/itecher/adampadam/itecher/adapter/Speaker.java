package com.itecher.adampadam.itecher.adapter;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Locale;

public class Speaker implements TextToSpeech.OnInitListener {

    private static TextToSpeech engine;
    private static final double pitch = 1.1;
    private static final double speed = 0.7;

    public Speaker(Context context) {

        engine = new TextToSpeech(context, this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void speak(String s) {
        engine.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInit(int status) {
        Log.d("Speech", "OnInit - Status [" + status + "]");

        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            engine.setEngineByPackageName("com.svox.pico");
            engine.setLanguage(Locale.US);
            engine.setPitch((float) pitch);
            engine.setSpeechRate((float) speed);
        }
    }

}