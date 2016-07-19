package com.nohowdezign.butler.intent;

import com.nohowdezign.butler.modules.ModuleRegistry;
import edu.stanford.nlp.simple.Sentence;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Howard
 */
public class IntentParser {

    public AbstractIntent parseIntentFromSentence(String sentence) {
        AbstractIntent abstractIntent = new AbstractIntent();
        Sentence sentence1 = new Sentence(sentence);
        processSentence(abstractIntent, sentence1);
        return abstractIntent;
    }

    private void processSentence(AbstractIntent intent, Sentence sentence) {
        for(String word : sentence.words()) {
            if(ModuleRegistry.getModuleClassForIntent(word) != null) {
                intent.setIntentType(word);
                processSentenceExtras(intent, sentence);
                break;
            }
        }
    }

    private void processSentenceExtras(AbstractIntent intent, Sentence sentence) {
        Map<String, String> extras = new HashMap<>();
        for(int i = 0; i == sentence.nerTags().size(); i++) {
            if(!sentence.nerTag(i).equals("O")) {
                extras.put(sentence.nerTag(i), sentence.word(i));
            }
        }
    }

}
