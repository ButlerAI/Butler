package com.nohowdezign.butler.input;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.IntentParser;
import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.modules.ModuleRunner;
import com.nohowdezign.butler.responder.VoiceResponder;
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

            //configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setAcousticModelPath("file:resources/cmusphinx-en-us-ptm-8khz-5.2");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);
            SpeechResult result;
            ModuleRunner moduleRunner = new ModuleRunner();
            VoiceResponder responder = (VoiceResponder) Constants.DEFAULT_RESPONDER;
            Constants.DEFAULT_RESPONDER.respondWithMessage("I am ready to assist.");
            while((result = recognizer.getResult()) != null) {
                if(!responder.isTalking()) {
                    result.getHypothesis();
                    input = result.getHypothesis(); // Set the input to the speech recognizer's hypothesis
                    logger.info("Got input: " + input);
                    if(isActive) {
                        // Run module in new thread
                        AbstractIntent intent = new IntentParser().parseIntentFromSentence(input.toLowerCase());
                        new Thread() {
                            @Override
                            public void run() {
                                if(ModuleRegistry.getModuleClassForIntent(intent.getIntentType()) != null) {
                                    moduleRunner.runModuleForSubject(intent.getIntentType(), intent, loader);
                                }
                            }
                        }.start();
                        isActive = false;
                    } else if (input.toLowerCase().contains("butler")) {
                        Constants.DEFAULT_RESPONDER.respondWithMessage("Yes?");
                        isActive = true;
                    } else if (input.toLowerCase().contains("goodbye")) {
                        Constants.DEFAULT_RESPONDER.respondWithMessage("Goodbye, talk to you later.");
                        System.exit(1);
                    }
                } else {
                    logger.info("IM TALKING ASSCLOWN");
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
