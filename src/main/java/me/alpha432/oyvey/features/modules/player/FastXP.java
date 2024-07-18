package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.InventoryUtil;
import net.minecraft.item.ItemExpBottle;

public class FastXP
        extends Module {
    public FastXP() {
        super("FastXP", "FastXP.", Module.Category.PLAYER, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (FastXP.fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class)) {
        	FastXP.mc.rightClickDelayTimer = 0;
        }
    }
}