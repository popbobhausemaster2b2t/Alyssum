package me.alpha432.oyvey.features.modules.client;

import com.google.common.eventbus.Subscribe;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.ClientEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ColorsEnums.rainbowMode;
import me.alpha432.oyvey.features.modules.client.ColorsEnums.rainbowModeArray;
import me.alpha432.oyvey.features.setting.Setting;

public class Colors
extends Module {

	 private static Colors INSTANCE = new Colors();
public Setting<Integer> red = this.register(new Setting<Integer>("Red", 0, 0, 255));
public Setting<Integer> green = this.register(new Setting<Integer>("Green", 0, 0, 255));
public Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
public Setting<Integer> hoverAlpha = this.register(new Setting<Integer>("Alpha", 180, 0, 255));
public Setting<Boolean> rainbow = this.register(new Setting<Boolean>("Rainbow", false));
public Setting<rainbowMode> rainbowModeHud = this.register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> this.rainbow.getValue()));
public Setting<rainbowModeArray> rainbowModeA = this.register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> this.rainbow.getValue()));
public Setting<Integer> rainbowHue = this.register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600), v -> this.rainbow.getValue()));
public Setting<Float> rainbowBrightness = this.register(new Setting<Object>("Brightness ", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));
public Setting<Float> rainbowSaturation = this.register(new Setting<Object>("Saturation", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));

public Colors() {
    super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
    this.setInstance();
}

public static Colors getInstance() {
    if (INSTANCE == null) {
        INSTANCE = new Colors();
    }
    return INSTANCE;
}

private void setInstance() {
    INSTANCE = this;
}

@Subscribe 
public void onSettingChange(ClientEvent event) {
    if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
    	OyVey.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
    }
}
@Override
public void disable() {
}
@Override
public void onLoad() {
	 OyVey.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());

}

public int getRed() {
    return this.red.getValue();
}

public int getGreen() {
    return this.green.getValue();
}

public int getBlue() {
    return this.blue.getValue();
}
public int getAlpha() {
    return this.hoverAlpha.getValue();
}
}
