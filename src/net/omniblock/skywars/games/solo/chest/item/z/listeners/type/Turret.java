package net.omniblock.skywars.games.solo.chest.item.z.listeners.type;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Item;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import net.citizensnpcs.api.npc.NPC;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.type.Turret.TurretUtil.AwakeTurret;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.NumberUtil;
import omniblock.on.util.TextUtil;

public interface Turret {

	public Player[] inmunity = null;
	
	public NPC turret = null;
	public Block[] base = null;
	
	public void build(Player constructor, Block place);
	public void craft(Player constructor, Block place);
	public void setEyeIA(AwakeTurret at, int range, boolean onlyinmunity);
	public void shoot(AwakeTurret at, Entity toshoot);
	public void shoot(AwakeTurret at, AwakeTurret toshoot);
	public void destroy(AwakeTurret at, Player destructor);
	
	public static class TurretUtil {
		
		public static List<AwakeTurret> awake_turrets = new ArrayList<AwakeTurret>();
		
		public static class AwakeTurret {
			
			public Player owner;
			
			public List<Block> components = Lists.newArrayList();
			public List<String> extra_exclude = Lists.newArrayList();
			
			public boolean alive = false;
			
			public NPC turret = null;
			public boolean IA = false;
			
			public ArmorStand info_hud = null;
			public NPC damage_hud = null;
			
			public AwakeTurret(Player owner) {
				
				this.owner = owner;
				
			}
			
			public int getMaxHealth() {
				return TurretUtil.getMaxHealth(turret);
			}
			
			public int getHealth() {
				return TurretUtil.getHealth(turret);
			}
			
			public void setHealth(int health, int maxhealth) {
				
				if(health == maxhealth) {
					return;
				}
				
				if(!(health < 0) && !(health > maxhealth)) {
					
					int badhealth = maxhealth - health;
					String render = TextUtil.format("&c&lMUERTA");
					
					if(badhealth == 0) {
						
						render = TextUtil.format("&b&l");
						for(int i = 0; i < health; i++) {
							render += "▄";
						}
						
					} else {
						
						render = TextUtil.format("&b&l");
						for(int i = 0; i < health; i++) {
							render += "▄";
						}
						
						render += TextUtil.format("&8&l");
						
						for(int i = 0; i < badhealth; i++) {
							render += "▄";
						}
						
					}
					
					turret.setName(render);
					turret.getEntity().setCustomName(render);
					
				}
				
			}
			
			public void health() {
				if(turret != null) {
					
					int health = getHealth();
					int maxhealth = getMaxHealth();
					
					int addition = health + 1;
					
					if(addition <= maxhealth) {
						setHealth(addition, maxhealth);
					}
					
				}
			}
			
			public void damage(Entity enemy) {
				if(turret != null) {
					
					int health = getHealth();
					int maxhealth = getMaxHealth();
					
					int substract = health - 1;
					
					if((health - 1) <= maxhealth && (health - 1) >= 1) {
						
						setHealth(substract, maxhealth);
						
						switch(turret.getEntity().getType()) {
							case GUARDIAN: 
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.IRONGOLEM_HIT, 5, -10);
								Guardian g = (Guardian) turret.getEntity();
								g.damage(0.01);
								break;
							case VILLAGER:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.VILLAGER_HIT, 5, -5);
								Villager v = (Villager) turret.getEntity();
								v.damage(0.01);
								break;
							case PIG_ZOMBIE:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.ZOMBIE_PIG_HURT, 5, -5);
								PigZombie pz = (PigZombie) turret.getEntity();
								pz.damage(0.01);
								break;
							case SNOWMAN:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.WOLF_SHAKE, 5, -20);
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.SKELETON_HURT, 5, -20);
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.COW_HURT, 5, -20);
								Snowman sm = (Snowman) turret.getEntity();
								sm.damage(0.01);
								break;
							default: break;
						}
						
					} else {
						alive = false;
					}
					
				}
			}
			
			public void damage(AwakeTurret enemy) {
				if(turret != null) {
					
					int health = getHealth();
					int maxhealth = getMaxHealth();
					
					int substract = health - 1;
					
					if(substract <= maxhealth && substract >= 1) {
						
						setHealth(substract, maxhealth);
						
						switch(turret.getEntity().getType()) {
							case GUARDIAN: 
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.IRONGOLEM_HIT, 5, -10);
								Guardian g = (Guardian) turret.getEntity();
								g.damage(0.01);
								break;
							case VILLAGER:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.VILLAGER_HIT, 5, -5);
								Villager v = (Villager) turret.getEntity();
								v.damage(0.01);
								break;
							case PIG_ZOMBIE:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.ZOMBIE_PIG_HURT, 5, -5);
								PigZombie pz = (PigZombie) turret.getEntity();
								pz.damage(0.01);
								break;
							case SNOWMAN:
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.WOLF_SHAKE, 5, -20);
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.SKELETON_HURT, 5, -20);
								turret.getEntity().getLocation().getWorld().playSound(turret.getEntity().getLocation(), Sound.COW_HURT, 5, -20);
								Snowman sm = (Snowman) turret.getEntity();
								sm.damage(0.01);
								break;
							default: break;
						}
						
					} else {
						alive = false;
					}
					
				}
			}
			
			public void initialize() {
				awake_turrets.add(this);
			}
			
			public void finalize() {
				
				if(awake_turrets.contains(this)) {
					awake_turrets.remove(this);
				}
				
				for(Block b : components) {
					
					if(SoloPlayerCustomProtocols.PROTECTED_BLOCK_LIST.contains(b)) {
						SoloPlayerCustomProtocols.PROTECTED_BLOCK_LIST.remove(b);
					}
					
					if(b.getType() == Material.HOPPER) {
						b.getRelative(BlockFace.UP).setType(Material.AIR);
					}
					
					b.setType(Material.AIR);
					
				}
				
				turret.despawn();
				turret.destroy();
				
				info_hud.remove();
				damage_hud.despawn();
				damage_hud.destroy();
				
			}
			
		}
		
		public static class TurretBuilder extends BukkitRunnable {

			private boolean canceled = false;
			private boolean completed = false;
			
			private int round = 1;
			private int pitch = 1;
		    private	double percent = 0.00;
		    
		    TurretType tt = null;
		    Block b = null;
		    Location l = null;
			
			ArmorStand s1 = null;
			ArmorStand s2 = null;
			
		    public void setDefaults(TurretType turrettype, Block block) {
		    	
		    	b = block;
		    	tt = turrettype;
		    	
		    	l = block.getLocation();
				
				s1 = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX() + 0.5, l.getY() + 0.3, l.getZ() + 0.5), EntityType.ARMOR_STAND);
				s2 = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX() + 0.5, l.getY() - 0.2, l.getZ() + 0.5), EntityType.ARMOR_STAND);
		    	
			    s1.setCustomName("§aCreando torreta...");
				s1.setCustomNameVisible(true);
				s1.setVisible(false);
				s1.setCanPickupItems(false);
				s1.setGravity(false);
				s1.setBasePlate(false);
				
				s2.setCustomName("§e" + 0.00);
				s2.setCustomNameVisible(true);
				s2.setVisible(false);
				s2.setCanPickupItems(false);
				s2.setGravity(false);
				s2.setBasePlate(false);
				
				l.getWorld().playEffect(l, Effect.LAVA_POP, 6);
				l.getBlock().setType(tt.getMaterial());
				
		    }
		    
			@Override
			public void run(){
				
				Block b = l.getBlock();
				if(b.getType() == tt.getMaterial()
						&& s1 != null
						&& s2 != null
						&& s1.isDead() == false
						&& s2.isDead() == false){
					if(percent >= 100.00){
						cancel();
						s1.remove();
						s2.remove();
						
						b.setType(Material.AIR);
						
						b.getWorld().playSound(l, Sound.BAT_TAKEOFF, 5, 1);
						b.getWorld().playSound(l, tt.getBuildsound(), 5, 1);
						
						b.getWorld().playEffect(l, Effect.LAVA_POP, 2);
						b.getWorld().strikeLightningEffect(l);
						
						this.setCompleted(true);
						this.setCanceled(false);
						cancel();
						return;
					} else {
						percent = percent + 0.80;
						s2.setCustomName(TextUtil.format("§e" + new DecimalFormat("#.###").format(percent) + "%"));
						if(round == 5){
							round = 1;
							l.getWorld().playSound(l, Sound.CLICK, 5, pitch);
							pitch++;
						} else {
							round++;
						}
					}
				} else {
					cancel();
					List<Entity> en = new ArrayList<Entity>();
					if(s1 != null){
						en.add(s1);
					}
					if(s2 != null){
						en.add(s2);
					}
					List<Block> bl = new ArrayList<Block>();
					bl.add(l.getBlock());
					
					s1.remove();
					s2.remove();
					
					this.setCompleted(false);
					this.setCanceled(true);
					cancel();
					return;
				}
				
			}

			public boolean isCompleted() {
				return completed;
			}

			public void setCompleted(boolean completed) {
				this.completed = completed;
			}

			public boolean isCanceled() {
				return canceled;
			}

			public void setCanceled(boolean canceled) {
				this.canceled = canceled;
			}
			
		}
		
		public static TurretBuilder startBuildAnimation(TurretType tt, Block block) {
			
			TurretBuilder tb = new TurretBuilder();
			tb.setDefaults(tt, block);
			tb.runTaskTimer(Skywars.getInstance(), 1L, 1L);
			
			return tb;
			
		}
		
		public static int getMaxHealth(NPC npc) {
			
			if(npc.getName() != null) {
				
				int ocurrences = StringUtils.countMatches(npc.getName(), "▄");
				return ocurrences;
				
			}
			
			return 1;
		}
		
		public static int getHealth(NPC npc) {
			
			if(npc.getName() != null) {
				
				String rework = npc.getName();
				
				if(rework.contains(TextUtil.format("&8&l"))) {
					rework = rework.split(TextUtil.format("&8&l"))[0];
				}
				if(rework.contains(TextUtil.format("&b&l"))) {
					
					int ocurrences = StringUtils.countMatches(rework, "▄");
					return ocurrences;
					
				}
				
			}
			
			return 1;
		}
		
		public static void explodeTower(TurretType tt, Location l){
			
			Sound explosion_sound = null;
			Material explosion_mat = Material.AIR;
			short explosion_data = 0;
			
			l.getWorld().playEffect(l, Effect.EXPLOSION_HUGE, 4);
			l.getWorld().playSound(l, Sound.EXPLODE, 10, 4);
			
			switch(tt) {
			case LASER_TURRET:
				
				explosion_sound = Sound.HORSE_SADDLE;
				explosion_mat = Material.INK_SACK;
				explosion_data = 1;
				break;
				
			case PORK_TURRET:
				
				explosion_mat = Material.PORK;
				break;
				
			case ICE_TURRET:
				
				explosion_mat = Material.ICE;
				break;
				
			case HEALTH_TURRET:
				
				explosion_mat = Material.GOLDEN_CARROT;
				break;
				
			}
			
			if(explosion_sound != null) {
				l.getWorld().playSound(l, explosion_sound, 5, 1);
			}
			
			for(int i = 0; i < 8; i++) {
				
				ItemStack item = new ItemBuilder(explosion_mat)
												 .amount(1)
												 .durability(explosion_data)
												 .name("" + NumberUtil.getRandomInt(-100, 100))
												 .build();
				
				final Item itemdrop = l.getWorld().dropItemNaturally(l, item);
				itemdrop.setVelocity(new Vector(NumberUtil.getRandomDouble(0.1, 0.2), NumberUtil.getRandomDouble(0.1, 0.2), NumberUtil.getRandomDouble(0.1, 0.2)));
				itemdrop.setPickupDelay(1000000);
				
				new BukkitRunnable(){
					 @Override
					 public void run(){
						 if(itemdrop.isDead() == false){
							 itemdrop.remove();
						 }
						 cancel();
					 }
				}.runTaskLater(Skywars.getInstance(), 6*20);
				
			}
			
		}
		
	}
	
}
