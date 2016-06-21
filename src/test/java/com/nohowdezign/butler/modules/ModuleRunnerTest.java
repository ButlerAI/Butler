package com.nohowdezign.butler.modules;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Howard
 */
public class ModuleRunnerTest {

    @Before
    public void setUp() throws Exception {
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.loadModulesFromDirectory("./modules");
    }

    @Test
    public void testRunModuleForSubject() {
        ModuleRunner runner = new ModuleRunner();
        runner.runModuleForSubject("foobar");
    }

}
