package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;

public class InventoryMove
extends Module {
    private static InventoryMove INSTANCE = new InventoryMove();
    public final Setting<Boolean> shift = this.register(new Setting<>("Sneak", false));

    public InventoryMove() {
    super("GuiMove", "InventoryMove", Module.Category.MISC, true, false, false);
        this.setInstance();
    }

    public static InventoryMove INSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new InventoryMove();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

