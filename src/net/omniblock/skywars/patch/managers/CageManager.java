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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.ParticleEffect;
import net.omniblock.skywars.util.ResourceExtractor;
import omniblock.ot.errorapi.ErrorAPI;

@SuppressWarnings("deprecation")
public class CageManager {

	public static Map<Player, Location> cagesdata = new HashMap<Player, Location>();
	
	public static List<EditSession> pastedcages = new ArrayList<EditSession>();
	public static List<BukkitTask> animations = new ArrayList<BukkitTask>();
	
	public enum CageType {
		/*
		 * > Type: Normal Cages
		 */
		DEFAULT("Jaula de Cristal",
				new String[] { "",
				  			   " &8- &7Un poco anticuada pero",
				  			   " &7está bastante bien."
				  			 },
				Material.GLASS,
				0,
				0,
				"J0", "default"),
		JAULA_BLANCA("Jaula de Cristal Blanco",
				new String[] { "",
						       " &8- &7Proviene del cielo más",
							   " &7resplandeciente."
							 },
				Material.STAINED_GLASS,
				0,
				0,
				"J1", "blanco"),
		JAULA_NARANJA("Jaula de Cristal Naranja",
				new String[] { "",
						       " &8- &7Probablemente naranja",
							   " &7pero no es una fruta."
							 },
				Material.STAINED_GLASS,
				1,
				0,
				"J2", "naranja"),
		JAULA_MAGENTA("Jaula de Cristal Magenta",
				new String[] { "",
						       " &8- &7Semejante al augurio",
							   " &7de una flor."
							 },
				Material.STAINED_GLASS,
				2,
				0,
				"J3", "magenta"),
		JAULA_AZUL_CLARO("Jaula de Cristal Azul Claro",
				new String[] { "",
						       " &8- &7Proviene del dominante",
							   " &7color del agua."
							 },
				Material.STAINED_GLASS,
				3,
				0,
				"J4", "azulclaro"),
		JAULA_AMARILLA("Jaula de Cristal Amarillo",
				new String[] { "",
						       " &8- &7Semejante al brillo",
							   " &7del sol."
							 },
				Material.STAINED_GLASS,
				4,
				0,
				"J5", "amarillo"),
		JAULA_LIMA("Jaula de Cristal Lima",
				new String[] { "",
						       " &8- &7Una combinación de",
							   " &7colores unica!"
							 },
				Material.STAINED_GLASS,
				5,
				0,
				"J6", "lima"),
		JAULA_ROSA("Jaula de Cristal Rosa",
				new String[] { "",
						       " &8- &7Un color un tanto",
							   " &7chicloso!"
							 },
				Material.STAINED_GLASS,
				6,
				0,
				"J7", "rosa"),
		JAULA_GRIS("Jaula de Cristal Gris",
				new String[] { "",
						       " &8- &7Una jaula con un color",
						       " &7muy elegante."
							 },
				Material.STAINED_GLASS,
				7,
				0,
				"J8", "gris"),
		JAULA_GRIS_CLARO("Jaula de Cristal Gris Claro",
				new String[] { "",
						       " &8- &7Una jaula con un color",
						       " &7muy elegante y más transparente",
						       " &7pero muy sutíl."
							 },
				Material.STAINED_GLASS,
				8,
				0,
				"J9", "grisclaro"),
		JAULA_CIAN("Jaula de Cristal Cian",
				new String[] { "",
						       " &8- &7No, no es azul, es el majestuoso",
						       " &7CIAN, es... Genial!"
							 },
				Material.STAINED_GLASS,
				9,
				0,
				"J10", "cian"),
		JAULA_MORADA("Jaula de Cristal Morado",
				new String[] { "",
						       " &8- &7Dale un toque morado a tus",
						       " &7partidas!"
							 },
				Material.STAINED_GLASS,
				10,
				0,
				"J11", "morado"),
		JAULA_AZUL("Jaula de Cristal Azul",
				new String[] { "",
						       " &8- &7Un color muy increible en una",
						       " &7jaula muy increible de un juego",
						       " &7muy increible. ¿No es increible?"
							 },
				Material.STAINED_GLASS,
				11,
				0,
				"J12", "azul"),
		JAULA_CAFE("Jaula de Cristal Café",
				new String[] { "",
						       " &8- &7Parecido al color de la",
						       " &7madera."
							 },
				Material.STAINED_GLASS,
				12,
				0,
				"J13", "marron"),
		JAULA_VERDE("Jaula de Cristal Verde",
				new String[] { "",
						       " &8- &7Algo muy natural!"
							 },
				Material.STAINED_GLASS,
				13,
				0,
				"J14", "verde"),
		JAULA_ROJA("Jaula de Cristal Rojo",
				new String[] { "",
						       " &8- &7Un color muy elegante",
						       " &7y siniestro!"
							 },
				Material.STAINED_GLASS,
				14,
				0,
				"J15", "rojo"),
		JAULA_NEGRA("Jaula de Cristal Negro",
				new String[] { "",
						       " &8- &7Un color muy fantastico",
						       " &7obscuro y elegante."
							 },
				Material.STAINED_GLASS,
				15,
				0,
				"J16", "negro"),
		/*
		 * > Type: Vip Cages
		 */
		JAULA_INFERNAL("Jaula Infernal",
				new String[] { "",
							   " &8- &7Las almas del limbo",
							   " &7atrapadas en la destrucción",
							   " &7infernal."
				 			 },
				Material.STAINED_GLASS,
				15,
				"J800", "infernal",
				new CageAnimator[] {
						makeAnimation(20,
						 		  	  Material.STAINED_GLASS,
						 		  	  14,
						 		  	  CageMaterial.STAINED_GLASS_BLACK,
						 		  	  CageMaterial.STAINED_GLASS_GRAY,
						 		  	  CageMaterial.STAINED_GLASS_LIGHT_GRAY)
								   }),
		
		;
		
		private String name;
		private String[] lore;
		
		private String code;
		private String hashcode;
		
		private Material mat;
		
		private boolean vip = false;
		private boolean animation = false;
		
		private int price = 0;
		private int data = 0;
		
		private CageAnimator[] animations;
		
		CageType(String name, String[] lore, Material mat, int data, int price, String code, String hashcode){
			this.name = name;
			this.lore = lore;
			this.mat = mat;
			this.data = data;
			this.price = price;
			this.code = code;
			this.hashcode = hashcode;
		}
		
		CageType(String name, String[] lore, Material mat, int data, String code, String hashcode){
			this.name = name;
			this.lore = lore;
			this.mat = mat;
			this.data = data;
			this.code = code;
			this.hashcode = hashcode;
			
			this.vip = true;
			this.animation = false;
		}
		
		CageType(String name, String[] lore, Material mat, int data, String code, String hashcode, CageAnimator...animations){
			this.name = name;
			this.lore = lore;
			this.mat = mat;
			this.data = data;
			this.code = code;
			this.hashcode = hashcode;
			this.animations = animations;
			
			this.vip = true;
			this.animation = true;
		}

		public void animate(CuboidClipboard cc, Vector loc, World world){
			if(animation == true){
				if(animations.length >= 1){
					for(CageAnimator ca : animations){
						ca.setClipboard(cc, loc, world);
						registerAnimation(ca);
					}
				}
			}
		}
		
		public Map<EditSession, CuboidClipboard> paste(Vector loc, World world) {
			
			Map<EditSession, CuboidClipboard> map = new HashMap<EditSession, CuboidClipboard>();
			File schematic = getCageSchematic(this);
			
			if(schematic != null){
				
                try {
                	WorldEditPlugin we = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
    				
    				EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession((LocalWorld)new BukkitWorld(world), 10000000);
					CuboidClipboard cc = MCEditSchematicFormat.getFormat(schematic).load(schematic);
					
					cc.paste(session,
							 new com.sk89q.worldedit.Vector(loc.getX(),
									    					loc.getY(),
									    					loc.getZ()),
							 								false);
					
					map.put(session, cc);
					return map;
				} catch (MaxChangedBlocksException | DataException | IOException e) {
					ErrorAPI.sendError(e);
					e.printStackTrace();
				}
                
			}
			
			return null;
			
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String[] getLore() {
			return lore;
		}

		public void setLore(String[] lore) {
			this.lore = lore;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Material getMaterial() {
			return mat;
		}

		public void setMaterial(Material mat) {
			this.mat = mat;
		}

		public boolean isVip() {
			return vip;
		}

		public void setVip(boolean vip) {
			this.vip = vip;
		}

		public boolean isAnimation() {
			return animation;
		}

		public void setAnimation(boolean animation) {
			this.animation = animation;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public CageAnimator[] getAnimations() {
			return animations;
		}

		public void setAnimations(CageAnimator[] animations) {
			this.animations = animations;
		}

		public String getHashcode() {
			return hashcode;
		}

		public void setHashcode(String hashcode) {
			this.hashcode = hashcode;
		}

	}
	
	public static CageAnimator makeAnimation(int delay, Material mat, int data, CageMaterial...cm){
		return new CageAnimator(delay, mat, data, cm);
	}
	
	public static File getCageSchematic(CageType type){
		
		String code = type.getHashcode();
		
		if(   Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO 
		   || Skywars.currentMatchType == SkywarsType.SW_NORMAL_SOLO
		   || Skywars.currentMatchType == SkywarsType.SW_Z_SOLO){
			
			String dir = "/data/cages/solo/";
			File file = new File(Skywars.getInstance().getDataFolder(), dir + "cap." + code + ".schematic");
			
			return file;
		} else {
			
			String dir = "/data/cages/team/";
			File file = new File(Skywars.getInstance().getDataFolder(), dir + "cap2p." + code + ".schematic");
			
			return file;
			
		}
	}
	
	public static CageType getCageType(String code){
		for(CageType ct : CageType.values()){
			if(ct.getCode() == code){
				return ct;
			}
		}
		return CageType.DEFAULT;
	}
	
	public static void registerCage(CageType ca, Location loc){
		
		Map<EditSession, CuboidClipboard> stored = ca.paste(loc.toVector(),  loc.getWorld());
		for(Map.Entry<EditSession, CuboidClipboard> k : stored.entrySet()){
			pastedcages.add(k.getKey());
		}
		
	}
	
	public static void removeCages(){
		
		for(EditSession k : pastedcages){
			k.undo(k);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
			ParticleEffect.LAVA.display(0.5F, 0.5F, 0.5F, 1F, 20, p.getLocation(), 20D);
		}
		
	}
	
	public static void registerAnimation(CageAnimator ca){
		
	    BukkitTask bt;
		try{
			bt = ca.start();
			if(bt != null){
				animations.add(bt);
			}
		}catch(Exception e){
			ErrorAPI.sendError(e);
		}
		
	}
	
	public static void stopAnimations(){
		
		for(BukkitTask bt : animations){
			bt.cancel();
		}
		
	}
	
	public static void extractCages() {
		
		ResourceExtractor extractsolo = new ResourceExtractor(Skywars.getInstance(), new File(Skywars.getInstance().getDataFolder(), "data/cages/solo"), "data/cages/solo", "([^\\s]+(\\.(?i)(schematic))$)");
		ResourceExtractor extractteam = new ResourceExtractor(Skywars.getInstance(), new File(Skywars.getInstance().getDataFolder(), "data/cages/team"), "data/cages/team", "([^\\s]+(\\.(?i)(schematic))$)");
		
		try {
			
			extractsolo.extract();
		    extractteam.extract();
		      
		} catch (IOException e) {
		      e.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 * - Apartado para el Z utils...
	 *
	 */
	
	public static class CageZCameraUtil {
		
		public static void makeElevation(Player p, Location cl) {
			
			int x = cl.getBlockX();
			int z = cl.getBlockZ();
			
			List<Location> points = Lists.newArrayList();
			Location toLoc = p.getLocation().clone().add(0, 40, 0);
			
			for(int y = 0; y < 256; y++) {
				Location cache = new Location(p.getWorld(), x, y, z);
				if(cache.getBlock().getType() != Material.AIR) {
					if(cache.getBlock().getType() == Material.STAINED_GLASS ||
							cache.getBlock().getType() == Material.GLASS) {
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
	 *
	 */
	
	public static class CageAnimator {
		
		private int delay;
		
		private CageMaterial[] animation;
		private Block[] blocks;
		
		private CuboidClipboard clipboard;
		private Material identifier = Material.AIR;
		private int identifierdata = 0;
		
		private Vector pastedloc;
		
		public CageAnimator(int delay, Material identifier, int identifierdata, CageMaterial...animation){
			
			this.delay = delay;
			this.animation = animation;
			
			this.identifier = identifier;
			this.identifierdata = identifierdata;
			
		}
		
		public BukkitTask start(){
			
			if(clipboard == null || blocks == null){
				throw new RuntimeException("Clipboard no puede ser Nulo.");
			}
			
			if(animation.length >= 2){
				
				return new BukkitRunnable(){
					
					private CageMaterial current = animation[0];
					
					@Override
					public void run() {
						
						for(CageMaterial k : animation){
							
							if(current == k){
								
								int cache = 0;
										
								List<CageMaterial> list = Arrays.asList(animation);
								int index = list.indexOf(animation);
								
								if(!(new Integer(index + 1) > animation.length)){
									cache = new Integer(index + 1);
								}
								
								current = animation[cache];
								
								changeBlocks(current);
								break;
							}
							
						}
						
					}
					
				}.runTaskTimer(Skywars.getInstance(), 0, delay);
				
			} else {
				throw new RuntimeException("No se puede recrear una animación con menos de 2 tipos de materiales en su conjunto.");
			}
			
		}

		public void changeBlocks(CageMaterial to){
			
			if(clipboard == null || blocks == null){
				throw new RuntimeException("Clipboard no puede ser Nulo.");
			}
			
			if(blocks.length >= 1){
				
				for(Block b : blocks){
					b.setType(to.getM());
					b.setData(to.getData());
				}
				
			}
			
		}
		
		public void setClipboard(CuboidClipboard cc, Vector location, World world){
			
			List<Block> blocks = new ArrayList<Block>();
			
			if(cc != null){
				
				for (int x = 0; x < cc.getSize().getBlockX(); ++x) {
		            for (int y = 0; y < cc.getSize().getBlockY(); ++y) {
		                for (int z = 0; z < cc.getSize().getBlockZ(); ++z) {
		                	Vector cache = new Vector(x, y, z).add(location);
		                	Location loc = new Location(world,
									 cache.getBlockX(),
									 cache.getBlockY(),
									 cache.getBlockZ());
		                	Block block = world.getBlockAt(loc);
		                	if(block.getType() == identifier && block.getData() == (byte) identifierdata){
		                		blocks.add(world.getBlockAt(loc));
		                	}
		                }
		            }
				}
				
			}
			
			this.clipboard = cc;
			this.blocks = (Block[]) blocks.toArray();
			
		}
		
		public CageMaterial[] getAnimation() {
			return animation;
		}

		public void setAnimation(CageMaterial[] animation) {
			this.animation = animation;
		}
		
		public Material getIdentifier() {
			return identifier;
		}

		public void setIdentifier(Material identifier) {
			this.identifier = identifier;
		}

		public Block[] getBlocks() {
			return blocks;
		}

		public void setBlocks(Block[] blocks) {
			this.blocks = blocks;
		}

		public Vector getPastedloc() {
			return pastedloc;
		}

		public void setPastedloc(Vector pastedloc) {
			this.pastedloc = pastedloc;
		}

		public int getIdentifierdata() {
			return identifierdata;
		}

		public void setIdentifierdata(int identifierdata) {
			this.identifierdata = identifierdata;
		}
		
	}
	
	public enum CageMaterial {
		STAINED_GLASS_BLACK(Material.STAINED_GLASS, (byte) 15),
		STAINED_GLASS_BLUE(Material.STAINED_GLASS, (byte) 11),
		STAINED_GLASS_BROWN(Material.STAINED_GLASS, (byte) 12),
		STAINED_GLASS_CYAN(Material.STAINED_GLASS, (byte) 9),
		STAINED_GLASS_GRAY(Material.STAINED_GLASS, (byte) 7),
		STAINED_GLASS_GREEN(Material.STAINED_GLASS, (byte) 13),
		STAINED_GLASS_LIGHT_BLUE(Material.STAINED_GLASS, (byte) 3),
		STAINED_GLASS_LIME(Material.STAINED_GLASS, (byte) 5),
		STAINED_GLASS_MAGENTA(Material.STAINED_GLASS, (byte) 2),
		STAINED_GLASS_ORANGE(Material.STAINED_GLASS, (byte) 1),
		STAINED_GLASS_PINK(Material.STAINED_GLASS, (byte) 6),
		STAINED_GLASS_PURPLE(Material.STAINED_GLASS, (byte) 10),
		STAINED_GLASS_RED(Material.STAINED_GLASS, (byte) 14),
		STAINED_GLASS_LIGHT_GRAY(Material.STAINED_GLASS, (byte) 8),
		STAINED_GLASS_WHITE(Material.STAINED_GLASS, (byte) 0),
		STAINED_GLASS_YELLOW(Material.STAINED_GLASS, (byte) 4),
		GLASS(Material.GLASS, (byte) 0);
		
		private Material m;
		private byte data;
		
		CageMaterial(Material m, byte data){
			this.setM(m);
			this.setData(data);
		}

		public byte getData() {
			return data;
		}

		public void setData(byte data) {
			this.data = data;
		}

		public Material getM() {
			return m;
		}

		public void setM(Material m) {
			this.m = m;
		}
		
	}
	
}
