package com.nohowdezign.butler.responder;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * @author Noah Howard
 */
public class VoiceResponder extends Responder {

    public VoiceResponder() {
        this.responseType = "voice";
    }

    @Override
    public void respondWithMessage(String message) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak(message);
    }
}
