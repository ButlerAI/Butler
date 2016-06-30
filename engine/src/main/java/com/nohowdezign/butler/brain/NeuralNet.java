package com.nohowdezign.butler.brain;

import com.nohowdezign.butler.modules.ModuleRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Noah Howard
 */
public class NeuralNet {
    private Logger logger = LoggerFactory.getLogger(NeuralNet.class);

    // Maps a subject string, return class to a weight float
    private HashMap<HashMap<String, Class>, Float> choiceList = new HashMap<>();

    public Class getActionForSubject(String subject) {
        Class toReturn = null;
        for(HashMap<String, Class> map : choiceList.keySet()) {
            if(map.keySet().contains(subject)) {
                toReturn = map.get(subject);
            }
        }
        return toReturn;
    }

    public void addSubjectToList(String subject, Class classToRun) {
        if(classToRun != null) {
            HashMap<String, Class> map = new HashMap<>();
            map.put(subject, classToRun);
            choiceList.put(map, 1.0f);
            logger.info(String.format("Added class %s to network for subject %s with weight of %s.", classToRun.getName(), subject, 1));
        }
    }

}
