package net.omniblock.skywars.patch.managers.chest;

import net.omniblock.skywars.patch.managers.chest.handler.ChestFillerHandler;
import net.omniblock.skywars.patch.managers.chest.handler.ChestGetterHandler;
import net.omniblock.skywars.patch.managers.chest.handler.ChestPackagerHandler;
import net.omniblock.skywars.patch.types.MatchType;

public class Chests {

	public static MatchType currentMatchType = MatchType.NONE;

	public static final ChestFillerHandler FILLER = new ChestFillerHandler();
	public static final ChestGetterHandler GETTER = new ChestGetterHandler();

	public static final ChestPackagerHandler PACKAGER = new ChestPackagerHandler();

}
