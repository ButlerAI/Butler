package com.nohowdezign.butler.eventsystem.handlers;

import com.nohowdezign.butler.eventsystem.annotations.ReceiveEvent;
import com.nohowdezign.butler.eventsystem.events.FakeEvent1;
import com.nohowdezign.butler.eventsystem.events.FakeEvent2;
import com.nohowdezign.butler.eventsystem.events.FakeEvent3;

/**
 * @author Noah Howard
 */
public class FakeEventHandler {

    @ReceiveEvent
    public void onFakeEvent1(FakeEvent1 event1) {
    }

    @ReceiveEvent
    public void onFakeEvent2(FakeEvent2 event1) {
    }

    @ReceiveEvent
    public void onFakeEvent3(FakeEvent3 event1) {
    }

}
