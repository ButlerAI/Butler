package com.nohowdezign.butler.modules;

import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
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

        List<File> grammarFiles = new ArrayList<>();
        //grammarFiles.add(new File("resource:grammar/butler.gram"));

        if(moduleDirectory.listFiles() != null) {
            for(File plugin : moduleDirectory.listFiles()) {
                System.out.println(plugin.getAbsolutePath());
                if(plugin.exists() && !plugin.isDirectory()) {
                    if(plugin.getName().endsWith(".jar")) {
                        logger.info("Loading plugin: " + plugin.getName());
                        loadModuleByJarname(plugin.getAbsolutePath(), grammarFiles);
                    } // TODO: Implement loading compiled .class files instead of just .jar's
                } else if(plugin.isDirectory()) {
                    logger.debug("Found directory, doing recursive scan");
                    loadModulesFromDirectory(plugin.getAbsolutePath());
                }
            }
            Constants.GRAMMAR_FILE = createGrammarFileForFiles(grammarFiles);
        }
    }

    private void loadModuleByJarname(String filename, List<File> grammarFiles) throws IOException, ClassNotFoundException {
        logger.debug("Processing classes");
        JarFile jarFile = new JarFile(filename);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + filename + "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while(e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            System.out.println(je.getName());
            if(je.getName().endsWith(".gram")) {
                InputStream input = jarFile.getInputStream(je);
                File f = createGrammarForJarentry(input);
                grammarFiles.add(f);
                continue;
            }
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                System.out.println("Not a class");
                continue;
            }

            // -6 because of it needs to remove the .class extension
            String className = je.getName().substring(0, je.getName().length() - 6);
            logger.debug("Found class: " + className);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            for(Method m : c.getMethods()) {
                if(m.isAnnotationPresent(Initialize.class)) {
                    findAndRunInitializeMethods(c, m);
                } else if(m.isAnnotationPresent(Intent.class)) {
                    registry.addModuleClass(m.getAnnotation(Intent.class).keyword(), c);
                }
            }
        }
    }

    private File createGrammarForJarentry(InputStream input) throws IOException {
        List<String> grammarToAppend = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        File tempFile = File.createTempFile("jegrammar", ".gram");
        while((line = reader.readLine()) != null) {
            grammarToAppend.add(line);
        }
        Files.write(Paths.get(tempFile.toURI()), grammarToAppend, Charset.forName("UTF-8"));
        reader.close();
        return tempFile;
    }

    private File createGrammarFileForFiles(List<File> grammarFiles) throws IOException {
        List<String> grammarToAppend = new ArrayList<>();
        for(File f : grammarFiles) {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                grammarToAppend.add(s.next());
                System.out.println(grammarToAppend);
            }
        }

        // Create temporary grammar file and write to it
        File tempFile = File.createTempFile("grammar", ".gram");
        Files.write(Paths.get(tempFile.toURI()), grammarToAppend, Charset.forName("UTF-8"));
        return tempFile;
    }

    private void findAndRunInitializeMethods(Class<?> handler, Method method) {
        Initialize initMethod = method.getAnnotation(Initialize.class);
        try {
            Class<?>[] methodParams = method.getParameterTypes();
            method.invoke(handler.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
