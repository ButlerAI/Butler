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

    public void runModuleForSubject(String subject, String originalQuery) {
        Class c = ModuleRegistry.getModuleClassForSubject(subject);
        if(c != null) {
            logger.debug(String.format("Found module %s for subject %s", c.getName(), subject));
            runModule(c, originalQuery);
        }
    }

    private void runModule(Class handler, String originalQuery) {
        for(Method method : handler.getMethods()) {
            logger.debug("Found method " + method.getName());
            findAndRunInitializeMethods(handler, method, originalQuery);
        }
    }

    private void findAndRunInitializeMethods(Class<?> handler, Method method, String originalQuery) {
        Initialize initializeMethod = method.getAnnotation(Initialize.class);
        if (initializeMethod != null) {
            logger.debug("Initialize method found: " + method.getName());
            runInitMethod(method, handler, originalQuery);
        }
    }

    private void runInitMethod(Method method, Class<?> handler, String originalQuery) {
        try {
            Class<?>[] methodParams = method.getParameterTypes();

            if(methodParams.length < 1) {
                logger.debug("This method does not need a query string.");
                method.invoke(handler.newInstance());
            } else {
                method.invoke(handler.newInstance(), originalQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
