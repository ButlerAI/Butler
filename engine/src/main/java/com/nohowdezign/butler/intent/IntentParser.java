package com.nohowdezign.butler.intent;

import com.nohowdezign.butler.modules.ModuleRegistry;
import edu.stanford.nlp.simple.Sentence;

/**
 * @author Noah Howard
 */
public class IntentParser {

    public AbstractIntent parseIntentFromSentance(String sentence) {
        AbstractIntent abstractIntent = new AbstractIntent();
        Sentence sentence1 = new Sentence(sentence);
        return abstractIntent;
    }

    private void processSentance(Sentence sentence) {
        for(String word : sentence.words()) {
            if(ModuleRegistry.getModuleClassForSubject(word) != null) {
                //
            }
        }
    }

}
