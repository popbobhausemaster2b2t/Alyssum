package me.alpha432.oyvey.features.modules.movement;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayer;

import net.minecraft.client.gui.GuiChat;

import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.item.Item;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import org.lwjgl.input.Keyboard;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.KeyPressedEvent;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.settings.KeyBinding;


public class NoSlow extends Module
{
    public Setting<Boolean> guiMove;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> soulSand;
    public Setting<Boolean> strict;
    public Setting<Boolean> sneakPacket;
    public Setting<Boolean> endPortal;
    public Setting<Boolean> webs;
    public final Setting<Double> webHorizontalFactor;
    public final Setting<Double> webVerticalFactor;
    private static NoSlow INSTANCE;
    private boolean sneaking;
    private static KeyBinding[] keys;
    
    public NoSlow() {
        super("NoSlowDown", "Prevents you from getting slowed down.", Category.MOVEMENT, true, false, false);
        this.guiMove = (Setting<Boolean>)this.register(new Setting("GuiMove", true));
        this.noSlow = (Setting<Boolean>)this.register(new Setting("NoSlow", true));
        this.soulSand = (Setting<Boolean>)this.register(new Setting("SoulSand", true));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", false));
        this.sneakPacket = (Setting<Boolean>)this.register(new Setting("SneakPacket", false));
        this.endPortal = (Setting<Boolean>)this.register(new Setting("EndPortal", true));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", false));
        this.webHorizontalFactor = (Setting<Double>)this.register(new Setting("WebHSpeed", 2.0, 0.0, 100.0, v -> this.webs.getValue()));
        this.webVerticalFactor = (Setting<Double>)this.register(new Setting("WebVSpeed", 2.0, 0.0, 100.0, v -> this.webs.getValue()));
        this.sneaking = false;
        this.setInstance();
    }
    
    private void setInstance() {
        NoSlow.INSTANCE = this;
    }
    
    public static NoSlow getInstance() {
        if (NoSlow.INSTANCE == null) {
            NoSlow.INSTANCE = new NoSlow();
        }
        return NoSlow.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (this.guiMove.getValue()) {
            if (NoSlow.mc.currentScreen instanceof GuiOptions || NoSlow.mc.currentScreen instanceof GuiVideoSettings || NoSlow.mc.currentScreen instanceof GuiScreenOptionsSounds || NoSlow.mc.currentScreen instanceof GuiContainer || NoSlow.mc.currentScreen instanceof GuiIngameMenu) {
                for (final KeyBinding bind : NoSlow.keys) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
                }
            }
            else if (NoSlow.mc.currentScreen == null) {
                for (final KeyBinding bind : NoSlow.keys) {
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
        if (this.webs.getValue() && NoSlow.mc.player.isInWeb) {
            final EntityPlayerSP player4;
            final EntityPlayerSP player = player4 = NoSlow.mc.player;
            player4.motionX *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player5;
            final EntityPlayerSP player2 = player5 = NoSlow.mc.player;
            player5.motionZ *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player6;
            final EntityPlayerSP player3 = player6 = NoSlow.mc.player;
            player6.motionY *= this.webVerticalFactor.getValue();
        }
        final Item item = NoSlow.mc.player.getActiveItemStack().getItem();
        if (this.sneaking && !NoSlow.mc.player.isHandActive() && this.sneakPacket.getValue()) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = false;
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final PlayerInteractEvent.RightClickItem event) {
        final Item item = NoSlow.mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || (item instanceof ItemPotion && this.sneakPacket.getValue())) && !this.sneaking) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent event) {
        if (this.noSlow.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            final MovementInput movementInput3;
            final MovementInput movementInput = movementInput3 = event.getMovementInput();
            movementInput3.moveStrafe *= 5.0f;
            final MovementInput movementInput4;
            final MovementInput movementInput2 = movementInput4 = event.getMovementInput();
            movementInput4.moveForward *= 5.0f;
        }
    }
    
    @SubscribeEvent
    public void onKeyEvent(final KeyPressedEvent event) {
        if (this.guiMove.getValue() && event.getStage() == 0 && !(NoSlow.mc.currentScreen instanceof GuiChat)) {
            event.info = event.pressed;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.strict.getValue() && this.noSlow.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(NoSlow.mc.player.posX), Math.floor(NoSlow.mc.player.posY), Math.floor(NoSlow.mc.player.posZ)), EnumFacing.DOWN));
        }
    }
    
    static {
        NoSlow.INSTANCE = new NoSlow();
        NoSlow.keys = new KeyBinding[] { NoSlow.mc.gameSettings.keyBindForward, NoSlow.mc.gameSettings.keyBindBack, NoSlow.mc.gameSettings.keyBindLeft, NoSlow.mc.gameSettings.keyBindRight, NoSlow.mc.gameSettings.keyBindJump, NoSlow.mc.gameSettings.keyBindSprint };
    }
}
