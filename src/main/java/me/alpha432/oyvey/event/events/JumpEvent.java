package me.alpha432.oyvey.event.events;

import me.alpha432.oyvey.event.EventStage;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class JumpEvent extends EventStage {
    private float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}