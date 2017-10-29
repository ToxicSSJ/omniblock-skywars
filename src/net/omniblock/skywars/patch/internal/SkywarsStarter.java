/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch.internal;

import net.omniblock.skywars.patch.types.SkywarsType;

public abstract interface SkywarsStarter {

	public void run(SkywarsType skywarsType, SkywarsResolver sr) throws RuntimeException;

	public void reset();

}
