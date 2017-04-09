package net.omniblock.skywars.games.solo.chest;

public interface ChestType {

	public abstract void normalChest();

	public abstract void trappedChest();

	public abstract void numberOfItemInChest(final int numberN, final int numberT);

	public abstract void clearChest(boolean clear);
}
