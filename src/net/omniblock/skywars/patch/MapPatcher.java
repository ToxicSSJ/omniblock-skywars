/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.patch;

import java.io.File;
import java.io.IOException;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.internal.Patcher;
import net.omniblock.skywars.util.ResourceExtractor;

public class MapPatcher implements Patcher {

	public void initialize() {

		extractSchematics();

	}

	public void extractSchematics() {

		ResourceExtractor extractschems = new ResourceExtractor(Skywars.getInstance(),
				new File(Skywars.getInstance().getDataFolder(), "data/schems/"), "data/schems/",
				"([^\\s]+(\\.(?i)(schematic))$)");

		try {
			extractschems.extract();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] resume() {
		return new String[] { "sucess" };
	}

}
