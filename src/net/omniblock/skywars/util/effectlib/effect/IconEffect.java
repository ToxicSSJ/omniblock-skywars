package net.omniblock.skywars.util.effectlib.effect;

import net.omniblock.skywars.util.effectlib.Effect;
import net.omniblock.skywars.util.effectlib.EffectManager;
import net.omniblock.skywars.util.effectlib.EffectType;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;
import org.bukkit.Location;

public class IconEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.VILLAGER_ANGRY;

	public int yOffset = 2;

	public IconEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 4;
		iterations = 25;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		location.add(0, yOffset, 0);
		display(particle, location);
	}
}
