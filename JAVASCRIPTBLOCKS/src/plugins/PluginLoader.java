package plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {

	public static Class<?> loadClass(String dir, String config) throws ClassNotFoundException, IOException {
		return loadClass(new File(dir), config);
	}
	
	public static Class<?> loadClass(File dir, String config) throws IOException, ClassNotFoundException {
		final JarFile jf = new JarFile(dir);
		final JarEntry je = jf.getJarEntry(config);

		final BufferedReader br = new BufferedReader(new InputStreamReader(jf.getInputStream(je)));
		final HashMap<String, String> data = new HashMap<>();
		String in;
		while((in = br.readLine()) != null) {
			if(in.isEmpty() || in.startsWith("#"))
				continue;
			final String[] split = in.split(" ");
			data.put(split[0], split[1]);
		}
		
		jf.close();
		return Class.forName(data.get("Main"), true, new URLClassLoader(new URL[]{dir.toURI().toURL()}));
	}
	
	public static Class<?>[] loadDirectoryC(String dir, String config) throws ClassNotFoundException, IOException {
		return loadDirectoryC(new File(dir), config);
	}
	
	public static Class<?>[] loadDirectoryC(File dir, String config) throws ClassNotFoundException, IOException {
		final File[] files = dir.listFiles();
		
		final Class<?>[] classes = new Class<?>[files.length];


		for (int i=0; i<files.length; i++)
			classes[i] = loadClass(files[i], config);


		return classes;
	}
	
	public static Plugin initAsPlugin(Class<?> group) throws InstantiationException, IllegalAccessException {
		return (Plugin) group.newInstance();
	}
	
	public static Plugin[] initAsPlugin(Class<?>[] group) throws InstantiationException, IllegalAccessException {

		final Plugin[] plugins = new Plugin[group.length];
		for (int i=0; i<group.length; i++)
			plugins[i] = initAsPlugin(group[i]);

		return plugins;
	}
	
}
