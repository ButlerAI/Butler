package com.nohowdezign.butler;

import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.input.CLIInput;
import com.nohowdezign.butler.input.Input;
import com.nohowdezign.butler.input.VoiceInput;
import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public class Butler {
    private static Logger logger = LoggerFactory.getLogger(Butler.class);
    private static Input input;

    public static void main(String[] args) {
        Butler butler = new Butler();
        butler.printInit();

        ModuleLoader moduleLoader = new ModuleLoader();
        try {
            logger.info("Loading modules...");
            moduleLoader.loadModulesFromDirectory("./modules");
            Constants.GRAMMAR_FILE = moduleLoader.createGrammarFileForFiles(moduleLoader.getGrammarFiles());
            logger.info("Modules loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.debug(String.format("Loaded modules: %s", ModuleRegistry.getModuleClasses()));

        logger.info("Opening connection to user database...");
        new Thread() {
            @Override
            public void run() {
                UserProfile profile = new UserProfile();
                profile.load();
            }
        }.start();

        logger.info(String.format("Butler v. %1$,.2f loaded. Now listening for input.", Constants.VERSION));
        new Thread() {
            @Override
            public void run() {
                input = new CLIInput(moduleLoader);
                input.listenForInput();
            }
        }.start();
    }

    public static Input getDefaultInput() {
        return input;
    }

    private void printInit() {
        logger.info("-------------------------------------------------------");
        logger.info(" _______  __   __  _______  ___      _______  ______   ");
        logger.info("|  _    ||  | |  ||       ||   |    |       ||    _ |  ");
        logger.info("| |_|   ||  | |  ||_     _||   |    |    ___||   | ||  ");
        logger.info("|       ||  |_|  |  |   |  |   |    |   |___ |   |_||_ ");
        logger.info("|  _   | |       |  |   |  |   |___ |    ___||    __  |");
        logger.info("| |_|   ||       |  |   |  |       ||   |___ |   |  | |");
        logger.info("|_______||_______|  |___|  |_______||_______||___|  |_|");
        logger.info("-------------------------------------------------------");
        logger.info("Initializing...");
    }

}
