package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.modules.annotations.Execute;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.responder.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "reload")
public class ModuleReloader {
    private static Logger logger = LoggerFactory.getLogger(ModuleReloader.class);

    @Execute
    public void run(String query, Responder responder) {
        logger.info("Attempting to reload modules...");
        ModuleRegistry registry = new ModuleRegistry();
        registry.reset();
        logger.info("Registry has been reset.");

        ModuleLoader moduleLoader = new ModuleLoader();
        try {
            moduleLoader.loadModulesFromDirectory("./modules");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("Modules have been reloaded.");
    }

}
