package com.nohowdezign.butler.input;

import com.nohowdezign.butler.database.UserProfile;
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
import edu.cmu.sphinx.util.props.ConfigurationManager;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class VoiceInput extends Input {
    private static ConfigurationManager cm;
    private ModuleLoader loader;
    private boolean isActive = false;

    public VoiceInput(ModuleLoader loader) {
        this.loader = loader;
        this.inputMethod = "voice";
    }

    @Override
    public void listenForInput() {
        try {
            Configuration configuration = createConfiguration();
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
                        if(ModuleRegistry.getModuleClassForIntent(intent.getIntentType()) != null) {
                            recognizer.stopRecognition();
                            moduleRunner.runModuleForSubject(intent.getIntentType(), intent, loader);
                            recognizer.startRecognition(true);
                        }
                        isActive = false;
                    } else if(input.toLowerCase().contains("bye")) {
                        Constants.DEFAULT_RESPONDER.respondWithMessage(String.format("Goodbye, talk to you later %s.", UserProfile.DEFAULT_USER));
                        System.exit(1);
                    } else if(input.toLowerCase().contains("butler")) {
                        recognizer.stopRecognition();
                        Constants.DEFAULT_RESPONDER.respondWithMessage("Sir?");
                        isActive = true;
                        recognizer.startRecognition(true);
                    }
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

    private Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("file:resources/cmusphinx-en-us-ptm-8khz-5.2");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        if(Constants.GRAMMAR_FILE == null) {
            configuration.setGrammarPath("resource:/grammar");
            configuration.setGrammarName("butler");
        } else {
            configuration.setGrammarPath("file:" + String.valueOf(Constants.GRAMMAR_FILE.getParent()) + "/");
            configuration.setGrammarName(Constants.GRAMMAR_FILE.getName().replace(".gram", ""));
        }
        configuration.setUseGrammar(true);
        return configuration;
    }
}
