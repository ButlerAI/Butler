package com.nohowdezign.butler.voice.sphinxfixes;

import edu.cmu.sphinx.api.Microphone;

/**
 * @author Noah Howard
 */
public class SpeechSourceProviderExtension {
    private Microphone mic = new Microphone(16000, 16, true, false);

    Microphone getMicrophone() {
        return mic;
    }
}
