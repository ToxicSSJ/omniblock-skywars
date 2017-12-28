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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.ResourceExtractor;
import net.omniblock.skywars.util.Schematic;

@SuppressWarnings("deprecation")
public class CageManager {

	public static Map<Player, Location> cagesdata = new HashMap<Player, Location>();

	public static List<EditSession> pastedcages = new ArrayList<EditSession>();
	public static List<CageAnimator> animators = new ArrayList<CageAnimator>();

	public enum CageKind  {

		COLOR("&9&lJaulas de Colores"),
		CUSTOM("&4&lJaulas Customizadas"),
		SEASONAL("&6&lJaulas de Temporadas"),
		VIP("&e&lJaulas VIP"),
		
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
		DEFAULT(CageKind.COLOR, new ItemBuilder(Material.GLASS).amount(1).build(), "Jaula de Cristal", new String[] {
			" &8- &7Un poco anticuada pero",
			" &7está bastante bien."
		}, 0, "J0", "default"),
		
		JAULA_BLANCA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(0).build(), "Jaula de Cristal Blanco", new String[] {
			" &8- &7Proviene del cielo más",
			" &7resplandeciente."
		}, 0, "J1", "blanco"),
		
		JAULA_NARANJA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(1).build(), "Jaula de Cristal Naranja", new String[] {
			" &8- &7Probablemente naranja",
			" &7pero no es una fruta."
		}, 0, "J2", "naranja"),
		
		JAULA_MAGENTA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(2).build(), "Jaula de Cristal Magenta", new String[] {
			" &8- &7Semejante al augurio",
			" &7de una flor."
		}, 0, "J3", "magenta"),
		
		JAULA_AZUL_CLARO(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(3).build(), "Jaula de Cristal Azul Claro", new String[] {
			" &8- &7Proviene del dominante",
			" &7color del agua."
		}, 0, "J4", "azulclaro"),
		
		JAULA_AMARILLA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(4).build(), "Jaula de Cristal Amarillo", new String[] {
			" &8- &7Semejante al brillo",
			" &7del sol."
		}, 0, "J5", "amarillo"),
		
		JAULA_LIMA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(5).build(), "Jaula de Cristal Lima", new String[] {
			" &8- &7Una combinación de",
			" &7colores unica, como si",
			" &7de un limón se tratase."
		}, 0, "J6", "lima"),
		
		JAULA_ROSA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(6).build(), "Jaula de Cristal Rosa", new String[] {
			" &8- &7Un color un tanto",
			" &7chicloso!"
		}, 0, "J7", "rosa"),
		
		JAULA_GRIS(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(7).build(), "Jaula de Cristal Gris", new String[] {
			" &8- &7Una jaula con un color",
			" &7muy elegante."
		}, 0, "J8", "gris"),
		
		JAULA_GRIS_CLARO(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(8).build(), "Jaula de Cristal Gris Claro", new String[] {
			" &8- &7Una jaula con un color",
			" &7muy elegante y más fuerte",
			" &7pero muy sutíl."
		}, 0, "J9", "grisclaro"),
		
		JAULA_CIAN(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(9).build(), "Jaula de Cristal Cian", new String[] {
			" &8- &7No, no es azul, es el majestuoso",
			" &7CIAN, es... Genial!"
		}, 0, "J10", "cian"),
		
		JAULA_MORADA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(10).build(), "Jaula de Cristal Morado", new String[] {
			" &8- &7Dale un toque morado a tus",
			" &7partidas!"
		}, 0, "J11", "morado"),
		
		JAULA_AZUL(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(11).build(), "Jaula de Cristal Azul", new String[] {
			" &8- &7Un color muy increible en una",
			" &7jaula muy increible de un juego",
			" &7muy increible. ¿No es azulmente",
			" &7increible?"
		}, 0, "J12", "azul"),
		
		JAULA_CAFE(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(12).build(), "Jaula de Cristal Café", new String[] {
			" &8- &7Parecido al color de la",
			" &7madera."
		}, 0, "J13", "marron"),
		
		JAULA_VERDE(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(13).build(), "Jaula de Cristal Verde", new String[] {
			" &8- &7Algo muy natural!"
		}, 0, "J14", "verde"),
		
		JAULA_ROJA(CageKind.COLOR,  new ItemBuilder(Material.STAINED_GLASS).amount(1).data(14).build(), "Jaula de Cristal Rojo", new String[] {
			" &8- &7Un color muy elegante",
			" &7y siniestro!"
		}, 0, "J15", "rojo"),
		
		JAULA_NEGRA(CageKind.COLOR, new ItemBuilder(Material.STAINED_GLASS).amount(1).data(15).build(), "Jaula de Cristal Negro", new String[] {
			" &8- &7Un color muy fantastico",
			" &7obscuro y elegante."
		}, 0, "J16", "negro"),
		
		/*
		 * > Type: Vip Cages
		 */
		JAULA_RAINBOW(CageKind.VIP, new ItemBuilder(Material.PAINTING).amount(1).build(), "Jaula Rainbow", new String[] {
			" &8- &7Todo es perfecto y genial",
			" &7en Minecraft, siempre y cuando",
			" &7tenga muchos &aC&cO&dL&eO&9R&bE&6S&7."
		}, "J500", "rainbow", 10, new String[] { 
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