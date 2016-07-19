package com.nohowdezign.butler.input;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.IntentParser;
import com.nohowdezign.butler.modules.ModuleLoader;
import com.nohowdezign.butler.modules.ModuleRegistry;
import com.nohowdezign.butler.modules.ModuleRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Noah Howard
 */
public class CLIInput extends Input {
    private boolean isNextInput = false;
    private ModuleLoader loader;

    public CLIInput(ModuleLoader loader) {
        this.loader = loader;
        this.inputMethod = "cli";
    }

    @Override
    public void listenForInput() {
        try {
            System.out.print("? ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ModuleRunner moduleRunner = new ModuleRunner();
            while(!input.equalsIgnoreCase("goodbye butler")) {
                input = in.readLine();
                isNextInput = true;
                AbstractIntent intent = new IntentParser().parseIntentFromSentence(input);
                if(ModuleRegistry.getModuleClassForIntent(intent.getIntentType()) != null) {
                    moduleRunner.runModuleForSubject(intent.getIntentType(), intent, loader);
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
