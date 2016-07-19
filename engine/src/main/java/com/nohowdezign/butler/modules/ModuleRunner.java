package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.modules.annotations.Execute;
import com.nohowdezign.butler.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Noah Howard
 */
public class ModuleRunner {
    private Logger logger = LoggerFactory.getLogger(ModuleRunner.class);

    public void runModuleForSubject(String subject, AbstractIntent intent, ModuleLoader loader) {
        Class c = ModuleRegistry.getModuleClassForIntent(subject);

        if(c != null) {
            logger.debug(String.format("Found module %s for subject %s", c.getName(), subject));
            runModule(c, intent);
        } else {
            logger.debug("Running default response");
            Constants.DEFAULT_RESPONDER.respondWithMessage("I do not understand what you mean.");
        }
    }

    private void runModule(Class handler, AbstractIntent intent) {
        for(Method method : handler.getMethods()) {
            logger.debug("Found method " + method.getName());
            findAndRunExecuteMethods(handler, method, intent);
        }
    }

    private void findAndRunExecuteMethods(Class<?> handler, Method method, AbstractIntent intent) {
        Intent intentMethod = method.getAnnotation(Intent.class);
        if(intentMethod != null) {
            logger.debug("Execute method found: " + method.getName());
            runExecuteMethod(method, handler, intent);
        }
    }

    private void runExecuteMethod(Method method, Class<?> handler, AbstractIntent intent) {
        try {
            Class<?>[] methodParams = method.getParameterTypes();

            if(methodParams.length < 2) {
                logger.error("This method does not have enough arguments.");
            } else {
                method.invoke(handler.newInstance(), intent, Constants.DEFAULT_RESPONDER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
