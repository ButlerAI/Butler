package com.nohowdezign.butler.eventsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class EventRegistry {

    private static List<Class> handlers = new ArrayList<Class>();

    public static void register(Class clazz) {
        handlers.add(clazz);
    }

    public static List<Class> getHandlers() {
        return handlers;
    }

}
