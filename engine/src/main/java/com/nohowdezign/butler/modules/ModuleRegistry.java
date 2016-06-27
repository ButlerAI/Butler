package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.modules.annotations.ModuleLogic;

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
            for(Class c : map.values()) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public static Class getModuleClassForSubject(String subject) {
        Class toReturn = null;
        for(HashMap<String, Class> map : moduleClasses) {
            for(String key : map.keySet()) {
                if(key.toLowerCase().contains(subject)) {
                    for (Class c : map.values()) {
                        toReturn = c;
                    }
                }
            }
        }

        return toReturn;
    }

    public void addModuleClass(String subject, Class moduleClass) {
        if(moduleClass.getAnnotation(ModuleLogic.class) != null) {
            HashMap<String, Class> map = new HashMap<>();
            map.put(subject, moduleClass);
            moduleClasses.add(map); // Only add class to registry if it is a main class
        }
    }

    public void reset() {
        moduleClasses.clear();
    }
}
