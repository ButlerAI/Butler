package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.modules.annotations.Initialize;

import java.lang.reflect.Method;

/**
 * @author Noah Howard
 */
public class ModuleRunner {

    public static void moduleRunner() {
        new Thread() {
            @Override
            public void run() {
                runModule();
            }
        }.start();
    }

    private static void runModule() {
        for (Class<?> handler : ModuleRegistry.getModuleClasses()) {
            Method[] methods = handler.getMethods();

            for (int i = 0; i < methods.length; ++i) {
                Initialize initializeMethod = methods[i].getAnnotation(Initialize.class);
                if (initializeMethod != null) {
                    try {
                        methods[i].invoke(handler.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
