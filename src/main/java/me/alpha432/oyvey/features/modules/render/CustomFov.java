package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.client.settings.GameSettings;

public class CustomFov
extends Module {

public CustomFov() {
    super("CustomFov", "Makes your game brighter.", Module.Category.RENDER, true, false, false);
}

private final Setting<Integer> fov = this.register(new Setting<Integer>("Fov", 70, 0, 180));


@Override
public void onUpdate() {
        ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue().floatValue());
    }
}