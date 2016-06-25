package com.nohowdezign.butler.voice.sphinxfixes;

import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.frontend.util.StreamDataSource;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class LiveSpeechRecognizerExtension extends AbstractSpeechRecognizer {
    private final SpeechSourceProviderExtension speechSourceProviderExtension;
    private final Microphone microphone;

    /**
     * Constructs new live recognition object.
     *
     * @param configuration common configuration
     * @throws IOException if model IO went wrong
     */
    public LiveSpeechRecognizerExtension(Configuration configuration) throws IOException {
        super(configuration);
        speechSourceProviderExtension = new SpeechSourceProviderExtension();
        microphone = speechSourceProviderExtension.getMicrophone();
        context.getInstance(StreamDataSource.class)
                .setInputStream(microphone.getStream());
    }

    /**
     * Starts recognition process.
     *
     * @param clear clear cached microphone data
     * @see         LiveSpeechRecognizer#stopRecognition()
     */
    public void startRecognition(boolean clear) {
        recognizer.allocate();
        microphone.startRecording();
    }

    /**
     * Stops recognition process.
     *
     * Recognition process is paused until the next call to startRecognition.
     *
     * @see LiveSpeechRecognizer#startRecognition(boolean)
     */
    public void stopRecognition() {
        microphone.stopRecording();
        recognizer.deallocate();
    }
}
