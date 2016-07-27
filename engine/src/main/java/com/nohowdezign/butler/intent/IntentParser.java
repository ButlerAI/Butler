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
        abstractIntent.setOriginalSentence(sentence);
        Sentence sentence1 = new Sentence(sentence);
        processSentence(abstractIntent, sentence1);
        processSentenceExtras(abstractIntent, sentence1);
        return abstractIntent;
    }

    private void processSentence(AbstractIntent intent, Sentence sentence) {
        for(String word : sentence.words()) {
            if(ModuleRegistry.getModuleClassForIntent(word) != null) {
                intent.setIntentType(word);
            }
        }
    }

    private void processSentenceExtras(AbstractIntent intent, Sentence sentence) {
        Map<String, String> extras = new HashMap<>();
        for(int i = 0; i < sentence.nerTags().size(); i++) {
            if(!sentence.nerTag(i).equals("O")) {
                if(!extras.containsKey(sentence.nerTag(i))) {
                    extras.put(sentence.nerTag(i), sentence.word(i));
                } else {
                    extras.put(sentence.nerTag(i), extras.get(sentence.nerTag(i)) + " " + sentence.word(i));
                }
            }
        }
        intent.setOptionalArguments(extras);
    }

}
