/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.graph;

import java.util.Objects;

public class Arista implements Comparable<Arista> {
    private final int u;
    private final int v;
    private final double weight;
    private AristaState state;
    private boolean highlighted;
    
    public Arista(int u, int v, double weight) {
        this.u = u;
        this.v = v;
        this.weight = Math.round(weight * 100.0) / 100.0; // Redondear a 2 decimales
        this.state = AristaState.PENDING;
        this.highlighted = false;
    }
    
    public int getOtherVertex(int vertex) {
        if (vertex == u) return v;
        if (vertex == v) return u;
        throw new IllegalArgumentException("Vertex " + vertex + " not in edge (" + u + "-" + v + ")");
    }
    
    public boolean connects(int v1, int v2) {
        return (u == v1 && v == v2) || (u == v2 && v == v1);
    }
    
    public boolean isIncidentTo(int vertex) {
        return u == vertex || v == vertex;
    }
    
    public void setState(AristaState newState) {
        this.state = newState;
    }
    
   
    
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    
    /**
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Arista o) {
        return Double.compare(this.weight, o.weight);
       
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Arista edge = (Arista) obj;
        return (u == edge.u && v == edge.v) || (u == edge.v && v == edge.u);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(Math.min(u, v), Math.max(u, v));
    }
    
    @Override
    public String toString() {
        return u + "-" + v + "(" + String.format("%.2f", weight) + ")";
    }
    
    // Getters
    public int getU() { return u; }
    public int getV() { return v; }
    public double getWeight() { return weight; }
    public AristaState getState() { return state; }
    public boolean isHighlighted() { return highlighted; }

    
}