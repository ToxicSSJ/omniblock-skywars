package net.omniblock.skywars.patch.managers.lobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.mapmanager.manager.MapManager;
import org.inventivetalent.mapmanager.wrapper.MapWrapper;

import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.packets.util.Lists;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.patch.managers.CommandManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.patch.managers.lobby.object.PowerItem;
import net.omniblock.skywars.patch.managers.lobby.object.PowerItem.PowerItemManager;
import net.omniblock.skywars.patch.managers.lobby.object.PowerItem.PowerItemType;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.inventory.InventoryBuilder;
import net.omniblock.skywars.util.inventory.InventoryBuilder.Action;
import org.inventivetalent.mapmanager.controller.MapController;

public class LobbyManager implements Listener {

	public static MapManager mapManagerZ, mapManagerN;
	public static MapWrapper mapWrapperZ, mapWrapperN;
	public static MapController mapControllerZ, mapControllerN;
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		if (Skywars.getGameState() != SkywarsGameState.IN_LOBBY)
			return;

		if (SoloPlayerManager.getPlayersInLobbyList().contains(e.getPlayer())
				|| TeamPlayerManager.getPlayersInLobbyList().contains(e.getPlayer())) {

			if (PowerItem.player_votes.containsKey(e.getPlayer())) {

				List<PowerItemType> votes = PowerItem.player_votes.get(e.getPlayer());

				if (votes != null) {

					for (PowerItemType vote : PowerItemType.values()) {

						PowerItemManager.removeVote(vote);
						continue;

					}

				}

			}

			PowerItem.player_votes.remove(e.getPlayer());

		}

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (e.getItem() == null || Skywars.getGameState() != SkywarsGameState.IN_LOBBY)
			return;
		if (!e.getItem().hasItemMeta())
			return;
		if (e.getItem().getItemMeta().getDisplayName() == null)
			return;

		e.setCancelled(true);

		ItemStack item = e.getItem();

		if (SoloPlayerManager.getPlayersInLobbyList().contains(e.getPlayer())
				|| TeamPlayerManager.getPlayersInLobbyList().contains(e.getPlayer())) {

			for (LobbyItem li : LobbyItem.values()) {

				if (li.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {

					li.getItemClick().click(e.getPlayer());
					return;

				}

			}

		}

	}
	
	public static void start() {
		
		CommandManager executor = new CommandManager();
		String[] commands = new String[] {
				"lobby",
				"salir",
				"leave",
				"hub",
				"gen_command_vote",
				"gen_command_test"
		};
		
		for(String command : commands){
			Skywars.getInstance().getCommand(command).setExecutor(executor);
		}
		
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new LobbyManager(), Skywars.getInstance());
		Skywars.getInstance().getServer().getPluginManager().registerEvents(new SpectatorManager(), Skywars.getInstance());
		
		for (PowerItemType pit : PowerItemType.values()) {

			PowerItem.votes.put(pit, 0);
			continue;

		}

		return;

	}

	public static void giveItems(Player player) {
		
		MapController cacheController = null;
		
		player.getInventory().clear();
		player.getEquipment().clear();

		switch (Skywars.currentMatchType) {

		case SW_INSANE_SOLO:

			if(mapControllerN != null)
				cacheController = mapControllerN;
			
			player.getInventory().setItem(4, LobbyItem.POWER_INSANE_MODE.getItem());
			player.getInventory().setItem(7, LobbyItem.KITS.getItem());
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		case SW_INSANE_TEAMS:

			if(mapControllerN != null)
				cacheController = mapControllerN;
			
			player.getInventory().setItem(4, LobbyItem.POWER_INSANE_MODE.getItem());
			player.getInventory().setItem(7, LobbyItem.KITS.getItem());
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		case SW_NORMAL_SOLO:

			if(mapControllerN != null)
				cacheController = mapControllerN;
			
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		case SW_NORMAL_TEAMS:

			if(mapControllerN != null)
				cacheController = mapControllerN;
			
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		case SW_Z_SOLO:

			if(mapControllerZ != null)
				cacheController = mapControllerZ;
			
			player.getInventory().setItem(4, LobbyItem.POWER_Z_MODE.getItem());
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		case SW_Z_TEAMS:

			if(mapControllerZ != null)
				cacheController = mapControllerZ;
			
			player.getInventory().setItem(4, LobbyItem.POWER_Z_MODE.getItem());
			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		default:

			player.getInventory().setItem(8, LobbyItem.EXIT.getItem());

			break;

		}
		
		if(cacheController != null) {
			
			player.getInventory().setItem(0, LobbyItem.MAP_INFO.getItem());
			
			cacheController.addViewer(player);
			cacheController.sendContent(player);
			cacheController.showInHand(player);
			
		}

	}

	public static enum LobbyItem {

		EXIT(new ItemBuilder(Material.ACACIA_DOOR_ITEM).amount(1).name(TextUtil.format("&c&lAbandonar"))
				.lore(TextUtil.format("")).lore(TextUtil.format("&9&l- &7Abandonar la partida y volver"))
				.lore(TextUtil.format("&7al lobby de la modalidad de Skywars.")).lore(TextUtil.format(""))
				.hideAtributes().build(),

				new ItemClick() {

					@Override
					public void click(Player player) {

						player.sendMessage(TextUtil.format("&bConectandote al lobby de Skywars..."));

						Packets.STREAMER.streamPacket(new PlayerSendToServerPacket()

								.setPlayername(player.getName()).setServertype(ServerType.SKYWARS_LOBBY_SERVER)
								.setParty(false)

								.build().setReceiver(PacketSenderType.OMNICORE));
						return;

					}

				}

		),

		MAP_INFO(new ItemBuilder(Material.MAP).amount(1).name(TextUtil.format("&2&lInformación del Mapa"))
				.lore(TextUtil.format("")).lore(TextUtil.format("&9&l- &7Revisa la información adicional"))
				.lore(TextUtil.format("&7de este mapa.")).lore(TextUtil.format("")).hideAtributes().build(),

				new ItemClick() {

					@Override
					public void click(Player player) {

						player.sendMessage(TextUtil.format("&6Proximamente..."));
						return;

					}

				}

		),
		
		@SuppressWarnings("deprecation")
		KITS(new ItemBuilder(Material.getMaterial(439)).amount(1)
				.name(TextUtil.format("&6Kits"))
				.lore("")
				.lore(TextUtil.format("&9&m-&r &7Puedes equiparte algún Kit"))
				.lore(TextUtil.format("&7que ya tengas disponible,"))
				.lore(TextUtil.format("&7recuerda que puedes conseguir"))
				.lore(TextUtil.format("&7mas en nuestra tienda!")).build(),
				
				new ItemClick() {

					@Override
					public void click(Player player) {
						
						InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&2&lKits Disponibles"), 6 * 9, true);
						
						// final String color = "&6";
						// final int MAX_SLOT = (6 * 9) - 1;
						
						// int CURRENT_SLOT = 0;
	
						ib.addItem(new ItemBuilder(Material.ARROW).amount(1)
								.name(TextUtil.format("&7Volver")).build(), 48, new Action(){

									@Override
									public void click(ClickType click, Player player) {
										
										player.closeInventory();
										return;
										
									}
							
						});
						
						ib.addItem(new ItemBuilder(Material.ARROW).amount(1)
								.name(TextUtil.format("&7Volver")).build(), 50, new Action(){

									@Override
									public void click(ClickType click, Player player) {
										
										player.closeInventory();
										return;
										
									}
							
						});
						
						ib.open(player);
						return;
						
					}
			
		}),

		POWER_INSANE_MODE(new ItemBuilder(Material.SLIME_BALL).enchant(Enchantment.FIRE_ASPECT, 1).amount(1)
				.name(TextUtil.format("&b&lPODERES")).lore(TextUtil.format(""))
				.lore(TextUtil.format("&9&l- &7Altera el tiempo o añade nuevas"))
				.lore(TextUtil.format("&7funcionalidades, nuevos eventos y más!"))
				.lore(TextUtil.format("&7Con este item podrás votar por alteraciones"))
				.lore(TextUtil.format("&7en la partida del Modo Mejorado! Sin embargo,"))
				.lore(TextUtil.format("&7es necesario que tengas un rango VIP para poder"))
				.lore(TextUtil.format("&7utilizarlas!")).lore(TextUtil.format("")).hideAtributes().build(),

				new ItemClick() {

					ItemClick itemclick = this;

					@Override
					public void click(Player player) {

						if (player.hasPermission("skywars.powers")) {

							InventoryBuilder ib = new InventoryBuilder("&b&lPODERES - ¡VOTAR!", 6 * 9, true);

							ib.addItem(InventoryItem.CHANGE_TIME.getBuilder().build(), 11, new Action() {

								@Override
								public void click(ClickType click, Player player) {

									player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
									time(player);

									return;

								}

							});

							ib.addItem(
									InventoryItem.MORE_INSANE_ITEMS.getBuilder()
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.MORE_INSANE_ITEMS) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player))
													? (PowerItem.player_votes.get(player)
															.contains(PowerItemType.MORE_INSANE_ITEMS)
																	? TextUtil.format("&a¡Has votado por esto!")
																	: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									13, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player)
														.contains(PowerItemType.MORE_INSANE_ITEMS)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.MORE_INSANE_ITEMS);
												PowerItem.player_votes.get(player).add(PowerItemType.MORE_INSANE_ITEMS);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.MORE_INSANE_ITEMS.getVotedName() + "&8("
																+ PowerItem.votes.get(PowerItemType.MORE_INSANE_ITEMS)
																+ "/" + PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.CONTAMINATION.getBuilder()
									.lore(TextUtil
											.format("&8&lVOTOS: &a(" + PowerItem.votes.get(PowerItemType.CONTAMINATION)
													+ "/" + PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player))
											? (PowerItem.player_votes.get(player).contains(PowerItemType.CONTAMINATION)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 15, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player)
														.contains(PowerItemType.CONTAMINATION)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.CONTAMINATION);
												PowerItem.player_votes.get(player).add(PowerItemType.CONTAMINATION);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.CONTAMINATION.getVotedName() + "&8("
																+ PowerItem.votes.get(PowerItemType.CONTAMINATION) + "/"
																+ PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.NONE.getBuilder()
									.lore(TextUtil.format("&8&lVOTOS: &a(" + PowerItem.votes.get(PowerItemType.NONE)
											+ "/" + PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player))
											? (PowerItem.player_votes.get(player).contains(PowerItemType.NONE)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 29, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player).contains(PowerItemType.NONE)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.NONE);
												PowerItem.player_votes.get(player).add(PowerItemType.NONE);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.NONE.getVotedName() + "&8("
																+ PowerItem.votes.get(PowerItemType.NONE) + "/"
																+ PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.COMING_SOON.getBuilder().build(), 31);
							ib.addItem(InventoryItem.COMING_SOON.getBuilder().build(), 33);

							ib.open(player);
							return;
						}

						player.sendMessage(TextUtil
								.format("&7No eres &6&lVIP&7 para acceder a los poderes, En nuestra tienda encuentra todas "
										+ "las ofertas posibles para adquirir un rango mayor: &6&lwww.omniblock.net"));
						return;

					}

					private void time(Player player) {

						if (player.hasPermission("skywars.powers")) {

							InventoryBuilder ib = new InventoryBuilder("&b&lPODERES - ¡TIEMPO!", 6 * 9, true);

							ib.addItem(
									new ItemBuilder(Material.WATCH).name(TextUtil.format("&bMadrugada &8&l:&b 7:00 am"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como madrugada"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MORNING) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_MORNING)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									11, getActionByTime(PowerItemType.TIME_ALTERATOR_MORNING));

							ib.addItem(
									new ItemBuilder(Material.WATCH)
											.name(TextUtil.format("&bMedio Dia &8&l:&b 12:00 pm"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como medio dia"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MIDDAY) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_MIDDAY)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									13, getActionByTime(PowerItemType.TIME_ALTERATOR_MIDDAY));

							ib.addItem(new ItemBuilder(Material.WATCH)
									.name(TextUtil.format("&bAtardecer &8&l:&b 5:00 pm")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&m- &7Elige el tiempo como atardecer"))
									.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
									.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&lVOTOS: &a("
											+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_AFTERNOON) + "/"
											+ PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
											.get(player).contains(PowerItemType.TIME_ALTERATOR_AFTERNOON)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 15, getActionByTime(PowerItemType.TIME_ALTERATOR_AFTERNOON));

							ib.addItem(
									new ItemBuilder(Material.WATCH).name(TextUtil.format("&bNoche &8&l:&b 9:00 pm"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como atardecer"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_NIGHT) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_NIGHT)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									30, getActionByTime(PowerItemType.TIME_ALTERATOR_NIGHT));

							ib.addItem(new ItemBuilder(Material.WATCH)
									.name(TextUtil.format("&bMedia Noche &8&l:&b 12:00 pm")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&m- &7Elige el tiempo como media"))
									.lore(TextUtil.format("&7noche que hará que el mapa se"))
									.lore(TextUtil.format("&7mantenga fijamente en esa hora."))
									.lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&lVOTOS: &a("
											+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MIDNIGHT) + "/"
											+ PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
											.get(player).contains(PowerItemType.TIME_ALTERATOR_MIDNIGHT)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 32, getActionByTime(PowerItemType.TIME_ALTERATOR_MIDNIGHT));

							ib.addItem(new ItemBuilder(Material.ARROW).name(TextUtil.format("&7Volver")).build(), 49,
									new Action() {

										@Override
										public void click(ClickType click, Player player) {

											player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
											itemclick.click(player);
											return;

										}

									});

							ib.open(player);

						}

					}

					private Action getActionByTime(PowerItemType pt) {

						return new Action() {

							@Override
							public void click(ClickType click, Player player) {

								if (PowerItem.player_votes.containsKey(player)) {

									if (PowerItem.player_votes.get(player).contains(pt)) {

										player.closeInventory();

										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
										player.sendMessage(TextUtil.format("&cYa has votado por este tipo de tiempo!"));

										return;

									}

								} else {
									
									PowerItem.player_votes.put(player, Lists.newArrayList());
									
								}
								
								PowerItemManager.addVote(pt);
								PowerItem.player_votes.get(player).add(pt);

								for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
									p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
								}

								Skywars.getInstance().getServer()
										.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a" + player.getName()
												+ " &7ha votado por " + pt.getVotedName() + "&8("
												+ PowerItem.votes.get(pt) + "/" + PowerItem.MIN_VOTES + ")"));
								time(player);

								return;
								
							}

						};

					}

				}

		),

		POWER_Z_MODE(new ItemBuilder(Material.FIREBALL).enchant(Enchantment.FIRE_ASPECT, 1).amount(1)
				.name(TextUtil.format("&8&lPODERES &4&lZ")).lore(TextUtil.format(""))
				.lore(TextUtil.format("&9&l- &7Altera el tiempo o añade nuevas"))
				.lore(TextUtil.format("&7funcionalidades, nuevos eventos y más!"))
				.lore(TextUtil.format("&7Con este item podrás votar por alteraciones"))
				.lore(TextUtil.format("&7en la partida del &4&lZ&7, Sin embargo,"))
				.lore(TextUtil.format("&7es necesario que tengas un rango VIP para poder"))
				.lore(TextUtil.format("&7utilizarlas!")).lore(TextUtil.format("")).hideAtributes().build(),

				new ItemClick() {

					ItemClick itemclick = this;

					@Override
					public void click(Player player) {

						if (player.hasPermission("skywars.powers")) {

							InventoryBuilder ib = new InventoryBuilder("&8&lPODERES &4&lZ &8&l - ¡VOTAR!", 6 * 9, true);

							ib.addItem(InventoryItem.CHANGE_TIME.getBuilder().build(), 11, new Action() {

								@Override
								public void click(ClickType click, Player player) {

									player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
									time(player);

									return;

								}

							});

							ib.addItem(
									InventoryItem.MORE_LEGENDARY_ITEMS.getBuilder()
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.MORE_LEGENDARY_ITEMS) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player))
													? (PowerItem.player_votes.get(player)
															.contains(PowerItemType.MORE_LEGENDARY_ITEMS)
																	? TextUtil.format("&a¡Has votado por esto!")
																	: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									13, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player)
														.contains(PowerItemType.MORE_LEGENDARY_ITEMS)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.MORE_LEGENDARY_ITEMS);
												PowerItem.player_votes.get(player)
														.add(PowerItemType.MORE_LEGENDARY_ITEMS);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.MORE_INSANE_ITEMS.getVotedName() + "&8("
																+ PowerItem.votes
																		.get(PowerItemType.MORE_LEGENDARY_ITEMS)
																+ "/" + PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.DAYS_BLAZER.getBuilder().lore(TextUtil.format("&8&lVOTOS: &a("
									+ PowerItem.votes.get(PowerItemType.DAYS_BLAZER) + "/" + PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player))
											? (PowerItem.player_votes.get(player).contains(PowerItemType.DAYS_BLAZER)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 15, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player)
														.contains(PowerItemType.DAYS_BLAZER)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.DAYS_BLAZER);
												PowerItem.player_votes.get(player).add(PowerItemType.DAYS_BLAZER);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.DAYS_BLAZER.getVotedName() + "&8("
																+ PowerItem.votes.get(PowerItemType.DAYS_BLAZER) + "/"
																+ PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.NONE.getBuilder()
									.lore(TextUtil.format("&8&lVOTOS: &a(" + PowerItem.votes.get(PowerItemType.NONE)
											+ "/" + PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player))
											? (PowerItem.player_votes.get(player).contains(PowerItemType.NONE)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 29, new Action() {

										@Override
										public void click(ClickType click, Player player) {

											if (PowerItem.player_votes.get(player) == null)
												PowerItem.player_votes.put(player, new ArrayList<PowerItemType>());

											if (PowerItem.player_votes.containsKey(player)) {

												if (PowerItem.player_votes.get(player).contains(PowerItemType.NONE)) {

													player.closeInventory();

													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
													player.sendMessage(
															TextUtil.format("&cYa has votado por este poder!"));

													return;

												}

												PowerItemManager.addVote(PowerItemType.NONE);
												PowerItem.player_votes.get(player).add(PowerItemType.NONE);

												for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
													p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												}

												Skywars.getInstance().getServer()
														.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a"
																+ player.getName() + " &7ha votado por "
																+ PowerItemType.NONE.getVotedName() + "&8("
																+ PowerItem.votes.get(PowerItemType.NONE) + "/"
																+ PowerItem.MIN_VOTES + ")"));
												itemclick.click(player);

												return;

											}

										}

									});

							ib.addItem(InventoryItem.COMING_SOON.getBuilder().build(), 31);
							ib.addItem(InventoryItem.COMING_SOON.getBuilder().build(), 33);

							ib.open(player);
							return;
						}

						player.sendMessage(TextUtil
								.format("&7No eres &6&lVIP&7 para acceder a los poderes, En nuestra tienda encuentra todas "
										+ "las ofertas posibles para adquirir un rango mayor: &6&lwww.omniblock.net"));
						return;

					}

					private void time(Player player) {

						if (player.hasPermission("skywars.powers")) {

							InventoryBuilder ib = new InventoryBuilder("&b&lPODERES - ¡TIEMPO!", 6 * 9, true);

							ib.addItem(
									new ItemBuilder(Material.WATCH).name(TextUtil.format("&bMadrugada &8&l:&b 7:00 am"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como madrugada"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MORNING) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_MORNING)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									11, getActionByTime(PowerItemType.TIME_ALTERATOR_MORNING));

							ib.addItem(
									new ItemBuilder(Material.WATCH)
											.name(TextUtil.format("&bMedio Dia &8&l:&b 12:00 pm"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como medio dia"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MIDDAY) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_MIDDAY)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									13, getActionByTime(PowerItemType.TIME_ALTERATOR_MIDDAY));

							ib.addItem(new ItemBuilder(Material.WATCH)
									.name(TextUtil.format("&bAtardecer &8&l:&b 5:00 pm")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&m- &7Elige el tiempo como atardecer"))
									.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
									.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&lVOTOS: &a("
											+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_AFTERNOON) + "/"
											+ PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
											.get(player).contains(PowerItemType.TIME_ALTERATOR_AFTERNOON)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 15, getActionByTime(PowerItemType.TIME_ALTERATOR_AFTERNOON));

							ib.addItem(
									new ItemBuilder(Material.WATCH).name(TextUtil.format("&bNoche &8&l:&b 9:00 pm"))
											.lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&m- &7Elige el tiempo como atardecer"))
											.lore(TextUtil.format("&7que hará que el mapa se mantenga"))
											.lore(TextUtil.format("&7fijamente en esa hora.")).lore(TextUtil.format(""))
											.lore(TextUtil.format("&8&lVOTOS: &a("
													+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_NIGHT) + "/"
													+ PowerItem.MIN_VOTES + ")"))
											.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
													.get(player).contains(PowerItemType.TIME_ALTERATOR_NIGHT)
															? TextUtil.format("&a¡Has votado por esto!")
															: TextUtil.format("&c¡No has votado por esto!"))
													: TextUtil.format("&c¡No has votado por esto!"))
											.build(),
									30, getActionByTime(PowerItemType.TIME_ALTERATOR_NIGHT));

							ib.addItem(new ItemBuilder(Material.WATCH)
									.name(TextUtil.format("&bMedia Noche &8&l:&b 12:00 pm")).lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&m- &7Elige el tiempo como media"))
									.lore(TextUtil.format("&7noche que hará que el mapa se"))
									.lore(TextUtil.format("&7mantenga fijamente en esa hora."))
									.lore(TextUtil.format(""))
									.lore(TextUtil.format("&8&lVOTOS: &a("
											+ PowerItem.votes.get(PowerItemType.TIME_ALTERATOR_MIDNIGHT) + "/"
											+ PowerItem.MIN_VOTES + ")"))
									.lore((PowerItem.player_votes.containsKey(player)) ? (PowerItem.player_votes
											.get(player).contains(PowerItemType.TIME_ALTERATOR_MIDNIGHT)
													? TextUtil.format("&a¡Has votado por esto!")
													: TextUtil.format("&c¡No has votado por esto!"))
											: TextUtil.format("&c¡No has votado por esto!"))
									.build(), 32, getActionByTime(PowerItemType.TIME_ALTERATOR_MIDNIGHT));

							ib.addItem(new ItemBuilder(Material.ARROW).name(TextUtil.format("&7Volver")).build(), 49,
									new Action() {

										@Override
										public void click(ClickType click, Player player) {

											player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
											itemclick.click(player);
											return;

										}

									});

							ib.open(player);

						}

					}

					private Action getActionByTime(PowerItemType pt) {

						return new Action() {

							@Override
							public void click(ClickType click, Player player) {

								if (PowerItem.player_votes.containsKey(player)) {

									if (PowerItem.player_votes.get(player).contains(pt)) {

										player.closeInventory();

										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
										player.sendMessage(TextUtil.format("&cYa has votado por este tipo de tiempo!"));

										return;

									}

								} else {
									
									PowerItem.player_votes.put(player, Lists.newArrayList());
									
								}
								
								PowerItemManager.addVote(pt);
								PowerItem.player_votes.get(player).add(pt);

								for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
									p.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
								}

								Skywars.getInstance().getServer()
										.broadcastMessage(TextUtil.format(" &d&lPODERES &8&l: &a" + player.getName()
												+ " &7ha votado por " + pt.getVotedName() + "&8("
												+ PowerItem.votes.get(pt) + "/" + PowerItem.MIN_VOTES + ")"));
								time(player);

								return;

							}

						};

					}

				}

		),

		;

		private ItemClick click;
		private ItemStack item;

		LobbyItem(ItemStack item, ItemClick click) {

			this.item = item;
			this.click = click;

		}

		public ItemClick getItemClick() {
			return click;
		}

		public ItemStack getItem() {
			return item;
		}

		public static interface ItemClick {

			public void click(Player player);

		}

	}

	public static enum InventoryItem {

		CHANGE_TIME(new ItemBuilder(Material.WATCH).amount(1).name(TextUtil.format("&b&lCAMBIAR TIEMPO")).lore("")
				.lore(TextUtil.format("&9&m-&r &7Elige que hora del dia deseas"))
				.lore(TextUtil.format("&7colocarle a la partida!")).lore(TextUtil.format(""))),

		MORE_LEGENDARY_ITEMS(
				new ItemBuilder(Material.ENDER_CHEST).amount(1).name(TextUtil.format("&4&l¡Más Items Legendarios!"))
						.lore("").lore(TextUtil.format("&9&m-&r &7Esta opción añadirá más"))
						.lore(TextUtil.format("&7items legendarios y epicos en los"))
						.lore(TextUtil.format("&7cofres al iniciar la partida!")).lore(TextUtil.format(""))),

		MORE_INSANE_ITEMS(new ItemBuilder(Material.CHEST).amount(1).name(TextUtil.format("&4&l¡Más Items Mejorados!"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Esta opción añadirá más"))
				.lore(TextUtil.format("&7items mejorados en los cofres"))
				.lore(TextUtil.format("&7al iniciar la partida!")).lore(TextUtil.format(""))),

		DAYS_BLAZER(new ItemBuilder(Material.REDSTONE).amount(1).name(TextUtil.format("&9&l¡Dios de los dias!"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Activa al poderoso Dios"))
				.lore(TextUtil.format("&7de los dias el cual hará que los"))
				.lore(TextUtil.format("&7dias vuelen y tengan sus propias")).lore(TextUtil.format("&7normas!"))
				.lore(TextUtil.format(""))),

		REMOVE_DESTRUCTION(new ItemBuilder(Material.TNT).amount(1).name(TextUtil.format("&c&lRemover Destrucción"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Desactiva la destrucción"))
				.lore(TextUtil.format("&7que está por defecto al inicio")).lore(TextUtil.format("&7de la partida."))
				.lore(TextUtil.format(""))),

		CONTAMINATION(new ItemBuilder(Material.SNOW_BALL).amount(1).name(TextUtil.format("&9&lContaminación")).lore("")
				.lore(TextUtil.format("&9&m-&r &7Activa la contaminación,"))
				.lore(TextUtil.format("&7es un ente que consumirá el mapa"))
				.lore(TextUtil.format("&7por donde quiera que pase, No te")).lore(TextUtil.format("&7acerques!"))
				.lore(TextUtil.format(""))),

		COMING_SOON(new ItemBuilder(Material.COAL_BLOCK).amount(1).name(TextUtil.format("&c&lProximamente"))
				.lore(TextUtil.format(""))),

		NONE(new ItemBuilder(Material.BARRIER).amount(1).name(TextUtil.format("&c&lDesactivar Todo")).lore("")
				.lore(TextUtil.format("&9&m-&r &7Si es activada esta"))
				.lore(TextUtil.format("&7opción se desactivarán todos")).lore(TextUtil.format("&7los poderes."))
				.lore(TextUtil.format(""))),

		;

		private ItemBuilder builder = new ItemBuilder(Material.AIR);

		InventoryItem(ItemBuilder builder) {
			this.builder = builder;
		}

		public ItemBuilder getBuilder() {
			return new ItemBuilder(builder.build());
		}

	}

}
