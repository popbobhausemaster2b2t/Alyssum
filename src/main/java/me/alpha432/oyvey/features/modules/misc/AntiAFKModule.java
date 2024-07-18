package me.alpha432.oyvey.features.modules.misc;

import java.util.Timer;
import java.util.TimerTask;

import me.alpha432.oyvey.features.modules.Module;

public final class AntiAFKModule extends Module {
    private Timer timer = new Timer();

    public AntiAFKModule() {
        
        		super("Antiafk", "Sends msg after you kill someone", Module.Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (mc.player == null) {
            toggle();
            return;
        }

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mc.player.sendChatMessage("/stats");
            }
        }, 0, 120000);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (timer != null)
            timer.cancel();
    }
}