package net.omniblock.skywars.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import net.omniblock.skywars.Skywars;

/**
 * Utilidad para gestionar las configuraciones de los plugins de una manera
 * eficaz y cómoda.
 * 
 * @author Wirlie
 * @since Pre-Release
 *
 */
public class FileConfigurationUtil {

	/*
	 * No se puede usar Skywars.getInstance() ya que en ocasiones las variables
	 * fuera de alguna funcion pueden ser llamadas incluso antes de que
	 * onEnable() sea llamado, lo cual, si usamos Skywars.getInstance()
	 * devolverá un valor null. Para evitar esto simplemente usamos la API
	 * proporcionada por JavaPlugin (Bukkit) para obtener la instancia.
	 */
	private static Skywars instance = Skywars.getPlugin(Skywars.class);

	/**
	 * Configuraciones disponibles del plugin.
	 * 
	 * @author Wirlie
	 * @since Pre-Release
	 *
	 */
	public static enum ConfigurationType {
		/** Configuración default del plugin (Config.yml) */
		DEFAULT_CONFIGURATION(new File(instance.getDataFolder(), "Config.yml"), "Config.yml"),;

		/**
		 * Archivo de configuracion
		 */
		private File cfgFile;

		/**
		 * Objeto {@link Configuration} de esta configuracion.
		 */
		private Configuration configuration;

		/**
		 * Constructor del enum {@link ConfigurationType}
		 * 
		 * @param file
		 *            Archivo de configuración.
		 */
		ConfigurationType(File file, String resource) {
			cfgFile = file;

			if (!cfgFile.exists()) {
				cfgFile.getParentFile().mkdirs();

				if (resource == null) { // si no hay un resource especificado,
										// creamos el archivo
					try {
						cfgFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else { // el resource si esta especificado, tratamos de
							// obtenerlo
					Skywars.getInstance().saveResource(resource, true);
				}
			}

			configuration = new Configuration(cfgFile, YamlConfiguration.loadConfiguration(cfgFile));
		}

		/**
		 * Obtiene el Archivo ({@link File}) de esta configuracion.
		 * 
		 * @return Archivo ({@link File})
		 */
		public File getFile() {
			return cfgFile;
		}

		/**
		 * Obtiene la configuracion representado con la clase
		 * {@link Configuration}. {@link Configuration} es una clase auxiliar
		 * que ayuda a hacer el proceso de {@code save()} y {@code reload()} mas
		 * práctico.
		 * 
		 * @return {@link Configuration}
		 */
		public Configuration getConfig() {
			return configuration;
		}
	}

	/**
	 * Clase auxiliar que ayuda a realizar los procesos de <b>save()</b> y
	 * <b>reload()</b> de una manera más rápida y sencilla. Además de gestionar
	 * con más facilidad el objeto {@link YamlConfiguration}
	 * 
	 * @author Wirlie
	 * @since Pre-Release
	 *
	 */
	public static class Configuration {

		private YamlConfiguration configuration;
		private File file;

		/**
		 * Constructor de la clase.
		 * 
		 * @param file
		 *            Archivo ({@link File}) de la configuración.
		 * @param cfgFileConfiguration
		 *            Objeto de configuración ({@link YamlConfiguration})
		 */
		public Configuration(File file, YamlConfiguration cfgFileConfiguration) {
			configuration = cfgFileConfiguration;
			this.file = file;
		}

		/**
		 * Devuelve el objeto {@link YamlConfiguration} de la configuración.<br>
		 * <b>¡Importante!</b> No usar las función
		 * {@link YamlConfiguration#save(File)}, en su lugar usar
		 * {@link Configuration#save()} para guardar la configuración.
		 * 
		 * @return Objeto {@link YamlConfiguration} de la configuración
		 */
		public YamlConfiguration getYaml() {
			return configuration;
		}

		/**
		 * Recargar la configuración.
		 */
		public void reload() {
			configuration = YamlConfiguration.loadConfiguration(file);
		}

		/**
		 * Guarda la configuración.
		 */
		public void save() {
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
