package net.omniblock.skywars.util.effectlib.effect;

import net.omniblock.skywars.util.effectlib.Effect;
import net.omniblock.skywars.util.effectlib.EffectManager;
import net.omniblock.skywars.util.effectlib.EffectType;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;
import net.omniblock.skywars.util.effectlib.util.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Sound;

public class ExplodeEffect extends Effect {

    /**
     * Amount of spawned smoke-sparks
     */
    public int amount = 25;

    /**
     * Movement speed of smoke-sparks. Should be increases with force.
     */
    public float speed = .5f;

    public Sound sound = Sound.EXPLODE;

    public ExplodeEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onRun() {
        Location location = getLocation();
        location.getWorld().playSound(location, sound, 4.0F, (1.0F + (RandomUtils.random.nextFloat() - RandomUtils.random.nextFloat()) * 0.2F) * 0.7F);
        ParticleEffect.EXPLOSION_NORMAL.display(location, visibleRange, 0, 0, 0, speed, amount);
        ParticleEffect.EXPLOSION_HUGE.display(location, visibleRange, 0, 0, 0, 0, amount);
    }

}
