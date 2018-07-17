/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.network.library.helpers.ItemBuilder;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.ResourceExtractor;
import net.omniblock.skywars.util.Schematic;

@SuppressWarnings("deprecation")
public class CageManager {

	public static Map<Player, Location> cagesdata = new HashMap<Player, Location>();

	public static List<EditSession> pastedcages = new ArrayList<EditSession>();
	public static List<CageAnimator> animators = new ArrayList<CageAnimator>();

	public enum CageKind  {

		COLOR("&9&lCápsulas de Colores"),
		CUSTOM("&4&lCápsulas Customizadas"),
		SEASONAL("&6&lCápsulas de Temporadas"),
		VIP("&e&lCápsulas VIP"),
		
		;
		
		private String inventoryname;
		
		CageKind(String inventoryname){
			this.inventoryname = inventoryname;
		}

		public String getInventoryName() {
			return inventoryname;
		}
		
	}
	
	public enum CageType {
		/*
		 * > Type: Normal Cages
		 */
		DEFAULT(CageKind.COLOR, new ItemBuilder(Material.GLASS).amount(1).build(), "Cápsula de Cristal", new String[] {
			" &8- &7Para los que quieren",
			" &7las cosas claras."
		}, 0, "J0", "default"),
		
		Cápsula_BLANCA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(0).build(), "Cápsula de Cristal Blanco", new String[] {
			" &8- &7Si yo digo blanco,",
			" &7tu dices negro."
		}, 100, "J1", "blanco"),
		
		Cápsula_NARANJA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(1).build(), "Cápsula de Cristal Naranja", new String[] {
			" &8- &7¿Aceptas a tu media naranja?"
		}, 100, "J2", "naranja"),
		
		Cápsula_MAGENTA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(2).build(), "Cápsula de Cristal Magenta", new String[] {
			" &8- &7Un color diferente para",
			" &7una persona diferente."
		}, 100, "J3", "magenta"),
		
		Cápsula_AZUL_CLARO(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(3).build(), "Cápsula de Cristal Azul Claro", new String[] {
			" &8- &7Representa el cielo,",
			" &7la pureza y lo cognitivo."
		}, 100, "J4", "azulclaro"),
		
		Cápsula_AMARILLA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(4).build(), "Cápsula de Cristal Amarillo", new String[] {
			" &8- &7Creativo y lógico."
		}, 100, "J5", "amarillo"),
		
		Cápsula_LIMA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(5).build(), "Cápsula de Cristal Lima", new String[] {
			" &8- &7Lima limón, no te",
			" &7comas la cápsula."
		}, 100, "J6", "lima"),
		
		Cápsula_ROSA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(6).build(), "Cápsula de Cristal Rosa", new String[] {
			" &8- &7Si amas una rosa debes",
			" &7soportar que las espinas",
			" &7te hieran."
		}, 100, "J7", "rosa"),
		
		Cápsula_GRIS(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(7).build(), "Cápsula de Cristal Gris", new String[] {
			" &8- &7Simple y elegante."
		}, 100, "J8", "gris"),
		
		Cápsula_GRIS_CLARO(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(8).build(), "Cápsula de Cristal Gris Claro", new String[] {
			" &8- &7Simple y elegante y claro."
		}, 100, "J9", "grisclaro"),
		
		Cápsula_CIAN(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(9).build(), "Cápsula de Cristal Cian", new String[] {
			" &8- &7No es azul, no es DCIANCESTRAL... ",
			" &7es cian, a secas."
		}, 100, "J10", "cian"),
		
		Cápsula_MORADA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(10).build(), "Cápsula de Cristal Morado", new String[] {
			" &8- &7El color del veneno."
		}, 100, "J11", "morado"),
		
		Cápsula_AZUL(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(11).build(), "Cápsula de Cristal Azul", new String[] {
			" &8- &7Un color muy increible en una",
			" &7Cápsula muy increible de un juego",
			" &7muy increible. ¿No es azulmente",
			" &7increible?"
		}, 100, "J12", "azul"),
		
		Cápsula_CAFE(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(12).build(), "Cápsula de Cristal Café", new String[] {
			" &8- &7Para evitarte cualquier",
			" &7marrón."
		}, 100, "J13", "marron"),
		
		Cápsula_VERDE(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(13).build(), "Cápsula de Cristal Verde", new String[] {
			" &8- &7Biocolor."
		}, 100, "J14", "verde"),
		
		Cápsula_ROJA(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(14).build(), "Cápsula de Cristal Rojo", new String[] {
			" &8- &7El color de la valentia."
		}, 100, "J15", "rojo"),
		
		Cápsula_NEGRA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(15).build(), "Cápsula de Cristal Negro", new String[] {
			" &8- &7Si tu me dices negro",
			" &7yo te digo blanco."
		}, 100, "J16", "negro"),
		
		/*
		 * > Type: Vip Cages
		 */
		Cápsula_RAINBOW(CageKind.VIP, new ItemBuilder(Material.WOOL).data(2).amount(1).build(), "Cápsula Rainbow", new String[] {
			" &8- &7Todo es perfecto y genial",
			" &7en Minecraft, siempre y cuando",
			" &7tenga muchos &aC&cO&dL&eO&9R&bE&6S&7."
		}, "JV0", "rainbow", 10, new String[] {
				"1", "2", "3",
				"4", "5", "6",
				"7"
		}),
		
		Cápsula_CASA(CageKind.VIP, new ItemBuilder(Material.LOG).data(0).amount(1).build(), "Cápsula Casa", new String[] {
				" &8- &7La mejor casa en todo",
				" &7OmniBlock, con un único",
				" &7y espectacular estilo clásico."
			}, "JV1", "casa", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),
		
		Cápsula_COCHE(CageKind.VIP, new ItemBuilder(Material.FURNACE).amount(1).build(), "Cápsula Coche", new String[] {
				" &8- &7Coches muy rápidos y",
				" &7elegantes, perfectos para",
				" &7generar envidia a tus amigos."
			}, "JV2", "coche", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),
		
		Cápsula_MINA(CageKind.VIP, new ItemBuilder(Material.EXPLOSIVE_MINECART).amount(1).build(), "Cápsula Mina", new String[] {
				" &8- &7Solo para apasionados",
				" &7mineros que buscan",
				" &7piedras preciosas."
			}, "JV3", "mina", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),
		
		Cápsula_POCION(CageKind.VIP, new ItemBuilder(Material.POTION).setPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 1)).amount(1).build(), "Cápsula Poción", new String[] {
				" &8- &7Perfecta combinación",
				" &7de sustancias para aquellos",
				" &7maestres de la alquimia."
			}, "JV4", "poti", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),

		Cápsula_TARTA(CageKind.VIP, new ItemBuilder(Material.CAKE).amount(1).build(), "Cápsula Tarta", new String[] {
				" &8- &7Un sabor exquisito para",
				" &7una vida azucarada y para un",
				" &7juego azucaradamente loco."
			}, "JV5", "tarta", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),
		
		/*
		 * > Type: Seasonal Cages
		 */
		
		Cápsula_NAVIDAD(CageKind.SEASONAL, new ItemBuilder(Material.SKULL_ITEM).durability((short) 3).setSkullOwner("thresh3").amount(1).build(), "Cápsula Navideña", new String[] {
				" &8- &7¿Te has portado mal",
				" &7este año?",
			}, "JS0", "navidad", 10, new String[] {
					"1", "2", "3",
					"4", "5", "6",
					"7"
			}),
		
		;

		private CageKind kind;
		
		private String name;
		private String[] lore;

		private String code;
		private String hashcode;
		private String[] codeAnimation;

		private ItemStack icon;

		private boolean vip = false;
		private boolean animation = false;

		private int delay = 0;
		private int price = 0;

		CageType(CageKind kind, ItemStack icon, String name, String[] lore, int price, String code, String hashcode) {
			
			this.kind = kind;
			this.name = name;
			this.lore = lore;
			this.icon = icon;
			
			this.price = price;
			this.code = code;
			this.hashcode = hashcode;
			
		}

		CageType(CageKind kind, ItemStack icon, String name, String[] lore, String code, String hashcode) {
			
			this.kind = kind;
			this.name = name;
			this.lore = lore;
			this.icon = icon;
			
			this.code = code;
			this.hashcode = hashcode;
			this.price = 1000;
			
			this.vip = true;
			this.animation = false;
			
		}

		CageType(CageKind kind, ItemStack icon, String name, String[] lore, String code, String hashcode, int delay, String[] codeAnimation) {
			
			this.kind = kind;
			this.name = name;
			this.lore = lore;
			this.code = code;
			this.hashcode = hashcode;
			this.codeAnimation = codeAnimation;
			this.icon = icon;
			
			this.price = 1000;
			this.delay = delay;
			
			this.vip = true;
			this.animation = true;
			
		}

		public Map <EditSession, CuboidClipboard> paste(Vector loc, World world) {

			Map <EditSession, CuboidClipboard> map = new HashMap <EditSession, CuboidClipboard>();
			File schematic = getCageSchematic(this);

			if (schematic != null) {

				try {
					
					WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

					EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession((LocalWorld) new BukkitWorld(world), 10000000);
					CuboidClipboard cc = MCEditSchematicFormat.getFormat(schematic).load(schematic);

					cc.paste(session, new com.sk89q.worldedit.Vector(loc.getX(), loc.getY(), loc.getZ()), false);
					map.put(session, cc);
					return map;
					
				} catch(MaxChangedBlocksException | DataException | IOException e) {
					e.printStackTrace();
				}

			}

			return null;

		}

		public String getName() {
			return name;
		}

		public String[] getLore() {
			return lore;
		}

		public CageKind getKind() {
			return kind;
		}
		
		public String getCode() {
			return code;
		}

		public String[] getCodeAnimation() {
			return codeAnimation;
		}

		public ItemStack getIcon() {
			return icon;
		}

		public boolean isVip() {
			return vip;
		}

		public boolean hasAnimation() {
			return animation;
		}

		public int getPrice() {
			return price;
		}
		
		public long getDelay() {
			return (long) delay;
		}

		public String getHashcode() {
			return hashcode;
		}
		
		public CageAnimator createAnimator(Location loc) {
			
			if(this.hasAnimation())
				return makeAnimator(loc, delay, this);
			
			throw new RuntimeException("La capsula " + hashcode + " no posee una animación!");
						
		}
		
	}

	public static CageAnimator makeAnimator(Location loc, int delay, CageType ct) {
		return new CageAnimator(loc, delay, ct);
	}

	public static File getCageSchematic(CageType type) {

		String code = type.getHashcode();

		if (Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO || Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO || Skywars.currentMatchType == SkywarsType.SW_Z_SOLO) {

			String dir = "/data/cages/solo/";
			File file = new File(Skywars.getInstance().getDataFolder(), dir + "cap." + code + ".schematic");

			return file;
			
		} else {

			String dir = "/data/cages/team/";
			File file = new File(Skywars.getInstance().getDataFolder(), dir + "cap2p." + code + ".schematic");

			return file;

		}
	}

	public static CageType getCageType(String code) {
		for (CageType ct : CageType.values()) {
			if (ct.getCode() == code) {
				return ct;
			}
		}
		return CageType.DEFAULT;
	}

	public static void registerCage(CageType ct, Location loc) {
		
		if(ct.hasAnimation()) {
			
			CageAnimator animator = ct.createAnimator(loc);
			animator.start();
			
			animators.add(animator);
			return;
			
		}
		
		Map <EditSession, CuboidClipboard> stored = ct.paste(loc.toVector(), loc.getWorld());
		
		for (Map.Entry <EditSession, CuboidClipboard> entry : stored.entrySet()) {
			pastedcages.add(entry.getKey());
		}

	}

	public static void removeCages() {

		for(EditSession session : pastedcages)
			session.undo(session);

		for(CageAnimator animator : animators)
			animator.stop();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1F);
			ParticleEffect.LAVA.display(0.5F, 0.5F, 0.5F, 1F, 20, p.getLocation(), 20D);
		}

	}

	public static void extractCages() {

		ResourceExtractor extractsolo = new ResourceExtractor(Skywars.getInstance(), new File(Skywars.getInstance().getDataFolder(), "data/cages/solo"), "data/cages/solo", "([^\\s]+(\\.(?i)(schematic))$)");
		ResourceExtractor extractteam = new ResourceExtractor(Skywars.getInstance(), new File(Skywars.getInstance().getDataFolder(), "data/cages/team"), "data/cages/team", "([^\\s]+(\\.(?i)(schematic))$)");

		try {

			extractsolo.extract();
			extractteam.extract();

		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * - Apartado para el Z utils...
	 *
	 */

	public static class CageZCameraUtil {

		public static void makeElevation(List <Player> players, Location cl) {

			if (players.size() <= 0) return;

			int x = cl.getBlockX();
			int z = cl.getBlockZ();

			List <Location> points = Lists.newArrayList();
			Location toLoc = players.get(0).getLocation().clone().add(0, 40, 0);

			for (int y = 0; y < 256; y++) {
				Location cache = new Location(players.get(0).getWorld(), x, y, z);
				if (cache.getBlock().getType() != Material.AIR) {
					if (cache.getBlock().getType() == Material.STAINED_GLASS || cache.getBlock().getType() == Material.GLASS) {
						toLoc = cache;
						break;
					}
				}
			}

			points.add(players.get(0).getLocation());
			points.add(toLoc.clone().add(0.5, 1.5, 0.5));

			CameraUtil.travel(players, points, toLoc.getBlock(), 20 * 3);

		}

		public static void makeElevation(Player p, Location cl) {

			int x = cl.getBlockX();
			int z = cl.getBlockZ();

			List <Location> points = Lists.newArrayList();
			Location toLoc = p.getLocation().clone().add(0, 40, 0);

			for (int y = 0; y < 256; y++) {
				Location cache = new Location(p.getWorld(), x, y, z);
				if (cache.getBlock().getType() != Material.AIR) {
					if (cache.getBlock().getType() == Material.STAINED_GLASS || cache.getBlock().getType() == Material.GLASS) {
						toLoc = cache;
						break;
					}
				}
			}

			points.add(p.getLocation());
			points.add(toLoc.clone().add(0.5, 1.5, 0.5));

			CameraUtil.travel(p, points, toLoc.getBlock(), 20 * 3);

		}

	}

	/*
	 * 
	 * - Apartado para las animaciones...
	 * TODO
	 */

	public static class CageAnimator {
		
		private Location location;
		
		private String hashCode;
		private String[] codeAnimation;
		
		private int delay;
		
		private BukkitTask task;
		private Schematic schem = new Schematic();
		
		public CageAnimator(Location loc, final int delay, CageType ct) {
			
			this.location = loc;
			this.delay = delay;
			this.codeAnimation = ct.getCodeAnimation();
			this.hashCode = ct.getHashcode();

		}

		public void start() {

			if(codeAnimation.length > 0)
				task = new BukkitRunnable() {
					
					int start = 0;

					@Override
					public void run() {
						
						if(start + 1 == codeAnimation.length)
							start = 0;
							
						if(Skywars.currentMatchType.isSolo()) {

							String dir = "/data/cages/solo/";
							String code = codeAnimation[start];
							String path = "cap." + hashCode + "." + code + ".schematic";

							schem.removeSchematic();
							schem.pasteSchematic(dir, path, location);

						} else {
							
							String dir = "/data/cages/team/";
							String code = codeAnimation[start];
							String path = "cap2p." + hashCode + "." + code + ".schematic";

							schem.removeSchematic();
							schem.pasteSchematic(dir, path, location);
							
						}
						
						start++;
						
					}

				}.runTaskTimer(Skywars.getInstance(), 0, delay);

			else
				throw new RuntimeException("No se puede recrear una animación con menos de 2 schematic en su conjunto.");
			
		}
		
		public void stop() {
			
			schem.removeSchematic();
			
			if(task != null)
				task.cancel();
			
		}
		
	}
	
}