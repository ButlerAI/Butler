package com.nohowdezign.butler.modules;

import org.junit.Test;

/**
 * @author Noah Howard
 */
public class ModuleLoaderTest {

    @Test
    public void loadModulesFromDirectory() throws Exception {
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.loadModulesFromDirectory("./modules");

        ModuleRegistry registry = new ModuleRegistry();

        for(Class c : registry.getModuleClasses()) {
            System.out.println(c.getName());
        }
    }

}