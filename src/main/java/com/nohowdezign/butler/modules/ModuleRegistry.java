package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.modules.annotations.ModuleLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class ModuleRegistry {
    private static final List<Class> moduleClasses = new ArrayList<>();

    public static List<Class> getModuleClasses() {
        return moduleClasses;
    }

    public void addModuleClass(Class moduleClass) {
        if(moduleClass.getAnnotation(ModuleLogic.class) != null) {
            this.moduleClasses.add(moduleClass); // Only add class to registry if it is a main class
        }
    }
}
