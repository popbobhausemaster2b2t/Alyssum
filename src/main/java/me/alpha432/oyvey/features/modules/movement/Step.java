package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;

public class Step
        extends Module {
    public Step() {
        super("Step", "Step.", Module.Category.MOVEMENT, true, false, false);
    }



@Override
public void onUpdate() {
    if (ReverseStep.mc.player.onGround) {  ReverseStep.mc.player.motionY -= 1.0;
    }
}
}