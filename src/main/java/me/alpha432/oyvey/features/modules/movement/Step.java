package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;

	public class Step
	extends Module {
public static Step instance;


public Step() {
	
super("Step", "Step.", Module.Category.MOVEMENT, true, false, false);
}

public final Setting<Integer> height = this.register(new Setting<Integer>("Height", 2, 1, 2));

public void onUpdate() {
mc.player.stepHeight = height.getValue().floatValue();
}

public void onDisable() {
mc.player.stepHeight = 0.5f;
}
}