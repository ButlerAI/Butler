package com.nohowdezign.butler.responder;

import com.nohowdezign.butler.Butler;
import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.simple.Sentence;

/**
 * @author Noah Howard
 * This is the default response, if Butler has nothing else to respond with
 */
public class DefaultResponse {

    public void run(String trigger, String query, Responder responder, ModuleLoader loader) {
        responder.respondWithMessage("This is not something I currently understand. " +
                "Is this like any of the modules I currently have?");
        boolean hasNextInput = false;
        String nextInput = "";

        while(!hasNextInput) {
            if(Butler.getDefaultInput().getNextInput() != null) {
                nextInput = Butler.getDefaultInput().getNextInput();
                hasNextInput = true;
            }
        }

        Sentence sentence = new Sentence(nextInput);
        for(RelationTriple triple : sentence.openieTriples()) {
            if(ModuleRegistry.getModuleClassForSubject(triple.objectLemmaGloss()) != null) {
                loader.getRegistry().addModuleClass(triple.subjectLemmaGloss(),
                        ModuleRegistry.getModuleClassForSubject(triple.objectLemmaGloss()));
            }
        }
    }

}
