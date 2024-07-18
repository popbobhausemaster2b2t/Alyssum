package me.alpha432.oyvey.event.events;

import me.alpha432.oyvey.event.EventStage;

public class KeyPressedEvent extends EventStage
{
    public boolean info;
    public boolean pressed;
    
    public KeyPressedEvent(final boolean info, final boolean pressed) {
        this.info = info;
        this.pressed = pressed;
    }
}