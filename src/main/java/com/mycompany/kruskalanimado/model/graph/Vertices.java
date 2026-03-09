/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.graph;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

public class Vertices {
    private final int id;
    private int x;
    private int y;
    private Color color;
    private String label;
    private boolean selected;
    private boolean highlighted;
    
    public Vertices(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = new Color(52, 152, 219);
        this.label = String.valueOf(id + 1);
        this.selected = false;
        this.highlighted = false;
    }
    
    public double distanceTo(Vertices other) {
        return Point2D.distance(this.x, this.y, other.x, other.y);
    }
    
    public double distanceTo(int x, int y) {
        return Point2D.distance(this.x, this.y, x, y);
    }
    
    public boolean contains(int px, int py, int radius) {
        return distanceTo(px, py) <= radius;
    }
    
    public void draw(Graphics2D g2) {
        int size = highlighted ? 18 : 16;
        int offset = size / 2;
        
        // Sombra
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval(x - offset + 2, y - offset + 2, size, size);
        
        // Vértice principal
        GradientPaint gradient;
        if (selected) {
            gradient = new GradientPaint(
                x - offset, y - offset, new Color(241, 196, 15),
                x + offset, y + offset, new Color(243, 156, 18)
            );
        } else {
            gradient = new GradientPaint(
                x - offset, y - offset, new Color(52, 152, 219),
                x + offset, y + offset, new Color(41, 128, 185)
            );
        }
        
        g2.setPaint(gradient);
        g2.fillOval(x - offset, y - offset, size, size);
        
        // Borde
        g2.setColor(highlighted ? Color.YELLOW : Color.WHITE);
        g2.setStroke(new BasicStroke(highlighted ? 3 : 2));
        g2.drawOval(x - offset, y - offset, size, size);
        
        // Etiqueta
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        int labelX = x - fm.stringWidth(label) / 2;
        int labelY = y + fm.getHeight() / 4;
        g2.drawString(label, labelX, labelY);
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public boolean isHighlighted() { return highlighted; }
    public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertices vertices = (Vertices) obj;
        return id == vertices.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "V" + label + "(" + x + "," + y + ")";
    }
}