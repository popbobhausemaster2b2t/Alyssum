package me.alpha432.oyvey.mixin.mixins;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.alpha432.oyvey.event.events.NoRenderEvent;

@Mixin(value={LayerBipedArmor.class}, priority=1898)
public class MixinLayerBipedArmor {
    @Inject(method={"setModelSlotVisible"}, at={@At(value="HEAD")}, cancellable=true)
    protected void setModelSlotVisible(ModelBiped model, EntityEquipmentSlot slotIn, CallbackInfo ci) {
        NoRenderEvent event = new NoRenderEvent(0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) return;
        ci.cancel();

        switch (slotIn.ordinal()) {
            case 2: {
                model.bipedHead.showModel = false;
                model.bipedHeadwear.showModel = false;
            }
            case 3: {
                model.bipedBody.showModel = false;
                model.bipedRightArm.showModel = false;
                model.bipedLeftArm.showModel = false;
            }
            case 4: {
                model.bipedBody.showModel = false;
                model.bipedRightLeg.showModel = false;
                model.bipedLeftLeg.showModel = false;
            }
            case 5: {
                model.bipedRightLeg.showModel = false;
                model.bipedLeftLeg.showModel = false;
                return;
            }
        }
    }
}