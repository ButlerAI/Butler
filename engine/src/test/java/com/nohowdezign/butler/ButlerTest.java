package com.nohowdezign.butler;

import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.processing.LanguageProcessor;
import org.junit.Before;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class ButlerTest {

    @Before
    public void init() {
        ModuleLoader loader = new ModuleLoader();
        try {
            loader.loadModulesFromDirectory("./modules");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testStartButlerSuite() {
        LanguageProcessor languageProcessor = new LanguageProcessor();
        String partsOfSentence = "";
        partsOfSentence = languageProcessor.getPartOfSpeechFromSentence("What is the weather like?", "NN");

        for(String s : partsOfSentence.split(" ")) {
            if(ModuleRegistry.getModuleClassForSubject(s) != null) {
                //TODO: Run the module first module that matches and throw some shit at the neural network
            }
        }
    }

}