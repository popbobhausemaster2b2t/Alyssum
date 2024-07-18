package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.DamageUtil;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.MathUtil;
import me.alpha432.oyvey.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Killaura extends Module {

    public Killaura() {
        super("Killaura", "auralol", Category.COMBAT, true, false, false);
    }
	
    public static Entity target;
    private final Timer timer = new Timer();
    public Setting<Integer> range = this.register(new Setting<Integer>("Range", 6, 0, 7 ));
    //  public Setting<Integer> wasteAmount = this.register(new Setting<Integer>("UseAmount", 4, 1, 5));
    public Setting<Boolean> delay = this.register(new Setting<Boolean>("HitDelay",  true));
    public Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", true));
    //  private final Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", true));
    public Setting<Boolean> onlySharp = this.register(new Setting<Boolean>("SwordOnly",  true));
    public Setting<Integer> raytrace = this.register(new Setting<Integer>("Raytrace", 6, 1, 7 ));
    //Integer
    public Setting<Boolean> players = this.register(new Setting<Boolean>("Players",  true));
    public Setting<Boolean> mobs = this.register(new Setting<Boolean>("Mobs", false));
    public Setting<Boolean> animals = this.register(new Setting<Boolean>("Animals",  false));
    public Setting<Boolean> vehicles = this.register(new Setting<Boolean>("Entities", false));
    public Setting<Boolean> projectiles = this.register(new Setting<Boolean>("Projectiles", false));
    public Setting<Boolean> packet = this.register(new Setting<Boolean>("Packet", false));

    public void onTick() {
        if (!rotate.getValue())
            doKillaura();
    }

    public void onUpdate() {
        doKillaura();
    }

    private void doKillaura() {
        if (onlySharp.getValue() && !EntityUtil.holdingWeapon(mc.player)) {
            target = null;
            return;
        }
        int wait = !delay.getValue() ? 0 : (int) (DamageUtil.getCooldownByWeapon(mc.player));
        if (!timer.passedMs(wait))
            return;
        target = getTarget();
        if (target == null)
            return;
        EntityUtil.attackEntity(target, packet.getValue(), true);
        timer.reset();
    }

    private Entity getTarget() {
        Entity target = null;
        double distance = range.getValue().floatValue();
        double maxHealth = 36.0D;
        for (Entity entity : mc.world.playerEntities) {
            if (((!players.getValue() || !(entity instanceof EntityPlayer)) && (!animals.getValue() || !EntityUtil.isPassive(entity)) && (!mobs.getValue() || !EntityUtil.isMobAggressive(entity)) && (!vehicles.getValue() || !EntityUtil.isVehicle(entity)) && (!projectiles.getValue() || !EntityUtil.isProjectile(entity))) || (entity instanceof net.minecraft.entity.EntityLivingBase &&
                    EntityUtil.isntValid(entity, distance)))
                continue;
            if (!mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && mc.player.getDistanceSq(entity) > MathUtil.square(raytrace.getValue().floatValue()))
                continue;
            if (target == null) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
                continue;
            }
            if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer) entity, 18)) {
                target = entity;
                break;
            }
            if (mc.player.getDistanceSq(entity) < distance) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
            }
            if (EntityUtil.getHealth(entity) < maxHealth) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
            }
        }
        return target;
    }

    public String getDisplayInfo() {
        if (target instanceof EntityPlayer)
            return target.getName();
        return null;
    }


}