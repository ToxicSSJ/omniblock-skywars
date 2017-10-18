/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch.types;

import java.util.Arrays;

import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.patch.internal.SkywarsResolver;
import net.omniblock.skywars.patch.internal.SkywarsStarter;

public enum SkywarsType {
	
	NONE(null, GamePreset.SKYWARS_MASK, null),
	
	SW_NORMAL_SOLO(new SoloSkywars(), GamePreset.SKYWARS_SOLO_NORMAL, new SkywarsResolver(
																							NetworkData.serial,
																							new String[] { "antihacks" , "normal" , "solo" } )),
	
	SW_NORMAL_TEAMS(new TeamSkywars(), GamePreset.SKYWARS_TEAM_NORMAL, new SkywarsResolver(
																							NetworkData.serial,
																							new String[] { "antihacks" , "normal" , "teams" } )),
	
	SW_INSANE_SOLO(new SoloSkywars(), GamePreset.SKYWARS_SOLO_INSANE, new SkywarsResolver(
																							NetworkData.serial,
																							new String[] { "antihacks" , "insane" , "solo" } )),
	
	SW_INSANE_TEAMS(new TeamSkywars(), GamePreset.SKYWARS_TEAM_INSANE, new SkywarsResolver(
																							NetworkData.serial,
																							new String[] { "antihacks" , "insane" , "teams" } )),
	
	SW_Z_SOLO(new SoloSkywars(), GamePreset.SKYWARS_SOLO_Z, new SkywarsResolver(
																					NetworkData.serial,
																					new String[] { "antihacks" , "z" , "solo" } )),
	
	SW_Z_TEAMS(new TeamSkywars(), GamePreset.SKYWARS_TEAM_Z, new SkywarsResolver(
																					NetworkData.serial,
																					new String[] { "antihacks" , "z" , "teams" } ));

	
	private SkywarsStarter ss;
	private SkywarsResolver resolver;
	
	private GamePreset preset;
	
	SkywarsType(SkywarsStarter ss, GamePreset preset, SkywarsResolver sr){
		
		this.ss = ss;
		this.resolver = sr;
		
		this.preset = preset;
		
	}

	public static SkywarsType withPreset(GamePreset preset){
		
		return Arrays.asList(SkywarsType.values()).stream().filter(k -> k.getPreset() == preset).findAny().orElse(null);
		
	}
	
	public boolean makeMatch(){
		
		try {
			
			Skywars.setSkywarsType(this);
			ss.run(this, resolver);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return false;
			
		}
		
		return true;
	}
	
	public GamePreset getPreset() {
		return preset;
	}
	
	public SkywarsStarter getStarter() {
		return ss;
	}

	public void setStarter(SkywarsStarter ss) {
		this.ss = ss;
	}

	public SkywarsResolver getResolver() {
		return resolver;
	}
	
}
