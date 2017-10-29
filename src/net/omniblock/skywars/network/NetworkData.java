/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.entity.Player;

import net.omniblock.network.handlers.games.NetworkBroadcaster;
import net.omniblock.skywars.patch.types.SkywarsType;

public class NetworkData {

	public static NetworkBroadcaster broadcaster;

	public static boolean generalbooster = true;

	public static String serial = "Unknow";
	public static NetworkRunnable runnable;

	public static Date date;

	public static SkywarsType swtype = SkywarsType.NONE;
	public static List<Player> serverplayers = new ArrayList<Player>();

}
