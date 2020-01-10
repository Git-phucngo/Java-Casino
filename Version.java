package com.version;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * XmlReader.java
 * Read and write XML file from resources.
 * By DTT
 */
public class Version {
    String path = "resources/version.txt";
    private File fileVersion;
    private final int MAJOR = 0;
    private final int MINOR = 1;
    private final int BUILD = 2;
    
    public String getCurrentVersion() throws FileNotFoundException {
        // get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader(); 
        fileVersion = new File(classLoader.getResource(path).getFile());
        
        // read the first line of file;
        Scanner scanner = new Scanner(fileVersion);
        String version = scanner.nextLine();
        
        return version;
    }
    
    public void updateNewVersion() throws FileNotFoundException {
        String oldVersion = getCurrentVersion();
        
        String [] temp = oldVersion.split(".");
        int major = Integer.parseInt(temp[MAJOR]);
        int minor = Integer.parseInt(temp[MINOR]);
        int build = Integer.parseInt(temp[BUILD]);
        
        build = build + 1;
        
        String tempVersion = major+"."+minor+"."+build;
        writeToFile(tempVersion);
    }
    
    private void writeToFile(String s) {
        try {
            if(!fileVersion.exists()) {
                System.out.println("version.txt does not exist.");
            } else {
                FileWriter fw = new FileWriter(fileVersion.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(s);
                bw.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Version version = new Version();
        
        try {
            String v = version.getCurrentVersion();
            
            System.out.println("Current version: "+v);
            version.updateNewVersion();
            System.out.println("New version: "+version.getCurrentVersion());
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Version.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}