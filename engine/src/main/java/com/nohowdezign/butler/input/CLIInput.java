package com.nohowdezign.butler.input;

import com.nohowdezign.butler.modules.ModuleRunner;
import com.nohowdezign.butler.processing.LanguageProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Noah Howard
 */
public class CLIInput extends Input {
    private LanguageProcessor processor = new LanguageProcessor();

    public CLIInput(LanguageProcessor processor) {
        this.processor = processor;
        this.inputMethod = "CLI";
    }

    @Override
    public void listenForInput() {
        try {
            System.out.print("? ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            ModuleRunner moduleRunner = new ModuleRunner();
            while (!line.equalsIgnoreCase("goodbye butler")) {
                line = in.readLine();
                for (String s : processUserInput(line).split(" ")) {
                    moduleRunner.runModuleForSubject(s, line);
                }
                System.out.print("? ");
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
