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
                ModuleRunner  moduleRunner = new ModuleRunner();
                moduleRunner.runModule();
            }
        }.start();
    }

    private void runModule() {
        for (Class<?> handler : ModuleRegistry.getModuleClasses()) {
            Method[] methods = handler.getMethods();

            for (int i = 0; i < methods.length; ++i) {
                findAndRunInitializeMethods(handler, methods, i);
            }
        }
    }

    private void findAndRunInitializeMethods(Class<?> handler, Method[] methods, int i) {
        Initialize initializeMethod = methods[i].getAnnotation(Initialize.class);
        if (initializeMethod != null) {
            runInitMethod(methods, handler, i);
        }
    }

    private void runInitMethod(Method[] methods, Class<?> handler, int i) {
        try {
            methods[i].invoke(handler.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
