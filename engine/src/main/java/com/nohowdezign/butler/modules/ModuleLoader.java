package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(ModuleLoader.class);
    private ModuleRegistry registry = new ModuleRegistry();

    public void loadModulesFromDirectory(String directoryName) throws
            IOException, ClassNotFoundException {
        File moduleDirectory = new File(directoryName);

        if(moduleDirectory.listFiles() != null) {
            for(File plugin : moduleDirectory.listFiles()) {
                if(plugin.exists() && !plugin.isDirectory()) {
                    if(plugin.getName().endsWith(".jar")) {
                        logger.info("Loading plugin: " + plugin.getName());
                        loadModuleByJarname(plugin.getAbsolutePath());
                    } // TODO: Implement loading compiled .class files instead of just .jar's
                } else if(plugin.isDirectory()) {
                    logger.debug("Found directory, doing recursive scan");
                    loadModulesFromDirectory(plugin.getAbsolutePath());
                }
            }
        }
    }

    private void loadModuleByJarname(String filename) throws IOException, ClassNotFoundException {
        logger.debug("Processing classes");
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
            logger.debug("Found class: " + className);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            ModuleLogic logic = (ModuleLogic) c.getAnnotation(ModuleLogic.class);
            try {
                logger.debug("This is a logic class. " +
                        "Trigger subject is " + logic.subjectWord() + ". Registering module");
                registry.addModuleClass(logic.subjectWord(), c);
            } catch(NullPointerException exception) {
                logger.debug("Annotation not found in class " + c.getName());
            }
        }
    }

    public ModuleRegistry getRegistry() {
        return registry;
    }

}
