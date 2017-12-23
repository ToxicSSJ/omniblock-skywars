package net.omniblock.skywars.util;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.omniblock.network.library.utils.TextUtil;

public class ActionBarApi {
	
	public static boolean works = true;
	public static String nmsver;

	public static void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(TextUtil.format(message)).create());
	}
	
}
