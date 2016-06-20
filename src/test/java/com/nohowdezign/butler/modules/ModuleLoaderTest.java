package com.nohowdezign.butler.modules;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Noah Howard
 */
public class ModuleLoaderTest {

    @Test
    public void loadModulesFromDirectory() throws Exception {
        Class loadedClass = null;
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.loadModulesFromDirectory("./modules");

        for(Class c : ModuleRegistry.getModuleClasses()) {
            loadedClass = c;
        }
        assertEquals("com.nohowdezign.testmodule.TestModule", loadedClass.getName());
    }

}