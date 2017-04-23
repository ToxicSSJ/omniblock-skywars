package net.omniblock.skywars.patch.managers.chest.item.z.object;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.omniblock.skywars.Skywars;

public class ClonData {

	private Player player;
	private NPC clon;
	
	private boolean protect;
	private Location saved;
	
	public ClonData(Player player, Location saved) {
		
		this.player = player;
		this.saved = saved;
		
	}
	
	public void makeClon() {
		
		if(player != null) {
			if(player.isOnline()) {
			
				clon = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
				clon.spawn(player.getLocation());
				
				Player cp = (Player) clon.getEntity();
				
				if(player.getEquipment() != null){
					if(player.getEquipment().getHelmet() != null){
						cp.getEquipment().setHelmet(player.getEquipment().getHelmet());
					}
					if(player.getEquipment().getHelmet() != null){
						cp.getEquipment().setChestplate(player.getEquipment().getChestplate());
					}
					if(player.getEquipment().getHelmet() != null){
						cp.getEquipment().setLeggings(player.getEquipment().getLeggings());
					}
					if(player.getEquipment().getHelmet() != null){
						cp.getEquipment().setBoots(player.getEquipment().getBoots());
					}
				}
				
				if(player.getItemInHand() != null) {
					cp.setItemInHand(player.getItemInHand());
				}
				
			}
		}
		
	}
	
	public void destroyClon(int ticks) {
		
		if(ticks != 0) {
			
			new BukkitRunnable() {
				@Override
				public void run() {
					if(clon != null) {
						if(clon.isSpawned()) {
							remove();
						}
					}
				}
			}.runTaskLater(Skywars.getInstance(), ticks);
			return;
			
		} else {
			
			if(clon != null) {
				if(clon.isSpawned()) {
					remove();
				}
			}
			return;
			
		}
		
	}

	public void remove() {
		clon.destroy();
	}

	public NPC getClon() {
		return clon;
	}

	public void setClon(NPC clon) {
		this.clon = clon;
	}

	public boolean isAlive() {
		return clon.isSpawned();
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
		
		if(protect) {
			if(clon != null) {
				clon.setProtected(false);
			}
		} else {
			clon.setProtected(true);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void targetEntiTy(Player player){
		
		if(clon.getNavigator().getEntityTarget() == player){
			
			clon.getNavigator().setTarget(player, true);

		}else{
			return;
		
		}
	}

	public Location getSaved() {
		return saved;
	}

	public void setSaved(Location saved) {
		this.saved = saved;
	}
	
}
