package net.omniblock.skywars.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResourceExtractor {

	protected final JavaPlugin plugin;
	protected final File extractfolder;
	protected final String folderpath;
	protected final String regex;

	public ResourceExtractor(JavaPlugin plugin, File extractfolder, String folderpath, String regex) {

		Validate.notNull(plugin, "The plugin cannot be null!");
		Validate.notNull(plugin, "The extract folder cannot be null!");
		Validate.notNull(plugin, "The folder path cannot be null!");

		this.extractfolder = extractfolder;
		this.folderpath = folderpath;
		this.plugin = plugin;
		this.regex = regex;

	}

	public void extract() throws IOException {
		extract(false, true);
	}

	public void extract(boolean override) throws IOException {
		extract(override, true);
	}

	public void extract(boolean override, boolean subpaths) throws IOException {
		File jarfile = null;

		try {
			Method method = JavaPlugin.class.getDeclaredMethod("getFile", new Class[0]);
			method.setAccessible(true);

			jarfile = (File) method.invoke(plugin, new Object[0]);
		} catch (Exception e) {
			throw new IOException(e);
		}

		if (!extractfolder.exists()) {
			extractfolder.mkdirs();
		}

		JarFile jar = new JarFile(jarfile);

		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = (JarEntry) entries.nextElement();
			String path = entry.getName();

			if (path.startsWith(folderpath)) {

				if (entry.isDirectory()) {
					if (subpaths) {
						File file = new File(extractfolder, entry.getName().replaceFirst(folderpath, ""));

						if (!file.exists()) {
							file.mkdirs();
						}
					}
				} else {
					File file;
					if (subpaths) {
						file = new File(extractfolder, path.replaceFirst(folderpath, ""));
					} else {
						file = new File(extractfolder, path.substring(path.indexOf(File.separatorChar), path.length()));
					}

					String name = file.getName();

					if ((regex == null) || (name.matches(regex))) {
						if ((file.exists()) && (override)) {
							file.delete();
						}

						if (!file.exists()) {

							InputStream is = jar.getInputStream(entry);
							FileOutputStream fos = new FileOutputStream(file);

							while (is.available() > 0) {
								fos.write(is.read());
							}

							fos.close();
							is.close();
						}
					}
				}
			}
		}
		jar.close();
	}

}