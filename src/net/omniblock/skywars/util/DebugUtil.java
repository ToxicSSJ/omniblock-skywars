package net.omniblock.skywars.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.logger.FileFormatter;

public class DebugUtil {

	private static boolean debugLogger = true;

	private static Logger logger = Logger.getLogger("SWFileLogger");
	private static FileHandler loggerFileHandler;

	private static int lastDay = 0; // usado para saber si el logger debe o no
									// cambiar de archivo.

	public static void printStackTraceToConsole() {
		System.out.println("============= Debug StackTrace ==========");
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : stack) {
			System.out.println(element.toString());
		}
		System.out.println("=========================================");
	}

	public static void printStackTraceToLogger() {
		printStackTraceToLogger(null);
	}

	public static void printStackTraceToLogger(String message) {
		logger.info("[DEBUG] ============= Debug StackTrace ==========");

		if (message != null) {
			logger.info("[DEBUG] >> " + message);
		}

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : stack) {
			logger.info("[DEBUG] " + element.toString());
		}
		logger.info("[DEBUG] =========================================");
	}

	public static Logger getLogger() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		int currentDay = c.get(Calendar.DAY_OF_YEAR);

		if (currentDay != lastDay) {
			lastDay = currentDay;
			logger.removeHandler(loggerFileHandler);
			loggerFileHandler.close();
			setupLogger();
		}

		return logger;
	}

	public static void setupLogger() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String logFileName = c.get(Calendar.DAY_OF_MONTH) + "_" + (c.get(Calendar.MONTH) + 1) + "_"
				+ c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY) + "-" + c.get(Calendar.MINUTE) + "-"
				+ c.get(Calendar.SECOND) + ".log";
		File logsFolder = new File(Skywars.getInstance().getDataFolder(), "logs");

		if (!logsFolder.exists()) {
			logsFolder.mkdirs();
		}

		try {

			logger.setUseParentHandlers(false);

			loggerFileHandler = new FileHandler(new File(logsFolder, logFileName).getAbsolutePath(), true);

			logger.addHandler(loggerFileHandler);
			loggerFileHandler.setFormatter(new FileFormatter());

			logger.severe("¡Test! Logger configurado con exito, esto no representa un error.");

			lastDay = c.get(Calendar.DAY_OF_YEAR);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void info(String message) {

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		logger.logp(Level.INFO, sourceClass, sourceMethod, message);
	}

	public static void debugInfo(String message) {

		if (!debugLogger) {
			return;
		}

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		message = "[DEBUG][" + sourceClass.replace(".java", "") + "]" + message;

		logger.logp(Level.INFO, sourceClass, sourceMethod, message);
	}

	public static void warning(String message) {

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		logger.logp(Level.WARNING, sourceClass, sourceMethod, message);
	}

	public static void severe(String message) {

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		logger.logp(Level.SEVERE, sourceClass, sourceMethod, message);
	}

	public static void debugSevere(String message) {

		if (!debugLogger) {
			return;
		}

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		message = "[DEBUG][" + sourceClass.replace(".java", "") + "]" + message;

		logger.logp(Level.SEVERE, sourceClass, sourceMethod, message);
	}

	public static void debugWarning(String message) {

		if (!debugLogger) {
			return;
		}

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		String sourceClass = "UnknownSource";
		String sourceMethod = "UnknownMethod";

		if (stack.length > 2) {
			sourceClass = stack[2].getClassName();
			sourceMethod = stack[2].getMethodName();
		}

		message = "[DEBUG][" + sourceClass.replace(".java", "") + "]" + message;

		logger.logp(Level.WARNING, sourceClass, sourceMethod, message);
	}

}
