package com.nohowdezign.butler.eventsystem;

import com.nohowdezign.butler.eventsystem.handlers.FakeEventHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Noah Howard
 */
public class EventRegistryTest {

    @Test
    public void registerEvent() {
        EventRegistry.register(FakeEventHandler.class);
        assertEquals("[class com.nohowdezign.butler.eventsystem.handlers.FakeEventHandler]", EventRegistry.getHandlers().toString());
    }

}
