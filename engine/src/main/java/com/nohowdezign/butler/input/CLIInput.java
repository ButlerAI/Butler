package com.nohowdezign.butler.input;

import com.nohowdezign.butler.modules.ModuleLoader;
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
    private boolean isNextInput = false;
    private ModuleLoader loader;

    public CLIInput(LanguageProcessor processor, ModuleLoader loader) {
        this.processor = processor;
        this.loader = loader;
        this.inputMethod = "cli";
    }

    @Override
    public void listenForInput() {
        try {
            System.out.print("? ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ModuleRunner moduleRunner = new ModuleRunner();
            while (!input.equalsIgnoreCase("goodbye butler")) {
                input = in.readLine();
                isNextInput = true;
                for (String s : processUserInput(input).split(" ")) {
                    moduleRunner.runModuleForSubject(s, input, loader);
                }
                isNextInput = false;
                System.out.print("? ");
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNextInput() {
        if(isNextInput) {
            return input;
        }
        return null;
    }

}
