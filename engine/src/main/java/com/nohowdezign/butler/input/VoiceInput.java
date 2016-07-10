package com.nohowdezign.butler.input;

import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRunner;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class VoiceInput extends Input {
    private ModuleLoader loader;

    public VoiceInput(ModuleLoader loader) {
        this.loader = loader;
        this.inputMethod = "voice";
    }

    @Override
    public void listenForInput() {
        try {
            Configuration configuration = new Configuration();

            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setLanguageModelPath("file:resources/en-us.lm");

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);
            SpeechResult result;
            ModuleRunner moduleRunner = new ModuleRunner();
            while((result = recognizer.getResult()) != null) {
                input = result.getHypothesis(); // Set the input to the speech recognizer's hypothesis
                logger.info("Running module for query: " + input);
                for(String s : processUserInput(input).split(" ")) {
                    moduleRunner.runModuleForSubject(s, input, loader);
                }
            }
            recognizer.stopRecognition();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNextInput() {
        return null;
    }
}
