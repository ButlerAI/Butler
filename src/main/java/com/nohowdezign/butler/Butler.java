package com.nohowdezign.butler;

import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.modules.ModuleRunner;
import com.nohowdezign.butler.processing.LanguageProcessor;
import com.nohowdezign.butler.utils.Constants;
import com.nohowdezign.butler.voice.VoiceInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Noah Howard
 */
public class Butler {
    private static Logger logger = LoggerFactory.getLogger(Butler.class);

    public static void main(String[] args) {
        Butler butler = new Butler();
        butler.printInit();

        ModuleLoader moduleLoader = new ModuleLoader();
        try {
            logger.info("Loading modules...");
            moduleLoader.loadModulesFromDirectory("./modules");
            logger.info("Modules loaded.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        logger.info("Initializing language processor...");
        LanguageProcessor languageProcessor = new LanguageProcessor();

        try {
            logger.info(String.format("Butler v. %1$,.2f loaded. Now listening for input.", Constants.VERSION));
            butler.readUserInput(languageProcessor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readUserInput(LanguageProcessor processor) throws IOException {
        System.out.print("? ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while(!line.equalsIgnoreCase("end conversation")) {
            line = in.readLine();
            String pos = processor.normalizeSentence(line);
            pos = processor.getPartOfSpeechFromSentence(pos, "NN");
            for(String s : pos.split(" ")) {
                ModuleRunner moduleRunner = new ModuleRunner();
                moduleRunner.runModuleForSubject(s);
            }
            System.out.print("? ");
        }
        in.close();
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
