package me.alpha432.oyvey.features.modules.combat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Bind;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.InventoryUtil;


public class AutoXP
        extends Module {
    public Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.MIDDLECLICK));
    //public Setting<Boolean> antiFriend = this.register(new Setting<>("AntiFriend", true));
    public Setting<Bind> key = this.register(new Setting<>("Bind", new Bind(-1), v -> mode.getValue() != Mode.MIDDLECLICK));
   // public Setting<Boolean> groundOnly = this.register(new Setting<>("BelowHorizon", false));
    private boolean last;
    private boolean on;

    public AutoXP() {
        super("AutoXP", "Silent XP", Module.Category.COMBAT, false, false, false);
    }

    @Override
    public void onUpdate() {
        if (AutoXP.fullNullCheck()) return;
        switch (this.mode.getValue()) {
            case PRESS:
                if (this.key.getValue().isDown())
                    this.throwXP(false);
                break;
            case TOGGLE:
                if (toggled()) {
                    this.throwXP(false);
                }
                break;
            default:
            //    if (this.groundOnly.getValue() && AutoXP.mc.player.rotationPitch < 0) return;
                if (Mouse.isButtonDown(2))
                    this.throwXP(true);
        }
    }

    private boolean toggled() {
        if (this.key.getValue().getKey() == -1)
            return false;
        if (!Keyboard.isKeyDown(this.key.getValue().getKey())) {
            this.last = true;
        } else if ((Keyboard.isKeyDown(this.key.getValue().getKey())) && this.last && !this.on) {
            this.last = false;
            this.on = true;
            return this.on;
        } else if ((Keyboard.isKeyDown(this.key.getValue().getKey())) && this.last && this.on) {
            this.last = false;
            this.on = false;
            return this.on;
        }
        return this.on;
    }

    private void throwXP(boolean mcf) {
        boolean offhand;
        RayTraceResult result;
     ///   if (mcf && this.antiFriend.getValue() && (result = AutoXP.mc.objectMouseOver) != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer)
           // return;
        int xpSlot = InventoryUtil.findHotbarBlock(ItemExpBottle.class);
        offhand = AutoXP.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE;
        if (xpSlot != -1 || offhand) {
            int oldslot = AutoXP.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(xpSlot, false);
            }
            AutoXP.mc.playerController.processRightClick(AutoXP.mc.player, AutoXP.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }

    public
    enum Mode {
        MIDDLECLICK,
        TOGGLE,
        PRESS
    }
}