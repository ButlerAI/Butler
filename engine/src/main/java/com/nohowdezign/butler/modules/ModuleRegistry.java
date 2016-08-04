package com.nohowdezign.butler.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Noah Howard
 */
public class ModuleRegistry {
    private static final List<HashMap<String, Class>> moduleClasses = new ArrayList<>();

    public static List<Class> getModuleClasses() {
        List<Class> toReturn = new ArrayList<>();
        for(HashMap<String, Class> map : moduleClasses) {
            toReturn.addAll(map.values());
        }
        return toReturn;
    }

    public static List<String> getModuleTriggers() {
        List<String> toReturn = new ArrayList<>();
        for(HashMap<String, Class> map : moduleClasses) {
            toReturn.addAll(map.keySet());
        }
        return toReturn;
    }

    public static Class getModuleClassForIntent(String intent) {
        Class toReturn = null;
        for(HashMap<String, Class> map : moduleClasses) {
            for(String key : map.keySet()) {
                if(key.toLowerCase().equalsIgnoreCase(intent)) {
                    for (Class c : map.values()) {
                        toReturn = c;
                    }
                }
            }
        }

        return toReturn;
    }

    public void addModuleClass(String intent, Class moduleClass) {
        HashMap<String, Class> map = new HashMap<>();
        for(String s : intent.split("\\s")) {
            map.put(s, moduleClass);
        }
        moduleClasses.add(map);
    }

    public void reset() {
        moduleClasses.clear();
    }
}
