/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.games.teams;

import net.omniblock.skywars.patch.internal.SkywarsResolver;
import net.omniblock.skywars.patch.internal.SkywarsStarter;
import net.omniblock.skywars.patch.types.SkywarsType;

public class TeamSkywars implements SkywarsStarter {

	public static SkywarsResolver resolver;
	
	@Override
	public void run(SkywarsType skywarsType, SkywarsResolver sr) {
		resolver = sr;
		
		if(resolver.getListArgs().contains("normal")){
			// TODO
		} else if(resolver.getListArgs().contains("insane")){
			// TODO
		} else {
			new IllegalStateException("SkywarsResolver no fué especificado con un modo correcto y el sistema no sabe cómo continuar: " + resolver.getListArgs());
			stop();
		}
		
	}
	
	@Override
	public void reset() {
		
	}

	@Override
	public void stop() {
		
	}

}
