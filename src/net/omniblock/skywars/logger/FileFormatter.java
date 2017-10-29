package net.omniblock.skywars.logger;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		String datePrefix = "[%1%][%2%] ";
		String text = "";

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		datePrefix = datePrefix.replace("%1%", ((hourOfDay < 10) ? "0" + hourOfDay : hourOfDay) + ":"
				+ ((min < 10) ? "0" + min : min) + ":" + ((second < 10) ? "0" + second : second));
		datePrefix = datePrefix.replace("%2%", record.getLevel().toString());

		String fullClassPath = record.getSourceClassName();
		String className = fullClassPath.substring(fullClassPath.lastIndexOf(".") + 1);

		text += String.format("%s%n",
				datePrefix + className + ".java#" + record.getSourceMethodName() + "() : " + record.getMessage());

		return text;
	}

}
