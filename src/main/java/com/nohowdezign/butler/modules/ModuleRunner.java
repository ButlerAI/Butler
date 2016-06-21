package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.modules.annotations.Initialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Noah Howard
 */
public class ModuleRunner {
    private Logger logger = LoggerFactory.getLogger(ModuleRunner.class);

    public void runModuleForSubject(String subject) {
        Class c = ModuleRegistry.getModuleClassForSubject(subject);
        if(c != null) {
            logger.debug(String.format("Found module %s for subject %s", c.getName(), subject));
            moduleRunner(c);
        }
    }

    private void moduleRunner(Class c) {
        new Thread() {
            @Override
            public void run() {
                ModuleRunner moduleRunner = new ModuleRunner();
                moduleRunner.runModule(c);
            }
        }.start();
    }

    private void runModule(Class handler) {
        for(Method method : handler.getMethods()) {
            logger.debug("Found method " + method.getName());
            findAndRunInitializeMethods(handler, method);
        }
    }

    private void findAndRunInitializeMethods(Class<?> handler, Method method) {
        Initialize initializeMethod = method.getAnnotation(Initialize.class);
        if (initializeMethod != null) {
            logger.debug("Initialize method found: " + method.getName());
            runInitMethod(method, handler);
        }
    }

    private void runInitMethod(Method method, Class<?> handler) {
        try {
            method.invoke(handler.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
