package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class ReverseStep
        extends Module {
    private static ReverseStep INSTANCE = new ReverseStep();
    private final Setting<Boolean> twoBlocks = this.register(new Setting<Boolean>("2Blocks", Boolean.FALSE));

    public ReverseStep() {
        super("ReverseStep", "ReverseStep.", Module.Category.MOVEMENT, true, false, false);
     ///   this.setInstance();
    }


@Override
public void onUpdate() {
    if (ReverseStep.mc.player.onGround) {  ReverseStep.mc.player.motionY -= 1.0;
    }
}
}