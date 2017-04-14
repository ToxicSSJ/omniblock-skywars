package net.omniblock.skywars.util.fix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.omniblock.skywars.Skywars;

public class TeleportFixThree implements Listener {
	
    public final int TELEPORT_FIX_DELAY = 15;
    
    public static void initialize() {
        Skywars.getInstance().getServer().getPluginManager().registerEvents(new TeleportFixThree(), Skywars.getInstance());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {

        final Player player = event.getPlayer();
        final int visibleDistance = Skywars.getInstance().getServer().getViewDistance() * 16;
        
        Skywars.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(), new Runnable() {
            @Override
            public void run() {
               
                final List<Player> nearby = getPlayersWithin(player, visibleDistance);
                
                updateEntities(player, nearby, false);
                
                // Then show them again
                Skywars.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Skywars.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        updateEntities(player, nearby, true);
                    }
                }, 1);
            }
        }, TELEPORT_FIX_DELAY);
    }
    
    private void updateEntities(Player tpedPlayer, List<Player> players, boolean visible) {
        for (Player player : players) {
            if (visible) {
                tpedPlayer.showPlayer(player);
                player.showPlayer(tpedPlayer);
            } else {
                tpedPlayer.hidePlayer(player);
                player.hidePlayer(tpedPlayer);
            }
        }
    }
    
    private List<Player> getPlayersWithin(Player player, int distance) {
        List<Player> res = new ArrayList<Player>();
        int d2 = distance * distance;
        for (Player p : Skywars.getInstance().getServer().getOnlinePlayers()) {
            if (p != player && p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }
        return res;
    }
    
}