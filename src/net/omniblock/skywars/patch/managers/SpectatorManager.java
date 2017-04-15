package net.omniblock.skywars.patch.managers;

import java.util.List;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.VanishUtil;
import net.omniblock.skywars.util.inventory.InventoryBuilder;
import net.omniblock.skywars.util.inventory.InventoryBuilder.Action;
import net.omniblock.skywars.util.inventory.InventoryBuilder.RowsIntegers;
import omniblock.on.addons.queue.VirtuaLQueue;
import omniblock.on.network.NetworkManager;
import omniblock.on.util.TextUtil;

public class SpectatorManager {

	public static List<Player> playersSpectators = Lists.newArrayList();
	
	public boolean playerIsSpectator(Player p) {
		return playersSpectators.contains(p);
	}
	
	public List<Player> getSpectators() {
		return playersSpectators;
	}
	
	public static void addPlayerToSpectator(Player p) {
		
		playersSpectators.add(p);
		
		PlayerInventory pi = p.getInventory();
		pi.clear();
		pi.setArmorContents(null);
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
		p.setExp(0);
		p.setHealth(20D);
		p.setFoodLevel(20);
		p.resetMaxHealth();
		
		p.spigot().setCollidesWithEntities(false);
		p.setCanPickupItems(false);
		p.setAllowFlight(true);
		p.setFlying(true);
		
		VanishUtil.makeInvisible(p);
		
		for(SpectatorItem si : SpectatorItem.values()) {
			p.getInventory().setItem(si.getSlot(), si.getItem().build());
		}
		
	}
	
	public enum SpectatorItem {
		
		PLAYER_SELECTOR(new ItemBuilder(Material.COMPASS).amount(1)
											.name(TextUtil.format("&a&lSelector de Jugadores"))
											.lore("")
											.lore(TextUtil.format("&9&l- &7Selecciona el jugador al cual"))
											.lore(TextUtil.format("&7te deseas teletransportar."))
											.lore(""), 0,
											
					    new ItemExecutor() {

							@Override
							public void execute(Player player) {
				
								if(   Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO 
										   || Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
										   || Skywars.currentMatchType == SkywarsType.SW_Z_SOLO){
									
									if(SoloPlayerManager.getPlayersInGameAmount() >= 1) {
										
										int size = getInventorySize();
										
										InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&8&lSeleccionar Jugador"), size * 9, true);
										List<Integer> pos_arr = Lists.newArrayList();
										
										if(size == 4) {
											
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_2.getIntegers()));
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_3.getIntegers()));
											
										} else if(size == 3) {
											
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_2.getIntegers()));
											
										} else {
											
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_2.getIntegers()));
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_3.getIntegers()));
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_4.getIntegers()));
											pos_arr.addAll(Arrays.asList(RowsIntegers.NON_LATERAL_ROW_5.getIntegers()));
											
										}
										
										int round = 0;
										int pos_x = pos_arr.get(round);
										
										for(Player p : SoloPlayerManager.getPlayersInGameList()) {
											
											round++;
											
											ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
							                SkullMeta sk = (SkullMeta) item.getItemMeta();
							                
							                sk.setDisplayName(TextUtil.format("&f" + p.getName() + " &c&l" + p.getHealth() + "❤"));
							                sk.setOwner(p.getName());
							                
							                item.setItemMeta(sk);
							                
											ib.addItem(item, pos_x, new Action() {
												
												@Override
												public void click(ClickType click, Player player) {
													
													if(p.isOnline()) {
														if(SoloPlayerManager.getPlayersInGameList().contains(p)) {
															
															player.sendMessage(TextUtil.format("&aTeletransportado a: &f" + p.getName()));
															player.teleport(p);
															
															player.closeInventory();
															return;
															
														}
													}
													
													player.playSound(player.getLocation(), Sound.NOTE_BASS, 2, -5);
													player.sendMessage(TextUtil.format("&cEl jugador ya murió."));
													return;
													
												}
												
											});
											
											pos_x = pos_arr.get(round);
											
											player.playSound(player.getLocation(), Sound.CLICK, 2, -5);
											ib.open(player);
											
										}
											
									} else {
										
										player.sendMessage(TextUtil.format("&cNo hay jugadores para espectear."));
										return;
										
									}
									
								}
				
							}
							
							public int getInventorySize() {
								
								if(   Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO 
										   || Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
										   || Skywars.currentMatchType == SkywarsType.SW_Z_SOLO){
									
									if(SoloPlayerManager.getPlayersInGameAmount() > 6) {
										return 4;
									} else if(SoloPlayerManager.getPlayersInGameAmount() <= 6) {
										return 3;
									}
									
								}
								
								return 6;
								
							}
			
						}),
		
		VOTE_PLAYER(new ItemBuilder(Material.NAME_TAG).amount(1)
										.name(TextUtil.format("&e&lVotar Ganador"))
										.lore("")
										.lore(TextUtil.format("&9&l- &7Vota por el jugador que crees"))
										.lore(TextUtil.format("&7que ganará la partida, si aciertas, ganarás"))
										.lore(TextUtil.format("&7coins."))
										.lore(""), 3,
										
					new ItemExecutor() {

						@Override
						public void execute(Player player) {

							player.playSound(player.getLocation(), Sound.NOTE_BASS, 2, -5);
							player.sendMessage(TextUtil.format("&6Proximamente..."));
							return;

						}

					}),
 
		SETTINGS(new ItemBuilder(Material.REDSTONE_COMPARATOR).amount(1)
									 .name(TextUtil.format("&8&lConfiguración"))
									 .lore("")
									 .lore(TextUtil.format("&9&l- &7Elige las opciones de espectador"))
									 .lore(TextUtil.format("&7mas convenientes para ti."))
									 .lore(""), 4,
									 
				 new ItemExecutor() {

						@Override
						public void execute(Player player) {

							InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&8&lConfiguraciones"), 3 * 9, true);

							boolean nightvision = false;
							
							ItemBuilder VEL_NORMAL = new ItemBuilder(Material.CHAINMAIL_BOOTS, 1).name(TextUtil.format("&a&lVelocidad Normal"));
							ItemBuilder VEL_I = new ItemBuilder(Material.IRON_BOOTS, 1).name(TextUtil.format("&a&lVelocidad I"));
							ItemBuilder VEL_II = new ItemBuilder(Material.GOLD_BOOTS, 1).name(TextUtil.format("&a&lVelocidad II"));
							ItemBuilder VEL_III = new ItemBuilder(Material.DIAMOND_BOOTS, 1).name(TextUtil.format("&a&lVelocidad III"));
							
							ItemBuilder RESET = new ItemBuilder(Material.MILK_BUCKET, 1).name(TextUtil.format("&c&lResetear Efectos"));
							
							ItemBuilder NOCTURNE_VISION = new ItemBuilder(Material.EYE_OF_ENDER, 1).name(TextUtil.format("&c&lDesactivar Visión Nocturna"));
							ItemBuilder NORMAL_VISION = new ItemBuilder(Material.ENDER_PEARL, 1).name(TextUtil.format("&9&lActivar Visión Nocturna"));
							
							for(PotionEffect pe : player.getActivePotionEffects()) {
								
								if(pe.getType() == PotionEffectType.NIGHT_VISION) {
									NOCTURNE_VISION.enchant(Enchantment.DURABILITY, 1);
									nightvision = true;
									continue;
								}
								
								if(pe.getType() == PotionEffectType.SPEED) {
									
									int amplifier = pe.getAmplifier();
									
									switch(amplifier) {
										case 1:
											VEL_I.enchant(Enchantment.DURABILITY, 1);
											break;
										case 2:
											VEL_II.enchant(Enchantment.DURABILITY, 1);
											break;
										case 3:
											VEL_III.enchant(Enchantment.DURABILITY, 1);
											break;
										default:
											VEL_NORMAL.enchant(Enchantment.DURABILITY, 1);
											break;
									}
									
									continue;
								}

							}
							
							ib.addItem(VEL_NORMAL.hideAtributes().build(), 10, new Action() {

								@Override
								public void click(ClickType click, Player player) {
									
									player.playSound(player.getLocation(), Sound.CLICK, 2, 15);
									player.removePotionEffect(PotionEffectType.SPEED);
									
									SpectatorItem.SETTINGS.executor.execute(player);
									
								}
								
							});
							
							ib.addItem(VEL_I.hideAtributes().build(), 11, new Action() {

								@Override
								public void click(ClickType click, Player player) {
									
									player.playSound(player.getLocation(), Sound.CLICK, 2, 15);
									
									player.removePotionEffect(PotionEffectType.SPEED);
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false), false);
									
									SpectatorItem.SETTINGS.executor.execute(player);
									
								}
								
							});
							
							ib.addItem(VEL_II.hideAtributes().build(), 12, new Action() {

								@Override
								public void click(ClickType click, Player player) {
									
									player.playSound(player.getLocation(), Sound.CLICK, 2, 15);
									
									player.removePotionEffect(PotionEffectType.SPEED);
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false), false);
									
									SpectatorItem.SETTINGS.executor.execute(player);
									
								}
								
							});
							
							ib.addItem(VEL_III.hideAtributes().build(), 13, new Action() {

								@Override
								public void click(ClickType click, Player player) {
									
									player.playSound(player.getLocation(), Sound.CLICK, 2, 15);
									
									player.removePotionEffect(PotionEffectType.SPEED);
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false), false);
									
									SpectatorItem.SETTINGS.executor.execute(player);
									
								}
								
							});
							
							ib.addItem(RESET.hideAtributes().build(), 15, new Action() {

								@Override
								public void click(ClickType click, Player player) {
									
									player.playSound(player.getLocation(), Sound.DRINK, 2, -5);
									
									for(PotionEffect pe : player.getActivePotionEffects()) {
										player.removePotionEffect(pe.getType());
									}
									
									SpectatorItem.SETTINGS.executor.execute(player);
									
								}
								
							});
							
							if(nightvision) {
								
								ib.addItem(NOCTURNE_VISION.hideAtributes().build(), 16, new Action() {

									@Override
									public void click(ClickType click, Player player) {
										
										player.playSound(player.getLocation(), Sound.CLICK, 2, -5);
										player.removePotionEffect(PotionEffectType.NIGHT_VISION);
										
										SpectatorItem.SETTINGS.executor.execute(player);
										
									}
									
								});
								
							} else {
								
								ib.addItem(NORMAL_VISION.hideAtributes().build(), 16, new Action() {

									@Override
									public void click(ClickType click, Player player) {
										
										player.playSound(player.getLocation(), Sound.CLICK, 2, -5);
										
										player.removePotionEffect(PotionEffectType.NIGHT_VISION);
										player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false), false);
										
										SpectatorItem.SETTINGS.executor.execute(player);
										
									}
									
								});
								
							}
							
							ib.open(player);
							
						}

				}),		
		
		PLAY_AGAIN(new ItemBuilder(Material.PAPER).amount(1)
									   .name(TextUtil.format("&b&lVolver a Jugar"))
									   .lore("")
									   .lore(TextUtil.format("&9&l- &7Te envia a otra partida de skywars"))
									   .lore(TextUtil.format("&7con las mismas especificaciones de la"))
									   .lore(TextUtil.format("&7actual."))
									   .lore(""), 6,
				
				   new ItemExecutor() {

					   @Override
					   public void execute(Player player) {

						   // TODO
						   
						   player.playSound(player.getLocation(), Sound.NOTE_BASS, 2, -5);
						   player.sendMessage(TextUtil.format("&bEnviandote a otra partida..."));
						   VirtuaLQueue.startGame(player, NetworkManager.gametype);
						   return;

					   }

				   }),		
		
		EXIT_DOOR(new ItemBuilder(Material.ACACIA_DOOR).amount(1)
									   .name(TextUtil.format("&c&lSalir"))
									   .lore("")
									   .lore(TextUtil.format("&9&l- &7Te devolverá al lobby de la"))
									   .lore(TextUtil.format("&7modalidad de Skywars."))
									   .lore(""), 7,

				  new ItemExecutor() {

					  @Override
					  public void execute(Player player) {

						  // TODO
						  
						  player.playSound(player.getLocation(), Sound.NOTE_BASS, 2, -5);
						  player.sendMessage(TextUtil.format("&9Volviendo al lobby."));
						  return;

					  }

				  }),		
		;
		
		private ItemBuilder item;
		private int slot = 0;
		
		private ItemExecutor executor;
		
		SpectatorItem(ItemBuilder item, int slot, ItemExecutor executor) {
			
			this.item = item;
			this.slot = slot;
			
			this.executor = executor;
			
		}

		public ItemBuilder getItem() {
			return item;
		}

		public void setItem(ItemBuilder item) {
			this.item = item;
		}

		public ItemExecutor getExecutor() {
			return executor;
		}

		public void setExecutor(ItemExecutor executor) {
			this.executor = executor;
		}

		public int getSlot() {
			return slot;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}
		
		
		
	}
	
	public interface ItemExecutor {
		
		public void execute(Player player);
		
	}
	
}
