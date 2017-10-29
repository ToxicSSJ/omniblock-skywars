package net.omniblock.skywars.util.effectlib.effect;

import net.omniblock.skywars.util.effectlib.Effect;
import net.omniblock.skywars.util.effectlib.EffectManager;
import net.omniblock.skywars.util.effectlib.EffectType;
import net.omniblock.skywars.util.effectlib.util.ParticleEffect;
import net.omniblock.skywars.util.effectlib.util.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FlameEffect extends Effect {

	public ParticleEffect particle = ParticleEffect.FLAME;

	public FlameEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 1;
		iterations = 600;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRun() {
		Location location = getLocation();
		for (int i = 0; i < 10; i++) {
			Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d);
			v.setY(RandomUtils.random.nextFloat() * 1.8);
			location.add(v);
			particle.display(location, visibleRange);
			location.subtract(v);
		}
	}

}
