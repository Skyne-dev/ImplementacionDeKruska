/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.config;

import java.io.*;
import java.util.Properties;

public class ApplicationConfig {
    private static ApplicationConfig instance;
    private Properties properties;
    private static final String CONFIG_FILE = "kruskal.properties";
    
    private ApplicationConfig() {
        properties = new Properties();
        loadDefaults();
        loadFromFile();
    }
    
    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }
    
    private void loadDefaults() {
        properties.setProperty("max.msts", "100");
        properties.setProperty("animation.speed", "5");
        properties.setProperty("vertex.radius", "15");
        properties.setProperty("show.all.edges", "true");
        properties.setProperty("timeout.ms", "5000");
        properties.setProperty("history.size", "50");
    }
    
    private void loadFromFile() {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("Error loading config: " + e.getMessage());
            }
        }
    }
    
    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Kruskal Animado Configuration");
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }
    
    public int getMaxMSTsToShow() {
        return Integer.parseInt(properties.getProperty("max.msts", "100"));
    }
    
    public void setMaxMSTsToShow(int max) {
        properties.setProperty("max.msts", String.valueOf(max));
    }
    
    public int getDefaultSpeed() {
        return Integer.parseInt(properties.getProperty("animation.speed", "5"));
    }
    
    public int getVertexRadius() {
        return Integer.parseInt(properties.getProperty("vertex.radius", "15"));
    }
    
    public boolean isShowAllEdges() {
        return Boolean.parseBoolean(properties.getProperty("show.all.edges", "true"));
    }
    
    public int getTimeoutMs() {
        return Integer.parseInt(properties.getProperty("timeout.ms", "5000"));
    }
    
    public int getHistorySize() {
        return Integer.parseInt(properties.getProperty("history.size", "50"));
    }
}