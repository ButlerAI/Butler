package com.nohowdezign.butler;

import com.nohowdezign.butler.voice.VoiceInput;

import java.io.IOException;

/**
 * Created by Noah on 6/14/2016.
 */
public class Butler {

    public static void main(String[] args) {
        VoiceInput voice = new VoiceInput();
        try {
            voice.parseVoice();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
