package com.nohowdezign.butler.brain;

import com.nohowdezign.butler.eventsystem.Event;
import com.nohowdezign.butler.eventsystem.EventRegistry;
import com.nohowdezign.butler.eventsystem.annotations.ReceiveEvent;
import com.nohowdezign.butler.modules.ModuleRegistry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class StandardizeInput {
    private List<String> moduleTriggers = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    public void init() {
        moduleTriggers = ModuleRegistry.getModuleTriggers();
        storeEvents();
    }

    private List<Float> standardizeTrigger(String trigger) {
        List<Float> toReturn = new ArrayList<>();
        for(String moduleTrigger : moduleTriggers) {
            if(moduleTrigger.equals(trigger)) {
                toReturn.add(1.0f);
            } else {
                toReturn.add(0.0f);
            }
        }
        return toReturn;
    }

    private List<Float> standardizeEvent(Event event) {
        List<Float> toReturn = new ArrayList<>();
        for(String storedEvent : events) {
            if(storedEvent.equals(event.getClass().getName())) {
                toReturn.add(1.0f);
            } else {
                toReturn.add(0.0f);
            }
        }
        return toReturn;
    }

    private void storeEvents() {
        for (Class<?> handler : EventRegistry.getHandlers()) {
            Method[] methods = handler.getMethods();

            for(int i = 0; i < methods.length; ++i) {
                ReceiveEvent eventReceiver = methods[i].getAnnotation(ReceiveEvent.class);
                if (eventReceiver != null) {
                    Class<?>[] methodParams = methods[i].getParameterTypes();

                    if (methodParams.length < 1)
                        continue;

                    events.add(methodParams[0].getSimpleName());
                }
            }
        }
    }

}
