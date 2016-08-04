package com.nohowdezign.butler.brain;

import com.nohowdezign.butler.eventsystem.EventRegistry;
import com.nohowdezign.butler.eventsystem.events.FakeEvent1;
import com.nohowdezign.butler.eventsystem.events.FakeEvent2;
import com.nohowdezign.butler.eventsystem.events.FakeEvent3;
import com.nohowdezign.butler.eventsystem.handlers.FakeEventHandler;
import com.nohowdezign.butler.modules.ModuleRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Noah Howard
 */
public class InputStandardizationTest {
    private StandardizeInput standardizeInput = new StandardizeInput();
    private ModuleRegistry registry = new ModuleRegistry();

    @Before
    public void setup() {
        // Register some test triggers
        registry.addModuleClass("weather", this.getClass());
        registry.addModuleClass("time", this.getClass());
        registry.addModuleClass("test", this.getClass());

        EventRegistry.register(FakeEventHandler.class);

        standardizeInput.init();
    }

    @After
    public void cleanup() {
        registry.cleanup();
        EventRegistry.cleanup();
        standardizeInput.cleanup();
    }

    @Test
    public void testStandardizeTrigger() {
        List<List<Float>> standardizedTriggers = new ArrayList<>();
        String trigger1 = "weather";
        String trigger2 = "time";
        String trigger3 = "test";

        standardizedTriggers.add(standardizeInput.standardizeTrigger(trigger1));
        standardizedTriggers.add(standardizeInput.standardizeTrigger(trigger2));
        standardizedTriggers.add(standardizeInput.standardizeTrigger(trigger3));

        assertEquals("[[1.0, 0.0, 0.0], [0.0, 1.0, 0.0], [0.0, 0.0, 1.0]]", standardizedTriggers.toString());
    }

    @Test
    public void testStandardizeEvent() {
        List<List<Float>> standardizedEvents = new ArrayList<>();

        standardizedEvents.add(standardizeInput.standardizeEvent(new FakeEvent1()));
        standardizedEvents.add(standardizeInput.standardizeEvent(new FakeEvent2()));
        standardizedEvents.add(standardizeInput.standardizeEvent(new FakeEvent3()));

        assertEquals("[[1.0, 0.0, 0.0], [0.0, 1.0, 0.0], [0.0, 0.0, 1.0]]", standardizedEvents.toString());
    }

}
