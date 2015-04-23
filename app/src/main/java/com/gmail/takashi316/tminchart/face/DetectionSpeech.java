package com.gmail.takashi316.tminchart.face;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by sasaki on 2015/02/25.
 */
public class DetectionSpeech {
    public enum SPEECH {
        SPEECH_CLOSE, SPEECH_FAR, SPEECH_FRONT, SPEECH_NONE, SPEECH_NOISY
    };
    private static TextToSpeech textToSpeech;
    private SPEECH lastSpeech = SPEECH.SPEECH_NONE;

    public DetectionSpeech(Context context) {
        this.textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.JAPANESE);
            }
        });
    }

    public void speechTooLarge(){
        if(this.textToSpeech.isSpeaking()) return;
        if(this.lastSpeech == SPEECH.SPEECH_FAR) return;
        this.lastSpeech = SPEECH.SPEECH_FAR;
        this.textToSpeech.speak("離れて", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speechTooSmall() {
        if(this.textToSpeech.isSpeaking()) return;
        if(this.lastSpeech == SPEECH.SPEECH_CLOSE) return;
        this.lastSpeech = SPEECH.SPEECH_CLOSE;
        this.textToSpeech.speak("近づいて", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speechFront() {
        if(this.textToSpeech.isSpeaking()) return;
        if(this.lastSpeech == SPEECH.SPEECH_FRONT) return;
        this.lastSpeech = SPEECH.SPEECH_FRONT;
        textToSpeech.speak("正面に置いて下さい", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speechNoisy() {
        //this.lastSpeech = SPEECH.SPEECH_NOISY;
    }

    public void speechGood() {
        this.lastSpeech = SPEECH.SPEECH_NONE;
    }
}//DetectionSpeech

