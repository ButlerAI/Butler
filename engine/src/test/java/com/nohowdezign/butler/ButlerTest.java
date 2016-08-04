package com.nohowdezign.butler;

import com.nohowdezign.butler.modules.ModuleLoader;
import org.junit.Before;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class ButlerTest {

    @Before
    public void init() {
        ModuleLoader loader = new ModuleLoader();
        try {
            loader.loadModulesFromDirectory("./modules");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}