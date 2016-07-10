package com.nohowdezign.butler.voice;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.junit.Test;

/**
 * @author Noah Howard
 */
public class TextToSpeechTest {

    @Test
    public void testTextToSpeech() {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak("Hello, this is a test of the Butler text to speech engine.");
    }

}
