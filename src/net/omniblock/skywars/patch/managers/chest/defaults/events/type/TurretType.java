package net.omniblock.skywars.patch.managers.chest.defaults.events.type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import net.omniblock.network.library.utils.TextUtil;

public enum TurretType {
	
	LASER_TURRET(TextUtil.format("&c&lTorreta Laser"), "Laser", 0.5, Material.SPONGE, EntityType.GUARDIAN, Sound.ENDERDRAGON_WINGS),
	PORK_TURRET(TextUtil.format("&6&lTorreta Porcina"), "Porcina", 7.0, Material.HARD_CLAY, EntityType.PIG_ZOMBIE, Sound.PIG_DEATH),
	ICE_TURRET(TextUtil.format("&b&lTorreta Congeladora"), "Congeladora", 0.0, Material.NOTE_BLOCK, EntityType.SNOWMAN, Sound.FUSE),
	HEALTH_TURRET(TextUtil.format("&a&lTorreta Sanadora"), "Sanadora", 0.0, Material.JUKEBOX, EntityType.VILLAGER, Sound.VILLAGER_YES),
	
	;
	
	private String name;
	private String name_type;
	
	private double damage;
	private Material material;
	private EntityType entitytype;
	private Sound buildsound;
	
	TurretType(String name, String name_type, double damage, Material material, EntityType entitytype, Sound buildsound){
		
		this.name = name;
		this.name_type = name_type;
		
		this.setDamage(damage);
		this.material = material;
		this.entitytype = entitytype;
		this.buildsound = buildsound;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public EntityType getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(EntityType entitytype) {
		this.entitytype = entitytype;
	}

	public Sound getBuildsound() {
		return buildsound;
	}

	public void setBuildsound(Sound buildsound) {
		this.buildsound = buildsound;
	}

	public String getName_type() {
		return name_type;
	}

	public void setName_type(String name_type) {
		this.name_type = name_type;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	
}
