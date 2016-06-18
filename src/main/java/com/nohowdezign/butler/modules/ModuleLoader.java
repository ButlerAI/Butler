package com.nohowdezign.butler.modules;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Noah Howard
 */
public class ModuleLoader {
    private ModuleRegistry registry = new ModuleRegistry();

    public void loadModulesFromDirectory(String directoryName) throws IOException, ClassNotFoundException {
        File moduleDirectory = new File(directoryName);

        if(moduleDirectory.listFiles() != null) {
            for(File plugin : moduleDirectory.listFiles()) {
                if(plugin.exists() && !plugin.isDirectory()) {
                    if(plugin.getName().endsWith(".jar")) {
                        System.out.println("PLUGIN FOUND!");
                        loadModuleByJarname(plugin.getAbsolutePath());
                    } // TODO: Implement loading compiled .class files instead of just .jar's
                }
            }
        }
    }

    private void loadModuleByJarname(String filename) throws IOException, ClassNotFoundException {
        JarFile jarFile = new JarFile(filename);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + filename + "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of it needs to remove the .class extension
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            registry.addModuleClass(c);
        }
    }

    public ModuleRegistry getRegistry() {
        return registry;
    }

}
