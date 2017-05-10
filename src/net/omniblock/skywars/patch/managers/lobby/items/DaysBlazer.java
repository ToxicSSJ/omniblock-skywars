package net.omniblock.skywars.patch.managers.lobby.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.ChestManager;
import net.omniblock.skywars.patch.managers.chest.item.SkywarsItem;
import net.omniblock.skywars.patch.managers.chest.item.object.FillChest;
import net.omniblock.skywars.patch.managers.lobby.object.PowerItem;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.TextUtil;
import net.omniblock.skywars.util.block.SpawnBlock;

public class DaysBlazer implements PowerItem, Listener {

	public static enum DayType {
		
		PEACE_DAY("De la Paz", 
				new String[] { "&8&m-&r&7 Atacar necesario no será, El amor ",
				  			   "&7sobre todo prevalecerá!"} ),
		
		ZEUS_ATTACK_DAY("Del Ataque de Zeus", 
				new String[] { "&8&m-&r&7 La furia de zeus y sus rayos caerán ",
		   					   "&7sobre todos ustedes!"} ),
		
		RANDOM_KILL("De la muerte Aleatoria", 
				new String[] { "&8&m-&r&7 El tiempo avanza y los minutos se ",
		   					   "&7agotan, Alguien al azar morirá!"} ),
		
		NOT_BOW_DAY("Dia sin arcos", 
				new String[] { "&8&m-&r&7 Los arcos no serán necesarios, La ",
		   					   "&7distancia es para cobardes!"} ),
		
		TROLL_CHESTS("De los cofres Trolls", 
				new String[] { "&8&m-&r&7 ¡Los cofres no funcionan! ¿Que está ",
		   					   "&7sucediendo?"} ),
		
		INVISIBLE_PLAYERS("De los jugadores Invisibles", 
				new String[] { "&8&m-&r&7 ¿Y los jugadores? ¿En donde están ",
							   "&7los jugadores?!"} ),
		
		SKY_CHEST("¡Del cofre Sorpresa!", 
				new String[] { "&8&m-&r&7 Del mundo de los cofres magicos, ",
							   "&7Mira en el cielo!"} ),
		/* WITCHER("Del Mago", 
				new String[] { "&8&m-&r&7 No uses armas ni nada extraño, Con el ",
		   					   "&7simple click, Serás el mejor mago de todos!"} ), */
		
		BOW_TO_MACHINE_GUNS("¡De arcos a ametralladoras!", 
				new String[] { "&8&m-&r&7 ¿Quien necesita arcos? ¡Cuando pueden ",
		   					   "&7ser Ametralladoras!"} ),
		
		;
		
		private String name = "Unknow";
		private String[] desc = new String[] { "Unknow" };
		
		DayType(String name, String...desc){
			this.name = name;
			this.desc = desc;
		}
		
		public String[] getDescription() {
			return desc;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public static List<Listener> CURRENT_LISTENERS = Lists.newArrayList();
	
	public static BukkitTask MAIN_TASK = null;
	public static BukkitTask CURRENT_TASK = null;
	
	public static DayType CURRENT_DAY = null;
	
	public static int CHANGE_TIME = 0;
	public static int CHANGE_TIME_COMPLETED = 24000;
	
	public static int CHANGE_TIME_OFFSET = 0;
	public static int MAX_CHANGE_TIME_OFFSET = 4;
	
	private FillChest fillChest;
	
	public static List<DayType> DAYS = new ArrayList<DayType>() {

		private static final long serialVersionUID = 6475319426294174885L;

		{
			
			for(DayType day : DayType.values()) {
				add(day);
			}
			
		}
		
	};
	
	@SuppressWarnings("deprecation")
	public void changeDay() {
		
		if(DAYS.size() <= 0) { if(CURRENT_TASK != null) { CURRENT_TASK.cancel(); return; } return; }
		if(CHANGE_TIME_OFFSET >= MAX_CHANGE_TIME_OFFSET) return;
		
		if(CURRENT_TASK != null) CURRENT_TASK.cancel();
		
		Collections.shuffle(DAYS); CURRENT_DAY = DAYS.get(0); DAYS.remove(CURRENT_DAY);
		
		Bukkit.broadcastMessage(TextUtil.format(""));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&2&l&m=====================================")));
		Bukkit.broadcastMessage(TextUtil.format(""));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&8&l¡CAMBIO DE DIA!")));
		Bukkit.broadcastMessage(TextUtil.format(""));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&6&lNUEVO DIA: &7" + CURRENT_DAY.getName())));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&6&l&lDESCRIPCIÓN&6&l: &7")));
		
		for(String k : CURRENT_DAY.getDescription()) {
			Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&7" + k)));
		}
		
		Bukkit.broadcastMessage(TextUtil.format(""));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage(TextUtil.format("&2&l&m=====================================")));
		
		switch(CURRENT_DAY) {
		
			case BOW_TO_MACHINE_GUNS:
				
				CURRENT_TASK = new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player p : getInGamePlayers()) {
							
							Map<Integer, ? extends ItemStack> bows = p.getInventory().all(Material.BOW);
							if(bows == null) continue;
							if(bows.size() <= 0) continue;
							
							for(Map.Entry<Integer, ? extends ItemStack> k : bows.entrySet()) {
								
								p.getInventory().setItem(k.getKey(),
										new ItemBuilder(Material.DIAMOND_BARDING)
										.amount(1).enchant(Enchantment.ARROW_INFINITE, 1)
										.name(TextUtil.format("&c&lSUPER AMETRALLADORA"))
										.lore(TextUtil.format(""))
										.lore(TextUtil.format(" &7'Ametralladora de flechas"))
										.lore(TextUtil.format("  &7infinitas!' - Steve"))
										.build());
								
							}
							
						}
						
					}
					
				}.runTaskTimer(Skywars.getInstance(), 0L, 35L);
				
				break;
				
			case INVISIBLE_PLAYERS:
				
				CURRENT_TASK = new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player p : getInGamePlayers()) {
							
							if(!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
								
								p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 2, 1));
								continue;
								
							}
							
						}
						
					}
					
				}.runTaskTimer(Skywars.getInstance(), 10L, 10L);
				
				break;
				
			case NOT_BOW_DAY:
				
				Listener listener_001 = new Listener() {
					
					@EventHandler(priority = EventPriority.MONITOR)
					public void onInteract(PlayerInteractEvent e) {
						
						if(Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
						
						if(getInGamePlayers().contains(e.getPlayer())) {
							
							if(e.getItem() == null) return;
							
							if(e.getItem().getType() == Material.BOW) 
								e.getPlayer().setItemInHand(null);
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_BREAK, 10, -10);
							
						}
						
					}
					
				};
				
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener_001, Skywars.getInstance());
				CURRENT_LISTENERS.add(listener_001);
				
				break;
				
			case PEACE_DAY:
				
				Listener listener_002 = new Listener() {
					
					@EventHandler(priority = EventPriority.MONITOR)
					public void onDamage(EntityDamageByEntityEvent e) {
					
						if(Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
						
						if(e.getEntity() instanceof Player) {
							
							Player player = (Player) e.getEntity();
							
							if(getInGamePlayers().contains(player)) {
								
								e.setCancelled(true);
								
								player.getWorld().playSound(player.getLocation(), Sound.CAT_MEOW, 10, 1);
								player.playEffect(EntityEffect.VILLAGER_HEART);
								
							}
							
						}
						
					}
					
				};
				
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener_002, Skywars.getInstance());
				CURRENT_LISTENERS.add(listener_002);
				
				break;
				
			case RANDOM_KILL:
				
				CURRENT_TASK = new BukkitRunnable() {
					
					int round = 1;
					
					@Override
					public void run() {
						
						if(Skywars.getGameState() == SkywarsGameState.IN_GAME) {
							
							round++;
							
						} else { return; }
						
						if(round == 55) {
							
							List<Player> players = getInGamePlayers();
							
							if(players.size() <= 0) return;
							
							Collections.shuffle(players);
							Player player = players.get(0);
							
							if(		Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO ||
									Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO ||
									Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {
								
								SoloPlayerBattleListener.killPlayer(player);
								
							} else { TeamPlayerBattleListener.killPlayer(player); }
							
							player.getWorld().strikeLightningEffect(player.getLocation());
							Bukkit.broadcastMessage(TextUtil.format("&8&lLA MUERTE &7a tocado la puerta de &c" + player.getName()));
							
							for(Player p : Bukkit.getOnlinePlayers()) {
								
								p.playSound(p.getLocation(), Sound.BURP, 10, -10);
								p.playSound(p.getLocation(), Sound.FALL_BIG, 10, -10);
								p.playSound(p.getLocation(), Sound.EXPLODE, 10, -10);
								
							}
							
							return;
							
						}
						
						if((round >= 50 && round <= 54)) {
							
							for(Player p : Bukkit.getOnlinePlayers()) {
							
								p.playSound(p.getLocation(), Sound.NOTE_PIANO, 10, -10);
								
							}
							
							Bukkit.broadcastMessage(TextUtil.format("&4En &8" + (55 - round) + "&4 segundos alguien morirá!"));
							
							return;
						}
						
						if(round == 5) {
							
							for(Player p : Bukkit.getOnlinePlayers()) {
								
								p.playSound(p.getLocation(), Sound.NOTE_PIANO, 10, -10);
								
							}
							
							Bukkit.broadcastMessage(("&4En &8" + (55 - round) + "&4 segundos alguien morirá!"));
							
							return;
						}
						
					}
					
				}.runTaskTimer(Skywars.getInstance(), 20L, 20L);
				
				break;
				
			case SKY_CHEST:
				
				Listener listener_003 = new Listener() {
					
					@EventHandler(priority = EventPriority.MONITOR)
					public void onDamage(EntityChangeBlockEvent e) {
					
						if(Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
						
						if(e.getEntity() instanceof FallingBlock) {
							
							FallingBlock fb = (FallingBlock) e.getEntity();
							
							if(fb.hasMetadata("SKYCHEST")) {
								
								fb.getWorld().playSound(fb.getLocation(), Sound.ZOMBIE_WOODBREAK, 30, 10);
								fb.remove();
								
								Block b = e.getBlock();
								b.setType(Material.CHEST);
								
								fillChest = new FillChest(SkywarsItem.getOnlyItemLegendady(), 10).startFilledOneChest(b.getLocation());
								
							}
							
						}
						
					}
					
				};
				
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener_003, Skywars.getInstance());
				CURRENT_LISTENERS.add(listener_003);
				
				for(Player p : getInGamePlayers()) {
					
					Location loc = p.getLocation().clone().add(0, 50, 0);
					FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 0);
					
					fb.setDropItem(false);
					fb.setTicksLived(20 * 8);
					fb.setMetadata("SKYCHEST", new FixedMetadataValue(Skywars.getInstance(), "dummy"));
					fb.setVelocity(new Vector(0, -2, 0).multiply(0.6));
					continue;
					
				}
				
				break;
				
			case TROLL_CHESTS:
				
				Listener listener_004 = new Listener() {
					
					@EventHandler(priority = EventPriority.MONITOR)
					public void onClick(PlayerInteractEvent e) {
					
						if(Skywars.getGameState() != SkywarsGameState.IN_GAME) return;
						if(e.getClickedBlock() == null) return;
						
						if(getInGamePlayers().contains(e.getPlayer())) {
							
							if(e.getClickedBlock().getType() == Material.AIR) return;
							
							if(		e.getClickedBlock().getType() == Material.CHEST ||
									e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
								
								@SuppressWarnings("serial")
								List<Block> FACES = new ArrayList<Block>() {{
									for(BlockFace bf : BlockFace.values()) {
										if(bf != BlockFace.SELF) {
											add(e.getClickedBlock().getRelative(bf));
										}
									}
								}};
								
								Collections.shuffle(FACES);
								
								for(Block b : FACES) {
									
									if(b.getType() == Material.AIR && !CustomProtocolManager.PROTECTED_BLOCK_LIST.contains(b)) {
										
										Material m = e.getClickedBlock().getType();
										byte data = e.getClickedBlock().getData();
										
										Chest chest = (Chest) e.getClickedBlock().getState();
										ItemStack[] contents = chest.getBlockInventory().getContents();
										
										chest.getInventory().clear();
										e.getClickedBlock().setType(Material.AIR);
										e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.LAVA_POP, 100);
										
										b.setType(m);
										b.setData(data);
										
										Chest cache = (Chest) b.getState();
										cache.getBlockInventory().setContents(contents);
										
										break;
										
									}
									
								}
								
							}
							
						}
						
					}
					
				};
				
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener_004, Skywars.getInstance());
				CURRENT_LISTENERS.add(listener_004);
				
				break;
				
			case ZEUS_ATTACK_DAY:
				
				CURRENT_TASK = new BukkitRunnable() {
					
					int round = 1;
					
					@SuppressWarnings("serial")
					@Override
					public void run() {
						
						if(Skywars.getGameState() == SkywarsGameState.IN_GAME) {
							
							round++;
							
						} else { return; }
						
						if(round == 55) {
							
							cancel();
							return;
							
						}
						
						if(NumberUtil.getRandomInt(1, 7) == 7) {
							
							List<Player> players = getInGamePlayers();
							
							List<Location> random_locs = new ArrayList<Location>() {{
									
									for(Player p : players) {
										add(p.getLocation());
									}
									
									List<Location> chestLocations = Lists.newArrayList();
									chestLocations.addAll(ChestManager.trappedchest);
									
									addAll(chestLocations);
									
							}};
							
							Collections.shuffle(random_locs);
							
							Location loc_w = MapManager.lobbyschematic.getLocation();
							Location loc_r = random_locs.get(0);
							
							loc_w.getWorld().strikeLightning(loc_r);
							loc_w.getWorld().createExplosion(loc_r, 2.0F);
							List<Block> cube = SpawnBlock.circle(loc_r, 3, 3, true, true, -2);
							
							for(Block b : cube ){
								SpawnBlock.bounceBlock(b, (float) 0.8);
							}
							
						}
						
					}
					
				}.runTaskTimer(Skywars.getInstance(), 20L, 20L);
				
				break;
				
			default:
				break;
		
		}
		
	}
	
	public List<Player> getInGamePlayers(){
		
		if(		Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO ||
				Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {
			
			return SoloPlayerManager.getPlayersInGameListAsCopy();
			
		}
		
		return TeamPlayerManager.getPlayersInGameListAsCopy();
		
	}
	
	public void resetCurrentDay() {
		
		CHANGE_TIME = 0;
		MapManager.CURRENT_MAP.setTime(CHANGE_TIME);
		
		if(CURRENT_LISTENERS.size() > 0) {
			
			for(Listener listener : CURRENT_LISTENERS) {
				HandlerList.unregisterAll(listener);
			}
			
		}
		
		CURRENT_LISTENERS.clear();
		
		if(CURRENT_TASK != null) CURRENT_TASK.cancel(); CURRENT_TASK = null; return;
		
	}
	
	@Override
	public void setup() {
		
		MAIN_TASK = new BukkitRunnable() {
			
			boolean firstday = false;
			
			@Override
			public void run() {
				
				if(Skywars.getGameState() == SkywarsGameState.IN_PRE_GAME) {
					return;
				}
				
				if(Skywars.getGameState() != SkywarsGameState.IN_GAME ||
						CHANGE_TIME_OFFSET >= MAX_CHANGE_TIME_OFFSET) {
					
					cancel();
					stop();
					return;
					
				}
				
				if(!firstday) changeDay(); firstday = true;
				
				CHANGE_TIME += 20;
				
				if(CHANGE_TIME >= CHANGE_TIME_COMPLETED) {
					
					CHANGE_TIME = 0;
					
					if(CHANGE_TIME_OFFSET >= MAX_CHANGE_TIME_OFFSET) {
						
						cancel();
						stop();
						return;
						
					}
					
					CHANGE_TIME_OFFSET++;
					
					resetCurrentDay();
					changeDay();
					
				}
				
				MapManager.CURRENT_MAP.setTime(CHANGE_TIME);
				MapManager.CURRENT_MAP.setStorm(false);
				MapManager.CURRENT_MAP.setThundering(false);
				return;
				
			}
			
		}.runTaskTimer(Skywars.getInstance(), 1l, 1l);
		
	}

	@Override
	public void stop() {
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 5, 2);
		}
		
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(TextUtil.format("&4&lEL DIOS DE LOS DIAS &7a abandonado la partida."));
		Bukkit.broadcastMessage("");
		
		MAIN_TASK = null;
		resetCurrentDay();
		
	}

}
