package com.nohowdezign.butler.input;

import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRunner;
import com.nohowdezign.butler.utils.Constants;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class VoiceInput extends Input {
    private ModuleLoader loader;
    private boolean isActive = false;

    public VoiceInput(ModuleLoader loader) {
        this.loader = loader;
        this.inputMethod = "voice";
    }

    @Override
    public void listenForInput() {
        try {
            Configuration configuration = new Configuration();

            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("file:resources/models/en-us.dict");
            configuration.setLanguageModelPath("file:resources/models/en-us.lm");

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);
            SpeechResult result;
            ModuleRunner moduleRunner = new ModuleRunner();
            Constants.DEFAULT_RESPONDER.respondWithMessage("I am ready to assist.");
            while((result = recognizer.getResult()) != null) {
                result.getHypothesis();
                input = result.getHypothesis(); // Set the input to the speech recognizer's hypothesis
                logger.info("Got input: " + input);
                if(isActive) {
                    for (String s : processUserInput(input).split(" ")) {
                        moduleRunner.runModuleForSubject(s, input, loader);
                    }
                    isActive = false;
                } else if(input.contains("butler")) {
                    Constants.DEFAULT_RESPONDER.respondWithMessage("Yes?");
                    isActive = true;
                } else if(input.contains("goodbye")) {
                    Constants.DEFAULT_RESPONDER.respondWithMessage("Goodbye, talk to you later.");
                    System.exit(1);
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
