package net.omniblock.skywars.patch.managers.lobby.items;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.lobby.object.PowerItem;

public class TimeAlterator implements PowerItem,
Listener {

	public static enum TimeType {

		MORNING(PowerItemType.TIME_ALTERATOR_MORNING, 24000),
		MIDDAY(PowerItemType.TIME_ALTERATOR_MIDDAY, 1800),

		AFTERNOON(PowerItemType.TIME_ALTERATOR_AFTERNOON, 13000),
		NIGHT(PowerItemType.TIME_ALTERATOR_NIGHT, 15000),
		MIDNIGHT(PowerItemType.TIME_ALTERATOR_MIDNIGHT, 18000),

		;

		private PowerItemType pit = PowerItemType.TIME_ALTERATOR_DEFAULT;
		private int timedata = 1800;

		TimeType(PowerItemType pit, int timedata) {

			this.timedata = timedata;
			this.pit = pit;

		}

		public static TimeType getDefault() {
			return TimeType.MIDDAY;
		}

		public static TimeType getByPit(PowerItemType pit) {

			for (TimeType tt: TimeType.values()) {

				if (tt.getPit() == pit) return tt;

			}

			return getDefault();

		}

		public int getTimedata() {
			return timedata;
		}

		public PowerItemType getPit() {
			return pit;
		}

	}

	public static BukkitTask TASK = null;

	public static TimeType SELECTED_TIME = TimeType.getDefault();
	public static int TIME = 0;

	@Override
	public void setup() {

		TIME = SELECTED_TIME.getTimedata();

		if (!PowerItem.actived_powers.contains(PowerItemType.DAYS_BLAZER)) {

			BukkitTask runnable = new BukkitRunnable() {

				@Override
				public void run() {

					MapManager.CURRENT_MAP.setFullTime(TIME);

					MapManager.CURRENT_MAP.setThundering(false);
					MapManager.CURRENT_MAP.setStorm(false);

				}

			}.runTaskTimer(Skywars.getInstance(), 1l, 1l);

			PowerItem.actived_tasks.add(runnable);

			return;

		}

	}

	@Override
	public void stop() {

		if (TASK != null) {
			TASK.cancel();
		}

	}

}